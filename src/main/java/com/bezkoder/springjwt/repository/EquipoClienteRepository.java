package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.EquipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipoClienteRepository extends JpaRepository<EquipoCliente, Long> {


    List<EquipoCliente> findByClienteIdCliente(Long idCliente);
}
