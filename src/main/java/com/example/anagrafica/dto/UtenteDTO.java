package com.example.anagrafica.dto;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
    
    private Long id;
    
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve essere compreso tra 2 e 50 caratteri")
    private String nome;
    
    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve essere compreso tra 2 e 50 caratteri")
    private String cognome;
    
    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", 
             message = "Il codice fiscale deve essere nel formato corretto")
    private String codiceFiscale;
    
    @NotNull(message = "La data di nascita è obbligatoria")
    @Past(message = "La data di nascita deve essere nel passato")
    private LocalDate dataNascita;
    
    @NotBlank(message = "Il luogo di nascita è obbligatorio")
    private String luogoNascita;
    
    @NotBlank(message = "L'indirizzo è obbligatorio")
    private String indirizzo;
    
    @NotBlank(message = "La città è obbligatoria")
    private String citta;
    
    @NotBlank(message = "Il CAP è obbligatorio")
    @Pattern(regexp = "^\\d{5}$", message = "Il CAP deve essere composto da 5 cifre")
    private String cap;
    
    @Email(message = "L'email deve essere valida")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Il numero di telefono deve essere valido")
    private String telefono;
    
    private String note;
}