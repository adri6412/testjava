package com.example.anagrafica.controller;

import com.example.anagrafica.dto.UtenteDTO;
import com.example.anagrafica.service.UtenteService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATORE')")
    public ResponseEntity<List<UtenteDTO>> getAllUtenti() {
        List<UtenteDTO> utenti = utenteService.getAllUtenti();
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATORE') or hasRole('UTENTE')")
    public ResponseEntity<UtenteDTO> getUtenteById(@PathVariable Long id) {
        UtenteDTO utente = utenteService.getUtenteById(id);
        return ResponseEntity.ok(utente);
    }

    @GetMapping("/codiceFiscale/{codiceFiscale}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATORE')")
    public ResponseEntity<UtenteDTO> getUtenteByCodiceFiscale(@PathVariable String codiceFiscale) {
        UtenteDTO utente = utenteService.getUtenteByCodiceFiscale(codiceFiscale);
        return ResponseEntity.ok(utente);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATORE')")
    public ResponseEntity<UtenteDTO> createUtente(@Valid @RequestBody UtenteDTO utenteDTO) {
        UtenteDTO createdUtente = utenteService.createUtente(utenteDTO);
        return new ResponseEntity<>(createdUtente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATORE')")
    public ResponseEntity<UtenteDTO> updateUtente(@PathVariable Long id, @Valid @RequestBody UtenteDTO utenteDTO) {
        UtenteDTO updatedUtente = utenteService.updateUtente(id, utenteDTO);
        return ResponseEntity.ok(updatedUtente);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }
}