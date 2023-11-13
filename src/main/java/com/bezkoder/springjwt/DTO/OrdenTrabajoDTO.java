package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.Cliente;
import com.bezkoder.springjwt.models.EquipoCliente;

import java.util.Date;

public class OrdenTrabajoDTO {
    private Long idOT;

    private String no_ordent;

    private String no_solicitud;

    private String no_ordencompra;

    private String no_cotizacion;


    private Date fecha_entrega_certificado;

    private String no_certificado;

    private String no_reporte_tecnico;

    private String metrologo_responsable;

    private Date fecha_calibracion;

    private boolean prox_calibracion=true;

    private String nombre_cliente;

    private String direccion_cliente;

    private String usuario_final;


    private String lugar_calibracion;


    private String contacto_cliente;

    private String telefono_contacto;

    private String cargo;

    private String correo;

    private String contacto_cargo;
    //recepcion tecnica del equipo


    private String descripcion_recep;

    // datos indicador


    private String fabricante_ind;


    private String modelo_ind;


    private String serie_ind;


    private String clase_ind;


    private String alcance;
    //datos receptor estructura


    private String fabricande_rec;


    private String modelo_rec;


    private String serie_rec;


    private String resolucion;
    //datos celda sensor;

    private String fabricante_sen;


    private String modelo_sen;


    private String serie_sen;
    //detalles recepccion tecnica del equipo

    private String observaciones;

    private boolean rechazotecno;

    private String motivo;


    private String nombre_entrega;


    private String cedula;
    // Datos Entrega del IBC y Certificado


    private Date fecha_entrega;


    private String metrologo_entrega;

    private String recibe_ibc;

    private boolean entrego_con_ibc=true;

    private String observaciones_entrega;


    private Date fecha_entrega_ibc;

    private boolean es_fecha_entrga;

    private String persona_entrega_ibc;

    private String recibe_certificado;

    private String recibe_cedula;

    private boolean activo;


    private Cliente cliente;

    private EquipoCliente equipo;


}
