package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.ReporteTecnico;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ReporteTecnicoRepository extends JpaRepository<ReporteTecnico, Long> {

}
