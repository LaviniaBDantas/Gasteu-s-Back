package com.example.Gasteus.repository;

import com.example.Gasteus.model.reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository <Reserva,Integer> {
}
