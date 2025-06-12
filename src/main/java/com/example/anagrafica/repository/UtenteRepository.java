package com.example.anagrafica.repository;

import com.example.anagrafica.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    
    Optional<Utente> findByCodiceFiscale(String codiceFiscale);
    
    Optional<Utente> findByEmail(String email);
    
    boolean existsByCodiceFiscale(String codiceFiscale);
    
    boolean existsByEmail(String email);
}