package com.bezkoder.springjwt.impl.service;

import com.bezkoder.springjwt.models.OrdenTrabajo;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface IOrdenesTrabajoService {
    List<OrdenTrabajo> findAll(Pageable pageable);
    @Transactional
    OrdenTrabajo findById(Long idOT);
    @Query("SELECT O FROM OrdenesTrabajo O WHERE O.idOT=:idOT")
   Optional <OrdenTrabajo> buscarporId(Long idOT);
    OrdenTrabajo save(OrdenTrabajo ordenTrabajo);
    @Transactional
    boolean delete(Long idOT);
    public List<OrdenTrabajo> todos();
    // Agregamos un método para crear un nuevo cliente o recuperar uno existente por su identificador
    OrdenTrabajo createOrUpdate(OrdenTrabajo ordenTrabajo);
    String generarCodigoOrdenTrabajo();
    byte[] exportOtPdf(OrdenTrabajo ordenTrabajo) throws JRException, FileNotFoundException;
    // byte[] exportXls(OrdenTrabajo ordenTrabajo) throws JRException, FileNotFoundException;
}
