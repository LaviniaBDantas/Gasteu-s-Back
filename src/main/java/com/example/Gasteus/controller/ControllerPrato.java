package com.example.Gasteus.controller;

import com.example.Gasteus.model.prato.*;
import com.example.Gasteus.repository.PratoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ControllerPrato {

    @Autowired
    private PratoRepository pratoRepository;


    // Rota para ADMIN: Cadastrar um novo prato
    @PostMapping("/admin/prato")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<DadosDetalhamentoExtraPrato> cadastrar(@RequestBody @Valid DadosCadastroPrato dados) {
        var prato = new Prato();
        prato.setNome(dados.nome());
        prato.setPreco(dados.preco());
        prato.setDescricao(dados.descricao());
        prato.setModoPreparo(dados.modoPreparo());
        pratoRepository.save(prato);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoExtraPrato(prato));
    }

    // Rota para ADMIN: Atualizar o preço de um prato
    @PutMapping("/admin/prato/{cod}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<DadosDetalhamentoPrato> atualizar(@PathVariable Integer cod, @RequestBody @Valid DadosAtualizaPrato dados) {
        var pratoOptional = pratoRepository.findByCod(cod);
        if (pratoOptional.isPresent()) {
            Prato prato = pratoOptional.get();

            // Verifica e atualiza cada campo apenas se o dado não for nulo
            if (dados.nome() != null) {
                prato.setNome(dados.nome());
            }
            if (dados.preco() != null) {
                prato.setPreco(dados.preco());
            }
            if (dados.descricao() != null) {
                prato.setDescricao(dados.descricao());
            }

            pratoRepository.save(prato);
            return ResponseEntity.ok(new DadosDetalhamentoPrato(prato));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // Rota para ADMIN: Deletar um prato
    @DeleteMapping("/admin/prato/{cod}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Integer cod) {
        var pratoOptional = pratoRepository.findByCod(cod);
        if (pratoOptional.isPresent()) {
            pratoRepository.delete(pratoOptional.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Rota para USER e ADMIN: Listar todos os pratos
    @GetMapping("/pratos")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<DadosDetalhamentoPrato>> listar() {
        List<Prato> pratos = pratoRepository.findAll();
        return ResponseEntity.ok(pratos.stream().map(DadosDetalhamentoPrato::new).toList());
    }
    //Mostrar modo de preparo do prato
    @GetMapping("/admin/prato/{cod}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoExtraPrato> detalhes(@PathVariable Integer cod) {
        var pratoOptional = pratoRepository.findByCod(cod);
        if (pratoOptional.isPresent()) {
            Prato prato = pratoOptional.get();
            return ResponseEntity.ok(new DadosDetalhamentoExtraPrato(prato));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
