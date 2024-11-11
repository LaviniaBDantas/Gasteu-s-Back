package com.example.Gasteus.model.proxy;

import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.repository.ClienteRepository;
import org.springframework.http.ResponseEntity; // Import necessário

//O ClienteProxy atua como uma intermediario que encapsula a lógica de atualização do telefone de um Cliente. 
//Ele recebe um novo valor de telefone e realiza validações 
//antes de atualizar o objeto Cliente e persistir a alteração no repositório.

public class ClienteProxy {

    private Cliente cliente;

    public ClienteProxy(Cliente cliente) {
        this.cliente = cliente;
    }

    public ResponseEntity atualizarTelefone(String novoTelefone, ClienteRepository clienteRepositorio) {
        // Validação adicional ou controle de acesso
        if (novoTelefone == null || novoTelefone.isBlank()) {
            return ResponseEntity.badRequest().body("Telefone inválido");
        }
        cliente.setTelefone(novoTelefone);
        clienteRepositorio.save(cliente);
        return ResponseEntity.ok("Telefone atualizado com sucesso.");
    }
}
