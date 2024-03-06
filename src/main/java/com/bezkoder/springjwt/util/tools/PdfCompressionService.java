package com.bezkoder.springjwt.util.tools;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class PdfCompressionService {

    // Wrapper personalizado para Document que implementa AutoCloseable
    private static class AutoCloseableDocument implements AutoCloseable {
        private final Document document;

        public AutoCloseableDocument() {
            this.document = new Document();
        }

        public Document getDocument() {
            return document;
        }

        @Override
        public void close() throws Exception {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    private PdfCopy pdfCopy;

    public void comprimirPDF(String rutaEntrada, String rutaSalida) throws IOException, DocumentException {
        try (AutoCloseableDocument autoCloseableDocument = new AutoCloseableDocument()) {
            PdfReader reader = new PdfReader(rutaEntrada);
            PdfCopy pdfCopy = new PdfCopy(autoCloseableDocument.getDocument(), new FileOutputStream(rutaSalida));
            pdfCopy.setCompressionLevel(9);

            autoCloseableDocument.getDocument().open();

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                pdfCopy.addPage(pdfCopy.getImportedPage(reader, i));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void comprimirYCerrar(String rutaEntrada, String rutaSalida) throws Exception {
        try (AutoCloseableDocument autoCloseableDocument = new AutoCloseableDocument()) {
            PdfCopy pdfCopy = new PdfCopy(autoCloseableDocument.getDocument(), new FileOutputStream(rutaSalida));
            pdfCopy.setCompressionLevel(9);

            autoCloseableDocument.getDocument().open();

            PdfReader reader = new PdfReader(rutaEntrada);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                pdfCopy.addPage(pdfCopy.getImportedPage(reader, i));
            }
        }

        // Agrega un retardo antes de eliminar el archivo temporal
        Thread.sleep(1000); // Retardo de 1 segundo (ajústalo según sea necesario)
        // Elimina el PDF temporal
        Files.deleteIfExists(Paths.get(rutaEntrada));
    }
}
