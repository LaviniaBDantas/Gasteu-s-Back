package com.example.Gasteus.model.cliente.factory;

import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.cliente.DadosCadastroCliente;

public interface IClienteFactory {
    Cliente criarCliente(DadosCadastroCliente dadosCadastroCliente);
}
