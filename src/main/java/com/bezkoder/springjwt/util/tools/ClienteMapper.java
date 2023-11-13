package com.bezkoder.springjwt.util.tools;

import com.bezkoder.springjwt.DTO.ClienteDTO;
import com.bezkoder.springjwt.models.Cliente;

public class ClienteMapper {

    public static ClienteDTO convertirClienteAClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        //clienteDTO.setId(cliente.getIdCliente());

        clienteDTO.setNombre(cliente.getNombre());
        //clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setActivo(cliente.isActivo());
        clienteDTO.setTelefono_empresa(cliente.getTelefono_empresa());
        clienteDTO.setCod_cliente(cliente.getCod_cliente());
        clienteDTO.setRazon_social(cliente.getRazon_social());
        clienteDTO.setNombre_comercial(cliente.getNombre_comercial());
        clienteDTO.setRuc(cliente.getRuc());
        clienteDTO.setDv(cliente.getDv());
        clienteDTO.setDireccion(cliente.getDireccion());
     //   clienteDTO.setTelefono_jefe(cliente.getTelefono_jefe());
       // clienteDTO.setCelular_jefe(cliente.getCelular_jefe());
      //  clienteDTO.setCorreo_electronico(cliente.getCorreo_electronico());
      //  clienteDTO.setActividad_economica(cliente.getActividad_economica());
        clienteDTO.setAbreviatura(cliente.getAbreviatura());
        clienteDTO.setNombre_contacto(cliente.getNombre_contacto());
        clienteDTO.setCargo_servicio(cliente.getCargo_servicio());
        clienteDTO.setCelular_servicio(cliente.getCelular_servicio());
        clienteDTO.setCorreo_servicio(cliente.getCorreo_servicio());
       // clienteDTO.setTelefono_servicio(cliente.getTelefono_servicio());
        clienteDTO.setNombre_cobro(cliente.getNombre_cobro());
        clienteDTO.setCargo_cobro(cliente.getCargo_cobro());
        clienteDTO.setTelefono_cobro(cliente.getTelefono_cobro());
        clienteDTO.setCelular_cobro(cliente.getCelular_cobro());
        clienteDTO.setCorreo_cobro(cliente.getCorreo_cobro());
        return clienteDTO;
    }
}