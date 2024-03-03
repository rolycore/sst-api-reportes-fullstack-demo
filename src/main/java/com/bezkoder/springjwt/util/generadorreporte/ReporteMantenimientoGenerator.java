package com.bezkoder.springjwt.util.generadorreporte;
import com.bezkoder.springjwt.models.ReporteMantenimiento;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLHandshakeException;
import javax.transaction.Transactional;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ReporteMantenimientoGenerator {
    public Resource obtenerRecursoDesdeURL(String url, String mediaLocation) {
        String[] partesURL = url.split("/");
        String nombreArchivo = partesURL[partesURL.length - 1];
        String rutaRelativa = mediaLocation + nombreArchivo;

        System.out.println("Ruta Relativa: " + rutaRelativa);

        return new ClassPathResource(rutaRelativa);
    }

    public byte[] generateReport(ReporteMantenimiento reporteMantenimiento)throws JRException, IOException, SSLHandshakeException, MalformedURLException {
        Resource resource = new ClassPathResource("reporte_mantenimiento.jasper");
        InputStream jasperStream = resource.getInputStream();
        Resource logoResource = new ClassPathResource("logo-icm-rbg.png");
        InputStream logoInputStream = logoResource.getInputStream();
        String reporteImagen1 =  reporteMantenimiento.getRutaImagen1();
        String reporteImagen2 =  reporteMantenimiento.getRutaImagen2();
        String reporteImagen3 =  reporteMantenimiento.getRutaImagen3();
        String reporteImagen4 =  reporteMantenimiento.getRutaImagen4();
        String reporteImagen5 = reporteMantenimiento.getRutaImagen5();
        String reporteImagen6 = reporteMantenimiento.getRutaImagen6();
        String reporteImagen7 = reporteMantenimiento.getRutaImagen7();
        String reporteImagen8 = reporteMantenimiento.getRutaImagen8();
        String reporteImagen9 = reporteMantenimiento.getRutaImagen9();
        System.out.println("reporteImagen4 = " + reporteImagen4);

        // Parsear la URL
        URL urlObj1 = new URL(reporteImagen1);
        URL urlObj2 = new URL(reporteImagen2);
        URL urlObj3 = new URL(reporteImagen3);
        URL urlObj4 = new URL(reporteImagen4);
        URL urlObj5 = new URL(reporteImagen5);
        URL urlObj6 = new URL(reporteImagen6);
        URL urlObj7 = new URL(reporteImagen7);
        URL urlObj8 = new URL(reporteImagen8);
        URL urlObj9 = new URL(reporteImagen9);
        System.out.println("urlObj4 = " + urlObj4);

        // Obtener el nombre del archivo
        String nombreArchivo1 = new File(urlObj1.getPath()).getName();
        String nombreArchivo2 = new File(urlObj2.getPath()).getName();
        String nombreArchivo3 = new File(urlObj3.getPath()).getName();
        String nombreArchivo4 = new File(urlObj4.getPath()).getName();
        String nombreArchivo5 = new File(urlObj5.getPath()).getName();
        String nombreArchivo6 = new File(urlObj6.getPath()).getName();
        String nombreArchivo7 = new File(urlObj7.getPath()).getName();
        String nombreArchivo8 = new File(urlObj8.getPath()).getName();
        String nombreArchivo9 = new File(urlObj9.getPath()).getName();
        System.out.println("nombreArchivo4 = " + nombreArchivo4);
        System.out.println("nombreArchivo4 = " + nombreArchivo9);
        // Ahora puedes concatenar el nombre del archivo con la ruta local
        String archivoImagen1 = "C:/mediafiles/" + nombreArchivo1;//  ruta Local Windows
        String archivoImagen2 = "C:/mediafiles/" + nombreArchivo2;
        String archivoImagen3 = "C:/mediafiles/" + nombreArchivo3;
        String archivoImagen4 = "C:/mediafiles/" + nombreArchivo4;
        String archivoImagen5 = "C:/mediafiles/" + nombreArchivo5;//  ruta Local Windows
        String archivoImagen6 = "C:/mediafiles/" + nombreArchivo6;
        String archivoImagen7 = "C:/mediafiles/" + nombreArchivo7;
        String archivoImagen8 = "C:/mediafiles/" + nombreArchivo8;
        String archivoImagen9 = "C:/mediafiles/" + nombreArchivo9;
        System.out.println("archivoImagen4 = " + archivoImagen4);

        Resource rutaImagen1 = new FileSystemResource(archivoImagen1);
        Resource rutaImagen2 = new FileSystemResource(archivoImagen2);
        Resource rutaImagen3 = new FileSystemResource(archivoImagen3);
        Resource rutaImagen4 = new FileSystemResource(archivoImagen4);
        Resource rutaImagen5 = new FileSystemResource(archivoImagen5);
        Resource rutaImagen6 = new FileSystemResource(archivoImagen6);
        Resource rutaImagen7 = new FileSystemResource(archivoImagen7);
        Resource rutaImagen8 = new FileSystemResource(archivoImagen8);
        Resource rutaImagen9 = new FileSystemResource(archivoImagen9);
        //System.out.println(nombreArchivo4); // Esto imprimirá "BAS004-1.jpg"
        System.out.println(rutaImagen4);    // Esto imprimirá "C:/mediafiles/BAS004-1.jpg"
        // Supongamos que ya tienes los InputStream de tus recursos
        InputStream inputStream1 = rutaImagen1.getInputStream();
        InputStream inputStream2 = rutaImagen2.getInputStream();
        InputStream inputStream3 = rutaImagen3.getInputStream();
        InputStream inputStream4 = rutaImagen4.getInputStream();
        InputStream inputStream5 = rutaImagen5.getInputStream();
        InputStream inputStream6 = rutaImagen6.getInputStream();
        InputStream inputStream7 = rutaImagen7.getInputStream();
        InputStream inputStream8 = rutaImagen8.getInputStream();
        InputStream inputStream9 = rutaImagen9.getInputStream();
        System.out.println("Imagen 1 " + inputStream1);
        System.out.println("Imagen 2 " + inputStream2);
        System.out.println("Imagen 3 " + inputStream3);
        System.out.println("Imagen 4 " + inputStream4);
        final JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
        Map<String, Object> params = new HashMap<>();
        params.put("cliente", reporteMantenimiento.getNombrecliente());
        params.put("equipo", reporteMantenimiento.getNombreequipo());
        params.put("no_reporte", reporteMantenimiento.getNo_reporte());
        params.put("no_cotizacion", reporteMantenimiento.getNo_cotizacion());
        params.put("tecnico", reporteMantenimiento.getTecnico());
        params.put("horaentrada", reporteMantenimiento.getHoraEntradaFormatted());
        params.put("horasalida", reporteMantenimiento.getHoraSalidaFormatted());
        params.put("horaviajes", reporteMantenimiento.getHoraviajes());
        params.put("fechareporte", reporteMantenimiento.getFechareporte() );
        params.put("contacto", reporteMantenimiento.getContacto());
        params.put("cargo",reporteMantenimiento.getCargo());
        params.put("direccion", reporteMantenimiento.getDireccion());
        params.put("ubicacionequipo",reporteMantenimiento.getUbicacionequipo());
        params.put("fabricanteindicador",reporteMantenimiento.getFabricanteindicador());
        params.put("fabricantemarco",reporteMantenimiento.getFabricantemarco());
        params.put("fabricantetransductor",reporteMantenimiento.getFabricantetransductor());
        params.put("modeloindicador",reporteMantenimiento.getModeloindicador());
        params.put("modelomarco",reporteMantenimiento.getModelomarco());
        params.put("modelotransductor",reporteMantenimiento.getModelotransductor());
        params.put("serieindicador",reporteMantenimiento.getSerieindicador());
        params.put("seriemarco",reporteMantenimiento.getSeriemarco());
        params.put("serietransductor",reporteMantenimiento.getSerietransductor());
        params.put("capacidadindicador",reporteMantenimiento.getCapacidadindicador());
        params.put("capacidadmarco",reporteMantenimiento.getCapacidadmarco());
        params.put("capacidadtransductor",reporteMantenimiento.getCapacidadtransductor());
        params.put("notamantprevent",reporteMantenimiento.getNotamantprevent());
        params.put("notahallazgos",reporteMantenimiento.getNotahallazgo());
        params.put("recomendaciones",reporteMantenimiento.getRecomendaciones());
        params.put("capacidadtrasnductor",reporteMantenimiento.getModelotransductor());
        params.put("imgLogo", logoInputStream);
// Luego, puedes pasar los InputStream directamente como parámetros a tu generador de informes
        params.put("imagen_1", inputStream1);
        params.put("imagen_2", inputStream2);
        params.put("imagen_3", inputStream3);
        params.put("imagen_4", inputStream4);
        params.put("imagen_5", inputStream5);
        params.put("imagen_6", inputStream6);
        params.put("imagen_7", inputStream7);
        params.put("imagen_8", inputStream8);
        params.put("imagen_9", inputStream9);
        params.put("descripcion1", reporteMantenimiento.getDescripcion1());
        params.put("descripcion2", reporteMantenimiento.getDescripcion2());
        params.put("descripcion3", reporteMantenimiento.getDescripcion3());
        params.put("descripcion4", reporteMantenimiento.getDescripcion4());
        params.put("descripcion5", reporteMantenimiento.getDescripcion5());
        params.put("descripcion6", reporteMantenimiento.getDescripcion6());
        params.put("descripcion7", reporteMantenimiento.getDescripcion7());
        params.put("descripcion8", reporteMantenimiento.getDescripcion8());
        params.put("descripcion9", reporteMantenimiento.getDescripcion9());
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
        byte[] pdfBytes = exportReportToPdf(jasperPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "ReporteMantPDF-" + reporteMantenimiento.getIdrepmant() + ".pdf");
        System.out.println("pdfBytes = " + pdfBytes);
        return ResponseEntity.ok()
                .contentLength((long) pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(pdfBytes).getBody();

        //}
        /*catch (JRException | FileNotFoundException ex) {
            throw new RuntimeException("Error al generar el informe", ex);
        }*/
    }
    private byte[] exportReportToPdf(JasperPrint jasperPrint) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("reporte_de_tecnico.pdf"));

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
