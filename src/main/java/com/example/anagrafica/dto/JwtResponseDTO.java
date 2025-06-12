package com.example.anagrafica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String username;
    private String ruolo;
    
    public JwtResponseDTO(String token, Long id, String username, String ruolo) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.ruolo = ruolo;
    }
}