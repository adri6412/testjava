package com.example.anagrafica.security;

import com.example.anagrafica.model.Utenza;
import com.example.anagrafica.repository.UtenzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtenzaRepository utenzaRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utenza utenza = utenzaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con username: " + username));

        return User.builder()
                .username(utenza.getUsername())
                .password(utenza.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utenza.getRuolo().name())))
                .build();
    }
}