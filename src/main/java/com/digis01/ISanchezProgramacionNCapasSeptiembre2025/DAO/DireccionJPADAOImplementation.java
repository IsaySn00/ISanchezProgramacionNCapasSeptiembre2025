package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper.DireccionMapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Autowired 
    private DireccionMapper direccionMapper;
    
    @Override
    @Transactional
    public Result AddDireccion(Direccion direccion, int id) {
        Result result = new Result();
        
        try{
            DireccionJPA direccionJPA = direccionMapper.MLToEntity(direccion);
            direccionJPA.UsuarioJPA = new UsuarioJPA();
            direccionJPA.UsuarioJPA.setIdUsuario(id);
            
            entityManager.persist(direccionJPA);
            
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
    public Result UpdateDireccion(Direccion direccion, int id) {
        Result result = new Result();
        try{
            DireccionJPA direccionJPA = direccionMapper.MLToEntity(direccion);
            direccionJPA.UsuarioJPA = new UsuarioJPA();
            direccionJPA.UsuarioJPA.setIdUsuario(id);
            
            entityManager.merge(direccionJPA);
            
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
    public Result DeleteDireccion(int id) {
        Result result = new Result();
        
        try{
            DireccionJPA direccionJPA = entityManager.getReference(DireccionJPA.class, id);
            entityManager.remove(direccionJPA);
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
