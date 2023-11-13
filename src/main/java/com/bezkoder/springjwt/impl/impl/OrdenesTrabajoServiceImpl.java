package com.bezkoder.springjwt.impl.impl;

import com.bezkoder.springjwt.impl.service.IOrdenesTrabajoService;
import com.bezkoder.springjwt.models.EquipoCliente;
import com.bezkoder.springjwt.models.OrdenTrabajo;
import com.bezkoder.springjwt.repository.OrdenesTrabajoRepository;
import com.bezkoder.springjwt.util.generadorreporte.OrdenTrabajoReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdenesTrabajoServiceImpl implements IOrdenesTrabajoService {
    @Autowired
    private OrdenTrabajoReportGenerator ordenTrabajoReportGenerator;

    @Autowired
    private OrdenesTrabajoRepository ordenesTrabajoRepository;
    @Override
    public List<OrdenTrabajo> todos(){
       return (List<OrdenTrabajo>) ordenesTrabajoRepository.findAll();
    }
    @Override

    public List<OrdenTrabajo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public OrdenTrabajo findById(Long idOT) {
        return ordenesTrabajoRepository.findById(idOT).orElse(null);
    }

  @Override
    public Optional<OrdenTrabajo> buscarporId(Long idOT) {
        return ordenesTrabajoRepository.findById(idOT);
    }

    @Override
    public OrdenTrabajo save(OrdenTrabajo ordenTrabajo) {
        return ordenesTrabajoRepository.save(ordenTrabajo);
    }

    @Override
    public boolean delete(Long idOT) {
        try {
            ordenesTrabajoRepository.deleteById(idOT);
            return true; // Devuelve true para indicar éxito
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public OrdenTrabajo createOrUpdate(OrdenTrabajo ordenTrabajo) {
        // Si el cliente tiene un ID válido, intenta recuperarlo
        if (ordenTrabajo.getIdOT() != null) {
            OrdenTrabajo existingOT = ordenesTrabajoRepository.findById(ordenTrabajo.getIdOT()).orElse(null);
            if (existingOT != null) {
                // Actualiza los campos relevantes del cliente existente si es necesario
                existingOT.setCliente(ordenTrabajo.getCliente());
                existingOT.setEquipo(ordenTrabajo.getEquipo());
                // Actualiza otros campos según tus necesidades
                // ...
            }
        }
        // Guarda el cliente actualizado en la base de datos
        return ordenesTrabajoRepository.save(ordenTrabajo);
    }

    @Override
    public byte[] exportOtPdf(OrdenTrabajo ordenTrabajo) throws JRException, FileNotFoundException {
        return ordenTrabajoReportGenerator.exportToPdf(ordenTrabajo.getIdOT());
    }
    public String generarCodigoOrdenTrabajo() {
        int numero = 0;
        String numeroConcatenado = "";
        List<OrdenTrabajo> ordenestrabajos = todos();
        List<Long> numeros = new ArrayList<Long>();

        ordenestrabajos.forEach(o -> numeros.add(o.getIdOT()));

        if (ordenestrabajos.isEmpty()) {
            numero = 1;
        } else {
            Long maxId = numeros.stream().max(Long::compare).orElse(0L);
            numero = maxId.intValue() + 1;
        }

        if (numero <= 9) {
            numeroConcatenado = "OT-000" + numero;
        } else if (numero <= 99) {
            numeroConcatenado = "OT-00" + numero;
        } else if (numero <= 999) {
            numeroConcatenado = "OT-0" + numero;
        } else {
            numeroConcatenado = "OT-" + numero;
        }

        return numeroConcatenado;
    }

}
