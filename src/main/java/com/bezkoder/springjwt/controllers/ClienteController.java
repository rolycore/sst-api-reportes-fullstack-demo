    //Buscar cliente Por nombre
    package com.bezkoder.springjwt.controllers;

    import com.bezkoder.springjwt.DTO.ClienteDTO;
    import com.bezkoder.springjwt.impl.service.IClienteService;
    import com.bezkoder.springjwt.models.Cliente;
    import com.bezkoder.springjwt.repository.ClienteRepository;
    import com.bezkoder.springjwt.util.tools.ClienteMapper;
    import com.bezkoder.springjwt.util.tools.ExcelUtil;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataAccessException;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.*;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import javax.validation.*;
    import java.io.IOException;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;
    import java.util.stream.Collectors;

    @CrossOrigin(origins = { "https://appicmlab.icmetrologia.com" })//https://appicmlab.icmetrologia.com
    @RestController
    @RequestMapping("/api/v1")
    @PreAuthorize("hasRole('ADMIN')")
    public class ClienteController {

        //private final Logger logger = LoggerFactory.getLogger(ClienteController.class);
        @Autowired
        private IClienteService iClienteService;
        //private ClienteRepository clienteRepository;
        //Datos del Cliente
        //Cliente ncliente = new Cliente();

        //Listar todos clientes paginados
        // @GetMapping("/clientes")
        //public List<Cliente> todosClientes(Pageable pageable) {
        //      return iClienteService.findAll(pageable);

        // }
        //Listado de cliente
        @GetMapping("/clientes")
        @PreAuthorize("hasRole('ADMIN')")
        public List<Cliente> todos(){
            return iClienteService.todos();
        }
        // Crear Cliente por ID
        @PostMapping("/clientes")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
            Cliente clienteNew = null;

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


            try {
                cliente.setCod_cliente(iClienteService.generarCodigoCliente());
                clienteNew =  iClienteService.save(cliente);

            } catch (DataAccessException e) {

                response.put("mensaje", "Error al realizar el insertar en la base de datos!");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            response.put("mensaje", "El cliente ha sido creado con éxito!");
            response.put("cliente", clienteNew);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        }
        // Buscar Cliente por ID
        @GetMapping("/clientes/{id}")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> show(@PathVariable Long id) {
            Cliente cliente = null;
            Map<String, Object> response = new HashMap<>();
            try {
                cliente = iClienteService.findById(id);
            } catch (DataAccessException e) {
                response.put("mensaje", "Error al realizar la consulta en la base de datos!");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (cliente == null) {
                response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);

        }
        // Actualizar Cliente por ID
        @PutMapping("/clientes/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, @PathVariable Long id) {
            Map<String, Object> response = new HashMap<>();

            try {
                Cliente clienteActual = iClienteService.findById(id);

                if (clienteActual == null) {
                    response.put("mensaje", "Error: no se pudo editar, el cliente ID: " + id + " no existe en la base de datos.");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }

                // Copiar propiedades actualizadas desde cliente al clienteActual
                BeanUtils.copyProperties(cliente, clienteActual, "id");

                Cliente clienteUpdated = iClienteService.save(clienteActual);

                response.put("mensaje", "El cliente ha sido actualizado con éxito.");
                response.put("cliente", clienteUpdated);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (DataIntegrityViolationException e) {
                response.put("mensaje", "Error al actualizar el cliente en la base de datos.");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                response.put("mensaje", "Error interno del servidor.");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Borrar Cliente Por ID
        @DeleteMapping("/clientes/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> delete(@PathVariable Long id) {
            Map<String, Object> response = new HashMap<>();
            try {
                iClienteService.delete(id);
            } catch (DataAccessException e) {
                if(iClienteService ==null) {
                    response.put("mensaje", "Error al eliminar el cliente de la base de datos!");
                    response.put("mensaje", "Error:no se pudo eliminar, el cliente ID: ".concat(" no existe en la base de datos!"));
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }


            response.put("mensaje", "El cliente eliminado con éxito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
        //importar y exportar Clientes
        @PostMapping(value = "/clientes/importar", consumes = "multipart/form-data")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> importarClientes(@RequestParam("archivo") MultipartFile archivo) throws IOException {
            List<ClienteDTO> clientesDTO = ExcelUtil.leerClientesDesdeArchivo(archivo.getInputStream());

            // Convierte la lista de ClienteDTO a una lista de Cliente
            List<Cliente> clientes = clientesDTO.stream()
                    .map(this::convertirClienteDTOaCliente)
                    .collect(Collectors.toList());

            // Validar los clientes antes de guardarlos
            for (Cliente cliente : clientes) {
                // Validar el cliente usando el Validator
                javax.validation.Validator validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
                Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

                if (!violations.isEmpty()) {
                    // Si hay errores de validación, respondemos con una lista de errores y el código de estado HTTP 400 (Bad Request)
                    List<String> errores = violations.stream()
                            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                            .collect(Collectors.toList());
                    return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
                }
            }

            // Guarda la lista de clientes en la base de datos
            iClienteService.saveAll(clientes);

            // Devuelve una respuesta JSON en lugar de una cadena de texto
            Map<String, String> response = new HashMap<>();
            response.put("message", "Clientes importados exitosamente");
            return ResponseEntity.ok(response);
        }
        //En este código, se crea un mapa (Map) llamado response para almacenar el mensaje de éxito y se devuelve este mapa como una respuesta JSON. El cliente Angular podrá analizar esta respuesta JSON sin problemas.










        @GetMapping("/clientes/exportar")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<byte[]> exportarClientes() throws IOException {
            List<ClienteDTO> clientes = iClienteService.obtenerClientesDTO();
            byte[] excelBytes = ExcelUtil.exportarClientesACSV(clientes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("clientes.xlsx").build());

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        }
        // Método para convertir ClienteDTO a Cliente
        private Cliente convertirClienteDTOaCliente(ClienteDTO clienteDTO) {
            Cliente cliente = new Cliente();
            // Realiza la asignación de propiedades desde clienteDTO a cliente aquí
            // Por ejemplo:
            cliente.setNombre(clienteDTO.getNombre());
            // cliente.setApellido(clienteDTO.getApellido());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setActivo(clienteDTO.isActivo());
            cliente.setTelefono_empresa(clienteDTO.getTelefono_empresa());
            cliente.setCod_cliente(clienteDTO.getCod_cliente());
            cliente.setRazon_social(clienteDTO.getRazon_social());
            cliente.setNombre_comercial(clienteDTO.getNombre_comercial());
            cliente.setRuc(clienteDTO.getRuc());
            cliente.setDv(clienteDTO.getDv());
            cliente.setDireccion(clienteDTO.getDireccion());
            //  cliente.setTelefono_jefe(clienteDTO.getTelefono_jefe());
            //   cliente.setCelular_jefe(clienteDTO.getCelular_jefe());
            // cliente.setCorreo_electronico(clienteDTO.getCorreo_electronico());
            // cliente.setActividad_economica(clienteDTO.getActividad_economica());
            cliente.setAbreviatura(clienteDTO.getAbreviatura());
            cliente.setNombre_contacto(clienteDTO.getNombre_contacto());
            cliente.setCargo_servicio(clienteDTO.getCargo_servicio());
            cliente.setCelular_servicio(clienteDTO.getCelular_servicio());
            cliente.setCorreo_servicio(clienteDTO.getCorreo_servicio());
            //     cliente.setTelefono_servicio(clienteDTO.getTelefono_servicio());
            cliente.setNombre_cobro(clienteDTO.getNombre_cobro());
            cliente.setCargo_cobro(clienteDTO.getCargo_cobro());
            cliente.setTelefono_cobro(clienteDTO.getTelefono_cobro());
            cliente.setCelular_cobro(clienteDTO.getCelular_cobro());
            cliente.setCorreo_cobro(clienteDTO.getCorreo_cobro());

            return cliente;
        }
 /*   @PostMapping("/search")
    public String bucarcliente(@RequestParam String nombre, Model model) {
        log.info("Nombre del Cliente: {}", nombre);
        List<Cliente> clientes= iClienteService.todos().stream().filter( cli -> cli.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("Clientes", clientes);
        return "/clientes";
    }*/
}
