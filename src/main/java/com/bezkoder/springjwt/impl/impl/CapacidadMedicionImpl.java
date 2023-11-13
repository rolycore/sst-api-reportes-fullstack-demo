package com.bezkoder.springjwt.impl.impl;

import com.bezkoder.springjwt.impl.service.ICapacidadMedicionService;
import com.bezkoder.springjwt.models.CapacidadMedicion;
import com.bezkoder.springjwt.repository.CapacidadMedicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacidadMedicionImpl implements ICapacidadMedicionService {
    @Autowired
    private CapacidadMedicionRepository capacidadMedicionRepository;

    @Override
    public List<CapacidadMedicion> findAll() {
        return (List<CapacidadMedicion>)capacidadMedicionRepository.findAll();
    }

    @Override
    public CapacidadMedicion findById(Long idCmc) {
        return capacidadMedicionRepository.findById(idCmc).orElse(null);
    }

    @Override
    public CapacidadMedicion save(CapacidadMedicion capacidadMedicion) {
        return capacidadMedicionRepository.save(capacidadMedicion);
    }

    @Override
    public boolean delete(Long idCmc) {
        try {
            capacidadMedicionRepository.deleteById(idCmc);
            return true; // Devuelve true para indicar éxito
        }catch (Exception e){
            return false;

        }
    }
}
