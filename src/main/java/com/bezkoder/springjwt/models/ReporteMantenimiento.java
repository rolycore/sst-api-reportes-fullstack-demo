package com.bezkoder.springjwt.models;
import com.bezkoder.springjwt.auditoria.modelo.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Entity
@Table(name="ReporteMantenimiento")

@EqualsAndHashCode(of = "idrepmant", callSuper = false)
public class ReporteMantenimiento extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idrepmant;
    @Column
    private String no_reporte;
    @Column
    private String nombrecliente;
    @Column
    private String nombreequipo;
    @Column
    private String tecnico;
    @Column

    private LocalTime horaentrada;
    @Column

    private LocalTime horasalida;
    @Column
    private String horaviajes;
    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date fechareporte;
    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date fecha;
    @Column
    private String contacto;
    @Column
    private String cargo;
    @Column
    private String direccion;
    @Column
    private String no_cotizacion;
    @Column(length = 2000)
    private String ubicacionequipo;
    @Column
    private String fabricanteindicador;
    @Column
    private String fabricantemarco;
    @Column
    private String fabricantetransductor;
    @Column
    private String modeloindicador;
    @Column
    private String modelomarco;
    @Column
    private String modelotransductor;
    @Column
    private String serieindicador;
    @Column
    private String seriemarco;
    @Column
    private String serietransductor;
    @Column
    private String capacidadindicador;
    @Column
    private String capacidadmarco;
    @Column
    private String capacidadtransductor;
    @Column
    private String notamantprevent;
    @Column
    private String notahallazgo;
    @Column
    private String recomendaciones;
    @Column
    private String imagen_1;
    @Column
    private String imagen_2;
    @Column
    private String imagen_3;
    @Column
    private String imagen_4;
    @Column
    private String imagen_5;
    @Column
    private String imagen_6;
    @Column
    private String imagen_7;
    @Column
    private String imagen_8;
    @Column
    private String imagen_9;
    @Column
    private String descripcion1;
    @Column
    private String descripcion2;
    @Column
    private String descripcion3;
    @Column
    private String descripcion4;
    @Column
    private String descripcion5;
    @Column
    private String descripcion6;
    @Column
    private String descripcion7;
    @Column
    private String descripcion8;
    @Column
    private String descripcion9;
    @Column
    private String rutaImagen1 ;
    @Column
    private String rutaImagen2 ;
    @Column
    private String rutaImagen3 ;
    @Column
    private String rutaImagen4 ;
    @Column
    private  String rutaImagen5 ;
    @Column
    private String rutaImagen6;
    @Column
    private String rutaImagen7 ;
    @Column
    private String rutaImagen8;
    @Column
    private String rutaImagen9;
    private static final long serialVersionUID= 1L;
    public String getHoraEntradaFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return horaentrada.format(formatter);
    }


    // Método para obtener la hora de salida en formato AM/PM
    public String getHoraSalidaFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return horasalida.format(formatter);
    }
    public LocalTime getHoraentrada() {
        return horaentrada;
    }

    public void setHoraentrada(LocalTime horaentrada) {
        this.horaentrada = horaentrada;
    }

    public LocalTime getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(LocalTime horasalida) {
        this.horasalida = horasalida;
    }

    public Long getIdrepmant() {
        return idrepmant;
    }

    public void setIdrepmant(Long idrepmant) {
        this.idrepmant = idrepmant;
    }

    public String getNo_reporte() {
        return no_reporte;
    }

    public void setNo_reporte(String no_reporte) {
        this.no_reporte = no_reporte;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getNombreequipo() {
        return nombreequipo;
    }

    public void setNombreequipo(String nombreequipo) {
        this.nombreequipo = nombreequipo;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getHoraviajes() {
        return horaviajes;
    }

    public void setHoraviajes(String horaviajes) {
        this.horaviajes = horaviajes;
    }

    public Date getFechareporte() {
        return fechareporte;
    }

    public void setFechareporte(String fechaStr) throws ParseException {
        // Reemplazar guiones con barras
        fechaStr = fechaStr.replace("-", "/");

        // Formatear la fecha en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaFormateada = dateFormat.parse(fechaStr);
        this.fechareporte = fechaFormateada;
    }

    public void setFechareporte(Date fechareporte) {
        this.fechareporte = fechareporte;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(String fechaStr) throws ParseException {
        // Reemplazar guiones con barras
        fechaStr = fechaStr.replace("-", "/");

        // Formatear la fecha en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaFormateada = dateFormat.parse(fechaStr);
        this.fecha = fechaFormateada;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNo_cotizacion() {
        return no_cotizacion;
    }

    public void setNo_cotizacion(String no_cotizacion) {
        this.no_cotizacion = no_cotizacion;
    }

    public String getUbicacionequipo() {
        return ubicacionequipo;
    }

    public void setUbicacionequipo(String ubicacionequipo) {
        this.ubicacionequipo = ubicacionequipo;
    }

    public String getFabricanteindicador() {
        return fabricanteindicador;
    }

    public void setFabricanteindicador(String fabricanteindicador) {
        this.fabricanteindicador = fabricanteindicador;
    }

    public String getFabricantemarco() {
        return fabricantemarco;
    }

    public void setFabricantemarco(String fabricantemarco) {
        this.fabricantemarco = fabricantemarco;
    }

    public String getFabricantetransductor() {
        return fabricantetransductor;
    }

    public void setFabricantetransductor(String fabricantetransductor) {
        this.fabricantetransductor = fabricantetransductor;
    }

    public String getModeloindicador() {
        return modeloindicador;
    }

    public void setModeloindicador(String modeloindicador) {
        this.modeloindicador = modeloindicador;
    }

    public String getModelomarco() {
        return modelomarco;
    }

    public void setModelomarco(String modelomarco) {
        this.modelomarco = modelomarco;
    }

    public String getModelotransductor() {
        return modelotransductor;
    }

    public void setModelotransductor(String modelotransductor) {
        this.modelotransductor = modelotransductor;
    }

    public String getSerieindicador() {
        return serieindicador;
    }

    public void setSerieindicador(String serieindicador) {
        this.serieindicador = serieindicador;
    }

    public String getSeriemarco() {
        return seriemarco;
    }

    public void setSeriemarco(String seriemarco) {
        this.seriemarco = seriemarco;
    }

    public String getSerietransductor() {
        return serietransductor;
    }

    public void setSerietransductor(String serietransductor) {
        this.serietransductor = serietransductor;
    }

    public String getCapacidadindicador() {
        return capacidadindicador;
    }

    public void setCapacidadindicador(String capacidadindicador) {
        this.capacidadindicador = capacidadindicador;
    }

    public String getCapacidadmarco() {
        return capacidadmarco;
    }

    public void setCapacidadmarco(String capacidadmarco) {
        this.capacidadmarco = capacidadmarco;
    }

    public String getCapacidadtransductor() {
        return capacidadtransductor;
    }

    public void setCapacidadtransductor(String capacidadtransductor) {
        this.capacidadtransductor = capacidadtransductor;
    }

    public String getNotamantprevent() {
        return notamantprevent;
    }

    public void setNotamantprevent(String notamantprevent) {
        this.notamantprevent = notamantprevent;
    }

    public String getNotahallazgo() {
        return notahallazgo;
    }

    public void setNotahallazgo(String notahallazgo) {
        this.notahallazgo = notahallazgo;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getImagen_1() {
        return imagen_1;
    }

    public void setImagen_1(String imagen_1) {
        this.imagen_1 = imagen_1;
    }

    public String getImagen_2() {
        return imagen_2;
    }

    public void setImagen_2(String imagen_2) {
        this.imagen_2 = imagen_2;
    }

    public String getImagen_3() {
        return imagen_3;
    }

    public void setImagen_3(String imagen_3) {
        this.imagen_3 = imagen_3;
    }

    public String getImagen_4() {
        return imagen_4;
    }

    public void setImagen_4(String imagen_4) {
        this.imagen_4 = imagen_4;
    }

    public String getImagen_5() {
        return imagen_5;
    }

    public void setImagen_5(String imagen_5) {
        this.imagen_5 = imagen_5;
    }

    public String getImagen_6() {
        return imagen_6;
    }

    public void setImagen_6(String imagen_6) {
        this.imagen_6 = imagen_6;
    }

    public String getImagen_7() {
        return imagen_7;
    }

    public void setImagen_7(String imagen_7) {
        this.imagen_7 = imagen_7;
    }

    public String getImagen_8() {
        return imagen_8;
    }

    public void setImagen_8(String imagen_8) {
        this.imagen_8 = imagen_8;
    }

    public String getImagen_9() {
        return imagen_9;
    }

    public void setImagen_9(String imagen_9) {
        this.imagen_9 = imagen_9;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public String getDescripcion4() {
        return descripcion4;
    }

    public void setDescripcion4(String descripcion4) {
        this.descripcion4 = descripcion4;
    }

    public String getDescripcion5() {
        return descripcion5;
    }

    public void setDescripcion5(String descripcion5) {
        this.descripcion5 = descripcion5;
    }

    public String getDescripcion6() {
        return descripcion6;
    }

    public void setDescripcion6(String descripcion6) {
        this.descripcion6 = descripcion6;
    }

    public String getDescripcion7() {
        return descripcion7;
    }

    public void setDescripcion7(String descripcion7) {
        this.descripcion7 = descripcion7;
    }

    public String getDescripcion8() {
        return descripcion8;
    }

    public void setDescripcion8(String descripcion8) {
        this.descripcion8 = descripcion8;
    }

    public String getDescripcion9() {
        return descripcion9;
    }

    public void setDescripcion9(String descripcion9) {
        this.descripcion9 = descripcion9;
    }

    public String getRutaImagen1() {
        return rutaImagen1;
    }

    public void setRutaImagen1(String rutaImagen1) {
        this.rutaImagen1 = rutaImagen1;
    }

    public String getRutaImagen2() {
        return rutaImagen2;
    }

    public void setRutaImagen2(String rutaImagen2) {
        this.rutaImagen2 = rutaImagen2;
    }

    public String getRutaImagen3() {
        return rutaImagen3;
    }

    public void setRutaImagen3(String rutaImagen3) {
        this.rutaImagen3 = rutaImagen3;
    }

    public String getRutaImagen4() {
        return rutaImagen4;
    }

    public void setRutaImagen4(String rutaImagen4) {
        this.rutaImagen4 = rutaImagen4;
    }

    public String getRutaImagen5() {
        return rutaImagen5;
    }

    public void setRutaImagen5(String rutaImagen5) {
        this.rutaImagen5 = rutaImagen5;
    }

    public String getRutaImagen6() {
        return rutaImagen6;
    }

    public void setRutaImagen6(String rutaImagen6) {
        this.rutaImagen6 = rutaImagen6;
    }

    public String getRutaImagen7() {
        return rutaImagen7;
    }

    public void setRutaImagen7(String rutaImagen7) {
        this.rutaImagen7 = rutaImagen7;
    }

    public String getRutaImagen8() {
        return rutaImagen8;
    }

    public void setRutaImagen8(String rutaImagen8) {
        this.rutaImagen8 = rutaImagen8;
    }

    public String getRutaImagen9() {
        return rutaImagen9;
    }

    public void setRutaImagen9(String rutaImagen9) {
        this.rutaImagen9 = rutaImagen9;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EquipoCliente getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoCliente equipo) {
        this.equipo = equipo;
    }

    public ReporteMantenimiento() {
    }

    public ReporteMantenimiento(Long idrepmant, String no_reporte, String nombrecliente, String nombreequipo, String tecnico, LocalTime horaentrada, LocalTime horasalida, String horaviajes, Date fechareporte, Date fecha, String contacto, String cargo, String direccion, String no_cotizacion, String ubicacionequipo, String fabricanteindicador, String fabricantemarco, String fabricantetransductor, String modeloindicador, String modelomarco, String modelotransductor, String serieindicador, String seriemarco, String serietransductor, String capacidadindicador, String capacidadmarco, String capacidadtransductor, String notamantprevent, String notahallazgo, String recomendaciones, String imagen_1, String imagen_2, String imagen_3, String imagen_4, String imagen_5, String imagen_6, String imagen_7, String imagen_8, String imagen_9, String descripcion1, String descripcion2, String descripcion3, String descripcion4, String descripcion5, String descripcion6, String descripcion7, String descripcion8, String descripcion9, Cliente cliente, EquipoCliente equipo) {
        this.idrepmant = idrepmant;
        this.no_reporte = no_reporte;
        this.nombrecliente = nombrecliente;
        this.nombreequipo = nombreequipo;
        this.tecnico = tecnico;
        this.horaentrada = horaentrada;
        this.horasalida = horasalida;
        this.horaviajes = horaviajes;
        this.fechareporte = fechareporte;
        this.fecha = fecha;
        this.contacto = contacto;
        this.cargo = cargo;
        this.direccion = direccion;
        this.no_cotizacion = no_cotizacion;
        this.ubicacionequipo = ubicacionequipo;
        this.fabricanteindicador = fabricanteindicador;
        this.fabricantemarco = fabricantemarco;
        this.fabricantetransductor = fabricantetransductor;
        this.modeloindicador = modeloindicador;
        this.modelomarco = modelomarco;
        this.modelotransductor = modelotransductor;
        this.serieindicador = serieindicador;
        this.seriemarco = seriemarco;
        this.serietransductor = serietransductor;
        this.capacidadindicador = capacidadindicador;
        this.capacidadmarco = capacidadmarco;
        this.capacidadtransductor = capacidadtransductor;
        this.notamantprevent = notamantprevent;
        this.notahallazgo = notahallazgo;
        this.recomendaciones = recomendaciones;
        this.imagen_1 = imagen_1;
        this.imagen_2 = imagen_2;
        this.imagen_3 = imagen_3;
        this.imagen_4 = imagen_4;
        this.imagen_5 = imagen_5;
        this.imagen_6 = imagen_6;
        this.imagen_7 = imagen_7;
        this.imagen_8 = imagen_8;
        this.imagen_9 = imagen_9;
        this.descripcion1 = descripcion1;
        this.descripcion2 = descripcion2;
        this.descripcion3 = descripcion3;
        this.descripcion4 = descripcion4;
        this.descripcion5 = descripcion5;
        this.descripcion6 = descripcion6;
        this.descripcion7 = descripcion7;
        this.descripcion8 = descripcion8;
        this.descripcion9 = descripcion9;
        this.cliente = cliente;
        this.equipo = equipo;
    }
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "idEquipo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EquipoCliente equipo;
}
