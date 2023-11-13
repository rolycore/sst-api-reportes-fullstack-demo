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
@Table(name="ReporteTecnico")

@EqualsAndHashCode(of = "idreptec", callSuper = false)
public class ReporteTecnico extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idreptec;
    @Column
    private String no_reporte_tecnico;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String no_cotizacion;
    @NotBlank(message =" no puede estar vacio")
    // @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
    @Column(nullable=false)
    private String nombrecliente;
    @NotBlank(message =" no puede estar vacio")
    // @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
    @Column(nullable=false)
    private String nombreequipo;
    @Column
    @NotBlank(message =" no puede estar vacio")
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
    private String contacto;
    @Column
    private String direccion;
    // 1	DATOS TÉCNICOS DEL INSTRUMENTO
    @Column
    private String marca;
    @Column
    private String modelo;
    @Column
    private String no_serie;
    @Column
    private String ubicacion_equipo;
    @Column
    private String idinterno;
    @Column
    private String capacidad;
    @Column
    private String resolucion;
//2	SERVICIO A REALIZAR

    @Column
    private boolean calibracion;
    @Column
    private boolean instalacion;
    @Column
    private boolean verificacion;
    @Column
    private boolean entregaequipo;
    @Column
    private boolean gestionmetrologica;
    @Column
    private boolean retiroequipo;
    @Column
    private boolean inspeccion;
    @Column
    private boolean otros;
    @Column
    private String observaciones;

    //3	DEFICIENCIAS ENCONTRADAS.
    @Column
    private boolean desnivel;
    @Column
    private boolean vibraciones;
    @Column
    private boolean averias;
    @Column
    private boolean erroresindicador;
    @Column
    private boolean soporteinadecuadas;
    @Column
    private boolean faltacomponente;
    @Column
    private boolean suceidad;
    @Column
    private boolean corrienteaire;
    @Column
    private boolean insectos;
    @Column
    private boolean golpe;
    @Column
    private boolean fuentexternacalor;
    @Column
    private boolean configuracion;
    @Column
    private String observaciones2;

    //4	OTROS TRABAJOS REALIZADOS.
    @Column
    private boolean nivelacion;
    @Column
    private boolean limpieza;
    @Column
    private boolean ajusteslinealidad;
    @Column
    private boolean configuracion1;
    @Column
    private boolean ajusteexcentricidad;
    @Column
    private boolean reemplazo;
    @Column
    private String observaciones3;
    //5	ESTATUS FINAL DEL SERVICIO.
    @Column
    private boolean completo;
    @Column
    private boolean incompleto;
    @Column
    private String observaciones4;

    //6	OBSERVACIONES DEL CLIENTE
    @Column
    private String nota;
    @Column
    private String recibidopor;
    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date fecha;
    @Column
    private boolean activo=true;

    @Column
    private String descripcion_1;

    @Column
    private String descripcion_2;

    @Column
    private String descripcion_3;

    @Column
    private String descripcion_4;
    @Column
    private  String rutaImagen1 ;
    @Column
    private String rutaImagen2 ;
    @Column
    private String rutaImagen3 ;
    @Column
    private String rutaImagen4 ;

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


/*    @PrePersist
    public void calcularHorasDeViaje() {
        if (horaentrada != null && horasalida != null) {
            // Verificar que horasalida no sea menor que horaentrada
            if (horasalida.isBefore(horaentrada)) {
                throw new IllegalArgumentException("La hora de salida no puede ser menor que la hora de entrada.");
            }

            long horas = horaentrada.until(horasalida, ChronoUnit.HOURS);
            long minutos = horaentrada.until(horasalida, ChronoUnit.MINUTES) % 60;

            horaviajes = String.format("%02d:%02d", horas, minutos);
        } else {
            horaviajes = null;
        }
    }*/

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

    public String getNo_cotizacion() {
        return no_cotizacion;
    }

    public void setNo_cotizacion(String no_cotizacion) {
        this.no_cotizacion = no_cotizacion;
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

    public void setFechareporte(String fechaStr) throws ParseException {
        // Reemplazar guiones con barras
        fechaStr = fechaStr.replace("-", "/");

        // Formatear la fecha en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaFormateada = dateFormat.parse(fechaStr);
        this.fechareporte = fechaFormateada;
    }

    public Date getFechareporte() {
        return fechareporte;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNo_serie() {
        return no_serie;
    }

    public void setNo_serie(String no_serie) {
        this.no_serie = no_serie;
    }

    public String getUbicacion_equipo() {
        return ubicacion_equipo;
    }

    public void setUbicacion_equipo(String ubicacion_equipo) {
        this.ubicacion_equipo = ubicacion_equipo;
    }

    public String getIdinterno() {
        return idinterno;
    }

    public void setIdinterno(String idinterno) {
        this.idinterno = idinterno;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public boolean isCalibracion() {
        return calibracion;
    }

    public void setCalibracion(boolean calibracion) {
        this.calibracion = calibracion;
    }

    public boolean isInstalacion() {
        return instalacion;
    }

    public void setInstalacion(boolean instalacion) {
        this.instalacion = instalacion;
    }

    public boolean isVerificacion() {
        return verificacion;
    }

    public void setVerificacion(boolean verificacion) {
        this.verificacion = verificacion;
    }

    public boolean isEntregaequipo() {
        return entregaequipo;
    }

    public void setEntregaequipo(boolean entregaequipo) {
        this.entregaequipo = entregaequipo;
    }

    public boolean isGestionmetrologica() {
        return gestionmetrologica;
    }

    public void setGestionmetrologica(boolean gestionmetrologica) {
        this.gestionmetrologica = gestionmetrologica;
    }

    public boolean isRetiroequipo() {
        return retiroequipo;
    }

    public void setRetiroequipo(boolean retiroequipo) {
        this.retiroequipo = retiroequipo;
    }

    public boolean isInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(boolean inspeccion) {
        this.inspeccion = inspeccion;
    }

    public boolean isOtros() {
        return otros;
    }

    public void setOtros(boolean otros) {
        this.otros = otros;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isDesnivel() {
        return desnivel;
    }

    public void setDesnivel(boolean desnivel) {
        this.desnivel = desnivel;
    }

    public boolean isVibraciones() {
        return vibraciones;
    }

    public void setVibraciones(boolean vibraciones) {
        this.vibraciones = vibraciones;
    }

    public boolean isAverias() {
        return averias;
    }

    public void setAverias(boolean averias) {
        this.averias = averias;
    }

    public boolean isErroresindicador() {
        return erroresindicador;
    }

    public void setErroresindicador(boolean erroresindicador) {
        this.erroresindicador = erroresindicador;
    }

    public boolean isSoporteinadecuadas() {
        return soporteinadecuadas;
    }

    public void setSoporteinadecuadas(boolean soporteinadecuadas) {
        this.soporteinadecuadas = soporteinadecuadas;
    }

    public boolean isFaltacomponente() {
        return faltacomponente;
    }

    public void setFaltacomponente(boolean faltacomponente) {
        this.faltacomponente = faltacomponente;
    }

    public boolean isSuceidad() {
        return suceidad;
    }

    public void setSuceidad(boolean suceidad) {
        this.suceidad = suceidad;
    }

    public boolean isCorrienteaire() {
        return corrienteaire;
    }

    public void setCorrienteaire(boolean corrienteaire) {
        this.corrienteaire = corrienteaire;
    }

    public boolean isInsectos() {
        return insectos;
    }

    public void setInsectos(boolean insectos) {
        this.insectos = insectos;
    }

    public boolean isGolpe() {
        return golpe;
    }

    public void setGolpe(boolean golpe) {
        this.golpe = golpe;
    }

    public boolean isFuentexternacalor() {
        return fuentexternacalor;
    }

    public void setFuentexternacalor(boolean fuentexternacalor) {
        this.fuentexternacalor = fuentexternacalor;
    }

    public boolean isConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(boolean configuracion) {
        this.configuracion = configuracion;
    }

    public String getObservaciones2() {
        return observaciones2;
    }

    public void setObservaciones2(String observaciones2) {
        this.observaciones2 = observaciones2;
    }

    public boolean isNivelacion() {
        return nivelacion;
    }

    public void setNivelacion(boolean nivelacion) {
        this.nivelacion = nivelacion;
    }

    public boolean isLimpieza() {
        return limpieza;
    }

    public void setLimpieza(boolean limpieza) {
        this.limpieza = limpieza;
    }

    public boolean isAjusteslinealidad() {
        return ajusteslinealidad;
    }

    public void setAjusteslinealidad(boolean ajusteslinealidad) {
        this.ajusteslinealidad = ajusteslinealidad;
    }

    public boolean isConfiguracion1() {
        return configuracion1;
    }

    public void setConfiguracion1(boolean configuracion1) {
        this.configuracion1 = configuracion1;
    }

    public boolean isAjusteexcentricidad() {
        return ajusteexcentricidad;
    }

    public void setAjusteexcentricidad(boolean ajusteexcentricidad) {
        this.ajusteexcentricidad = ajusteexcentricidad;
    }

    public boolean isReemplazo() {
        return reemplazo;
    }

    public void setReemplazo(boolean reemplazo) {
        this.reemplazo = reemplazo;
    }

    public String getObservaciones3() {
        return observaciones3;
    }

    public void setObservaciones3(String observaciones3) {
        this.observaciones3 = observaciones3;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public boolean isIncompleto() {
        return incompleto;
    }

    public void setIncompleto(boolean incompleto) {
        this.incompleto = incompleto;
    }

    public String getObservaciones4() {
        return observaciones4;
    }

    public void setObservaciones4(String observaciones4) {
        this.observaciones4 = observaciones4;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getRecibidopor() {
        return recibidopor;
    }

    public void setRecibidopor(String recibidopor) {
        this.recibidopor = recibidopor;
    }

    public void setFechareporte(Date fechareporte) {
        this.fechareporte = fechareporte;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }



    public String getDescripcion_1() {
        return descripcion_1;
    }

    public void setDescripcion_1(String descripcion_1) {
        this.descripcion_1 = descripcion_1;
    }



    public String getDescripcion_2() {
        return descripcion_2;
    }

    public void setDescripcion_2(String descripcion_2) {
        this.descripcion_2 = descripcion_2;
    }


    public String getDescripcion_3() {
        return descripcion_3;
    }

    public void setDescripcion_3(String descripcion_3) {
        this.descripcion_3 = descripcion_3;
    }



    public String getDescripcion_4() {
        return descripcion_4;
    }

    public void setDescripcion_4(String descripcion_4) {
        this.descripcion_4 = descripcion_4;
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

    public void setFecha(String fechaStr) throws ParseException {
        // Reemplazar guiones con barras
        fechaStr = fechaStr.replace("-", "/");

        // Formatear la fecha en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaFormateada = dateFormat.parse(fechaStr);
        this.fecha = fechaFormateada;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public ReporteTecnico() {
    }

    public ReporteTecnico(Long idreptec, String no_reporte_tecnico, String no_cotizacion, String nombrecliente, String nombreequipo, String tecnico, LocalTime horaentrada, LocalTime horasalida, String horaviajes, Date fechareporte, String contacto, String direccion, String marca, String modelo, String no_serie, String ubicacion_equipo, String idinterno, String capacidad, String resolucion, boolean calibracion, boolean instalacion, boolean verificacion, boolean entregaequipo, boolean gestionmetrologica, boolean retiroequipo, boolean inspeccion, boolean otros, String observaciones, boolean desnivel, boolean vibraciones, boolean averias, boolean erroresindicador, boolean soporteinadecuadas, boolean faltacomponente, boolean suceidad, boolean corrienteaire, boolean insectos, boolean golpe, boolean fuentexternacalor, boolean configuracion, String observaciones2, boolean nivelacion, boolean limpieza, boolean ajusteslinealidad, boolean configuracion1, boolean ajusteexcentricidad, boolean reemplazo, String observaciones3, boolean completo, boolean incompleto, String observaciones4, String nota, String recibidopor, Date fecha, boolean activo, byte[] imagen_1, String descripcion_1, byte[] imagen_2, String descripcion_2, byte[] imagen_3, String descripcion_3, byte[] imagen_4, String descripcion_4, String rutaImagen1, String rutaImagen2, String rutaImagen3, String rutaImagen4, Cliente cliente, EquipoCliente equipo) {
        this.idreptec = idreptec;
        this.no_reporte_tecnico = no_reporte_tecnico;
        this.no_cotizacion = no_cotizacion;
        this.nombrecliente = nombrecliente;
        this.nombreequipo = nombreequipo;
        this.tecnico = tecnico;
        this.horaentrada = horaentrada;
        this.horasalida = horasalida;
        this.horaviajes = horaviajes;
        this.fechareporte = fechareporte;
        this.contacto = contacto;
        this.direccion = direccion;
        this.marca = marca;
        this.modelo = modelo;
        this.no_serie = no_serie;
        this.ubicacion_equipo = ubicacion_equipo;
        this.idinterno = idinterno;
        this.capacidad = capacidad;
        this.resolucion = resolucion;
        this.calibracion = calibracion;
        this.instalacion = instalacion;
        this.verificacion = verificacion;
        this.entregaequipo = entregaequipo;
        this.gestionmetrologica = gestionmetrologica;
        this.retiroequipo = retiroequipo;
        this.inspeccion = inspeccion;
        this.otros = otros;
        this.observaciones = observaciones;
        this.desnivel = desnivel;
        this.vibraciones = vibraciones;
        this.averias = averias;
        this.erroresindicador = erroresindicador;
        this.soporteinadecuadas = soporteinadecuadas;
        this.faltacomponente = faltacomponente;
        this.suceidad = suceidad;
        this.corrienteaire = corrienteaire;
        this.insectos = insectos;
        this.golpe = golpe;
        this.fuentexternacalor = fuentexternacalor;
        this.configuracion = configuracion;
        this.observaciones2 = observaciones2;
        this.nivelacion = nivelacion;
        this.limpieza = limpieza;
        this.ajusteslinealidad = ajusteslinealidad;
        this.configuracion1 = configuracion1;
        this.ajusteexcentricidad = ajusteexcentricidad;
        this.reemplazo = reemplazo;
        this.observaciones3 = observaciones3;
        this.completo = completo;
        this.incompleto = incompleto;
        this.observaciones4 = observaciones4;
        this.nota = nota;
        this.recibidopor = recibidopor;
        this.fecha = fecha;
        this.activo = activo;

        this.descripcion_1 = descripcion_1;

        this.descripcion_2 = descripcion_2;

        this.descripcion_3 = descripcion_3;

        this.descripcion_4 = descripcion_4;
        this.rutaImagen1 = rutaImagen1;
        this.rutaImagen2 = rutaImagen2;
        this.rutaImagen3 = rutaImagen3;
        this.rutaImagen4 = rutaImagen4;
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
