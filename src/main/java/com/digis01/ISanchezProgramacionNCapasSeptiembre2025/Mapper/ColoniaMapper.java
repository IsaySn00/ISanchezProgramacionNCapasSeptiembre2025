package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import org.springframework.stereotype.Component;

@Component
public class ColoniaMapper {

    public Colonia EntityToML(ColoniaJPA coloniaJPA) {

        Colonia colonia = new Colonia();
        
        colonia.setIdColonia(coloniaJPA.getIdColonia());
        colonia.setNombreColonia(coloniaJPA.getNombreColonia());
        colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(coloniaJPA.MunicipioJPA.getIdMunicipio());
        municipio.setNombreMunicipio(coloniaJPA.MunicipioJPA.getNombreMunicipio());

        Estado estado = new Estado();
        estado.setIdEstado(coloniaJPA.MunicipioJPA.EstadoJPA.getIdEstado());
        estado.setNombreEstado(coloniaJPA.MunicipioJPA.EstadoJPA.getNombreEstado());

        Pais pais = new Pais();
        pais.setIdPais(coloniaJPA.MunicipioJPA.EstadoJPA.PaisJPA.getIdPais());
        pais.setNombrePais(coloniaJPA.MunicipioJPA.EstadoJPA.PaisJPA.getNombrePais());

        estado.Pais = pais;
        municipio.Estado = estado;
        colonia.Municipio = municipio;

        return colonia;
    }
}
