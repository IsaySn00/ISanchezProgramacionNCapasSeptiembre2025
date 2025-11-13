package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Result;


public interface IColoniaJPA {

    Result GetAllColoniaByIdMunicipio(int idMunicipio);
    
    Result GetCodigoPostalByNameColoniaIdMnpio(String nombreColonia, int idMunicipio);
}
