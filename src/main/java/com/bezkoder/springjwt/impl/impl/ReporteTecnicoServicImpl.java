package com.bezkoder.springjwt.impl.impl;

import com.bezkoder.springjwt.impl.service.IReporteTecnicoService;
import com.bezkoder.springjwt.models.OrdenTrabajo;
import com.bezkoder.springjwt.models.ReporteTecnico;
import com.bezkoder.springjwt.repository.ReporteTecnicoRepository;
import com.bezkoder.springjwt.util.generadorreporte.ReporteTecnicoReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class ReporteTecnicoServicImpl implements IReporteTecnicoService {
    @Autowired
    private ReporteTecnicoReportGenerator reporteTecnicoReportGenerator;
    @Autowired
    private ReporteTecnicoRepository reporteTecnicoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<ReporteTecnico> findAll() {
        return reporteTecnicoRepository.findAll();
    }

    @Override
    @Transactional
    public ReporteTecnico findById(Long idreptec) {
        return reporteTecnicoRepository.findById(idreptec).orElse(null);
    }

    @Override
    public ReporteTecnico save(ReporteTecnico reporteTecnico) {
        return reporteTecnicoRepository.save(reporteTecnico);
    }

    @Override
    public void deleteById(Long idreptec) {
        reporteTecnicoRepository.deleteById(idreptec);
    }

    @Override
    public ReporteTecnico createOrUpdate(ReporteTecnico reporteTecnico) {
        // Si el reporte tiene un ID válido, intenta recuperarlo
        ReporteTecnico existingReportetec = new ReporteTecnico();
        if (reporteTecnico.getIdreptec() != null) {
            existingReportetec = reporteTecnicoRepository.findById(reporteTecnico.getIdreptec()).orElse(null);
            if (existingReportetec != null) {
                // Actualiza los campos relevantes del EquipoCliente existente si es necesario
        /*        existingReportetec.setActivo(reporteTecnico.isActivo());//Desactivar reporte
                existingReportetec.setCliente(reporteTecnico.getCliente());
                existingReportetec.setEquipo(reporteTecnico.getEquipo());
                existingReportetec.setTecnico(reporteTecnico.getTecnico());
                existingReportetec.setNo_cotizacion(reporteTecnico.getNo_cotizacion());
                existingReportetec.setHoraentrada(reporteTecnico.getHoraentrada());
                existingReportetec.setHorasalida(reporteTecnico.getHorasalida());
                existingReportetec.setHoraviajes(reporteTecnico.getHoraviajes());
        existingReportetec.set(reporteTecnico.getFechareporte() );
        existingReportetec.set("contacto", reporteTecnico.getContacto() );
        existingReportetec.set("direccion", reporteTecnico.getDireccion());
        existingReportetec.set("marca", reporteTecnico.getMarca());
        existingReportetec.set("modelo", reporteTecnico.getModelo());
        existingReportetec.set("no_serie", reporteTecnico.getNo_serie());
        existingReportetec.set("ubicacion_equipo", reporteTecnico.getUbicacion_equipo() );
        existingReportetec.set("idinterno", reporteTecnico.getIdinterno());
        existingReportetec.set("capacidad", reporteTecnico.getCapacidad());
        existingReportetec.set("resolucion", reporteTecnico.getResolucion());
        existingReportetec.set("calibracion", reporteTecnico.isCalibracion());
        existingReportetec.set("instalacion", reporteTecnico.isInstalacion());
        existingReportetec.set("verificacion", reporteTecnico.isVerificacion());
        existingReportetec.set("entregaequipo", reporteTecnico.isEntregaequipo());
        existingReportetec.set("gestionmetrologica", reporteTecnico.isGestionmetrologica());
        existingReportetec.set("retiroequipo", reporteTecnico.isRetiroequipo());
        existingReportetec.set("inspeccion", reporteTecnico.isInspeccion());
        existingReportetec.set("otros", reporteTecnico.isOtros());
        existingReportetec.set("observaciones", reporteTecnico.getObservaciones());
        existingReportetec.set("desnivel", reporteTecnico.isDesnivel());
        existingReportetec.set("vibraciones", reporteTecnico.isVibraciones());
        existingReportetec.set("averias", reporteTecnico.isAverias());
        existingReportetec.set("erroresindicador", reporteTecnico.isErroresindicador());
        existingReportetec.set("soporteinadecuadas", reporteTecnico.isSoporteinadecuadas() );
        existingReportetec.setFaltacomponente(reporteTecnico.isFaltacomponente() );
        existingReportetec.setSuceidad(reporteTecnico.isSuceidad() );
        existingReportetec.setCorrienteaire(reporteTecnico.isCorrienteaire() );
        existingReportetec.setInsectos(reporteTecnico.isInsectos());
        existingReportetec.setGolpe(reporteTecnico.isGolpe());
        existingReportetec.setFuentexternacalor(reporteTecnico.isFuentexternacalor() );
        existingReportetec.setConfiguracion(reporteTecnico.isConfiguracion() );
        existingReportetec.setObservaciones2(reporteTecnico.getObservaciones2() );
        existingReportetec.setLimpieza(reporteTecnico.isLimpieza());
        existingReportetec.setAjusteslinealidad(reporteTecnico.isAjusteslinealidad() );
        existingReportetec.setConfiguracion1(reporteTecnico.isConfiguracion1());
        existingReportetec.setAjusteexcentricidad(reporteTecnico.isAjusteexcentricidad() );
        existingReportetec.setObservaciones3(reporteTecnico.getObservaciones3() );
        existingReportetec.setCompleto(reporteTecnico.isCompleto() );
        existingReportetec.setIncompleto(reporteTecnico.isIncompleto() );
        existingReportetec.setObservaciones4(reporteTecnico.getObservaciones4() );
        existingReportetec.setNota(reporteTecnico.getNota() );
        existingReportetec.setRecibidopor(reporteTecnico.getRecibidopor() );
        existingReportetec.setFecha(reporteTecnico.getFecha());*/
                // Actualiza otros campos según tus necesidades
                // ...

                // Guarda el reporte actualizado en la base de datos
                return reporteTecnicoRepository.save(existingReportetec);
            }
        }
// Si el reporte no tiene un ID válido o no existe, crea uno nuevo
        return reporteTecnicoRepository.save(reporteTecnico);
    }

   /* @Override
    public byte[] exportPdf(ReporteTecnico reporteTecnico) throws JRException, FileNotFoundException {
        return reporteTecnicoReportGenerator.exportToPdf(reporteTecnico);
    }*/
    public String generarCodigoReporteTecnico() {
        int numero = 0;
        String numeroConcatenado = "";
        List<ReporteTecnico> reporteTecnicos = findAll();
        List<Long> numeros = new ArrayList<Long>();

        reporteTecnicos.forEach(o -> numeros.add(o.getIdreptec()));

        if (reporteTecnicos.isEmpty()) {
            numero = 1;
        } else {
            Long maxId = numeros.stream().max(Long::compare).orElse(0L);
            numero = maxId.intValue() + 1;
        }

        if (numero <= 9) {
            numeroConcatenado = "RT-000" + numero;
        } else if (numero <= 99) {
            numeroConcatenado = "RT-00" + numero;
        } else if (numero <= 999) {
            numeroConcatenado = "RT-0" + numero;
        } else {
            numeroConcatenado = "RT-" + numero;
        }

        return numeroConcatenado;
    }
}
