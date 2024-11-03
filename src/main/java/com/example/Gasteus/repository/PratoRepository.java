package com.example.Gasteus.repository;
import com.example.Gasteus.model.prato.Prato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PratoRepository extends JpaRepository<Prato,Integer>{
        Optional<Prato> findByCod(Integer cod);

}
