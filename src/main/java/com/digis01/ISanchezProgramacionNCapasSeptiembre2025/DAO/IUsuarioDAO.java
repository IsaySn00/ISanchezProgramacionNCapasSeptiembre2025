package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import java.util.List;


public interface IUsuarioDAO {
    
    Result GetAll();
    Result GetById(int idUsuario);
    Result AddUsuario(Usuario usuario);
    Result UpdateUsuario(Usuario usuario);
    Result DeleteUsuaurio(int idUsuario);
    Result GetAllDinamico(Usuario usuario);
    Result UpdateImgUsuario(Usuario usuario);
    Result AddUsuariosByFile(List<Usuario> usuarios);
}
