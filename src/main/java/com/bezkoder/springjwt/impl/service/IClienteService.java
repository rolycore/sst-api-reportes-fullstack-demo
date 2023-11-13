package com.bezkoder.springjwt.impl.service;

import com.bezkoder.springjwt.DTO.ClienteDTO;
import com.bezkoder.springjwt.models.Cliente;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {
    List<Cliente> findAll(Pageable pageable);
    Cliente findById(Long idCliente);
    Cliente save(Cliente cliente);
    boolean delete(Long idCliente);

    // Agregamos un método para crear un nuevo cliente o recuperar uno existente por su identificador
    Cliente createOrUpdate(Cliente cliente);
    String generarCodigoCliente();
    public List<Cliente> todos();

    List<ClienteDTO> obtenerClientesDTO();
    void saveAll(List<Cliente> clientes);
}
