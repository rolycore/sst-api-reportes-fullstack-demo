package com.bezkoder.springjwt.util.generadorreporte;

import com.bezkoder.springjwt.models.ReporteTecnico;
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
public class ReporteTecnicoReportGenerator {
    public Resource obtenerRecursoDesdeURL(String url, String mediaLocation) {
        String[] partesURL = url.split("/");
        String nombreArchivo = partesURL[partesURL.length - 1];
        String rutaRelativa = mediaLocation + nombreArchivo;

        System.out.println("Ruta Relativa: " + rutaRelativa);

        return new ClassPathResource(rutaRelativa);
    }


    public byte[] generateReport(ReporteTecnico  reporteTecnico) throws JRException, IOException, SSLHandshakeException, MalformedURLException {
        //try {
        //final File file = ResourceUtils.getFile("classpath:reporte_de_tecnico.jasper");
        Resource resource = new ClassPathResource("reporte_de_tecnico.jasper");
        InputStream jasperStream = resource.getInputStream();
        Resource logoResource = new ClassPathResource("logo-icm-rbg.png");
        InputStream logoInputStream = logoResource.getInputStream();
      String reporteImagen1 =  reporteTecnico.getRutaImagen1();
       String reporteImagen2 =  reporteTecnico.getRutaImagen2();
       String reporteImagen3 =  reporteTecnico.getRutaImagen3();
       String reporteImagen4 =  reporteTecnico.getRutaImagen4();
        System.out.println("reporteImagen4 = " + reporteImagen4);

    /*    URL urlImagen1 = new URL(reporteImagen1);
        URL urlImagen2 = new URL(reporteImagen2);
        URL urlImagen3 = new URL(reporteImagen3);
        URL urlImagen4 = new URL(reporteImagen4);
        System.out.println("urlImagen4 = " + urlImagen4);*/
// Tu URL
      /*  String url1 = String.valueOf(urlImagen1);
        String url2 = String.valueOf(urlImagen2);
        String url3 = String.valueOf(urlImagen3);
        String url4 = String.valueOf(urlImagen4);
        System.out.println("url4 = " + url4);*/
// Parsear la URL
        URL urlObj1 = new URL(reporteImagen1);
        URL urlObj2 = new URL(reporteImagen2);
        URL urlObj3 = new URL(reporteImagen3);
        URL urlObj4 = new URL(reporteImagen4);
        System.out.println("urlObj4 = " + urlObj4);
// Obtener el nombre del archivo
        String nombreArchivo1 = new File(urlObj1.getPath()).getName();
        String nombreArchivo2 = new File(urlObj2.getPath()).getName();
        String nombreArchivo3 = new File(urlObj3.getPath()).getName();
        String nombreArchivo4 = new File(urlObj4.getPath()).getName();
        System.out.println("nombreArchivo4 = " + nombreArchivo4);

// Ahora puedes concatenar el nombre del archivo con la ruta local
        String archivoImagen1 = "C:/mediafiles/" + nombreArchivo1;//  ruta Local Windows
        String archivoImagen2 = "C:/mediafiles/" + nombreArchivo2;
        String archivoImagen3 = "C:/mediafiles/" + nombreArchivo3;
        String archivoImagen4 = "C:/mediafiles/" + nombreArchivo4;
        System.out.println("archivoImagen4 = " + archivoImagen4);


        Resource rutaImagen1 = new FileSystemResource(archivoImagen1);
        Resource rutaImagen2 = new FileSystemResource(archivoImagen2);
        Resource rutaImagen3 = new FileSystemResource(archivoImagen3);
        Resource rutaImagen4 = new FileSystemResource(archivoImagen4 );
        //System.out.println(nombreArchivo4); // Esto imprimirá "BAS004-1.jpg"
        System.out.println(rutaImagen4);    // Esto imprimirá "C:/mediafiles/BAS004-1.jpg"

// Función para obtener el recurso a partir de la URL


// Obtener los recursos para cada URL
        //Resource recurso1 = obtenerRecursoDesdeURL(rutaImagen1);
       // Resource recurso2 = obtenerRecursoDesdeURL(rutaImagen2);
       // Resource recurso3 = obtenerRecursoDesdeURL(rutaImagen3);
        //Resource recurso4 = obtenerRecursoDesdeURL(rutaImagen4);
// Supongamos que ya tienes los InputStream de tus recursos
        InputStream inputStream1 = rutaImagen1.getInputStream();
        InputStream inputStream2 = rutaImagen2.getInputStream();
        InputStream inputStream3 = rutaImagen3.getInputStream();
        InputStream inputStream4 = rutaImagen4.getInputStream();
        System.out.println("Imagen 1 " + inputStream1);
        System.out.println("Imagen 2 " + inputStream2);
        System.out.println("Imagen 3 " + inputStream3);
        System.out.println("Imagen 4 " + inputStream4);



        //final File imgLogo = ResourceUtils.getFile("classpath:logo-icm-rbg.png");
        final JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
            Map<String, Object> params = new HashMap<>();

            params.put("cliente", reporteTecnico.getNombrecliente());
            params.put("equipo", reporteTecnico.getNombreequipo());
            params.put("no_reporte_tecnico", reporteTecnico.getNo_reporte_tecnico());
            params.put("no_cotizacion", reporteTecnico.getNo_cotizacion());
            params.put("tecnico", reporteTecnico.getTecnico());
          params.put("horaentrada", reporteTecnico.getHoraEntradaFormatted());
           params.put("horasalida", reporteTecnico.getHoraSalidaFormatted());
            params.put("horaviajes", reporteTecnico.getHoraviajes());
            params.put("fechareporte", reporteTecnico.getFechareporte() );
            params.put("contacto", reporteTecnico.getContacto() );
            params.put("direccion", reporteTecnico.getDireccion());
            params.put("marca", reporteTecnico.getMarca());
            params.put("modelo", reporteTecnico.getModelo());
            params.put("no_serie", reporteTecnico.getNo_serie());
            params.put("ubicacion_equipo", reporteTecnico.getUbicacion_equipo() );
            params.put("idinterno", reporteTecnico.getIdinterno());
            params.put("capacidad", reporteTecnico.getCapacidad());
            params.put("resolucion", reporteTecnico.getResolucion());
            params.put("calibracion", reporteTecnico.isCalibracion());
            params.put("instalacion", reporteTecnico.isInstalacion());
            params.put("verificacion", reporteTecnico.isVerificacion());
            params.put("entregaequipo", reporteTecnico.isEntregaequipo());
            params.put("gestionmetrologica", reporteTecnico.isGestionmetrologica());
            params.put("retiroequipo", reporteTecnico.isRetiroequipo());
            params.put("inspeccion", reporteTecnico.isInspeccion());
            params.put("otros", reporteTecnico.isOtros());
            params.put("observaciones", reporteTecnico.getObservaciones());
            params.put("desnivel", reporteTecnico.isDesnivel());
            params.put("vibraciones", reporteTecnico.isVibraciones());
            params.put("averias", reporteTecnico.isAverias());
            params.put("erroresindicador", reporteTecnico.isErroresindicador());
            params.put("soporteinadecuadas", reporteTecnico.isSoporteinadecuadas() );
            params.put("faltacomponente", reporteTecnico.isFaltacomponente() );
            params.put("suceidad", reporteTecnico.isSuceidad() );
            params.put("corrienteaire", reporteTecnico.isCorrienteaire() );
            params.put("insectos", reporteTecnico.isInsectos());
            params.put("golpe", reporteTecnico.isGolpe());
            params.put("fuentexternacalor", reporteTecnico.isFuentexternacalor() );
            params.put("configuracion", reporteTecnico.isConfiguracion() );
            params.put("observaciones2", reporteTecnico.getObservaciones2() );
            params.put("limpieza", reporteTecnico.isLimpieza());
            params.put("ajusteslinealidad", reporteTecnico.isAjusteslinealidad() );
            params.put("configuracion1", reporteTecnico.isConfiguracion1());
            params.put("ajusteexcentricidad", reporteTecnico.isAjusteexcentricidad() );
            params.put("observaciones3", reporteTecnico.getObservaciones3() );
            params.put("completo", reporteTecnico.isCompleto() );
            params.put("incompleto", reporteTecnico.isIncompleto() );
            params.put("observaciones4", reporteTecnico.getObservaciones4() );
            params.put("nota", reporteTecnico.getNota() );
            params.put("recibidopor", reporteTecnico.getRecibidopor() );
            params.put("fecha", reporteTecnico.getFecha());
            params.put("imgLogo", logoInputStream);

// Luego, puedes pasar los InputStream directamente como parámetros a tu generador de informes
        params.put("imagen_1", inputStream1);
        params.put("imagen_2", inputStream2);
        params.put("imagen_3", inputStream3);
        params.put("imagen_4", inputStream4);

        params.put("descripcion1", reporteTecnico.getDescripcion_1());
        params.put("descripcion2", reporteTecnico.getDescripcion_2());
        params.put("descripcion3", reporteTecnico.getDescripcion_3());
        params.put("descripcion4", reporteTecnico.getDescripcion_4());
        //Resource resource = new ClassPathResource("reporte_de_tecnico.jrxml");

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            //if(jasperPrint !=null){
              //  JasperViewer view = new JasperViewer(jasperPrint, false);
                //view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
               // view.setVisible(true);
           // }
            // Exporta el informe a PDF
            byte[] pdfBytes = exportReportToPdf(jasperPrint);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "ReporteTecnicoPDF-" + reporteTecnico.getIdreptec() + ".pdf");
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
