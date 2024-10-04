package com.example.Gasteus.service;

import com.example.Gasteus.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCliente {
    @Autowired
    private ClienteRepository clienteRepositorio;

}
