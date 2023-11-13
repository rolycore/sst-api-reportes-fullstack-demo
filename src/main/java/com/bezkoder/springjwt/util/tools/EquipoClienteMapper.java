package com.bezkoder.springjwt.util.tools;

import com.bezkoder.springjwt.DTO.EquipoDTO;
import com.bezkoder.springjwt.models.EquipoCliente;

public class EquipoClienteMapper {
    public static EquipoDTO convertirEquipoAEquipoDTO(EquipoCliente equipoCliente){
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setCliente(equipoCliente.getCliente());
        equipoDTO.setCodigoequipo(equipoCliente.getCodigoequipo());
        equipoDTO.setNombre(equipoCliente.getNombre());
        equipoDTO.setNombrecliente(equipoCliente.getNombrecliente());
        equipoDTO.setCategoria_equipo(equipoCliente.getCategoria_equipo());
        equipoDTO.setMarca(equipoCliente.getMarca());
        equipoDTO.setModelo(equipoCliente.getModelo());
        equipoDTO.setNumero_serie(equipoCliente.getNumero_serie());
        equipoDTO.setCapacidad(equipoCliente.getCapacidad());
        equipoDTO.setCapacidad_maxima(equipoCliente.getCapacidad_maxima());
        equipoDTO.setCapacidad_minima(equipoCliente.getCapacidad_minima());
        equipoDTO.setResolucion(equipoCliente.getResolucion());
        equipoDTO.setDivisiones(equipoCliente.getDivisiones());
        equipoDTO.setObservaciones(equipoCliente.getObservaciones());
        //equipoDTO.setImagen_equipo(equipoCliente.getImagen_equipo());
        equipoDTO.setUnidad_medida(equipoCliente.getUnidad_medida());
        equipoDTO.setInstrumento(equipoCliente.getInstrumento());
        equipoDTO.setMide(equipoCliente.getMide());
        equipoDTO.setLista_precio(equipoCliente.getLista_precio());
        equipoDTO.setCmc_equipo(equipoCliente.getCmc_equipo());
        equipoDTO.setFabricante_receptor(equipoCliente.getFabricante_receptor());
        equipoDTO.setModelo_receptor(equipoCliente.getModelo_receptor());
        equipoDTO.setSerie_receptor(equipoCliente.getSerie_receptor());
        equipoDTO.setId_interno_receptor(equipoCliente.getId_interno_receptor());
        equipoDTO.setFabricante_sensor(equipoCliente.getFabricante_sensor());
        equipoDTO.setModelo_sensor(equipoCliente.getModelo_sensor());
        equipoDTO.setSerie_sensor(equipoCliente.getSerie_sensor());
        equipoDTO.setId_interno_sensor(equipoCliente.getId_interno_sensor());
        equipoDTO.setFabricante_indicador(equipoCliente.getFabricante_indicador());
        equipoDTO.setModelo_indicador(equipoCliente.getModelo_indicador());
        equipoDTO.setSerie_indicador(equipoCliente.getSerie_indicador());
        equipoDTO.setId_interno_indicador(equipoCliente.getId_interno_indicador());
        equipoDTO.setActivo(equipoCliente.isActivo());

        return equipoDTO;

    }
}
