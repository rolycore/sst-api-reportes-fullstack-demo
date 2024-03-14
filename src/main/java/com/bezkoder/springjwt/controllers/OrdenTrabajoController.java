package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.excepciones.ReportNotFoundException;
import com.bezkoder.springjwt.impl.service.IOrdenesTrabajoService;
import com.bezkoder.springjwt.models.EquipoCliente;
import com.bezkoder.springjwt.models.OrdenTrabajo;
import com.bezkoder.springjwt.repository.OrdenesTrabajoRepository;
import com.bezkoder.springjwt.util.generadorreporte.OrdenTrabajoReportGenerator;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "https://localhost:4200" })//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasRole('ADMIN')")
public class OrdenTrabajoController {
    @Autowired
    private IOrdenesTrabajoService iOrdenesTrabajoService;
    private OrdenesTrabajoRepository ordenesTrabajoRepository;
    //Datos de la Orden de Trabajo
    //OrdenTrabajo nordenTrabajo = new OrdenTrabajo();
    @Autowired
    private OrdenTrabajoReportGenerator ordenTrabajoReportGenerator;
    // Buscar todas las Ordenes de Trabajo
    @GetMapping("/ordenes-trabajos")
    public List<OrdenTrabajo> todos() {
        return iOrdenesTrabajoService.todos();

    }
    // Buscar orden de trabajo por ID
    @GetMapping("/ordenes-trabajos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        OrdenTrabajo orden=null;
        Map<String, Object> response = new HashMap<>();
        try {
            orden = iOrdenesTrabajoService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (orden == null) {
            response.put("mensaje", "El orden ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<OrdenTrabajo>(orden, HttpStatus.CREATED);

    }

    //Creando Ordenes de Trabajos
    @PostMapping("/ordenes-trabajos")
    public ResponseEntity<?> create(@Valid @RequestBody OrdenTrabajo ordenTrabajo, BindingResult result) throws IOException {
        OrdenTrabajo ordenNew=null;
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
            ordenTrabajo.setNo_ordent(iOrdenesTrabajoService.generarCodigoOrdenTrabajo());
            ordenNew =iOrdenesTrabajoService.save(ordenTrabajo);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insertar en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "El equipo ha sido creado con éxito!");
        response.put("orden", ordenNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    // Actualizar Orden Trabajo por ID
    @PutMapping("/ordenes-trabajos/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody OrdenTrabajo ordenTrabajo, BindingResult result) {
        OrdenTrabajo ordenUpdated = null;
        Map<String, Object> response = new HashMap<>();

        // Manejo de errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            OrdenTrabajo ordenActual = iOrdenesTrabajoService.findById(id);

            if (ordenActual == null) {
                response.put("mensaje", "La Orden de Trabajo con ID " + id + " no existe.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Copia propiedades actualizadas desde ordenTrabajo a ordenActual, excluyendo "id"
            BeanUtils.copyProperties(ordenTrabajo, ordenActual, "id");

            ordenUpdated = iOrdenesTrabajoService.save(ordenActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La Orden de Trabajo ha sido actualizada con éxito!");
        response.put("orden", ordenUpdated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Imprimir Reporte de Ordenes de Trabajos
    @GetMapping("/ordenes-trabajos/generate")
    public ResponseEntity<byte[]> generateReport(@RequestParam("orderId") Long orderId) {
        try {
        OrdenTrabajo ordenTrabajo = iOrdenesTrabajoService.findById(orderId);
        byte[] pdfBytes = ordenTrabajoReportGenerator.generateReport(ordenTrabajo);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "OrdenTrabajoPDF-" + ordenTrabajo.getIdOT() + ".pdf");

        return ResponseEntity.ok()
                .contentLength((long) pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(pdfBytes);
    } catch (Exception ex) {
            // En caso de excepción, devuelve un ResponseEntity con un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("Error al generar el informe: " + ex.getMessage()).getBytes());
    }
    }




}
