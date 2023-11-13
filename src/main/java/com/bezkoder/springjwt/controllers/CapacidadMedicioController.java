package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.impl.service.ICapacidadMedicionService;
import com.bezkoder.springjwt.models.CapacidadMedicion;
import com.bezkoder.springjwt.models.Cliente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class CapacidadMedicioController {

    @Autowired
    private ICapacidadMedicionService iCapacidadMedicionService;

    //Listado de CMC
    @GetMapping("/cmc")
    public List<CapacidadMedicion> findAll(){
        return iCapacidadMedicionService.findAll();

    }
    @PostMapping("/cmc")
    public ResponseEntity<?> create(@Valid @RequestBody CapacidadMedicion capacidadMedicion, BindingResult result) {
        CapacidadMedicion cmcNew= null;
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
            cmcNew= iCapacidadMedicionService.save(capacidadMedicion);
        }catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insertar en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cmc ha sido creado con éxito!");
        response.put("cliente", cmcNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    // Buscar CMC por ID
    @GetMapping("/cmc/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id) {
        CapacidadMedicion capacidadMedicion =null;
        Map<String, Object> response = new HashMap<>();
        try{
            capacidadMedicion = iCapacidadMedicionService.findById(id);
        }catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (capacidadMedicion==null){
            response.put("mensaje", "El CMC con  ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CapacidadMedicion>(capacidadMedicion,HttpStatus.CREATED);

    }
    // Actualizar CMC por ID
    @PutMapping("/cmc/{id}")ResponseEntity<?> update(@Valid @RequestBody CapacidadMedicion capacidadMedicion, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try{
            CapacidadMedicion capacidadMedicion1 = iCapacidadMedicionService.findById(id);
            if(capacidadMedicion1 ==null){
                response.put("mensaje", "Error: no se pudo editar, el cliente ID: " + id + " no existe en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            // Copiar propiedades actualizadas desde cmc al cmc actual
            BeanUtils.copyProperties(capacidadMedicion, capacidadMedicion1, "id");

            CapacidadMedicion cmcUpdated= iCapacidadMedicionService.save(capacidadMedicion1);
            response.put("mensaje","El CMC ha sido actualizado con éxito.");
            response.put("CMC",cmcUpdated);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("mensaje", "Error al actualizar el CMC en la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("mensaje", "Error interno del servidor.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Borrar CMC Por ID
    @DeleteMapping("/cmc/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            iCapacidadMedicionService.delete(id);
        } catch (DataAccessException e) {
            if(iCapacidadMedicionService ==null){
                response.put("mensaje", "Error al eliminar el cmc de la base de datos!");
                response.put("mensaje", "Error:no se pudo eliminar, el cmc ID: ".concat(" no existe en la base de datos!"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        response.put("mensaje", "El cmc eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
