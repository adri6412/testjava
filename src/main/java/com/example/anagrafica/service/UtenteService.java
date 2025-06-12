package com.example.anagrafica.service;

import com.example.anagrafica.dto.UtenteDTO;
import com.example.anagrafica.exception.ResourceNotFoundException;
import com.example.anagrafica.model.Utente;
import com.example.anagrafica.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public List<UtenteDTO> getAllUtenti() {
        return utenteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UtenteDTO getUtenteById(Long id) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));
        return convertToDTO(utente);
    }

    public UtenteDTO getUtenteByCodiceFiscale(String codiceFiscale) {
        Utente utente = utenteRepository.findByCodiceFiscale(codiceFiscale)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con codice fiscale: " + codiceFiscale));
        return convertToDTO(utente);
    }

    @Transactional
    public UtenteDTO createUtente(UtenteDTO utenteDTO) {
        // Verifica se esiste già un utente con lo stesso codice fiscale o email
        if (utenteRepository.existsByCodiceFiscale(utenteDTO.getCodiceFiscale())) {
            throw new IllegalArgumentException("Esiste già un utente con questo codice fiscale");
        }
        if (utenteRepository.existsByEmail(utenteDTO.getEmail())) {
            throw new IllegalArgumentException("Esiste già un utente con questa email");
        }

        Utente utente = convertToEntity(utenteDTO);
        Utente savedUtente = utenteRepository.save(utente);
        return convertToDTO(savedUtente);
    }

    @Transactional
    public UtenteDTO updateUtente(Long id, UtenteDTO utenteDTO) {
        Utente existingUtente = utenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));

        // Verifica se il codice fiscale o l'email sono già utilizzati da altri utenti
        if (!existingUtente.getCodiceFiscale().equals(utenteDTO.getCodiceFiscale()) &&
                utenteRepository.existsByCodiceFiscale(utenteDTO.getCodiceFiscale())) {
            throw new IllegalArgumentException("Esiste già un utente con questo codice fiscale");
        }
        if (!existingUtente.getEmail().equals(utenteDTO.getEmail()) &&
                utenteRepository.existsByEmail(utenteDTO.getEmail())) {
            throw new IllegalArgumentException("Esiste già un utente con questa email");
        }

        // Aggiorna i campi
        existingUtente.setNome(utenteDTO.getNome());
        existingUtente.setCognome(utenteDTO.getCognome());
        existingUtente.setCodiceFiscale(utenteDTO.getCodiceFiscale());
        existingUtente.setDataNascita(utenteDTO.getDataNascita());
        existingUtente.setLuogoNascita(utenteDTO.getLuogoNascita());
        existingUtente.setIndirizzo(utenteDTO.getIndirizzo());
        existingUtente.setCitta(utenteDTO.getCitta());
        existingUtente.setCap(utenteDTO.getCap());
        existingUtente.setEmail(utenteDTO.getEmail());
        existingUtente.setTelefono(utenteDTO.getTelefono());
        existingUtente.setNote(utenteDTO.getNote());

        Utente updatedUtente = utenteRepository.save(existingUtente);
        return convertToDTO(updatedUtente);
    }

    @Transactional
    public void deleteUtente(Long id) {
        if (!utenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utente non trovato con id: " + id);
        }
        utenteRepository.deleteById(id);
    }

    // Metodi di conversione tra Entity e DTO
    private UtenteDTO convertToDTO(Utente utente) {
        UtenteDTO dto = new UtenteDTO();
        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setCognome(utente.getCognome());
        dto.setCodiceFiscale(utente.getCodiceFiscale());
        dto.setDataNascita(utente.getDataNascita());
        dto.setLuogoNascita(utente.getLuogoNascita());
        dto.setIndirizzo(utente.getIndirizzo());
        dto.setCitta(utente.getCitta());
        dto.setCap(utente.getCap());
        dto.setEmail(utente.getEmail());
        dto.setTelefono(utente.getTelefono());
        dto.setNote(utente.getNote());
        return dto;
    }

    private Utente convertToEntity(UtenteDTO dto) {
        Utente utente = new Utente();
        utente.setId(dto.getId());
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setCodiceFiscale(dto.getCodiceFiscale());
        utente.setDataNascita(dto.getDataNascita());
        utente.setLuogoNascita(dto.getLuogoNascita());
        utente.setIndirizzo(dto.getIndirizzo());
        utente.setCitta(dto.getCitta());
        utente.setCap(dto.getCap());
        utente.setEmail(dto.getEmail());
        utente.setTelefono(dto.getTelefono());
        utente.setNote(dto.getNote());
        return utente;
    }
}