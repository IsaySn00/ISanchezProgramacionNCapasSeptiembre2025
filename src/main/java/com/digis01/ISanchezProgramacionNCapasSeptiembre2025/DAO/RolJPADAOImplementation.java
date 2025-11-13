package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Rol;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper.RolMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolDAO{

    @Autowired 
    private EntityManager entityManager;
    
    @Autowired 
    private RolMapper rolMapper;
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            TypedQuery<RolJPA> queryRol = entityManager.createQuery("FROM RolJPA", RolJPA.class);
            List<RolJPA> roles = queryRol.getResultList();
            
            for(RolJPA rolJPA : roles){
                Rol rol = new Rol();
                
                rol = rolMapper.EntityToML(rolJPA);
                
                result.objects.add(rol);
            }
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

}
