package com.bezkoder.springjwt.impl.service;

import com.bezkoder.springjwt.models.CapacidadMedicion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICapacidadMedicionService {
    List<CapacidadMedicion> findAll();
    @Transactional
    CapacidadMedicion findById(Long idCmc);
    CapacidadMedicion save(CapacidadMedicion capacidadMedicion);
    @Transactional
    boolean delete(Long idCmc);
}
