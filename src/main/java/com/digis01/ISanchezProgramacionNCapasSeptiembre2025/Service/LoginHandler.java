package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.ML.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class LoginHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    
        if(authorities.stream().anyMatch(a -> a.getAuthority().equals("admin"))){
            response.sendRedirect("/usuario/usuarioIndex");
        }else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("usuario"))){
            
            Usuario usuario = (Usuario) authentication.getPrincipal();
            int userId = usuario.getIdUsuario();
            
            response.sendRedirect("/usuario/details/" + userId);
        }
    }
    

}
