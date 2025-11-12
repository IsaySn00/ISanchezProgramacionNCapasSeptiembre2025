package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {
    
    @Autowired
    EntityManager entityManager;
    
    public Direccion EntityToML(DireccionJPA direccionJPA){
        Direccion direccion = new Direccion();
        
        direccion.setIdDireccion(direccionJPA.getIdDireccion());
        direccion.setCalle(direccionJPA.getCalle());
        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(direccionJPA.UsuarioJPA.getIdUsuario());
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(direccionJPA.ColoniaJPA.getIdColonia());
        
        return direccion;
    } 
    
    public DireccionJPA MLToEntity(Direccion direccion){
        DireccionJPA direccionJPA = new DireccionJPA();
        
        direccionJPA.setIdDireccion(direccion.getIdDireccion());
        direccionJPA.setCalle(direccion.getCalle());
        direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
        direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
       
        ColoniaJPA coloniaJPA = entityManager.getReference(ColoniaJPA.class, direccion.Colonia.getIdColonia());
        direccionJPA.setColonia(coloniaJPA);
        
        return direccionJPA;
    }
}
