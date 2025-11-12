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
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Result AddUsuario(Usuario usuario) {
        Result result = new Result();
        
        try{
            UsuarioJPA usuarioJPA = new UsuarioJPA();
            
            usuarioJPA = usuarioMapper.MLToEntity(usuario);
            
            entityManager.persist(usuarioJPA);
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result GetById(int id) {
        
        Result result = new Result();
        try{
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, id);
            
            Usuario usuario = new Usuario();
            usuario = usuarioMapper.EntityToML(usuarioJPA);
            result.object = usuario;
        }catch(Exception ex){
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.object = null;
        }
        return result;
    }

    @Override
    @Transactional
    public Result UpdateUsuario(Usuario usuario) {
        Result result = new Result();
        
        try{
            UsuarioJPA usuarioFind = entityManager.find(UsuarioJPA.class, usuario.getIdUsuario());
            
            UsuarioJPA usuarioJPA = usuarioMapper.MLToEntity(usuario);
            
            usuarioJPA.setPasswordUser(usuarioFind.getPasswordUser());
            usuarioJPA.setFotoUsuario(usuarioFind.getFotoUsuario());
            usuarioJPA.setDirecciones(usuarioFind.getDirecciones());
            
            entityManager.merge(usuarioJPA);
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result UpdateImgUsuario(Usuario usuario) {
        
        Result result = new Result();
        try{
            UsuarioJPA usuarioJPA = usuarioMapper.MLToEntity(usuario);
            
            String query = "UPDATE UsuarioJPA SET FotoUsuario = :newFoto WHERE IdUsuario = :id";
            
            entityManager.createQuery(query)
                    .setParameter("newFoto", usuarioJPA.getFotoUsuario())
                    .setParameter("id", usuarioJPA.getIdUsuario())
                    .executeUpdate();
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
