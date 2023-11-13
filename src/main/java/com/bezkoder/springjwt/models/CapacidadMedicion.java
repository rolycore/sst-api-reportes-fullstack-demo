package com.bezkoder.springjwt.models;
import com.bezkoder.springjwt.auditoria.modelo.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="capacidadmedicion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CapacidadMedicion extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCmc;
    @Column
    @NotBlank(message =" no puede estar vacio")
    private String instrumento_calibrar;
    @Column
    private String magnitud;
    @Column
    private String mensurando;
    @Column
    private String metodo_calibracion;
    @Column
    private String unidad_medida;
    @Column
    private String patron_ref;
    @Column
    private String fuente_traza;
    @Column
    private String intervalo_minimo;
    @Column
    private String intervalo_maximo;
    @Column
    private String resolucion;
    @Column
    private String incertidumrbre_minimo;
    @Column
    private String incertidumbre_maximo;
    @Column
    private String parametros;
    @Column
    private String especificaciones;
}
