package com.example.Gasteus.controller;

import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.cliente.DadosCadastroCliente;
import com.example.Gasteus.model.cliente.DadosDetalhamentoCliente;
import com.example.Gasteus.model.cliente.autenticacao.DadosAutenticacao;
import com.example.Gasteus.repository.ClienteRepository;
import com.example.Gasteus.security.DadosTokenJWT;
import com.example.Gasteus.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/home")
public class ControllerCliente {

    @Autowired
    private ClienteRepository clienteRepositorio;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCliente dados, UriComponentsBuilder uriBuilder){
        //colocar validacao de duplicidade
        if (clienteRepositorio.findByCpf(dados.cpf()) != null) {
            return ResponseEntity.badRequest().body("Cliente já cadastrado"); // Cliente já cadastrado
        }
        var cliente= new Cliente(dados);
        clienteRepositorio.save(cliente);
        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.cpf(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Cliente) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
