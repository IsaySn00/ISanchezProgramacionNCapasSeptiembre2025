package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Rol;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
    @Autowired
    private EntityManager entityManager;

    public Usuario EntityToML(UsuarioJPA usuarioJPA) {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioJPA.getIdUsuario());
        usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
        usuario.setApellidoPatUsuario(usuarioJPA.getApellidoPatUsuario());
        usuario.setApellidoMatUsuario(usuarioJPA.getApellidoMatUsuario());
        usuario.setPasswordUser(usuarioJPA.getPasswordUser());
        usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
        usuario.setStatusUsuario(usuarioJPA.getStatusUsuario());
        usuario.setFechaModificacion(usuarioJPA.getFechaModificacion());
        if (usuarioJPA.getFotoUsuario() != null) {
            usuario.setFotoUsuario(Base64.getEncoder().encodeToString(usuarioJPA.getFotoUsuario()));
        } else {
            usuario.setFotoUsuario(null);
        }
        usuario.setUserName(usuarioJPA.getUserName());
        usuario.setEmailUsuario(usuarioJPA.getEmailUsuario());
        usuario.setSexoUsuario(usuarioJPA.getSexoUsuario());
        usuario.setTelefonoUsuario(usuarioJPA.getTelefonoUsuario());
        usuario.setCelularUsuario(usuarioJPA.getCelularUsuario());
        usuario.setCurpUsuario(usuarioJPA.getCurpUsuario());

        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(usuarioJPA.RolJPA.getIdRol());
        usuario.Rol.setNombreRol(usuarioJPA.RolJPA.getNombreRol());

        usuario.Direcciones = new ArrayList<>();

        for (DireccionJPA direccionJPA : usuarioJPA.DireccionesJPA) {

            Direccion direccion = new Direccion();
            direccion.setIdDireccion(direccionJPA.getIdDireccion());
            direccion.setCalle(direccionJPA.getCalle());
            direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
            direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

            Colonia colonia = new Colonia();
            colonia.setNombreColonia(direccionJPA.ColoniaJPA.getNombreColonia());
            colonia.setCodigoPostal(direccionJPA.ColoniaJPA.getCodigoPostal());

            Municipio municipio = new Municipio();
            municipio.setNombreMunicipio(direccionJPA.ColoniaJPA.MunicipioJPA.getNombreMunicipio());

            Estado estado = new Estado();
            estado.setNombreEstado(direccionJPA.ColoniaJPA.MunicipioJPA.EstadoJPA.getNombreEstado());

            Pais pais = new Pais();
            pais.setNombrePais(direccionJPA.ColoniaJPA.MunicipioJPA.EstadoJPA.PaisJPA.getNombrePais());

            estado.Pais = pais;
            municipio.Estado = estado;
            colonia.Municipio = municipio;
            direccion.Colonia = colonia;
            usuario.Direcciones.add(direccion);
        }

        return usuario;

    }

    public UsuarioJPA MLToEntity(Usuario usuario) {

        UsuarioJPA usuarioJPA = new UsuarioJPA();
        usuarioJPA.setIdUsuario(usuario.getIdUsuario());
        usuarioJPA.setNombreUsuario(usuario.getNombreUsuario());
        usuarioJPA.setApellidoPatUsuario(usuario.getApellidoPatUsuario());
        usuarioJPA.setApellidoMatUsuario(usuario.getApellidoMatUsuario());
        usuarioJPA.setPasswordUser(usuario.getPasswordUser());
        usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioJPA.setStatusUsuario(usuario.getStatusUsuario());
        usuarioJPA.setFechaModificacion(usuario.getFechaModificacion());
        if (usuario.getFotoUsuario() != null) {
            usuarioJPA.setFotoUsuario(Base64.getDecoder().decode(usuario.getFotoUsuario()));
        } else {
            usuarioJPA.setFotoUsuario(null);
        }
        usuarioJPA.setUserName(usuario.getUserName());
        usuarioJPA.setEmailUsuario(usuario.getEmailUsuario());
        usuarioJPA.setSexoUsuario(usuario.getSexoUsuario());
        usuarioJPA.setTelefonoUsuario(usuario.getTelefonoUsuario());
        usuarioJPA.setCelularUsuario(usuario.getCelularUsuario());
        usuarioJPA.setCurpUsuario(usuario.getCurpUsuario());

        usuarioJPA.RolJPA = new RolJPA();
        usuarioJPA.RolJPA.setIdRol(usuario.Rol.getIdRol());
        usuarioJPA.RolJPA.setNombreRol(usuario.Rol.getNombreRol());

        usuarioJPA.DireccionesJPA = new ArrayList<>();
        Direccion direccion = usuario.Direcciones.get(0);

        DireccionJPA direccionJPA = new DireccionJPA();
        direccionJPA.setCalle(direccion.getCalle());
        direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
        direccionJPA.setNumeroExterior(direccion.getNumeroExterior());

        ColoniaJPA coloniaJPA = entityManager.getReference(ColoniaJPA.class, direccion.Colonia.getIdColonia());
        direccionJPA.setColonia(coloniaJPA);
        
        direccionJPA.UsuarioJPA = usuarioJPA;
        
        usuarioJPA.DireccionesJPA.add(direccionJPA);

        return usuarioJPA;

    }

}
