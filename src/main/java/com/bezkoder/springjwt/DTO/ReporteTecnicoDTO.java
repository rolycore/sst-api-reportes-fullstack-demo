package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.Cliente;
import com.bezkoder.springjwt.models.EquipoCliente;

import java.time.LocalTime;
import java.util.Date;

public class ReporteTecnicoDTO {
    private Long idreptec;

    private String no_reporte_tecnico;


    private String no_cotizacion;

    private String tecnico;


    private LocalTime horaentrada;


    private LocalTime horasalida;

    private String horaviajes;

    private Date fechareporte;

    private String contacto;

    private String direccion;
    // 1	DATOS TÉCNICOS DEL INSTRUMENTO

    private String marca;

    private String modelo;

    private String no_serie;

    private String ubicacion_equipo;

    private String idinterno;

    private String capacidad;

    private String resolucion;
//2	SERVICIO A REALIZAR


    private boolean calibracion;

    private boolean instalacion;

    private boolean verificacion;

    private boolean entregaequipo;

    private boolean gestionmetrologica;

    private boolean retiroequipo;

    private boolean inspeccion;

    private boolean otros;

    private String observaciones;

    //3	DEFICIENCIAS ENCONTRADAS.

    private boolean desnivel;

    private boolean vibraciones;

    private boolean averias;

    private boolean erroresindicador;

    private boolean soporteinadecuadas;

    private boolean faltacomponente;

    private boolean suceidad;

    private boolean corrienteaire;

    private boolean insectos;

    private boolean golpe;

    private boolean fuentexternacalor;

    private boolean configuracion;

    private String observaciones2;

    //4	OTROS TRABAJOS REALIZADOS.

    private boolean nivelacion;

    private boolean limpieza;

    private boolean ajusteslinealidad;

    private boolean configuracion1;

    private boolean ajusteexcentricidad;

    private boolean reemplazo;

    private String observaciones3;
    //5	ESTATUS FINAL DEL SERVICIO.

    private boolean completo;

    private boolean incompleto;

    private String observaciones4;

    //6	OBSERVACIONES DEL CLIENTE

    private String nota;

    private String recibidopor;


    private Date fecha;

    private boolean activo;




    private Cliente cliente;

    private EquipoCliente equipo;
    private byte[] imagen_1;
    private String descripcion_1;
    private byte[] imagen_2;
    private String descripcion_2;
    private byte[] imagen_3;
    private String descripcion_3;
    private byte[] imagen_4;
    private String descripcion_4;

    public Long getIdreptec() {
        return idreptec;
    }

    public void setIdreptec(Long idreptec) {
        this.idreptec = idreptec;
    }

    public String getNo_reporte_tecnico() {
        return no_reporte_tecnico;
    }

    public void setNo_reporte_tecnico(String no_reporte_tecnico) {
        this.no_reporte_tecnico = no_reporte_tecnico;
    }

    public byte[] getImagen_1() {
        return imagen_1;
    }

    public void setImagen_1(byte[] imagen_1) {
        this.imagen_1 = imagen_1;
    }

    public String getDescripcion_1() {
        return descripcion_1;
    }

    public void setDescripcion_1(String descripcion_1) {
        this.descripcion_1 = descripcion_1;
    }

    public byte[] getImagen_2() {
        return imagen_2;
    }

    public void setImagen_2(byte[] imagen_2) {
        this.imagen_2 = imagen_2;
    }

    public String getDescripcion_2() {
        return descripcion_2;
    }

    public void setDescripcion_2(String descripcion_2) {
        this.descripcion_2 = descripcion_2;
    }

    public byte[] getImagen_3() {
        return imagen_3;
    }

    public void setImagen_3(byte[] imagen_3) {
        this.imagen_3 = imagen_3;
    }

    public String getDescripcion_3() {
        return descripcion_3;
    }

    public void setDescripcion_3(String descripcion_3) {
        this.descripcion_3 = descripcion_3;
    }

    public byte[] getImagen_4() {
        return imagen_4;
    }

    public void setImagen_4(byte[] imagen_4) {
        this.imagen_4 = imagen_4;
    }

    public String getDescripcion_4() {
        return descripcion_4;
    }

    public void setDescripcion_4(String descripcion_4) {
        this.descripcion_4 = descripcion_4;
    }
}
