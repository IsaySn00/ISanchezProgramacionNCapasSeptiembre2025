package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import java.util.List;


public interface IUsuarioJPA {
    
    Result GetAll();
    Result AddUsuario(Usuario usuario);
    Result GetById(int id);
    Result UpdateUsuario(Usuario usuario);
    Result UpdateImgUsuario(Usuario usuario);
    Result DeleteUsuario(int id);
    Result AddUsuariosByFile(List<Usuario> usuarios);
    Result GetAllDinamico(Usuario usuario);
}
