package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAllColoniaByIdMunicipio(int idMunicipio) {
        return jdbcTemplate.execute("{CALL ColoniaGetAllById(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.setInt(1, idMunicipio);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                    result.objects.add(colonia);
                }
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
                result.objects = null;
            }
            return result;
        });
    }

    @Override
    public Result GetCodigoPostalByNameColoniaIdMnpio(String nombreColonia, int idMunicipio) {
        return jdbcTemplate.execute("{CALL CodigoPostalGetByNameColIdMnpio(?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setString(1, nombreColonia);
                callableStatement.setInt(2, idMunicipio);
                callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
                callableStatement.execute();
                String codigoPostal = (String) callableStatement.getObject(3);
                Colonia colonia = new Colonia();
                colonia.setCodigoPostal(codigoPostal);
                result.object = colonia;
                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        });
    }

    @Override
    public Result GetDireccionByCodigoPostal(String codigoPostal) {
        return jdbcTemplate.execute("{CALL GetDireccionByCodigoPostal(?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.setString(1, codigoPostal);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, java.sql.Types.REF_CURSOR);
                callableStatement.registerOutParameter(4, java.sql.Types.REF_CURSOR);
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetColonia = (ResultSet) callableStatement.getObject(2);
                ResultSet resultSetMunicipio = (ResultSet) callableStatement.getObject(3);
                ResultSet resultSetEstado = (ResultSet) callableStatement.getObject(4);
                ResultSet resultSetPais = (ResultSet) callableStatement.getObject(5);

                result.objects = new ArrayList<>();
                List<Colonia> colonias = new ArrayList<>();

                while (resultSetColonia.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSetColonia.getInt("IdColonia"));
                    colonia.setNombreColonia(resultSetColonia.getString("NombreColonia"));
                    colonias.add(colonia);
                }
                result.objects.add(colonias);

                if (resultSetMunicipio.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSetMunicipio.getInt("idMunicipio"));
                    municipio.setNombreMunicipio(resultSetMunicipio.getString("nombreMunicipio"));
                    result.objects.add(municipio);

                }

                if (resultSetEstado.next()) {
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSetEstado.getInt("idEstado"));
                    estado.setNombreEstado(resultSetEstado.getString("nombreEstado"));
                    result.objects.add(estado);

                }

                if (resultSetPais.next()) {
                    Pais pais = new Pais();
                    pais.setIdPais(resultSetPais.getInt("idPais"));
                    pais.setNombrePais(resultSetPais.getString("nombrePais"));
                    result.objects.add(pais);

                }

            } catch (Exception ex) {
                result.correct = true;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
                result.objects = null;
            }
            return result;
        });
    }

}
