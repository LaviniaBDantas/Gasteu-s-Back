package com.example.Gasteus.controller;

import com.example.Gasteus.model.funcionario.DadosCadastroFuncionario;
import com.example.Gasteus.model.funcionario.DadosDetalhamentoFuncionario;
import com.example.Gasteus.model.funcionario.Funcionario;
import com.example.Gasteus.model.funcionario.factory.FuncionarioFactory;
import com.example.Gasteus.model.funcionario.autenticacao.DadosAutenticacaoFuncionario;
import com.example.Gasteus.repository.FuncionarioRepository;
import com.example.Gasteus.repository.RoleRepository;
import com.example.Gasteus.security.DadosTokenJWT;
import com.example.Gasteus.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class ControllerFuncionario {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FuncionarioFactory funcionarioFactory;

    @PostMapping("/cadastro/funcionario")
    @Transactional
    public ResponseEntity cadastrar(@ModelAttribute @Valid DadosCadastroFuncionario dados, UriComponentsBuilder uriBuilder) throws IOException {
        if (funcionarioRepository.findByNroCarteira(dados.nroCarteira()).isPresent()) {
            return ResponseEntity.badRequest().body("Funcionário já cadastrado");
        }

        //ANTES da FACTORY:
//        Role funcRole = roleRepository.findByNome("ADMIN");
//        if (funcRole == null) {
//            return ResponseEntity.badRequest().body("Role ADMIN não encontrada.");
//        }
//
//        var funcionario = new Funcionario(dados);
//        funcionario.setRole(funcRole);
//
//        // Processar o documento, se fornecido
//        if (dados.curriculo() != null && !dados.curriculo().isEmpty()) {
//            try {
//                funcionario.setCurriculo(dados.curriculo().getBytes());
//            } catch (IOException e) {
//                return ResponseEntity.badRequest().body("Erro ao processar o documento.");
//            }
//        }

        //ANTES DO TEMPLATE
        //var funcionario= funcionarioFactory.criarFuncionario(dados);
        var funcionario = funcionarioFactory.criarUsuario(dados);
        funcionarioRepository.save(funcionario);
        return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
    }



    @PostMapping("/login/funcionario")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacaoFuncionario dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.nroCarteira(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


    @GetMapping("/admin")
    public ResponseEntity<DadosDetalhamentoFuncionario> obterFuncionarioLogado(Authentication authentication) {
        Funcionario funcionario = (Funcionario) authentication.getPrincipal();
        return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
    }
}
