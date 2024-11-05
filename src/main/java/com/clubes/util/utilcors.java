package com.clubes.util;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class utilcors implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if needed
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Permitir solicitudes desde cualquier origen, o especificar "http://localhost:4200" para mayor seguridad
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        
        // Si usas JWT, también deberías permitir las credenciales
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        // Métodos HTTP permitidos
        response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        
        // Tiempo de caché de la configuración preflight
        response.setHeader("Access-Control-Max-Age", "3600");

        // Encabezados permitidos (ajusta si es necesario)
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, credential, x-x");

        // Si el método es OPTIONS, devolver una respuesta 200 sin continuar con la cadena de filtros
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // De lo contrario, continuar con el siguiente filtro en la cadena
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // Destruction logic, if needed
    }
}

