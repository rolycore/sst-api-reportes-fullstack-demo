package com.bezkoder.springjwt.models;


import com.bezkoder.springjwt.auditoria.modelo.Auditable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="equipocliente",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})

@EqualsAndHashCode(of = "idEquipo",callSuper = false)
public class EquipoCliente extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo ;
    @Column
    private String codigoequipo;
    @Column
    private String codigoequipocliente;
    @NotEmpty(message =" no puede estar vacio")
    @NotBlank(message =" no puede estar vacio")
    // @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
    @Column(nullable=false)
    private String nombre;
    // @NotEmpty(message =" no puede estar vacio")
    @NotBlank(message =" no puede estar vacio")
    // @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
    @Column(nullable=false)
    private String nombrecliente;
    // @NotEmpty(message =" no puede estar vacio")
    // @NotBlank(message =" no puede estar vacio")
    // @Size(min=4, max=12, message="el tamaño tiene que estar entre 4 y 12")
    @Column
    private String categoria_equipo;
    @NotBlank(message =" no puede estar vacio")
    private String marca;
    @NotBlank(message =" no puede estar vacio")
    private String modelo;
    @NotBlank(message =" no puede estar vacio")
    private String numero_serie;
    @Column
    private  String capacidad;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String capacidad_maxima ;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String capacidad_minima ;
    @Column
    private String resolucion;
    @Column
    private String divisiones ;
    @Column
    private String observaciones ;
    // Cambia el tipo de la propiedad imagen_equipo a byte[]
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imagen_equipo;
    @Column
    private String unidad_medida;
    @Column
    private String instrumento;
    @Column
    private String mide ;
    @Column(nullable=false)
    private String lista_precio;
    @Column
    private String cmc_equipo;
    @Column
    private String fabricante_receptor;
    @Column
    private String modelo_receptor ;
    @Column
    private String serie_receptor ;
    @Column
    private String id_interno_receptor ;
    @Column
    private String fabricante_sensor;
    @Column
    private String modelo_sensor ;
    @Column
    private String id_interno_sensor ;
    @Column
    private String serie_sensor;
    @Column
    private String fabricante_indicador ;
    @Column
    private String modelo_indicador;
    @Column
    private String serie_indicador;
    @Column
    private String id_interno_indicador;
    @Temporal(TemporalType.DATE)
    private Date createAt;
    @Column(columnDefinition = "boolean default true") // Valor predeterminado establecido en "true" por defecto
    private boolean activo= true;
    private static final long serialVersionUID= 1L;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private Set<ReporteTecnico> reporteTecnicos= new HashSet<>();
    @PrePersist
    public void prePersist() {
        createAt = new Date();


    }

    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodigoequipo() {
        return codigoequipo;
    }

    public void setCodigoequipo(String codigoequipo) {
        this.codigoequipo = codigoequipo;
    }

    public String getCodigoequipocliente() {
        return codigoequipocliente;
    }

    public void setCodigoequipocliente(String codigoequipocliente) {
        this.codigoequipocliente = codigoequipocliente;
        updateNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && codigoequipocliente != null) {
            if (!nombre.endsWith(" - " + codigoequipocliente)) {
                this.nombre = nombre + " - " + codigoequipocliente;
            } else {
                this.nombre = nombre;
            }
        } else {
            this.nombre = nombre;
        }
    }
    // Método para actualizar el nombre
    private void updateNombre() {
        if (nombre != null && codigoequipocliente != null) {
            if (!nombre.endsWith(" - " + codigoequipocliente)) {
                this.nombre = nombre + " - " + codigoequipocliente;
            }
        }
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getCategoria_equipo() {
        return categoria_equipo;
    }

    public void setCategoria_equipo(String categoria_equipo) {
        this.categoria_equipo = categoria_equipo;
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

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        if (capacidad != null) {
            if (unidad_medida != null && !capacidad.endsWith(" " + unidad_medida)) {
                this.capacidad = capacidad + " " + unidad_medida;
            } else {
                this.capacidad = capacidad;
            }
        }
    }

    public String getCapacidad_maxima() {
        return capacidad_maxima;
    }

    public void setCapacidad_maxima(String capacidad_maxima) {
        if (capacidad_maxima != null) {
            if (unidad_medida != null && !capacidad_maxima.endsWith(" " + unidad_medida)) {
                this.capacidad_maxima = capacidad_maxima + " " + unidad_medida;
            } else {
                this.capacidad_maxima = capacidad_maxima;
            }
        }
    }

    public String getCapacidad_minima() {
        return capacidad_minima;
    }

    public void setCapacidad_minima(String capacidad_minima) {
        if (capacidad_minima != null) {
            if (unidad_medida != null && !capacidad_minima.endsWith(" " + unidad_medida)) {
                this.capacidad_minima = capacidad_minima + " " + unidad_medida;
            } else {
                this.capacidad_minima = capacidad_minima;
            }
        }
    }
    /*public void concatenarUnidadMedida() {
        if (capacidad != null && unidad_medida != null) {
            capacidad = capacidad + " " + unidad_medida;
        }

        if (capacidad_maxima != null && unidad_medida != null) {
            capacidad_maxima = capacidad_maxima + " " + unidad_medida;
        }

        if (capacidad_minima != null && unidad_medida != null) {
            capacidad_minima = capacidad_minima + " " + unidad_medida;
        }
    }*/

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getDivisiones() {
        return divisiones;
    }

    public void setDivisiones(String divisiones) {
        this.divisiones = divisiones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public byte[] getImagen_equipo() {
        return imagen_equipo;
    }

    public void setImagen_equipo(byte[] imagen_equipo) {
        this.imagen_equipo = imagen_equipo;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getMide() {
        return mide;
    }

    public void setMide(String mide) {
        this.mide = mide;
    }

    public String getLista_precio() {
        return lista_precio;
    }

    public void setLista_precio(String lista_precio) {
        this.lista_precio = lista_precio;
    }

    public String getCmc_equipo() {
        return cmc_equipo;
    }

    public void setCmc_equipo(String cmc_equipo) {
        this.cmc_equipo = cmc_equipo;
    }

    public String getFabricante_receptor() {
        return fabricante_receptor;
    }

    public void setFabricante_receptor(String fabricante_receptor) {
        this.fabricante_receptor = fabricante_receptor;
    }

    public String getModelo_receptor() {
        return modelo_receptor;
    }

    public void setModelo_receptor(String modelo_receptor) {
        this.modelo_receptor = modelo_receptor;
    }

    public String getSerie_receptor() {
        return serie_receptor;
    }

    public void setSerie_receptor(String serie_receptor) {
        this.serie_receptor = serie_receptor;
    }

    public String getId_interno_receptor() {
        return id_interno_receptor;
    }

    public void setId_interno_receptor(String id_interno_receptor) {
        this.id_interno_receptor = id_interno_receptor;
    }

    public String getFabricante_sensor() {
        return fabricante_sensor;
    }

    public void setFabricante_sensor(String fabricante_sensor) {
        this.fabricante_sensor = fabricante_sensor;
    }

    public String getModelo_sensor() {
        return modelo_sensor;
    }

    public void setModelo_sensor(String modelo_sensor) {
        this.modelo_sensor = modelo_sensor;
    }

    public String getId_interno_sensor() {
        return id_interno_sensor;
    }

    public void setId_interno_sensor(String id_interno_sensor) {
        this.id_interno_sensor = id_interno_sensor;
    }

    public String getSerie_sensor() {
        return serie_sensor;
    }

    public void setSerie_sensor(String serie_sensor) {
        this.serie_sensor = serie_sensor;
    }

    public String getFabricante_indicador() {
        return fabricante_indicador;
    }

    public void setFabricante_indicador(String fabricante_indicador) {
        this.fabricante_indicador = fabricante_indicador;
    }

    public String getModelo_indicador() {
        return modelo_indicador;
    }

    public void setModelo_indicador(String modelo_indicador) {
        this.modelo_indicador = modelo_indicador;
    }

    public String getSerie_indicador() {
        return serie_indicador;
    }

    public void setSerie_indicador(String serie_indicador) {
        this.serie_indicador = serie_indicador;
    }

    public String getId_interno_indicador() {
        return id_interno_indicador;
    }

    public void setId_interno_indicador(String id_interno_indicador) {
        this.id_interno_indicador = id_interno_indicador;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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

    public Set<ReporteTecnico> getReporteTecnicos() {
        return reporteTecnicos;
    }

    public void setReporteTecnicos(Set<ReporteTecnico> reporteTecnicos) {
        this.reporteTecnicos = reporteTecnicos;
    }

    public EquipoCliente() {
    }

    public EquipoCliente(Long idEquipo, String codigoequipo, String codigoequipocliente, String nombre, String nombrecliente, String categoria_equipo, String marca, String modelo, String numero_serie, String capacidad, String capacidad_maxima, String capacidad_minima, String resolucion, String divisiones, String observaciones, byte[] imagen_equipo, String unidad_medida, String instrumento, String mide, String lista_precio, String cmc_equipo, String fabricante_receptor, String modelo_receptor, String serie_receptor, String id_interno_receptor, String fabricante_sensor, String modelo_sensor, String id_interno_sensor, String serie_sensor, String fabricante_indicador, String modelo_indicador, String serie_indicador, String id_interno_indicador, Date createAt, boolean activo, Cliente cliente, Set<ReporteTecnico> reporteTecnicos) {
        this.idEquipo = idEquipo;
        this.codigoequipo = codigoequipo;
        this.codigoequipocliente = codigoequipocliente;
        this.nombre = nombre;
        this.nombrecliente = nombrecliente;
        this.categoria_equipo = categoria_equipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numero_serie = numero_serie;
        this.capacidad = capacidad;
        this.capacidad_maxima = capacidad_maxima;
        this.capacidad_minima = capacidad_minima;
        this.resolucion = resolucion;
        this.divisiones = divisiones;
        this.observaciones = observaciones;
        this.imagen_equipo = imagen_equipo;
        this.unidad_medida = unidad_medida;
        this.instrumento = instrumento;
        this.mide = mide;
        this.lista_precio = lista_precio;
        this.cmc_equipo = cmc_equipo;
        this.fabricante_receptor = fabricante_receptor;
        this.modelo_receptor = modelo_receptor;
        this.serie_receptor = serie_receptor;
        this.id_interno_receptor = id_interno_receptor;
        this.fabricante_sensor = fabricante_sensor;
        this.modelo_sensor = modelo_sensor;
        this.id_interno_sensor = id_interno_sensor;
        this.serie_sensor = serie_sensor;
        this.fabricante_indicador = fabricante_indicador;
        this.modelo_indicador = modelo_indicador;
        this.serie_indicador = serie_indicador;
        this.id_interno_indicador = id_interno_indicador;
        this.createAt = createAt;
        this.activo = activo;
        this.cliente = cliente;
        this.reporteTecnicos = reporteTecnicos;
    }
}

