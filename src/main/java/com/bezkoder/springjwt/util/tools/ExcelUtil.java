package com.bezkoder.springjwt.util.tools;
import com.bezkoder.springjwt.DTO.ClienteDTO;
import com.bezkoder.springjwt.DTO.EquipoDTO;
import com.bezkoder.springjwt.impl.service.IClienteService;
import com.bezkoder.springjwt.models.Cliente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExcelUtil {
    @Autowired
    private static IClienteService iClienteService;
    private static boolean hasNonNullCells(Row row) {
        for (Cell cell : row) {
            if (cell != null) {
                CellType cellType = cell.getCellType();
                if (cellType == CellType.NUMERIC || (cellType == CellType.STRING && !cell.getStringCellValue().isEmpty())) {
                    return true;
                }
            }
        }
        return false;
    }



    public static List<ClienteDTO> leerClientesDesdeArchivo(InputStream inputStream) throws IOException {
        List<ClienteDTO> clientes = new ArrayList<>();
        int clientesImportados = 0;
        int clientesEnBlancoONulos = 0;
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                // Manejar el caso de hoja nula, por ejemplo, lanzando una excepción o devolviendo una lista vacía.
                return clientes;
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0 || !hasNonNullCells(row)) {
                    // Salta la primera fila (encabezados) o filas sin celdas no nulas
                    if (!hasNonNullCells(row)) {
                        // Si la fila no tiene celdas con datos, aumenta el contador de clientes en blanco o nulos.
                        clientesEnBlancoONulos++;
                    }
                    continue;
                }

                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO.setNombre(getCellValueAsString(row.getCell(0)));  // Nombre
             //   clienteDTO.setApellido(getCellValueAsString(row.getCell(1)));  // Apellido
                clienteDTO.setEmail(getCellValueAsString(row.getCell(1)));  // Email
                clienteDTO.setActivo(getCellValueAsBoolean(row.getCell(2)));  // Activo
                clienteDTO.setTelefono_empresa(getCellValueAsString(row.getCell(3)));  // Teléfono empresa
                clienteDTO.setCod_cliente(getCellValueAsString(row.getCell(4)));  // Código cliente
                clienteDTO.setRazon_social(getCellValueAsString(row.getCell(5)));  // Razón social
                clienteDTO.setNombre_comercial(getCellValueAsString(row.getCell(6)));  // Nombre comercial
                clienteDTO.setRuc(getCellValueAsString(row.getCell(7)));  // RUC
                clienteDTO.setDv(getCellValueAsString(row.getCell(8)));  // DV
                clienteDTO.setDireccion(getCellValueAsString(row.getCell(9)));  // Dirección
             //   clienteDTO.setTelefono_jefe(getCellValueAsString(row.getCell(11)));  // Teléfono jefe
             //   clienteDTO.setCelular_jefe(getCellValueAsString(row.getCell(12)));  // Celular jefe
              //  clienteDTO.setCorreo_electronico(getCellValueAsString(row.getCell(13)));  // Correo electrónico
               // clienteDTO.setActividad_economica(getCellValueAsString(row.getCell(14)));  // Actividad económica
                clienteDTO.setAbreviatura(getCellValueAsString(row.getCell(10)));  // Abreviatura
                clienteDTO.setNombre_contacto(getCellValueAsString(row.getCell(11)));  // Nombre de contacto
                clienteDTO.setCargo_servicio(getCellValueAsString(row.getCell(12)));  // Cargo de servicio
                clienteDTO.setCelular_servicio(getCellValueAsString(row.getCell(13)));  // Celular de servicio
                clienteDTO.setCorreo_servicio(getCellValueAsString(row.getCell(14)));  // Correo de servicio
              //  clienteDTO.setTelefono_servicio(getCellValueAsString(row.getCell(20)));  // Teléfono de servicio
                clienteDTO.setNombre_cobro(getCellValueAsString(row.getCell(15)));  // Nombre de cobro
                clienteDTO.setCargo_cobro(getCellValueAsString(row.getCell(16)));  // Cargo de cobro
                clienteDTO.setTelefono_cobro(getCellValueAsString(row.getCell(17)));  // Teléfono de cobro
                clienteDTO.setCelular_cobro(getCellValueAsString(row.getCell(18)));  // Celular de cobro
                clienteDTO.setCorreo_cobro(getCellValueAsString(row.getCell(19)));  // Correo de cobro

                clientes.add(clienteDTO);
                clientesImportados++;


            }

        } catch (Exception e) {
            // Manejar la excepción, por ejemplo, imprimir un mensaje de error
            e.printStackTrace();
        }
        System.out.println("Clientes importados: " + clientesImportados);
        System.out.println("Clientes en blanco o nulos: " + clientesEnBlancoONulos);

        return clientes;
    }


    private static String getCellValueAsString(Cell cell) {
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            if (cellType == CellType.BLANK) {
                return ""; // La celda está en blanco
            } else if (cellType == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cellType == CellType.NUMERIC) {
                // Convierte el valor numérico a cadena
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return "";
    }








    private static Boolean getCellValueAsBoolean(Cell cell) {
        if (cell != null) {
            CellType cellType = cell.getCellType();
            System.out.println("cellType = " + cellType);
            if (CellType.BOOLEAN.equals(cellType)) {
                System.out.println("dato boolean es cellType = " + cellType);
                return cell.getBooleanCellValue();
            } else if (CellType.STRING.equals(cellType)) {
                String cellValue = cell.getStringCellValue();
                System.out.println("dato booleano string cellValue = " + cellValue);
                return "true".equalsIgnoreCase(cellValue) || "VERDADERO".equalsIgnoreCase(cellValue)
                        || "Sí".equalsIgnoreCase(cellValue);
            }
            System.out.println("los datos booleanos son cellType = " + cellType);
        }
        return false; // Valor predeterminado en caso de que no se pueda determinar
    }

        public static byte[] exportarClientesACSV(List<ClienteDTO> clientes) throws IOException {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Clientes");
                Row headerRow = sheet.createRow(0);

                // Crear encabezados

                String[] columnas = {"Nombre Apellido Representante legal", "Email Empresa", "Activo", "Telefono Empresa", "Cod Cliente", "Razon Social", "Nombre Comercial",
                        "Ruc", "DV", "Direccion", "Abreviatura",
                        "Nombre Contacto", "Cargo Servicio", "Celular Servicio", "Correo Servicio", "Nombre Cobro",
                        "Cargo Cobro", "Telefono Cobro", "Celular Cobro", "Correo Cobro"};
                for (int i = 0; i < columnas.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnas[i]);
                }

                // Agregar datos de clientes
                for (int i = 0; i < clientes.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    ClienteDTO cliente = clientes.get(i);

                    row.createCell(0).setCellValue(cliente.getNombre());
                 //   row.createCell(1).setCellValue(cliente.getApellido());
                    row.createCell(1).setCellValue(cliente.getEmail());
                    row.createCell(2).setCellValue(cliente.isActivo());
                    row.createCell(3).setCellValue(cliente.getTelefono_empresa());
                    row.createCell(4).setCellValue(cliente.getCod_cliente());
                    row.createCell(5).setCellValue(cliente.getRazon_social());
                    row.createCell(6).setCellValue(cliente.getNombre_comercial());
                    row.createCell(7).setCellValue(cliente.getRuc());
                    row.createCell(8).setCellValue(cliente.getDv());
                    row.createCell(9).setCellValue(cliente.getDireccion());
                //    row.createCell(11).setCellValue(cliente.getTelefono_jefe());
                  //  row.createCell(12).setCellValue(cliente.getCelular_jefe());
                   // row.createCell(13).setCellValue(cliente.getCorreo_electronico());
                   // row.createCell(14).setCellValue(cliente.getActividad_economica());
                    row.createCell(10).setCellValue(cliente.getAbreviatura());
                    row.createCell(11).setCellValue(cliente.getNombre_contacto());
                    row.createCell(12).setCellValue(cliente.getCargo_servicio());
                    row.createCell(13).setCellValue(cliente.getCelular_servicio());
                    row.createCell(14).setCellValue(cliente.getCorreo_servicio());
                  //  row.createCell(20).setCellValue(cliente.getTelefono_servicio());
                    row.createCell(15).setCellValue(cliente.getNombre_cobro());
                    row.createCell(16).setCellValue(cliente.getCargo_cobro());
                    row.createCell(17).setCellValue(cliente.getTelefono_cobro());
                    row.createCell(18).setCellValue(cliente.getCelular_cobro());
                    row.createCell(19).setCellValue(cliente.getCorreo_cobro());
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }

    public static byte[] exportarEquiposACSV(List<EquipoDTO> equipos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Equipos");
            Row headerRow = sheet.createRow(0);

            // Crear encabezados
            String[] columnas = {"Nombre", "Nombre Cliente","Categoria Equipo", "Codigo Equipo", "Activo", "Marca","Modelo","Numero Serie","Capacidad", "Capacidad Max", "Capacidad Min", "Resolucion", "Divisiones",
                    "Observaciones", "Unidad Medida", "Instrumento", "Mide", "Lista Precio", "CMC Equipo", "Fabricante Receptor",
                    "Modelo Receptor", "Serie Receptor", "Id Interno Receptor", "Fabricante Sensor", "Modelo Sensor", "Serie Sensor","Id Interno Sensor",
                    "Fabricante Indicador", "Modelo Indicador", "Serie Indicador", "Id Interno Indicador","Cliente"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Agregar datos de equipos
            for (int i = 0; i < equipos.size(); i++) {
                Row row = sheet.createRow(i + 1);
                EquipoDTO equipo = equipos.get(i);

                row.createCell(0).setCellValue(equipo.getNombre());
                row.createCell(1).setCellValue(equipo.getNombrecliente());
                row.createCell(2).setCellValue(equipo.getCategoria_equipo());
                row.createCell(3).setCellValue(equipo.getCodigoequipo());
                row.createCell(4).setCellValue(equipo.isActivo());
                row.createCell(5).setCellValue(equipo.getMarca());
                row.createCell(6).setCellValue(equipo.getModelo());
                row.createCell(7).setCellValue(equipo.getNumero_serie());
                row.createCell(8).setCellValue(equipo.getCapacidad());
                row.createCell(9).setCellValue(equipo.getCapacidad_maxima());
                row.createCell(10).setCellValue(equipo.getCapacidad_minima());
                row.createCell(11).setCellValue(equipo.getResolucion());
                row.createCell(12).setCellValue(equipo.getDivisiones());
                row.createCell(13).setCellValue(equipo.getObservaciones());
// Leer contenido de la imagen desde el archivo File
             //   byte[] imagenFile = equipo.getImagen_equipo();


// Luego, agregar la imagen al workbook

                // Manejo de la imagen
             //   Drawing<?> drawing = sheet.createDrawingPatriarch();
              //  ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 10, i + 1, 11, i + 2);
                //int pictureIndex = workbook.addPicture(imagenBytes, Workbook.PICTURE_TYPE_PNG);
                //int pictureIndex = workbook.addPicture(imagenFile, Workbook.PICTURE_TYPE_PNG);
               // Picture picture = drawing.createPicture(anchor, pictureIndex);
               // picture.resize(1, 1);

                //row.createCell(9).setCellValue("Imagen");

                row.createCell(14).setCellValue(equipo.getUnidad_medida());
                row.createCell(15).setCellValue(equipo.getInstrumento());
                row.createCell(16).setCellValue(equipo.getMide());
                row.createCell(17).setCellValue(equipo.getLista_precio());
                row.createCell(18).setCellValue(equipo.getCmc_equipo());
                row.createCell(19).setCellValue(equipo.getFabricante_receptor());
                row.createCell(20).setCellValue(equipo.getModelo_receptor());
                row.createCell(21).setCellValue(equipo.getSerie_receptor());
                row.createCell(22).setCellValue(equipo.getId_interno_receptor());
                row.createCell(23).setCellValue(equipo.getFabricante_sensor());
                row.createCell(24).setCellValue(equipo.getModelo_sensor());
                row.createCell(25).setCellValue(equipo.getSerie_sensor());
                row.createCell(26).setCellValue(equipo.getId_interno_sensor());
                row.createCell(27).setCellValue(equipo.getFabricante_indicador());
                row.createCell(28).setCellValue(equipo.getModelo_indicador());
                row.createCell(29).setCellValue(equipo.getSerie_indicador());
                row.createCell(30).setCellValue(equipo.getId_interno_indicador());
                row.createCell(31).setCellValue(equipo.getCliente().getIdCliente());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    /*private static void insertarImagen(Workbook workbook, Drawing<?> drawing, Row row, int cellIndex, File imageFile) throws IOException {
        // Crea un objeto ClientAnchor para especificar la posición de la imagen
        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cellIndex, row.getRowNum(), (short) (cellIndex + 1), row.getRowNum() + 1);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

        // Carga la imagen desde el archivo
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        // Crea un objeto Picture y configura la imagen
        XSSFPicture picture = (XSSFPicture) drawing.createPicture(anchor, loadPicture(workbook, imageBytes));

        // Escala la imagen si es necesario (ajusta el tamaño)
        picture.resize(); // Esto ajustará la imagen al tamaño de la celda
    }

    private static int loadPicture(Workbook workbook, byte[] imageBytes) {
        int pictureIndex;
        if (workbook instanceof XSSFWorkbook) {
            XSSFRichTextString richText = new XSSFRichTextString("");

            byte[] bytes = richText.getCTR().getRArray(0).getDrawingArray(0).getInlineArray(0).getBlipFill().getBlip().getEmbed();
            pictureIndex = ((XSSFWorkbook) workbook).addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
            bytes[15] = (byte) (pictureIndex & 0xFF);
            bytes[14] = (byte) ((pictureIndex >> 8) & 0xFF);
        } else {
            pictureIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
        }
        return pictureIndex;
    }*/
    private static boolean hasData(Row row) {
        // Verificar una celda específica que se espera contenga datos
        Cell cell = row.getCell(0); // Por ejemplo, verifica la primera celda (cambiar según tus necesidades)

        // Verifica si la celda no es nula y su valor es diferente de vacío o null
        if (cell != null && cell.getCellType() != CellType.BLANK && cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
            return true;
        }

        // Si no encontramos datos en la celda específica, podemos considerar que la fila no tiene datos
        return false;
    }
    public static List<EquipoDTO> leerEquiposDesdeArchivo(InputStream inputStream) throws IOException {
        List<EquipoDTO> equipos = new ArrayList<>();
        int equiposImportados = 0;
        int equiposEnBlancoONulos = 0;
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                // Manejar el caso de hoja nula, por ejemplo, lanzando una excepción o devolviendo una lista vacía.
                return equipos;
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0 || !hasNonNullCells(row)) {
                    // Salta la primera fila (encabezados) o filas sin celdas no nulas
                    if (!hasNonNullCells(row)) {
                        // Si la fila no tiene celdas con datos, aumenta el contador de clientes en blanco o nulos.
                        equiposEnBlancoONulos++;
                    }
                    continue;
                }

                EquipoDTO equipoDTO = new EquipoDTO();
                equipoDTO.setNombre(getCellValueAsString(row.getCell(0)));  // Nombre
                equipoDTO.setNombrecliente(getCellValueAsString(row.getCell(1)));  // Nombre
                equipoDTO.setCategoria_equipo(getCellValueAsString(row.getCell(2)));  // Apellido
                equipoDTO.setCodigoequipo(getCellValueAsString(row.getCell(3)));  // Email
                equipoDTO.setActivo(getCellValueAsBoolean(row.getCell(4)));  // Activo
                equipoDTO.setMarca(getCellValueAsString(row.getCell(5)));  // Marca
                equipoDTO.setModelo(getCellValueAsString(row.getCell(6)));  // Modelo
                equipoDTO.setNumero_serie(getCellValueAsString(row.getCell(7)));  // Código cliente
                equipoDTO.setCapacidad(getCellValueAsString(row.getCell(8)));  // Código cliente
                equipoDTO.setCapacidad_maxima(getCellValueAsString(row.getCell(9)));  // Código cliente
                equipoDTO.setCapacidad_minima(getCellValueAsString(row.getCell(10)));  // Razón social
                equipoDTO.setResolucion(getCellValueAsString(row.getCell(11)));  // Nombre comercial
                equipoDTO.setDivisiones(getCellValueAsString(row.getCell(12)));  // RUC
                equipoDTO.setObservaciones(getCellValueAsString(row.getCell(13)));  // DV
                //equipoDTO.setImagen_equipo(getCellValueAsString(row.getCell(9)).getBytes());  // Dirección
                equipoDTO.setUnidad_medida(getCellValueAsString(row.getCell(14)));  // Teléfono jefe
                equipoDTO.setInstrumento(getCellValueAsString(row.getCell(15)));  // Celular jefe
                equipoDTO.setMide(getCellValueAsString(row.getCell(16)));  // Correo electrónico
                equipoDTO.setLista_precio(getCellValueAsString(row.getCell(17)));  // Actividad económica
                equipoDTO.setCmc_equipo(getCellValueAsString(row.getCell(18)));  // Abreviatura
                equipoDTO.setFabricante_receptor(getCellValueAsString(row.getCell(19)));  // Nombre de contacto
                equipoDTO.setModelo_receptor(getCellValueAsString(row.getCell(20)));  // Cargo de servicio
                equipoDTO.setSerie_receptor(getCellValueAsString(row.getCell(21)));  // Celular de servicio
                equipoDTO.setId_interno_receptor(getCellValueAsString(row.getCell(22)));  // Correo de servicio
                equipoDTO.setFabricante_sensor(getCellValueAsString(row.getCell(23)));  // Teléfono de servicio
                equipoDTO.setModelo_sensor(getCellValueAsString(row.getCell(24)));  // Nombre de cobro
                equipoDTO.setSerie_sensor(getCellValueAsString(row.getCell(25)));  // Cargo de cobro
                equipoDTO.setId_interno_sensor(getCellValueAsString(row.getCell(26)));  // Teléfono de cobro
                equipoDTO.setFabricante_indicador(getCellValueAsString(row.getCell(27)));  // Celular de cobro
                equipoDTO.setModelo_indicador(getCellValueAsString(row.getCell(28)));  // Correo de cobro
                equipoDTO.setSerie_indicador(getCellValueAsString(row.getCell(29)));  // Correo de cobro
                equipoDTO.setId_interno_indicador(getCellValueAsString(row.getCell(30)));  // Correo de cobro
                Cell cell = row.getCell(31); // Obtén la celda
                System.out.println("cell 31 = " + cell);
                if (cell != null) {
                    try {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            double clienteId = cell.getNumericCellValue();
                            Long clienteIdLong = (long) clienteId;
                            Cliente cliente = iClienteService.findById(clienteIdLong); // Asumiendo que tienes un servicio para manejar Clientes
                            equipoDTO.setCliente(cliente);
                        } else {
                            // Manejar el caso en el que la celda no sea de tipo NUMERIC.
                            // Por ejemplo, puedes imprimir un mensaje de error o lanzar una excepción personalizada.
                            throw new RuntimeException("La celda no contiene un valor numérico en la columna 31.");
                        }
                    } catch (Exception e) {
                        // Manejar cualquier excepción que se haya producido al intentar obtener el valor numérico.
                        // Puedes imprimir un mensaje de error, registrar la excepción o tomar otras acciones.
                        e.printStackTrace();
                    }
                } else {
                    // Manejar el caso en que la celda sea nula.
                    // Puedes imprimir un mensaje de error, registrar el problema o tomar otras acciones.
                    throw new NullPointerException("La celda en la columna 31 es nula.");
                }

                equipos.add(equipoDTO);
                equiposImportados++;


            }

        } catch (Exception e) {
            // Manejar la excepción, por ejemplo, imprimir un mensaje de error
            e.printStackTrace();
        }
        System.out.println("Equipos importados: " + equiposImportados);
        System.out.println("Equipos en blanco o nulos: " + equiposEnBlancoONulos);

        return equipos;
    }




    }


