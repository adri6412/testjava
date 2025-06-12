package com.example.anagrafica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utenze")
public class Utenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Lo username è obbligatorio")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public enum Ruolo {
        ADMIN, OPERATORE, UTENTE
    }
}