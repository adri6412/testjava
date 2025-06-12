package com.example.anagrafica.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve essere compreso tra 2 e 50 caratteri")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve essere compreso tra 2 e 50 caratteri")
    @Column(nullable = false)
    private String cognome;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", 
             message = "Il codice fiscale deve essere nel formato corretto")
    @Column(nullable = false, unique = true, length = 16)
    private String codiceFiscale;

    @NotNull(message = "La data di nascita è obbligatoria")
    @Past(message = "La data di nascita deve essere nel passato")
    @Column(nullable = false)
    private LocalDate dataNascita;

    @NotBlank(message = "Il luogo di nascita è obbligatorio")
    @Column(nullable = false)
    private String luogoNascita;

    @NotBlank(message = "L'indirizzo è obbligatorio")
    @Column(nullable = false)
    private String indirizzo;

    @NotBlank(message = "La città è obbligatoria")
    @Column(nullable = false)
    private String citta;

    @NotBlank(message = "Il CAP è obbligatorio")
    @Pattern(regexp = "^\\d{5}$", message = "Il CAP deve essere composto da 5 cifre")
    @Column(nullable = false, length = 5)
    private String cap;

    @Email(message = "L'email deve essere valida")
    @NotBlank(message = "L'email è obbligatoria")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Il numero di telefono deve essere valido")
    @Column
    private String telefono;
    
    @Column
    private String note;
}