package com.bezkoder.springjwt.util.generadorreporte;

import com.bezkoder.springjwt.models.ReporteMantenimiento;
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
        Resource logoResource = new ClassPathResource("logo.jpg");
        InputStream logoInputStream = logoResource.getInputStream();
        InputStream[] inputStreams = new InputStream[4];
        Map<String, Object> params = new HashMap<>();

// Iterar sobre las rutas de imagen y procesarlas
        for (int i = 1; i <= 4; i++) {
            String rutaImagen = obtenerRutaImagen(reporteTecnico, i);

            // Procesar la ruta de la imagen y asignar al parámetro correspondiente
            procesarRutaImagen(rutaImagen, i, inputStreams, params);

            // Asignar el InputStream directamente como parámetro al generador de informes
            params.put("imagen_" + i, inputStreams[i - 1]);
        }
     /*    for (int i = 1; i <= 4; i++) {
            String rutaImagen = obtenerRutaImagen(reporteTecnico, i);
            System.out.println("dentro del for1 rutaImagen = " + rutaImagen);
            procesarRutaImagen(rutaImagen, i, inputStreams, params);

        }

// Luego, puedes pasar los InputStream directamente como parámetros a tu generador de informes
       int i = 1;
        for (InputStream inputStream : inputStreams) {
            params.put("imagen_" + i, inputStream);
            System.out.println("inputStream dentro del for2 = imagen_" + inputStream);
            i++;

        }*/



        //final File imgLogo = ResourceUtils.getFile("classpath:logo-icm-rbg.png");
        final JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);


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
    private String obtenerRutaImagen(ReporteTecnico reporteTecnico, int numeroImagen) {
        switch (numeroImagen) {
            case 1:
                //   System.out.println("obtenerRutaImagen - reporteMantenimiento.getRutaImagen1() = " + reporteMantenimiento.getRutaImagen1());
                return reporteTecnico.getRutaImagen1();

            case 2:
                // System.out.println("obtenerRutaImagen - reporteMantenimiento.getRutaImagen2() = " + reporteMantenimiento.getRutaImagen2());
                return reporteTecnico.getRutaImagen2();
            case 3:
                //    System.out.println("obtenerRutaImagen - reporteMantenimiento.getRutaImagen2() = " + reporteMantenimiento.getRutaImagen3());
                return reporteTecnico.getRutaImagen3();
            case 4: return reporteTecnico.getRutaImagen4();
            default: return null;
        }
    }

    private void procesarRutaImagen(String reporteImagen, int numeroImagen, InputStream[] inputStreams, Map<String, Object> params) throws IOException {
        // Verificar si la ruta de imagen es nula antes de crear el objeto URL
        //URL urlObj = (reporteImagen != null) ? new URL(reporteImagen) : null;
        URL urlObj  = new URL(reporteImagen);

        // Verificar si la URL es nula antes de continuar
        if (urlObj != null) {
            // Obtener el nombre del archivo solo si la URL no es nula
            String nombreArchivo = new File(urlObj.getPath()).getName();
            //  System.out.println("nombreArchivo" + numeroImagen + " = " + nombreArchivo);

            // Resto del código relacionado con la URL...
            String archivoImagen = "C:/mediafiles/" + nombreArchivo; //ruta local windows C:/mediafiles/ ruta local linux /root/mediafiles/
            Resource rutaImagen = new FileSystemResource(archivoImagen);
            inputStreams[numeroImagen - 1] = rutaImagen.getInputStream();
            //   System.out.println("archivoImagen" + numeroImagen + " = " + archivoImagen);

            // Continuar con el resto del código para InputStream, etc.
        } else {
            // Lógica para manejar la ausencia de la ruta de imagen
            System.out.println("La ruta de imagen " + numeroImagen + " es nula");
            // Puedes agregar aquí la lógica necesaria cuando la ruta es nula
        }
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
