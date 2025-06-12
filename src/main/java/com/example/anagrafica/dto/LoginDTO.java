package com.example.anagrafica.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    
    @NotBlank(message = "Lo username è obbligatorio")
    private String username;
    
    @NotBlank(message = "La password è obbligatoria")
    private String password;
}