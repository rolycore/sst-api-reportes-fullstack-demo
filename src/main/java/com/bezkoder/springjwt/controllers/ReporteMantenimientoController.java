package com.bezkoder.springjwt.controllers;
import com.bezkoder.springjwt.impl.service.IEquipoClienteService;
import com.bezkoder.springjwt.models.ReporteMantenimiento;
import com.bezkoder.springjwt.models.ReporteTecnico;
import com.bezkoder.springjwt.repository.ReporteMantenimientoRepository;
import com.bezkoder.springjwt.util.generadorreporte.ReporteMantenimientoGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "https://appicmlab.icmetrologia.com" })//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasRole('ADMIN')")
public class ReporteMantenimientoController {
    @Autowired
    private IEquipoClienteService equipoClienteService;
    @Autowired
    private IEquipoClienteService clienteService;

    private final ReporteMantenimientoRepository reporteMantenimientoRepository;

    @Autowired
    public ReporteMantenimientoController(ReporteMantenimientoRepository reporteMantenimientoRepository) {
        this.reporteMantenimientoRepository = reporteMantenimientoRepository;
    }
    @Autowired
    private ReporteMantenimientoGenerator reporteMantenimientoGenerator;
    public String generarCodigomantenimiento() {
        int numero = 0;
        String numeroConcatenado = "";
        List<ReporteMantenimiento> inventarios = reporteMantenimientoRepository.findAll();
        List<Long> numeros = new ArrayList<Long>();

        inventarios.forEach(o -> numeros.add(o.getIdrepmant()));

        if (inventarios.isEmpty()) {
            numero = 1;
        } else {
            Long maxId = numeros.stream().max(Long::compare).orElse(0L);
            numero = maxId.intValue() + 1;
        }

        if (numero <= 9) {
            numeroConcatenado = "RM-000" + numero;
        } else if (numero <= 99) {
            numeroConcatenado = "RM-00" + numero;
        } else if (numero <= 999) {
            numeroConcatenado = "RM-0" + numero;
        } else {
            numeroConcatenado = "RM-" + numero;
        }

        return numeroConcatenado;
    }

    @GetMapping("/reporte-mantenimiento")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(reporteMantenimientoRepository.findAll());
    }

    @GetMapping("/reporte-mantenimiento/{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteMantenimientoRepository.findById(id));
    }

    @PostMapping("/reporte-mantenimiento")
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody ReporteMantenimiento reporteMantenimiento, BindingResult result) {
        ReporteMantenimiento repoteNew=null;
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
            reporteMantenimiento.setNo_reporte(generarCodigomantenimiento());
            System.out.println("consecutivo = " + generarCodigomantenimiento());
            repoteNew = reporteMantenimientoRepository.save(reporteMantenimiento);
            response.put("mensaje", "El Reporte Mantenimiento ha sido creado con éxito!");
            response.put("reporte mantenimiento ", repoteNew);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        }
        catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insertar en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PutMapping("/reporte-mantenimiento/{id}")
    public ResponseEntity<?>updateMantenimiento(@PathVariable Long id, @RequestBody ReporteMantenimiento reporteMantenimiento){

        Map<String, Object> response = new HashMap<>();
        try {
            ReporteMantenimiento mantenimientoActual = reporteMantenimientoRepository.findById(id).get();
            if (mantenimientoActual ==null){
                response.put("mensaje","Error: no se pudo editar, el mantenimiento ID: " + id + " no existe en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            // Copiar propiedades actualizadas desde cliente al clienteActual
            BeanUtils.copyProperties(reporteMantenimiento, mantenimientoActual, "id");

            ReporteMantenimiento mantenimientoUpdated = reporteMantenimientoRepository.save(mantenimientoActual);
            response.put("mensaje", "El mantenimiento ha sido actualizado con éxito.");
            response.put("mantenimiento", mantenimientoUpdated);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (DataIntegrityViolationException e) {
            response.put("mensaje", "Error al actualizar el mantenimiento en la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("mensaje", "Error interno del servidor.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/reporte-mantenimiento/{id}")
    public ResponseEntity<ReporteMantenimiento> getMantenimientoById(@PathVariable Long id){
        ReporteMantenimiento reporteMantenimiento = reporteMantenimientoRepository.findById(id).orElse(null);
        if(reporteMantenimiento!=null){
            return ResponseEntity.ok(reporteMantenimiento);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //Imprimir Reporte Tecnico

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reporte-mantenimiento/generate")
    public ResponseEntity<byte[]> generateReport(@RequestParam("reportId") Long reportId) throws JRException, IOException {
        // try{
        ReporteMantenimiento reporteMantenimiento = reporteMantenimientoRepository.findById(reportId).get();
        System.out.println("reporteMantenimiento = " + reporteMantenimiento);
        byte[] pdfBytes = reporteMantenimientoGenerator.generateReport(reporteMantenimiento);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "ReporteMantPDF-" + reporteMantenimiento.getIdrepmant() + ".pdf");
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
