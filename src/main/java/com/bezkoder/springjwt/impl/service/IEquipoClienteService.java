package com.bezkoder.springjwt.impl.service;

import com.bezkoder.springjwt.DTO.ClienteDTO;
import com.bezkoder.springjwt.DTO.EquipoDTO;
import com.bezkoder.springjwt.models.Cliente;
import com.bezkoder.springjwt.models.EquipoCliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IEquipoClienteService {
    List<EquipoCliente> findAll();


    @Transactional
    EquipoCliente findById(Long idEquipo);
    @Transactional
    List<EquipoCliente> findByIdCliente(Long idCliente);
    EquipoCliente save(EquipoCliente equipocliente);


    @Transactional
    boolean delete(Long idEquipo);

    // Agregamos un método para crear un nuevo EquipoCliente o actualizar uno existente
    EquipoCliente createOrUpdate(EquipoCliente equipocliente);
    String generarCodigoEquipoCliente();
    List<EquipoDTO> obtenerEquipoDTO();
    void saveAll(List<EquipoCliente> equipocliente);
}
