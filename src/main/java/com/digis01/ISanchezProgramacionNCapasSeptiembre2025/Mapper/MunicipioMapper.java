package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.MunicipioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import org.springframework.stereotype.Component;

@Component
public class MunicipioMapper {

    public Municipio EntityToML(MunicipioJPA municipioJPA) {

        Municipio municipio = new Municipio();
        
        municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
        municipio.setNombreMunicipio(municipioJPA.getNombreMunicipio());

        Estado estado = new Estado();
        estado.setIdEstado(municipioJPA.EstadoJPA.getIdEstado());
        estado.setNombreEstado(municipioJPA.EstadoJPA.getNombreEstado());

        Pais pais = new Pais();
        pais.setIdPais(municipioJPA.EstadoJPA.PaisJPA.getIdPais());
        pais.setNombrePais(municipioJPA.EstadoJPA.PaisJPA.getNombrePais());

        estado.Pais = pais;
        municipio.Estado = estado;

        return municipio;
    }
}
