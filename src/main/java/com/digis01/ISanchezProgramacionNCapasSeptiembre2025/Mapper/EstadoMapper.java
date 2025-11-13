package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.EstadoJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {

    public Estado EntityToML(EstadoJPA estadoJPA) {
        Estado estado = new Estado();
        
        estado.setIdEstado(estadoJPA.getIdEstado());
        estado.setNombreEstado(estadoJPA.getNombreEstado());

        Pais pais = new Pais();
        pais.setIdPais(estadoJPA.PaisJPA.getIdPais());
        pais.setNombrePais(estadoJPA.PaisJPA.getNombrePais());

        estado.Pais = pais;
        
        return estado;
    }

}
