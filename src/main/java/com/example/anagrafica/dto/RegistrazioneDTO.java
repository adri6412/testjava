package com.example.anagrafica.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrazioneDTO {
    
    @NotBlank(message = "Lo username è obbligatorio")
    @Size(min = 4, max = 50, message = "Lo username deve essere compreso tra 4 e 50 caratteri")
    private String username;
    
    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 8, message = "La password deve essere di almeno 8 caratteri")
    private String password;
    
    private UtenteDTO utente;
}