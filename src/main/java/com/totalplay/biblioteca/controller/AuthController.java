package com.totalplay.biblioteca.controller;

import com.totalplay.biblioteca.model.Usuario;
import com.totalplay.biblioteca.repository.UsuarioRepository;
import com.totalplay.biblioteca.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Map<String, String> loginPost(@RequestBody LoginRequest request) {
        return autenticar(request.getUsername(), request.getPassword());
    }

    @GetMapping
    public Map<String, String> loginGet(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return autenticar(username, password);
    }

    private Map<String, String> autenticar(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtil.generarToken(usuario.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("tipo", "Bearer");
        response.put("expiraEn", "1 hora");

        return response;
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}