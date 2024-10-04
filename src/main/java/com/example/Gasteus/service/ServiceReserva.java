package com.example.Gasteus.service;

import com.example.Gasteus.repository.ClienteRepository;
import com.example.Gasteus.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceReserva {
    @Autowired
    private ReservaRepository reservaRepositorio;

}
