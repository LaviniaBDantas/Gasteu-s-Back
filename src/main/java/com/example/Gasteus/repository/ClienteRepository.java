package com.example.Gasteus.repository;

import com.example.Gasteus.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository <Cliente,String> {
    Optional<Cliente> findByCpf(String cpf);
}
