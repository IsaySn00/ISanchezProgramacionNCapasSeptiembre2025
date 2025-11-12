package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;


public interface IUsuarioJPA {
    
    Result GetAll();
    Result AddUsuario(Usuario usuario);
    Result GetById(int id);
    Result UpdateUsuario(Usuario usuario);

}
