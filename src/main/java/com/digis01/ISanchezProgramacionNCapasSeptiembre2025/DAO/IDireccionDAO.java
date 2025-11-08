package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;


public interface IDireccionDAO {
    Result AddDireccion(Direccion direccion, int idUsuario);
    
    Result UpdateDireccion(Direccion direccion, int IdUsuario);
    
    Result GetAddressById(int idDireccion);
    
    Result DeleteDireccion(int idDireccion);
}
