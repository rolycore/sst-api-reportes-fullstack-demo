package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.Cliente;

public class EquipoDTO {


    private String codigoequipo;

    private String nombre;
    private String nombrecliente;

    private String categoria_equipo;
    private String marca;

    private String modelo;

    private String numero_serie;

    private  String capacidad;


    private String capacidad_maxima ;

    private String capacidad_minima ;

    private String resolucion;

    private String divisiones ;

    private String observaciones ;

    private byte[] imagen_equipo;

    private String unidad_medida;

    private String instrumento;

    private String mide ;

    private String lista_precio;

    private String cmc_equipo;

    private String fabricante_receptor;

    private String modelo_receptor ;

    private String serie_receptor ;

    private String id_interno_receptor ;

    private String fabricante_sensor;

    private String modelo_sensor ;

    private String id_interno_sensor ;

    private String serie_sensor;

    private String fabricante_indicador ;

    private String modelo_indicador;

    private String serie_indicador;

    private String id_interno_indicador;
    private boolean activo;

    private Cliente cliente;

    public String getCodigoequipo() {
        return codigoequipo;
    }

    public void setCodigoequipo(String codigoequipo) {
        this.codigoequipo = codigoequipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        this.capacidad = capacidad;
    }

    public void setCategoria_equipo(String categoria_equipo) {
        this.categoria_equipo = categoria_equipo;
    }

    public String getCapacidad_maxima() {
        return capacidad_maxima;
    }

    public void setCapacidad_maxima(String capacidad_maxima) {
        this.capacidad_maxima = capacidad_maxima;
    }

    public String getCapacidad_minima() {
        return capacidad_minima;
    }

    public void setCapacidad_minima(String capacidad_minima) {
        this.capacidad_minima = capacidad_minima;
    }

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
}
