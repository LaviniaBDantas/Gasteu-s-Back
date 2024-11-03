package com.example.Gasteus.repository;

import com.example.Gasteus.model.funcionario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByNroCarteira(Long nroCarteira);
}
