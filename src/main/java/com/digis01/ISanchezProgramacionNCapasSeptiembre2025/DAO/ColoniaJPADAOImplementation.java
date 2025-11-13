package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Mapper.ColoniaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ColoniaMapper coloniaMapper;

    @Override
    public Result GetAllColoniaByIdMunicipio(int idMunicipio) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {

            TypedQuery<ColoniaJPA> queryColonias = entityManager.createQuery("FROM ColoniaJPA WHERE MunicipioJPA.IdMunicipio =  :id ORDER BY NombreColonia", ColoniaJPA.class);
            queryColonias.setParameter("id", idMunicipio);
            List<ColoniaJPA> colonias = queryColonias.getResultList();

            for (ColoniaJPA coloniaJPA : colonias) {
                Colonia colonia = new Colonia();

                colonia = coloniaMapper.EntityToML(coloniaJPA);

                result.objects.add(colonia);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetCodigoPostalByNameColoniaIdMnpio(String nombreColonia, int idMunicipio) {
        Result result = new Result();
        try {

            TypedQuery<ColoniaJPA> queryColonias = entityManager.createQuery("FROM ColoniaJPA WHERE MunicipioJPA.IdMunicipio =  :id AND NombreColonia = :nombre", ColoniaJPA.class);
            queryColonias.setParameter("id", idMunicipio);
            queryColonias.setParameter("nombre", nombreColonia);
            ColoniaJPA colonias = queryColonias.getSingleResult();

            Colonia colonia = new Colonia();

            colonia = coloniaMapper.EntityToML(colonias);
            
            String codigoPostal = colonia.getCodigoPostal();

            result.object = codigoPostal;

            result.correct = true;

        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
