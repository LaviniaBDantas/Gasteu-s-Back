package com.example.Gasteus.controller;
import com.example.Gasteus.model.adapter.AutenticacaoAdapter;
import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.factory.ClienteFactory;
import com.example.Gasteus.model.cliente.DadosCadastroCliente;
import com.example.Gasteus.model.cliente.DadosDetalhamentoCliente;
import com.example.Gasteus.model.cliente.autenticacaoANTES_ADAPTER.DadosAutenticacaoCliente;
import com.example.Gasteus.model.proxy.ClienteProxy;
import com.example.Gasteus.repository.ClienteRepository;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Autowired
    private ClienteFactory clienteFactory;

    @Autowired
    private AutenticacaoAdapter autenticacaoAdapter;

    @PostMapping("/cadastro/cliente")
    @Transactional
    public ResponseEntity<?> cadastrar(@ModelAttribute @Valid DadosCadastroCliente dados, UriComponentsBuilder uriBuilder) {
        // Verifica se o cliente já existe
        if (clienteRepositorio.findByCpf(dados.cpf()).isPresent()) {
            return ResponseEntity.badRequest().body("Cliente já cadastrado");
        }
//ANTES DO FACTORY:
//        // Busca a role USER
//        Role userRole = roleRepository.findByNome("USER");
//        if (userRole == null) {
//            return ResponseEntity.badRequest().body("Role USER não encontrada.");
//        }
//
//        // Cria o cliente e associa a role
//        var cliente = new Cliente(dados);
//        cliente.setRole(userRole);
//
//        // Processa a foto de perfil, se fornecida
//        if (dados.fotoPerfil() != null && !dados.fotoPerfil().isEmpty()) {
//            try {
//                cliente.setFotoPerfil(dados.fotoPerfil().getBytes());
//            } catch (IOException e) {
//                return ResponseEntity.badRequest().body("Erro ao processar a foto de perfil.");
//            }
//        }

        //ANTES DO TEMPLATE
        //Cliente cliente = clienteFactory.criarCliente(dados);
        Cliente cliente = clienteFactory.criarUsuario(dados);
        // Salva o cliente no repositório
        clienteRepositorio.save(cliente);
        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }

    //ANTES DO ADAPTER:
//    @PostMapping("/login/cliente")
//    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacaoCliente dados) {
//        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.cpf(), dados.senha());
//        var authentication = authenticationManager.authenticate(authenticationToken);
//        var tokenJWT = tokenService.gerarToken((UserDetails) authentication.getPrincipal());
//        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
//    }

    @PostMapping("/login/cliente")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacaoCliente dados) {
        try { //uso do adapter

            UserDetails userDetails = autenticacaoAdapter.autenticar(dados.cpf());
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), dados.senha());
            var authentication = authenticationManager.authenticate(authenticationToken);
            var tokenJWT = tokenService.gerarToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new DadosTokenJWT("Falha na autenticação: " + e.getMessage()));
        }
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
    ClienteProxy clienteProxy = new ClienteProxy(cliente);
    return clienteProxy.atualizarTelefone(novoTelefone, clienteRepositorio);
}


    @DeleteMapping("/user")
    @Transactional
    public ResponseEntity deletarConta(Authentication authentication) {
        Cliente cliente = (Cliente) authentication.getPrincipal();
        clienteRepositorio.delete(cliente);
        return ResponseEntity.ok("Conta deletada com sucesso.");
    }
}
