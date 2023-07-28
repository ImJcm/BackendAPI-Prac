package com.example.backendapiprac.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(req,res);
        } catch(JwtException e) {
            setErrorResponse(req,res,e);
        }
    }

    private void setErrorResponse(HttpServletRequest req, HttpServletResponse res, JwtException e) throws IOException{
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_BAD_REQUEST);
        body.put("message", e.getMessage());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(res.getOutputStream(),body);
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
