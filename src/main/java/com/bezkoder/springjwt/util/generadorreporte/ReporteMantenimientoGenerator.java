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
        // Crear un array de InputStream y otro de parámetros para almacenar la información
        InputStream[] inputStreams = new InputStream[12];
        Map<String, Object> params = new HashMap<>();

// Iterar sobre las rutas de imagen y procesarlas
        for (int i = 1; i <= 12; i++) {
            String rutaImagen = obtenerRutaImagen(reporteMantenimiento, i);
            System.out.println("dentro del for1 rutaImagen = " + rutaImagen);
            procesarRutaImagen(rutaImagen, i, inputStreams, params);

        }

// Luego, puedes pasar los InputStream directamente como parámetros a tu generador de informes
        int i = 1;
        for (InputStream inputStream : inputStreams) {
            params.put("imagen_" + i, inputStream);
            System.out.println("inputStream dentro del for2 = imagen_" + inputStream);
            i++;

        }


        final JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);

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

        params.put("descripcion1", reporteMantenimiento.getDescripcion1());
        params.put("descripcion2", reporteMantenimiento.getDescripcion2());
        params.put("descripcion3", reporteMantenimiento.getDescripcion3());
        params.put("descripcion4", reporteMantenimiento.getDescripcion4());
        params.put("descripcion5", reporteMantenimiento.getDescripcion5());
        params.put("descripcion6", reporteMantenimiento.getDescripcion6());
        params.put("descripcion7", reporteMantenimiento.getDescripcion7());
        params.put("descripcion8", reporteMantenimiento.getDescripcion8());
        params.put("descripcion9", reporteMantenimiento.getDescripcion9());
        params.put("descripcion10", reporteMantenimiento.getDescripcion10());
        params.put("descripcion11", reporteMantenimiento.getDescripcion11());
        params.put("descripcion12", reporteMantenimiento.getDescripcion12());
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
        byte[] pdfBytes = exportReportToPdf(jasperPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "ReporteMantPDF-" + reporteMantenimiento.getIdrepmant() + ".pdf");
        System.err.println("pdfBytes = " + pdfBytes);
        return ResponseEntity.ok()
                .contentLength((long) pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(pdfBytes).getBody();


    }
    // Métodos para obtener la ruta de imagen y procesarla
    private String obtenerRutaImagen(ReporteMantenimiento reporteMantenimiento, int numeroImagen) {
        switch (numeroImagen) {
            case 1:
             //   System.out.println("obtenerRutaImagen - reporteMantenimiento.getRutaImagen1() = " + reporteMantenimiento.getRutaImagen1());
                return reporteMantenimiento.getRutaImagen1();

            case 2:
               // System.out.println("obtenerRutaImagen - reporteMantenimiento.getRutaImagen2() = " + reporteMantenimiento.getRutaImagen2());
                return reporteMantenimiento.getRutaImagen2();
            case 3:
            //    System.out.println("obtenerRutaImagen - reporteMantenimiento.getRutaImagen2() = " + reporteMantenimiento.getRutaImagen3());
                return reporteMantenimiento.getRutaImagen3();
            case 4: return reporteMantenimiento.getRutaImagen4();
            case 5: return reporteMantenimiento.getRutaImagen5();
            case 6: return reporteMantenimiento.getRutaImagen6();
            case 7: return reporteMantenimiento.getRutaImagen7();
            case 8: return reporteMantenimiento.getRutaImagen8();
            case 9: return reporteMantenimiento.getRutaImagen9();
            case 10: return reporteMantenimiento.getRutaImagen10();
            case 11: return reporteMantenimiento.getRutaImagen11();
            case 12: return reporteMantenimiento.getRutaImagen12();
            default: return null;
        }
    }

    private void procesarRutaImagen(String reporteImagen, int numeroImagen, InputStream[] inputStreams, Map<String, Object> params) throws IOException {
        // Verificar si la ruta de imagen es nula antes de crear el objeto URL
        URL urlObj = (reporteImagen != null) ? new URL(reporteImagen) : null;

        // Verificar si la URL es nula antes de continuar
        if (urlObj != null) {
            // Obtener el nombre del archivo solo si la URL no es nula
            String nombreArchivo = new File(urlObj.getPath()).getName();
          //  System.out.println("nombreArchivo" + numeroImagen + " = " + nombreArchivo);

            // Resto del código relacionado con la URL...
            String archivoImagen = "/root/mediafiles/" + nombreArchivo; //C:/mediafiles/ linux /root/mediafiles/
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
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("reporte_mantenimiento.pdf"));

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
