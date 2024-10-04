package com.example.Gasteus.repository;

import com.example.Gasteus.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <Cliente,String> {

    Cliente findByCpf(String cpf);
}
