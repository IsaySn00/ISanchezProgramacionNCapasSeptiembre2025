package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Municipio;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Pais;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Rol;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = jdbcTemplate.execute("{CALL GetAllIdUsuarioAddress(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();

            try {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();

                int idUsuario = 0;

                while (resultSet.next()) {
                    idUsuario = resultSet.getInt("IdUsuario");

                    if (!resultSP.objects.isEmpty() && idUsuario == ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        Usuario usuario = ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1)));
                        usuario.Direcciones.add(direccion);
                    } else {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(idUsuario);
                        usuario.setNombreUsuario(resultSet.getString("nombre"));
                        usuario.setApellidoPatUsuario(resultSet.getString("ApellidoPaterno"));
                        usuario.setUserName(resultSet.getString("username"));
                        usuario.setEmailUsuario(resultSet.getString("email"));
                        usuario.setTelefonoUsuario(resultSet.getString("Telefono"));

                        byte[] byteImagen = resultSet.getBytes("Foto_Usuario");
                        if (byteImagen != null && byteImagen.length > 0) {
                            String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);
                            usuario.setFotoUsuario(imagenBase64);
                        } else {
                            usuario.setFotoUsuario(null);
                        }

                        usuario.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        usuario.Direcciones.add(direccion);
                        resultSP.objects.add(usuario);
                    }
                }
                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });
        return result;
    }

    @Override
    public Result GetById(int idUsuario) {

        Result result = jdbcTemplate.execute("{CALL GetByIdUsuarioAddress(?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();

            try {
                callableStatement.setInt(1, idUsuario);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                if (resultSetUsuario.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(idUsuario);
                    usuario.setNombreUsuario(resultSetUsuario.getString("nombre"));
                    usuario.setApellidoPatUsuario(resultSetUsuario.getString("ApellidoPaterno"));
                    usuario.setApellidoMatUsuario(resultSetUsuario.getString("ApellidoMaterno"));
                    usuario.setUserName(resultSetUsuario.getString("username"));
                    usuario.setEmailUsuario(resultSetUsuario.getString("email"));
                    usuario.setFechaNacimiento(resultSetUsuario.getDate("FechaNacimiento"));
                    usuario.setTelefonoUsuario(resultSetUsuario.getString("Telefono"));
                    usuario.setCelularUsuario(resultSetUsuario.getString("Celular"));
                    byte[] byteImagen = resultSetUsuario.getBytes("Foto_Usuario");
                    if (byteImagen != null && byteImagen.length > 0) {
                        String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);
                        usuario.setFotoUsuario(imagenBase64);
                    } else {
                        usuario.setFotoUsuario(null);
                    }
                    usuario.setSexoUsuario(resultSetUsuario.getString("Sexo"));
                    usuario.setCurpUsuario(resultSetUsuario.getString("curp"));
                    Rol rol = new Rol();
                    rol.setIdRol(resultSetUsuario.getInt("idRol"));
                    usuario.setRol(rol);

                    ResultSet resultSetDirecciones = (ResultSet) callableStatement.getObject(3);
                    usuario.Direcciones = new ArrayList<>();
                    while (resultSetDirecciones.next()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSetDirecciones.getInt("IdDireccion"));
                        direccion.setCalle(resultSetDirecciones.getString("Calle"));
                        direccion.setNumeroInterior(resultSetDirecciones.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSetDirecciones.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombreColonia(resultSetDirecciones.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSetDirecciones.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombreMunicipio(resultSetDirecciones.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombreEstado(resultSetDirecciones.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSetDirecciones.getString("NombrePais"));
                        usuario.Direcciones.add(direccion);
                    }
                    resultSP.object = usuario;
                    resultSP.correct = true;
                }

            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        }
        );
        return result;
    }

    @Override
    public Result AddUsuario(Usuario usuario) {
        return jdbcTemplate.execute("{call AddUsuarioWithAddress(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
            java.sql.Date sqlModificacion = new java.sql.Date(2020, 12, 12);

            Direccion direccion = usuario.Direcciones.get(0);
            try {
                callableStatement.setString(1, usuario.getNombreUsuario());
                callableStatement.setString(2, usuario.getApellidoPatUsuario());
                callableStatement.setString(3, usuario.getApellidoMatUsuario());
                callableStatement.setString(4, usuario.getPasswordUser());
                callableStatement.setDate(5, sqlDate);
                callableStatement.setInt(6, usuario.getStatusUsuario());
                callableStatement.setDate(7, sqlModificacion);
                byte[] imageBytes = Base64.getDecoder().decode(usuario.getFotoUsuario());
                callableStatement.setBytes(8, imageBytes);
                callableStatement.setString(9, usuario.getUserName());
                callableStatement.setString(10, usuario.getEmailUsuario());
                callableStatement.setString(11, usuario.getSexoUsuario());
                callableStatement.setString(12, usuario.getTelefonoUsuario());
                callableStatement.setString(13, usuario.getCelularUsuario());
                callableStatement.setString(14, usuario.getCurpUsuario());
                callableStatement.setInt(15, usuario.Rol.getIdRol());

                callableStatement.setString(16, direccion.getCalle());
                callableStatement.setString(17, direccion.getNumeroInterior());
                callableStatement.setString(18, direccion.getNumeroExterior());
                callableStatement.setInt(19, direccion.Colonia.getIdColonia());

                int rowAffecteds = callableStatement.executeUpdate();

                if (rowAffecteds > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        }
        );
    }

    @Override
    public Result UpdateUsuario(Usuario usuario) {
        return jdbcTemplate.execute("{CALL UpdateUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
            java.sql.Date sqlModificacion = new java.sql.Date(sqlDate.getTime());
            try {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombreUsuario());
                callableStatement.setString(3, usuario.getApellidoPatUsuario());
                callableStatement.setString(4, usuario.getApellidoMatUsuario());
                callableStatement.setDate(5, sqlDate);
                callableStatement.setInt(6, usuario.getStatusUsuario());
                callableStatement.setDate(7, sqlModificacion);
                callableStatement.setString(8, usuario.getUserName());
                callableStatement.setString(9, usuario.getEmailUsuario());
                callableStatement.setString(10, usuario.getSexoUsuario());
                callableStatement.setString(11, usuario.getTelefonoUsuario());
                callableStatement.setString(12, usuario.getCelularUsuario());
                callableStatement.setString(13, usuario.getCurpUsuario());
                callableStatement.setInt(14, usuario.Rol.getIdRol());

                int rowAffecteds = callableStatement.executeUpdate();

                if (rowAffecteds > 0) {
                    result.correct = true;
                } else {
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
    public Result DeleteUsuaurio(int idUsuario) {
        return jdbcTemplate.execute("{CALL DeleteUserWithDirection(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setInt(1, idUsuario);

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                }

            } catch (Exception ex) {
                result.correct = true;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        });
    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        return jdbcTemplate.execute("{CALL getAllDinamico(?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.setString(1, usuario.getNombreUsuario());
                callableStatement.setString(2, usuario.getApellidoPatUsuario());
                callableStatement.setString(3, usuario.getApellidoMatUsuario());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList<>();

                int idUsuario = 0;

                while (resultSet.next()) {
                    idUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        Usuario usuarioNew = ((Usuario) (result.objects.get(result.objects.size() - 1)));
                        usuarioNew.Direcciones.add(direccion);
                    } else {
                        Usuario usuarioNew = new Usuario();
                        usuarioNew.setIdUsuario(idUsuario);
                        usuarioNew.setNombreUsuario(resultSet.getString("nombre"));
                        usuarioNew.setApellidoPatUsuario(resultSet.getString("ApellidoPaterno"));
                        usuarioNew.setApellidoMatUsuario(resultSet.getString("ApellidoMaterno"));
                        usuarioNew.setUserName(resultSet.getString("username"));
                        usuarioNew.setEmailUsuario(resultSet.getString("email"));
                        usuarioNew.setTelefonoUsuario(resultSet.getString("Telefono"));

                        byte[] byteImagen = resultSet.getBytes("Foto_Usuario");
                        if (byteImagen != null && byteImagen.length > 0) {
                            String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);
                            usuarioNew.setFotoUsuario(imagenBase64);
                        } else {
                            usuarioNew.setFotoUsuario(null);
                        }

                        usuarioNew.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        usuarioNew.Direcciones.add(direccion);
                        result.objects.add(usuarioNew);
                    }
                }

            } catch (Exception ex) {
                result.correct = true;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            return result;
        });
    }

    @Override
    public Result UpdateImgUsuario(Usuario usuario) {
        return jdbcTemplate.execute("{CALL UpdateImgUsuario(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.setInt(1, usuario.getIdUsuario());
                if (usuario.getFotoUsuario() != null) {
                    byte[] imagenBytes = Base64.getDecoder().decode(usuario.getFotoUsuario());
                    callableStatement.setBytes(2, imagenBytes);
                } else {
                    callableStatement.setNull(2, java.sql.Types.BLOB);
                }

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;
                } else {
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddUsuariosByFile(List<Usuario> usuarios) {

        Result result = new Result();

        jdbcTemplate.batchUpdate(
                "{CALL AddUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                usuarios,
                100,
                (cs, usuario) -> {
                    try {
                        java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
                        
                        cs.setString(1, usuario.getNombreUsuario());
                        cs.setString(2, usuario.getApellidoPatUsuario());
                        cs.setString(3, usuario.getApellidoMatUsuario());
                        cs.setString(4, usuario.getPasswordUser());
                        cs.setDate(5, sqlDate);
                        cs.setInt(6, usuario.getStatusUsuario());
                        cs.setDate(7, new java.sql.Date(2020, 12, 12));
                        cs.setString(8, usuario.getUserName());
                        cs.setString(9, usuario.getEmailUsuario());
                        cs.setString(10, usuario.getSexoUsuario());
                        cs.setString(11, usuario.getTelefonoUsuario());
                        cs.setString(12, usuario.getCelularUsuario());
                        cs.setString(13, usuario.getCurpUsuario());
                        cs.setInt(14, usuario.Rol.getIdRol());
                        result.correct = true;
                    } catch (Exception ex) {
                        result.correct = false;
                        result.errorMessage = ex.getLocalizedMessage();
                        result.ex = ex;
                    }
                });
        return result;
    }
}
