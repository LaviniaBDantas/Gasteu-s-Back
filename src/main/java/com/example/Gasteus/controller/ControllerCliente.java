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
    public ResponseEntity cadastrar(
            @RequestParam("cpf") @NotBlank String cpf,
            @RequestParam("nome") @NotBlank String nome,
            @RequestParam("telefone") @NotBlank String telefone,
            @RequestParam("senha") @NotBlank String senha,
            @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
            UriComponentsBuilder uriBuilder) {

        // Verificação se o cliente já existe
        if (clienteRepositorio.findByCpf(cpf).isPresent()) {
            return ResponseEntity.badRequest().body("Cliente já cadastrado");
        }

        Role userRole = roleRepository.findByNome("USER");
        if (userRole == null) {
            return ResponseEntity.badRequest().body("Role USER não encontrada.");
        }

        var cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setSenha(senha); // Aqui você pode considerar a criptografia
        cliente.setRole(userRole);

        // Processar a foto de perfil se fornecida
        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            try {
                cliente.setFotoPerfil(fotoPerfil.getBytes());
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Erro ao processar a foto de perfil.");
            }
        }

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
