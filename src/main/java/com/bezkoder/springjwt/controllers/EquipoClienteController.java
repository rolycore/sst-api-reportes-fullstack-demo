package com.bezkoder.springjwt.controllers;
import com.bezkoder.springjwt.DTO.ClienteDTO;
import com.bezkoder.springjwt.DTO.EquipoDTO;
import com.bezkoder.springjwt.util.StorageService;
import com.bezkoder.springjwt.util.tools.ExcelUtil;
import org.apache.commons.io.FileUtils;
import com.bezkoder.springjwt.impl.service.IClienteService;
import com.bezkoder.springjwt.impl.service.IEquipoClienteService;
import com.bezkoder.springjwt.models.Cliente;
import com.bezkoder.springjwt.models.EquipoCliente;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "https://localhost:4200" })//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class EquipoClienteController {
   // private final Logger log = LoggerFactory.getLogger(EquipoClienteController.class);
    @Autowired
    private IEquipoClienteService equipoClienteService;
    @Autowired
    private IClienteService iClienteService;


    //Datos del Equipo Cliente
   // EquipoCliente nequipoCliente = new EquipoCliente();
    // Buscar todos los equipos de clientes
    @GetMapping("/equipos-clientes")
    public List<EquipoCliente>findAllEquipos() {

        return  equipoClienteService.findAll();
    }

    // Buscar equipo de cliente por ID
    @GetMapping("/equipos-clientes/{id}")
    public ResponseEntity<?> getEquipoWithImagen(@PathVariable Long id) {
        EquipoCliente equipo=null;
        Map<String, Object> response = new HashMap<>();
        try {
            equipo = equipoClienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (equipo == null) {
            response.put("mensaje", "El equipo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<EquipoCliente>(equipo, HttpStatus.CREATED);

    }


    // Crear equipo de cliente

    @PostMapping("/equipos-clientes")
    public ResponseEntity<?> createEquipo(
            @Valid @RequestBody EquipoCliente equipoCliente, BindingResult result) {
        EquipoCliente equiponew=null;
        Map<String, Object> response = new HashMap<>();
        //manejo de errores
        //si contiene errores lo validamos en este if
        if(result.hasErrors()) {

            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(err ->"El campo '"+ err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            equipoCliente.setCodigoequipo(equipoClienteService.generarCodigoEquipoCliente());
            equiponew= equipoClienteService.save(equipoCliente);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insertar en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El equipo ha sido creado con éxito!");
        response.put("equipo", equiponew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    private List<String> obtenerMensajesValidacion(Exception e) {
        // Implementa la lógica para obtener mensajes de validación detallados aquí
        // Por ejemplo, puedes recorrer las constraint violations y obtener los mensajes
        List<String> mensajes = new ArrayList<>();
        if (e instanceof ConstraintViolationException) {
            for (ConstraintViolation<?> violation : ((ConstraintViolationException) e).getConstraintViolations()) {
                mensajes.add(violation.getMessage());
            }
        } else if (e instanceof MethodArgumentNotValidException) {
            for (FieldError error : ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors()) {
                mensajes.add(error.getDefaultMessage());
            }
        }
        return mensajes;
    }


    // ...


    // Actualizar equipo de cliente por ID
    @PutMapping("/equipos-clientes/{id}")
    public ResponseEntity<?> updateEquipo(@PathVariable Long id, @Valid @RequestBody EquipoCliente equipo) {
      // EquipoCliente equipoUpdated = null;
        Map<String, Object> response = new HashMap<>();

        // Manejo de errores
     /*   if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }*/

        try {
            EquipoCliente equipoActual = equipoClienteService.findById(id);

            if (equipoActual == null) {
                response.put("mensaje", "El Equipo con ID " + id + " no existe.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Copia propiedades actualizadas desde equipo a equipoActual, excluyendo "id"
            BeanUtils.copyProperties(equipo, equipoActual, "id");

            EquipoCliente equipoUpdated = equipoClienteService.save(equipoActual);
            response.put("mensaje", "El equipo ha sido actualizado con éxito!");
            response.put("equipo", equipoUpdated);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("mensaje", "Error al actualizar el equipocliente en la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            response.put("mensaje", "Error interno del servidor.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    // Borrar equipo de cliente por ID
    @DeleteMapping("/equipos-clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean success = equipoClienteService.delete(id);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/equipos-clientes/importar", consumes = "multipart/form-data")
    public ResponseEntity<?> importarEquipos(@RequestParam("archivo") MultipartFile archivo) throws IOException {
        List<EquipoDTO> equiposDTO = ExcelUtil.leerEquiposDesdeArchivo(archivo.getInputStream());

        // Convierte la lista de EquipoDTO a una lista de Equipo
        List<EquipoCliente> equipos = equiposDTO.stream()
                .map(this::convertirEquipoDTOaEquipoCliente)
                .collect(Collectors.toList());

        // Validar los equipos antes de guardarlos
        for (EquipoCliente equipo : equipos) {
            // Validar el equipo usando el Validator
            javax.validation.Validator validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<EquipoCliente>> violations = validator.validate(equipo);

            if (!violations.isEmpty()) {
                // Si hay errores de validación, respondemos con una lista de errores y el código de estado HTTP 400 (Bad Request)
                List<String> errores = violations.stream()
                        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                        .collect(Collectors.toList());
                return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
            }
        }

        // Guarda la lista de equipos en la base de datos
        equipoClienteService.saveAll(equipos);

        return ResponseEntity.ok("Equipos importados exitosamente");
    }
    @GetMapping("/equipos-clientes/exportar")
    public ResponseEntity<byte[]> exportarEquiposClientes() throws IOException {
        List<EquipoDTO> equipos = equipoClienteService.obtenerEquipoDTO();
        byte[] excelBytes = ExcelUtil.exportarEquiposACSV(equipos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("equipos.xlsx").build());

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
private EquipoCliente convertirEquipoDTOaEquipoCliente(EquipoDTO equipoDTO){
        EquipoCliente equipoCliente = new EquipoCliente();
    // Luego, puedes usar clienteId para recuperar el cliente si es necesario.
    //Cliente clienteOptional = iClienteService.findById(clienteId);
    // Acceder al clienteId desde equipoDTO si está allí
   // Long clienteId = equipoDTO.getCliente();


    equipoCliente.setCliente(equipoDTO.getCliente());
    equipoCliente.setCodigoequipo(equipoDTO.getCodigoequipo());
    equipoCliente.setNombre(equipoDTO.getNombre());
    equipoCliente.setNombrecliente(equipoDTO.getNombrecliente());
    equipoCliente.setMarca(equipoDTO.getMarca());
    equipoCliente.setNumero_serie(equipoDTO.getNumero_serie());
    equipoCliente.setModelo(equipoDTO.getModelo());
    equipoCliente.setCapacidad(equipoDTO.getCapacidad());
    equipoCliente.setCategoria_equipo(equipoDTO.getCategoria_equipo());
    equipoCliente.setCapacidad_maxima(equipoDTO.getCapacidad_maxima());
    equipoCliente.setCapacidad_minima(equipoDTO.getCapacidad_minima());
    equipoCliente.setResolucion(equipoDTO.getResolucion());
    equipoCliente.setDivisiones(equipoDTO.getDivisiones());
    equipoCliente.setObservaciones(equipoDTO.getObservaciones());
    //equipoCliente.setImagen_equipo(equipoDTO.getImagen_equipo());
    equipoCliente.setUnidad_medida(equipoDTO.getUnidad_medida());
    equipoCliente.setInstrumento(equipoDTO.getInstrumento());
    equipoCliente.setMide(equipoDTO.getMide());
    equipoCliente.setLista_precio(equipoDTO.getLista_precio());
    equipoCliente.setCmc_equipo(equipoDTO.getCmc_equipo());
    equipoCliente.setFabricante_receptor(equipoDTO.getFabricante_receptor());
    equipoCliente.setModelo_receptor(equipoDTO.getModelo_receptor());
    equipoCliente.setSerie_receptor(equipoDTO.getSerie_receptor());
    equipoCliente.setId_interno_receptor(equipoDTO.getId_interno_receptor());
    equipoCliente.setFabricante_sensor(equipoDTO.getFabricante_sensor());
    equipoCliente.setModelo_sensor(equipoDTO.getModelo_sensor());
    equipoCliente.setSerie_sensor(equipoDTO.getSerie_sensor());
    equipoCliente.setId_interno_sensor(equipoDTO.getId_interno_sensor());
    equipoCliente.setFabricante_indicador(equipoDTO.getFabricante_indicador());
    equipoCliente.setModelo_indicador(equipoDTO.getModelo_indicador());
    equipoCliente.setSerie_indicador(equipoDTO.getSerie_indicador());
    equipoCliente.setId_interno_indicador(equipoDTO.getId_interno_indicador());
    equipoCliente.setActivo(equipoDTO.isActivo());

    return equipoCliente;
}
    @GetMapping("/equipos-clientes/{idCliente}/equipos")
    public ResponseEntity<List<EquipoCliente>> listarEquiposPorIdCliente(@PathVariable Long idCliente) {
        List<EquipoCliente> equipos = equipoClienteService.findByIdCliente(idCliente);

        if (equipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(equipos);
        }
    }
}
