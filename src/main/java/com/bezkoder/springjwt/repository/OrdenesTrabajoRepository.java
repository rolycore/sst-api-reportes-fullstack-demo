package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.OrdenTrabajo;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface OrdenesTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> {

   }
