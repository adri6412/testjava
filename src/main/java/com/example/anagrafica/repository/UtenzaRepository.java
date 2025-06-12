package com.example.anagrafica.repository;

import com.example.anagrafica.model.Utenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenzaRepository extends JpaRepository<Utenza, Long> {
    
    Optional<Utenza> findByUsername(String username);
    
    boolean existsByUsername(String username);
}