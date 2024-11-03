package com.example.Gasteus.repository;

import com.example.Gasteus.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long>{
    Role findByNome(String nome);
}
