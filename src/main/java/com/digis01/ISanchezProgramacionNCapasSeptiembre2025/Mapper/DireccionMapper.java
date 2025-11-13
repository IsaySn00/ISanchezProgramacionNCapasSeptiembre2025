package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    @Autowired
    EntityManager entityManager;

    public Direccion EntityToML(DireccionJPA direccionJPA) {
        Direccion direccion = new Direccion();

        direccion.setIdDireccion(direccionJPA.getIdDireccion());
        direccion.setCalle(direccionJPA.getCalle());
        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(direccionJPA.UsuarioJPA.getIdUsuario());
        
        Colonia colonia = new Colonia();
        colonia.setIdColonia(direccionJPA.ColoniaJPA.getIdColonia());
        colonia.setNombreColonia(direccionJPA.ColoniaJPA.getNombreColonia());
        colonia.setCodigoPostal(direccionJPA.ColoniaJPA.getCodigoPostal());

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(direccionJPA.ColoniaJPA.MunicipioJPA.getIdMunicipio());
        municipio.setNombreMunicipio(direccionJPA.ColoniaJPA.MunicipioJPA.getNombreMunicipio());

        Estado estado = new Estado();
        estado.setIdEstado(direccionJPA.ColoniaJPA.MunicipioJPA.EstadoJPA.getIdEstado());
        estado.setNombreEstado(direccionJPA.ColoniaJPA.MunicipioJPA.EstadoJPA.getNombreEstado());

        Pais pais = new Pais();
        pais.setIdPais(direccionJPA.ColoniaJPA.MunicipioJPA.EstadoJPA.PaisJPA.getIdPais());
        pais.setNombrePais(direccionJPA.ColoniaJPA.MunicipioJPA.EstadoJPA.PaisJPA.getNombrePais());

        estado.Pais = pais;
        municipio.Estado = estado;
        colonia.Municipio = municipio;
        direccion.Colonia = colonia;

        return direccion;
    }

    public DireccionJPA MLToEntity(Direccion direccion) {
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
