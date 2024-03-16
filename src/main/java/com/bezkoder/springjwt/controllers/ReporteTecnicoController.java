package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.impl.service.IEquipoClienteService;
import com.bezkoder.springjwt.impl.service.IReporteTecnicoService;
import com.bezkoder.springjwt.models.ReporteTecnico;
import com.bezkoder.springjwt.util.generadorreporte.ReporteTecnicoReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "https://localhost:4200" })//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasRole('ADMIN')")
public class ReporteTecnicoController {
    @Autowired
    private IEquipoClienteService equipoClienteService;
    @Autowired
    private IEquipoClienteService clienteService;
    @Autowired
    private IReporteTecnicoService iReporteTecnicoService;
    @Autowired
    private ReporteTecnicoReportGenerator reporteTecnicoReportGenerator;

    //Datos del Reporte Tecnico
    //ReporteTecnico nreporteTecnico = new ReporteTecnico();

    @GetMapping("/reporte-tecnico")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(iReporteTecnicoService.findAll());
    }

    @GetMapping("/reporte-tecnico/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        return ResponseEntity.ok(iReporteTecnicoService.findById(id));
    }

    @PostMapping("/reporte-tecnico")
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody ReporteTecnico reporteTecnico, BindingResult result) {
        ReporteTecnico repoteNew=null;
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
            reporteTecnico.setNo_reporte_tecnico(iReporteTecnicoService.generarCodigoReporteTecnico());

            repoteNew = iReporteTecnicoService.save(reporteTecnico);
            response.put("mensaje", "El Reporte Tecnico ha sido creado con éxito!");
            response.put("reporte tecnico ", repoteNew);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        }
        catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insertar en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PutMapping("/reporte-tecnico/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReporteTecnico reporteTecnico, BindingResult result) {
        ReporteTecnico reporteUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            ReporteTecnico existingReporte = iReporteTecnicoService.findById(id);

            if (existingReporte == null) {
                response.put("mensaje", "El Reporte Técnico con ID " + id + " no existe.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Copiar propiedades actualizadas desde reporteTecnico a existingReporte, excluyendo "id"
            BeanUtils.copyProperties(reporteTecnico, existingReporte, "id");

            reporteUpdated = iReporteTecnicoService.save(existingReporte);
            response.put("mensaje", "El Reporte Técnico ha sido actualizado con éxito.");
            response.put("reporteTecnico", reporteUpdated);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/reporte-tecnico/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        iReporteTecnicoService.deleteById(id);
        return ResponseEntity.ok(null);
    }
    //Imprimir Reporte Tecnico


    @GetMapping("/reporte-tecnico/generate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<byte[]> generateReport(@RequestParam("reportId") Long reportId) throws JRException, IOException {
          // try{
            ReporteTecnico reporteTecnico = iReporteTecnicoService.findById(reportId);
            System.out.println("reporteTecnico = " + reporteTecnico);
            byte[] pdfBytes = reporteTecnicoReportGenerator.generateReport(reporteTecnico);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "ReporteTecnicoPDF-" + reporteTecnico.getIdreptec() + ".pdf");
            return ResponseEntity.ok()
                    .contentLength((long) pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers)
                    .body(pdfBytes);
        //   }
           /*catch (Exception ex) {
               // En caso de excepción, devuelve un ResponseEntity con un mensaje de error
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .contentType(MediaType.TEXT_PLAIN)
                       .body(("Error al generar el informe: " + ex.getMessage()).getBytes());
           }*/

    }
}
