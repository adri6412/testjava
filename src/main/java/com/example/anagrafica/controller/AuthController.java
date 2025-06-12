package com.example.anagrafica.controller;

import com.example.anagrafica.dto.JwtResponseDTO;
import com.example.anagrafica.dto.LoginDTO;
import com.example.anagrafica.dto.RegistrazioneDTO;
import com.example.anagrafica.service.AuthService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        JwtResponseDTO jwtResponse = authService.authenticateUser(loginDTO);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrazioneDTO registrazioneDTO) {
        try {
            authService.registerUser(registrazioneDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente registrato con successo!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}