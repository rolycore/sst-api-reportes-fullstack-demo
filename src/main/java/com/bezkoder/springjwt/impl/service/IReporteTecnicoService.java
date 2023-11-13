package com.bezkoder.springjwt.impl.service;

import com.bezkoder.springjwt.models.ReporteTecnico;
import net.sf.jasperreports.engine.JRException;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

public interface IReporteTecnicoService {
    List<ReporteTecnico> findAll();
    @Transactional
    ReporteTecnico findById(Long id);
    ReporteTecnico save(ReporteTecnico reporteTecnico);
    void deleteById(Long id);

    ReporteTecnico createOrUpdate(ReporteTecnico reporteTecnico);
    String generarCodigoReporteTecnico();
    //byte[] exportPdf(ReporteTecnico reporteTecnico) throws JRException, FileNotFoundException;

}
