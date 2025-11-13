package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.PaisJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import org.springframework.stereotype.Component;

@Component
public class PaisMapper {

    public Pais EntityToML(PaisJPA paisJPA) {

        Pais pais = new Pais();
        
        pais.setIdPais(paisJPA.getIdPais());
        pais.setNombrePais(paisJPA.getNombrePais());

        return pais;
    }

    public PaisJPA MLToEntity(Pais pais) {
        
        PaisJPA paisJPA = new PaisJPA();
        
        paisJPA.setIdPais(pais.getIdPais());
        paisJPA.setNombrePais(pais.getNombrePais());

        return paisJPA;
    }
}
