package com.bezkoder.springjwt.impl.impl;

import com.bezkoder.springjwt.DTO.EquipoDTO;
import com.bezkoder.springjwt.impl.service.IEquipoClienteService;
import com.bezkoder.springjwt.models.EquipoCliente;
import com.bezkoder.springjwt.repository.EquipoClienteRepository;
import com.bezkoder.springjwt.util.tools.EquipoClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoClienteServiceImpl implements IEquipoClienteService {
    @Autowired
    private EquipoClienteRepository equipoClienteRepository;
    @Override
    @Transactional(readOnly = true)
    public List<EquipoCliente> findAll() {
        return (List<EquipoCliente>) equipoClienteRepository.findAll();
    }

    @Override
    @Transactional
    public EquipoCliente findById(Long idEquipo) {
        return equipoClienteRepository.findById(idEquipo).orElse(null);
    }

    @Override
    public List<EquipoCliente> findByIdCliente(Long idCliente) {
        return equipoClienteRepository.findByClienteIdCliente(idCliente);
    }

    @Override
    @Transactional
    public EquipoCliente save(EquipoCliente equipocliente) {
        return equipoClienteRepository.save(equipocliente);
    }

    @Override
    public boolean delete(Long idEquipo) {
        try {
            equipoClienteRepository.deleteById(idEquipo);
            return true; // Devuelve true para indicar éxito
        }catch (Exception e){
            return false;

        }


    }

    @Override
    public EquipoCliente createOrUpdate(EquipoCliente equipocliente) {
        // Si el EquipoCliente tiene un ID válido, intenta recuperarlo
        if (equipocliente.getIdEquipo() != null) {
            EquipoCliente existingEquipoCliente = equipoClienteRepository.findById(equipocliente.getIdEquipo()).orElse(null);
            if (existingEquipoCliente != null) {
                // Actualiza los campos relevantes del EquipoCliente existente si es necesario
                existingEquipoCliente.setNombre(equipocliente.getNombre());
                // Actualiza otros campos según tus necesidades
                // ...

                // Guarda el EquipoCliente actualizado en la base de datos
                return equipoClienteRepository.save(existingEquipoCliente);
            }
        }

        // Si el EquipoCliente no tiene un ID válido o no existe, crea uno nuevo
        return equipoClienteRepository.save(equipocliente);
    }
    public String generarCodigoEquipoCliente() {
        int numero = 0;
        String numeroConcatenado = "";
        List<EquipoCliente> equipoclientes = findAll();
        List<Long> numeros = new ArrayList<Long>();

        equipoclientes.forEach(equi -> numeros.add(equi.getIdEquipo()));

        if (equipoclientes.isEmpty()) {
            numero = 1;
        } else {
            Long maxId = numeros.stream().max(Long::compare).orElse(0L);
            numero = maxId.intValue() + 1;
        }

        if (numero <= 9) {
            numeroConcatenado = "CE-000" + numero;
        } else if (numero <= 99) {
            numeroConcatenado = "CE-00" + numero;
        } else if (numero <= 999) {
            numeroConcatenado = "CE-0" + numero;
        } else {
            numeroConcatenado = "CE-" + numero;
        }

        return numeroConcatenado;
    }

    public List<EquipoDTO> obtenerEquipoDTO() {
        List<EquipoCliente>equipos=equipoClienteRepository.findAll();
        // Convierte la lista de objetos Equipo en objetos EquipoDTO
        List<EquipoDTO> equipoDTOS = equipos.stream()
                .map(equipo -> EquipoClienteMapper.convertirEquipoAEquipoDTO(equipo))
                .collect(Collectors.toList());

        return equipoDTOS;
    }
    @Override
    public void saveAll(List<EquipoCliente> equipocliente) {
        equipoClienteRepository.saveAll(equipocliente);
    }


}
