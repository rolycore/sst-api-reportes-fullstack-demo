package com.bezkoder.springjwt.models;

import com.bezkoder.springjwt.auditoria.modelo.Auditable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="OrdenTrabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idOT", callSuper = false)
public class OrdenTrabajo extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idOT;
    @Column
    private String no_ordent;
    @Column
    private String no_solicitud;
    @Column
    private String no_ordencompra;
    @Column
    private String no_cotizacion;
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha_entrega_certificado;
    @Column
    private String no_certificado;
    @Column
    private String no_reporte_tecnico;
    @Column
    private String metrologo_responsable;
    // Informacion general
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha_calibracion;
    @Column
    private boolean prox_calibracion=true;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String nombre_cliente;

    @NotBlank(message =" no puede estar vacio")
    @Column(nullable=false)
    private String nombre_equipo;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String direccion_cliente;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String usuario_final;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String lugar_calibracion;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String contacto_cliente;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String telefono_contacto;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String cargo;
    @NotEmpty(message =" no puede estar vacio")
    @NotBlank(message =" no puede estar vacio")
    @Email(message=" no es una dirección bien formada")
    @Column
    private String correo;
    @Column
    private String contacto_cargo;
    //recepcion tecnica del equipo
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String descripcion_recep;

    // datos indicador
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String fabricante_ind;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String modelo_ind;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String serie_ind;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String clase_ind;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String alcance;
    //datos receptor estructura
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String fabricande_rec;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String modelo_rec;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String serie_rec;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String resolucion;
    //datos celda sensor;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String fabricante_sen;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String modelo_sen;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String serie_sen;
    //detalles recepccion tecnica del equipo
    @Column
    private String observaciones;
    @Column
    private boolean rechazotecno=false;
    @Column
    private String motivo;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String nombre_entrega;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String cedula;
    // Datos Entrega del IBC y Certificado
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha_entrega;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String metrologo_entrega;
    @Column
    private String recibe_ibc;
    @Column
    private boolean entrego_con_ibc=false;
    @Column
    private String observaciones_entrega;
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha_entrega_ibc;
    @Column
    private boolean es_fecha_entrga;
    @Column
    private String persona_entrega_ibc;
    @Column
    private String recibe_certificado;
    @Column
    private String recibe_cedula;
    @Column
    private boolean activo=true;
    private static final long serialVersionUID= 1L;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "idEquipo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EquipoCliente equipo;


}
