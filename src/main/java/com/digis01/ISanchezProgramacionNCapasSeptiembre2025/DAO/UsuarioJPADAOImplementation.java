package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper.UsuarioMapper;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{
    
    @Autowired
    EntityManager entityManager;
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();
            
            for(UsuarioJPA usuarioJPA : usuariosJPA){
                Usuario usuario = new Usuario();
                
                usuario = usuarioMapper.EntityToML(usuarioJPA);
                
                result.objects.add(usuario);
            }
            
            result.correct = true;
          
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

    
}
