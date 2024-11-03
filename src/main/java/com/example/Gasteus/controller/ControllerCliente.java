package com.example.Gasteus.controller;

import com.example.Gasteus.model.Role;
import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.cliente.DadosCadastroCliente;
import com.example.Gasteus.model.cliente.DadosDetalhamentoCliente;
import com.example.Gasteus.model.cliente.autenticacao.DadosAutenticacaoCliente;
import com.example.Gasteus.repository.ClienteRepository;
import com.example.Gasteus.repository.RoleRepository;
import com.example.Gasteus.security.DadosTokenJWT;
import com.example.Gasteus.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class ControllerCliente {

    @Autowired
    private ClienteRepository clienteRepositorio;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/cadastro/cliente")
    @Transactional
    public ResponseEntity<?> cadastrar(@ModelAttribute @Valid DadosCadastroCliente dados, UriComponentsBuilder uriBuilder) {
        // Verifica se o cliente já existe
        if (clienteRepositorio.findByCpf(dados.cpf()).isPresent()) {
            return ResponseEntity.badRequest().body("Cliente já cadastrado");
        }

        // Busca a role USER
        Role userRole = roleRepository.findByNome("USER");
        if (userRole == null) {
            return ResponseEntity.badRequest().body("Role USER não encontrada.");
        }

        // Cria o cliente e associa a role
        var cliente = new Cliente(dados);
        cliente.setRole(userRole);

        // Processa a foto de perfil, se fornecida
        if (dados.fotoPerfil() != null && !dados.fotoPerfil().isEmpty()) {
            try {
                cliente.setFotoPerfil(dados.fotoPerfil().getBytes());
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Erro ao processar a foto de perfil.");
            }
        }

        // Salva o cliente no repositório
        clienteRepositorio.save(cliente);
        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }

    @PostMapping("/login/cliente")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacaoCliente dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.cpf(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @GetMapping("/user")
    public ResponseEntity<DadosDetalhamentoCliente> obterClienteLogado(Authentication authentication) {
        Cliente cliente = (Cliente) authentication.getPrincipal();
        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }

    @PutMapping("/user/atualizar-telefone")
    @Transactional
    public ResponseEntity atualizarTelefone(@RequestParam String novoTelefone, Authentication authentication) {
        Cliente cliente = (Cliente) authentication.getPrincipal();
        cliente.setTelefone(novoTelefone);
        clienteRepositorio.save(cliente);
        return ResponseEntity.ok("Telefone atualizado com sucesso.");
    }

    @DeleteMapping("/user")
    @Transactional
    public ResponseEntity deletarConta(Authentication authentication) {
        Cliente cliente = (Cliente) authentication.getPrincipal();
        clienteRepositorio.delete(cliente);
        return ResponseEntity.ok("Conta deletada com sucesso.");
    }
}
