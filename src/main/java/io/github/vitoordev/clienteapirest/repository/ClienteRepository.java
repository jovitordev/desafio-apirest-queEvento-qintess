package io.github.vitoordev.clienteapirest.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import io.github.vitoordev.clienteapirest.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
