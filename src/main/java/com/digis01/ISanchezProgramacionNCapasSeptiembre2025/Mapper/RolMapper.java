package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {
    
    public Rol EntityToML(RolJPA rolJPA){
        Rol rol = new Rol();
        
        rol.setIdRol(rolJPA.getIdRol());
        rol.setNombreRol(rolJPA.getNombreRol());
        
        return rol;
    }

}
