package io.github.vitoordev.clienteapirest.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.vitoordev.clienteapirest.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}
