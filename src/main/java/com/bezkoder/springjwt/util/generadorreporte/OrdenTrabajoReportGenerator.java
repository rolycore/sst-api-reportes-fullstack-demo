package com.bezkoder.springjwt.util.generadorreporte;

import com.bezkoder.springjwt.excepciones.ReportNotFoundException;
import com.bezkoder.springjwt.impl.service.IOrdenesTrabajoService;
import com.bezkoder.springjwt.models.OrdenTrabajo;

import net.sf.jasperreports.engine.*;

import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class OrdenTrabajoReportGenerator {
    private IOrdenesTrabajoService ordenesTrabajoService;
    @NotNull
    public byte[] exportToPdf(Long idOT) throws JRException, FileNotFoundException {
        OrdenTrabajo ordenTrabajo = ordenesTrabajoService.findById(idOT);
        if (ordenTrabajo != null) {
            return JasperExportManager.exportReportToPdf(getReport(ordenTrabajo));
        } else {
            // Manejo de la situación en la que no se encuentra una Orden de trabajo con el ID proporcionado
            throw new ReportNotFoundException("No se encontró una Orden de trabajo con el ID " + idOT);
        }
    }


   /* public byte[] exportToXls(List<OrdenTrabajo> ordenTrabajo) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport((List<OrdenTrabajo>) ordenTrabajo)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }*/

    public JasperPrint getReport(OrdenTrabajo ordenesTrabajo) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("no_ordent", ordenesTrabajo.getNo_ordent());
        params.put("no_solicitud", ordenesTrabajo.getNo_solicitud());
        params.put("no_ordencompra", ordenesTrabajo.getNo_ordencompra());
        params.put("fecha_entrega_certificado", ordenesTrabajo.getFecha_entrega_certificado());
        params.put("no_certificado", ordenesTrabajo.getNo_certificado());
        params.put("no_cotizacion", ordenesTrabajo.getNo_cotizacion());
        params.put("no_reporte_tecnico", ordenesTrabajo.getNo_reporte_tecnico());
        params.put("metrologo_responsable", ordenesTrabajo.getMetrologo_responsable());
        params.put("fecha_calibracion", ordenesTrabajo.getFecha_calibracion());
        params.put("prox_calibracion", ordenesTrabajo.isProx_calibracion());
        params.put("nombre_cliente", ordenesTrabajo.getNombre_cliente());
        params.put("usuario_final", ordenesTrabajo.getUsuario_final());
        params.put("lugar_calibracion", ordenesTrabajo.getLugar_calibracion());
        params.put("contacto_cliente", ordenesTrabajo.getContacto_cliente());
        params.put("telefono_contacto", ordenesTrabajo.getTelefono_contacto());
        params.put("cargo", ordenesTrabajo.getCargo());
        params.put("contacto_cargo", ordenesTrabajo.getContacto_cargo());
        params.put("descripcion_recep", ordenesTrabajo.getDescripcion_recep());
        params.put("fabricante_ind", ordenesTrabajo.getFabricante_ind());
        params.put("modelo_ind", ordenesTrabajo.getModelo_ind());
        params.put("serie_ind", ordenesTrabajo.getSerie_ind());
        params.put("clase_ind", ordenesTrabajo.getClase_ind());
        params.put("fabricante_rec", ordenesTrabajo.getFabricande_rec());
        params.put("modelo_rec", ordenesTrabajo.getModelo_rec());
        params.put("serie_rec", ordenesTrabajo.getSerie_rec());
        params.put("alcance", ordenesTrabajo.getAlcance());
        params.put("fabricante_sen", ordenesTrabajo.getFabricante_sen());
        params.put("modelo_sen", ordenesTrabajo.getModelo_sen());
        params.put("serie_sen", ordenesTrabajo.getSerie_sen());
        params.put("resolucion", ordenesTrabajo.getResolucion());
        params.put("observaciones", ordenesTrabajo.getObservaciones());
        params.put("rechazotecno", ordenesTrabajo.isRechazotecno());
        params.put("motivo", ordenesTrabajo.getMotivo());
        params.put("nombre_entrega", ordenesTrabajo.getNombre_entrega());
        params.put("cedula", ordenesTrabajo.getCedula());
        params.put("fecha_entrega_certificado", ordenesTrabajo.getFecha_entrega_certificado());
        params.put("metrologo_entrega", ordenesTrabajo.getMetrologo_entrega());
        params.put("recibe_certificado", ordenesTrabajo.getRecibe_certificado());
        params.put("recibe_cedula", ordenesTrabajo.getRecibe_cedula());
        params.put("entrego_con_ibc", ordenesTrabajo.isEntrego_con_ibc());
        params.put("observaciones_entrega", ordenesTrabajo.getObservaciones_entrega());
        params.put("fecha_entrega_ibc", ordenesTrabajo.getFecha_entrega_ibc());
        params.put("persona_entrega_ibc", ordenesTrabajo.getPersona_entrega_ibc());
        params.put("recibe_ibc", ordenesTrabajo.getRecibe_ibc());
        params.put("recibe_cedula", ordenesTrabajo.getRecibe_cedula());

        //params.put("nombre_cliente", new JRBeanCollectionDataSource((Collection<?>) ordenesTrabajo));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:reporte_de_trabajo.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }


    public ResponseEntity<Resource> exportarOrden(Long idOT) {
        Optional<OrdenTrabajo> ordenTrabajo = this.ordenesTrabajoService.buscarporId(idOT);
        if (ordenTrabajo.isPresent()) {
            try {
                final OrdenTrabajo orden = ordenTrabajo.get();
                final File file = ResourceUtils.getFile("classpath:reporte_de_trabajo.jasper");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);
                final HashMap<String, Object> params = new HashMap<>();
                params.put("no_ordent", getNonNullValue(orden.getNo_ordent()));
                params.put("no_solicitud", getNonNullValue(orden.getNo_solicitud()));
                params.put("no_ordencompra", getNonNullValue(orden.getNo_ordencompra()));
                params.put("fecha_entrega_certificado", getNonNullValue(String.valueOf(orden.getFecha_entrega_certificado())));
                params.put("no_certificado", getNonNullValue(orden.getNo_certificado()));
                params.put("no_cotizacion", getNonNullValue(orden.getNo_cotizacion()));
                params.put("no_reporte_tecnico", getNonNullValue(orden.getNo_reporte_tecnico()));
                params.put("metrologo_responsable", getNonNullValue(orden.getMetrologo_responsable()));
                params.put("fecha_calibracion", getNonNullValue(String.valueOf(orden.getFecha_calibracion())));
                params.put("prox_calibracion", getNonNullValue(String.valueOf(orden.isProx_calibracion())));
                params.put("nombre_cliente", getNonNullValue(orden.getNombre_cliente()));
                params.put("usuario_final", getNonNullValue(orden.getUsuario_final()));
                params.put("lugar_calibracion", getNonNullValue(orden.getLugar_calibracion()));
                params.put("contacto_cliente", getNonNullValue(orden.getContacto_cliente()));
                params.put("telefono_contacto", getNonNullValue(orden.getTelefono_contacto()));
                params.put("cargo", getNonNullValue(orden.getCargo()));
                params.put("contacto_cargo", getNonNullValue(orden.getContacto_cargo()));
                params.put("descripcion_recep", getNonNullValue(orden.getDescripcion_recep()));
                params.put("fabricante_ind", getNonNullValue(orden.getFabricante_ind()));
                params.put("modelo_ind", getNonNullValue(orden.getModelo_ind()));
                params.put("serie_ind", getNonNullValue(orden.getSerie_ind()));
                params.put("clase_ind", getNonNullValue(orden.getClase_ind()));
                params.put("fabricante_rec", getNonNullValue(orden.getFabricande_rec()));
                params.put("modelo_rec", getNonNullValue(orden.getModelo_rec()));
                params.put("serie_rec", getNonNullValue(orden.getSerie_rec()));
                params.put("alcance", getNonNullValue(orden.getAlcance()));
                params.put("fabricante_sen", getNonNullValue(orden.getFabricante_sen()));
                params.put("modelo_sen", getNonNullValue(orden.getModelo_sen()));
                params.put("serie_sen", getNonNullValue(orden.getSerie_sen()));
                params.put("resolucion", getNonNullValue(orden.getResolucion()));
                params.put("observaciones", getNonNullValue(orden.getObservaciones()));
                params.put("rechazotecno", getNonNullValue(String.valueOf(orden.isRechazotecno())));
                params.put("motivo", getNonNullValue(orden.getMotivo()));
                params.put("nombre_entrega", getNonNullValue(orden.getNombre_entrega()));
                params.put("cedula", getNonNullValue(orden.getCedula()));
                params.put("fecha_entrega_certificado", getNonNullValue(String.valueOf(orden.getFecha_entrega_certificado())));
                params.put("metrologo_entrega", getNonNullValue(orden.getMetrologo_entrega()));
                params.put("recibe_certificado", getNonNullValue(orden.getRecibe_certificado()));
                params.put("recibe_cedula",getNonNullValue(orden.getRecibe_cedula()));
                params.put("entrego_con_ibc", getNonNullValue(String.valueOf(orden.isEntrego_con_ibc())));
                params.put("observaciones_entrega", getNonNullValue(orden.getObservaciones_entrega()));
                params.put("fecha_entrega_ibc", getNonNullValue(String.valueOf(orden.getFecha_entrega_ibc())));
                params.put("persona_entrega_ibc", getNonNullValue(orden.getPersona_entrega_ibc()));
                params.put("recibe_ibc", getNonNullValue(orden.getRecibe_ibc()));
                params.put("recibe_cedula", getNonNullValue(orden.getRecibe_cedula()));
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("reporte_de_orden_trabajo.pdf"));

                SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
                reportConfig.setSizePageToContent(true);
                reportConfig.setForceLineBreakPolicy(false);

                SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
                exportConfig.setMetadataAuthor("Author Name");
                exportConfig.setEncrypted(true);
                exportConfig.setAllowedPermissionsHint("PRINTING");

                exporter.setConfiguration(reportConfig);
                exporter.setConfiguration(exportConfig);

                exporter.exportReport();

                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("OrdenTrabajoPDF:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder.append(orden.getIdOT())
                                .append("generateDate:")
                                .append(sdf)
                                .append(".pdf")
                                .toString())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));

            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ResponseEntity.noContent().build(); // No se encontró la orden de trabajo
        }
        return null;
    }

    // Método para obtener valores no nulos
    private <T> T getNonNullValue(T value) {
        return value != null ? value : (T) "";
    }
    public byte[] generateReport(OrdenTrabajo ordenesTrabajo) throws IOException, JRException {
      //  try {
            //final File file = ResourceUtils.getFile("classpath:reporte_de_trabajo.jasper");
            //String rutaAbsoluta = resource.getFile().getAbsolutePath();

            //System.out.println("Ruta absoluta del archivo: " + rutaAbsoluta);
           // InputStream inputStream = resource.getInputStream();
        final File file = ResourceUtils.getFile("classpath:reporte_de_orden_trabajo.jasper");
        Resource logoResource = new ClassPathResource("logo-icm-rbg.png");
        InputStream logoInputStream = logoResource.getInputStream();
        final JasperReport report = (JasperReport) JRLoader.loadObject(file);
            Map<String, Object> params = new HashMap<>();
            // Llena los parámetros aquí según tu necesidad
            params.put("no_ordent", ordenesTrabajo.getNo_ordent());
            params.put("no_solicitud", ordenesTrabajo.getNo_solicitud());
            params.put("no_ordencompra", ordenesTrabajo.getNo_ordencompra());
            params.put("fecha_entrega_certificado", ordenesTrabajo.getFecha_entrega_certificado());
            params.put("no_certificado", ordenesTrabajo.getNo_certificado());
            params.put("no_cotizacion", ordenesTrabajo.getNo_cotizacion());
            params.put("no_reporte_tecnico", ordenesTrabajo.getNo_reporte_tecnico());
            params.put("metrologo_responsable", ordenesTrabajo.getMetrologo_responsable());
            params.put("fecha_calibracion", ordenesTrabajo.getFecha_calibracion());
            params.put("prox_calibracion", ordenesTrabajo.isProx_calibracion());
            params.put("nombre_cliente", ordenesTrabajo.getNombre_cliente());
            params.put("usuario_final", ordenesTrabajo.getUsuario_final());
            params.put("lugar_calibracion", ordenesTrabajo.getLugar_calibracion());
            params.put("contacto_cliente", ordenesTrabajo.getContacto_cliente());
            params.put("telefono_contacto", ordenesTrabajo.getTelefono_contacto());
            params.put("cargo", ordenesTrabajo.getCargo());
            params.put("contacto_cargo", ordenesTrabajo.getContacto_cargo());
            params.put("descripcion_recep", ordenesTrabajo.getDescripcion_recep());
            params.put("fabricante_ind", ordenesTrabajo.getFabricante_ind());
            params.put("modelo_ind", ordenesTrabajo.getModelo_ind());
            params.put("serie_ind", ordenesTrabajo.getSerie_ind());
            params.put("clase_ind", ordenesTrabajo.getClase_ind());
            params.put("fabricante_rec", ordenesTrabajo.getFabricande_rec());
            params.put("modelo_rec", ordenesTrabajo.getModelo_rec());
            params.put("serie_rec", ordenesTrabajo.getSerie_rec());
            params.put("alcance", ordenesTrabajo.getAlcance());
            params.put("fabricante_sen", ordenesTrabajo.getFabricante_sen());
            params.put("modelo_sen", ordenesTrabajo.getModelo_sen());
            params.put("serie_sen", ordenesTrabajo.getSerie_sen());
            params.put("resolucion", ordenesTrabajo.getResolucion());
            params.put("observaciones", ordenesTrabajo.getObservaciones());
            params.put("rechazotecno", ordenesTrabajo.isRechazotecno());
            params.put("motivo", ordenesTrabajo.getMotivo());
            params.put("nombre_entrega", ordenesTrabajo.getNombre_entrega());
            params.put("cedula", ordenesTrabajo.getCedula());
            params.put("fecha_entrega_certificado", ordenesTrabajo.getFecha_entrega_certificado());
            params.put("metrologo_entrega", ordenesTrabajo.getMetrologo_entrega());
            params.put("recibe_certificado", ordenesTrabajo.getRecibe_certificado());
            params.put("recibe_cedula", ordenesTrabajo.getRecibe_cedula());
            params.put("entrego_con_ibc", ordenesTrabajo.isEntrego_con_ibc());
            params.put("observaciones_entrega", ordenesTrabajo.getObservaciones_entrega());
            params.put("fecha_entrega_ibc", ordenesTrabajo.getFecha_entrega_ibc());
            params.put("persona_entrega_ibc", ordenesTrabajo.getPersona_entrega_ibc());
            params.put("recibe_ibc", ordenesTrabajo.getRecibe_ibc());
            params.put("recibe_cedula", ordenesTrabajo.getRecibe_cedula());
        params.put("imgLogo", logoInputStream);
            // Llena el informe
            //JasperReport report = (JasperReport) JRLoader.loadObject();
        //Resource resource = new ClassPathResource("reporte_de_orden_trabajo.jrxml");
        //JasperPrint jasperPrint = JasperFillManager.fillReport(
               // JasperCompileManager.compileReport(resource.getInputStream()), params, new JREmptyDataSource());
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
/*if(jasperPrint !=null){
                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                view.setVisible(true);
            }*/
            // Exporta el informe a PDF
            byte[] pdfBytes = exportReportToPdf(jasperPrint);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "OrdenTrabajoPDF-" + ordenesTrabajo.getIdOT() + ".pdf");
            System.out.println("pdfBytes = " + pdfBytes);
            return ResponseEntity.ok()
                    .contentLength((long) pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers)
                    .body(pdfBytes).getBody();

       // }
             /*catch (JRException | FileNotFoundException ex) {
            throw new RuntimeException("Error al generar el informe", ex);
        }*/
    }

    private byte[] exportReportToPdf(JasperPrint jasperPrint) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("reporte_de_orden_trabajo.pdf"));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("Author Name");
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        exporter.exportReport();

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }










    }





