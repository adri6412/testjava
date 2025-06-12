package com.example.anagrafica.service;

import com.example.anagrafica.dto.JwtResponseDTO;
import com.example.anagrafica.dto.LoginDTO;
import com.example.anagrafica.dto.RegistrazioneDTO;
import com.example.anagrafica.dto.UtenteDTO;
import com.example.anagrafica.model.Utente;
import com.example.anagrafica.model.Utenza;
import com.example.anagrafica.repository.UtenzaRepository;
import com.example.anagrafica.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UtenzaRepository utenzaRepository;
    private final UtenteService utenteService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, 
                       UtenzaRepository utenzaRepository,
                       UtenteService utenteService,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.utenzaRepository = utenzaRepository;
        this.utenteService = utenteService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponseDTO authenticateUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utenza utenza = utenzaRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return new JwtResponseDTO(
                jwt,
                utenza.getId(),
                utenza.getUsername(),
                utenza.getRuolo().name()
        );
    }

    @Transactional
    public void registerUser(RegistrazioneDTO registrazioneDTO) {
        // Verifica se lo username esiste già
        if (utenzaRepository.existsByUsername(registrazioneDTO.getUsername())) {
            throw new IllegalArgumentException("Username già in uso");
        }

        // Crea e salva l'utente
        UtenteDTO savedUtente = utenteService.createUtente(registrazioneDTO.getUtente());

        // Crea e salva l'utenza
        Utenza utenza = new Utenza();
        utenza.setUsername(registrazioneDTO.getUsername());
        utenza.setPassword(passwordEncoder.encode(registrazioneDTO.getPassword()));
        utenza.setRuolo(Utenza.Ruolo.UTENTE); // Ruolo predefinito per i nuovi utenti

        // Collega l'utenza all'utente
        Utente utente = new Utente();
        utente.setId(savedUtente.getId());
        utenza.setUtente(utente);

        utenzaRepository.save(utenza);
    }
}