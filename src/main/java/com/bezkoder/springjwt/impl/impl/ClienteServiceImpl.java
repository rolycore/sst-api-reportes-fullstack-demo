package com.bezkoder.springjwt.impl.impl;

import com.bezkoder.springjwt.DTO.ClienteDTO;
import com.bezkoder.springjwt.impl.service.IClienteService;
import com.bezkoder.springjwt.models.Cliente;
import com.bezkoder.springjwt.repository.ClienteRepository;
import com.bezkoder.springjwt.util.tools.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements IClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    public List<Cliente> todos(){
        return clienteRepository.findAll();
    }
    @Override
    public List<Cliente> findAll(Pageable pageable) {
        return (List<Cliente>) clienteRepository.findAll(pageable);
    }

    @Override
    public Cliente findById(Long idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public boolean delete(Long idCliente) {
        try {
            clienteRepository.deleteById(idCliente);// Esto eliminará el cliente por ID si existe
            return true;// Devuelve true para indicar éxito
        }catch (Exception e) {
            // En caso de error, puedes registrar el error o realizar otro manejo
            return false;// Devuelve false para indicar que no se pudo eliminar
        }
    }

    @Override
    public Cliente createOrUpdate(Cliente cliente) {
        // Si el cliente tiene un ID válido, intenta recuperarlo

        if (cliente.getIdCliente() != null) {
            Cliente existingCliente = clienteRepository.findById(cliente.getIdCliente()).orElse(null);

            if (existingCliente != null) {
                // Actualiza los campos relevantes del cliente existente si es necesario
                existingCliente.setNombre(cliente.getNombre());
                // Actualiza otros campos según tus necesidades
                // ...

                // Guarda el cliente actualizado en la base de datos
                return clienteRepository.save(existingCliente);
            }
        }
        // Si el cliente no tiene un ID válido o no existe, crea uno nuevo
        return  clienteRepository.save(cliente);
    }
    public String generarCodigoCliente() {
        int numero = 0;
        String numeroConcatenado = "";
        List<Cliente> clientes = todos();
        List<Long> numeros = new ArrayList<Long>();

        clientes.forEach(cli -> numeros.add(cli.getIdCliente()));

        if (clientes.isEmpty()) {
            numero = 1;
        } else {
            Long maxId = numeros.stream().max(Long::compare).orElse(0L);
            numero = maxId.intValue() + 1;
        }

        if (numero <= 9) {
            numeroConcatenado = "CC-000" + numero;
        } else if (numero <= 99) {
            numeroConcatenado = "CC-00" + numero;
        } else if (numero <= 999) {
            numeroConcatenado = "CC-0" + numero;
        } else {
            numeroConcatenado = "CC-" + numero;
        }

        return numeroConcatenado;
    }
    public List<ClienteDTO> obtenerClientesDTO() {
        List<Cliente> clientes = clienteRepository.findAll();

        // Convierte la lista de objetos Cliente en objetos ClienteDTO
        List<ClienteDTO> clienteDTOs = clientes.stream()
                .map(cliente -> ClienteMapper.convertirClienteAClienteDTO(cliente))
                .collect(Collectors.toList());

        return clienteDTOs;
    }

    @Override
    public void saveAll(List<Cliente> clientes) {
        // Implementa la lógica para guardar la lista de clientes en la base de datos
        clienteRepository.saveAll(clientes);
    }

}
