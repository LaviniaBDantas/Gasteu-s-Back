package com.example.Gasteus.controller;

import java.util.List;

import com.example.Gasteus.model.proxy.PratoProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gasteus.model.prato.DadosAtualizaPrato;
import com.example.Gasteus.model.prato.DadosCadastroPrato;
import com.example.Gasteus.model.prato.DadosDetalhamentoExtraPrato;
import com.example.Gasteus.model.prato.DadosDetalhamentoPrato;
import com.example.Gasteus.model.prato.Prato;
import com.example.Gasteus.repository.PratoRepository;

import jakarta.validation.Valid;

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
        var prato = new Prato(dados);
        pratoRepository.save(prato);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoExtraPrato(prato));
    }

    // Rota para ADMIN: Atualizar os dados de um prato usando o Proxy
    @PutMapping("/admin/prato/{cod}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Integer cod, @RequestBody @Valid DadosAtualizaPrato dados) {
        Prato prato = pratoRepository.findByCod(cod).orElse(null);
        if (prato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato não encontrado");
        }
        PratoProxy pratoProxy = new PratoProxy(pratoRepository);
        return pratoProxy.atualizarPrato(cod, dados);
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

    // Mostrar modo de preparo do prato
    @GetMapping("/admin/prato/{cod}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoExtraPrato> detalhes(@PathVariable Integer cod) {
        var pratoOptional = pratoRepository.findByCod(cod);
        if (pratoOptional.isPresent()) {
            Prato prato = pratoOptional.get();
            return ResponseEntity.ok(new DadosDetalhamentoExtraPrato(prato));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
