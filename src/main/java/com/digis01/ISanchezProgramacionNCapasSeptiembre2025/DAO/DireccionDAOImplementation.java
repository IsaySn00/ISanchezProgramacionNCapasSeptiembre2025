package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result AddDireccion(Direccion direccion, int idUsuario) {
        return jdbcTemplate.execute("{CALL AddAddressToUser(?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.getColonia().getIdColonia());
                callableStatement.setInt(5, idUsuario);
                
                int affectedRows = callableStatement.executeUpdate();

                if(affectedRows > 0){
                    result.correct = true;
                }else{
                    result.correct = false;
                }
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;

            }
            return result;
        });
    }

    @Override
    public Result UpdateDireccion(Direccion direccion, int idUsuario) {
        return jdbcTemplate.execute("{CALL UpdateDireccionUsuario(?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            
            try{
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setString(4, direccion.getNumeroExterior());
                callableStatement.setInt(5, direccion.getColonia().getIdColonia());
                
                int affectedRows = callableStatement.executeUpdate();
                
                if(affectedRows > 0){
                    result.correct = true;
                }else{
                    result.correct = false;
                }
                
            }catch(Exception ex){
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        });
    }

    @Override
    public Result GetAddressById(int idDireccion) {
        return jdbcTemplate.execute("{CALL GetAddressById(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
           Result result = new Result();
           try{
               callableStatement.setInt(1, idDireccion);
               callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
               callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
               callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
               callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(11, java.sql.Types.INTEGER);
               callableStatement.registerOutParameter(12, java.sql.Types.VARCHAR);
               callableStatement.registerOutParameter(13, java.sql.Types.INTEGER);
               callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
               
               callableStatement.execute();
               
               Direccion direccion = new Direccion();
               
               direccion.setIdDireccion(callableStatement.getInt(2));
               direccion.setCalle(callableStatement.getString(3));
               direccion.setNumeroInterior(callableStatement.getString(4));
               direccion.setNumeroExterior(callableStatement.getString(5));
               direccion.Colonia = new Colonia();
               direccion.Colonia.setIdColonia(callableStatement.getInt(6));
               direccion.Colonia.setCodigoPostal(callableStatement.getString(7));
               direccion.Colonia.setNombreColonia(callableStatement.getString(8));
               direccion.Colonia.Municipio = new Municipio();
               direccion.Colonia.Municipio.setIdMunicipio(callableStatement.getInt(9));
               direccion.Colonia.Municipio.setNombreMunicipio(callableStatement.getString(10));
               direccion.Colonia.Municipio.Estado = new Estado();
               direccion.Colonia.Municipio.Estado.setIdEstado(callableStatement.getInt(11));
               direccion.Colonia.Municipio.Estado.setNombreEstado(callableStatement.getString(12));
               direccion.Colonia.Municipio.Estado.Pais = new Pais();
               direccion.Colonia.Municipio.Estado.Pais.setIdPais(callableStatement.getInt(13));
               direccion.Colonia.Municipio.Estado.Pais.setNombrePais(callableStatement.getString(14));
               result.object = direccion;
               result.correct = true;
               
           }catch(Exception ex){
               result.correct = false;
               result.errorMessage = ex.getLocalizedMessage();
               result.ex = ex;
           }
           return result;
        });
    }

    @Override
    public Result DeleteDireccion(int idDireccion) {
        return jdbcTemplate.execute("{CALL DeleteAddressToUser(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            
            try{
                callableStatement.setInt(1, idDireccion);
                int affectedRows = callableStatement.executeUpdate();
                
                if(affectedRows > 0){
                    result.correct = true;
                }else{
                    result.correct = false;
                }
                
            }catch(Exception ex){
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        });
    }
    
    
}
