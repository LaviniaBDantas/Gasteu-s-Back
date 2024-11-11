package com.example.Gasteus.model.proxy;

import com.example.Gasteus.model.prato.DadosAtualizaPrato;
import com.example.Gasteus.model.prato.Prato;
import com.example.Gasteus.repository.PratoRepository;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

//O papel do Proxy aqui é atuar como uma intermediario entre o ControllerPrato e o PratoRepository, 
//encapsulando a lógica de atualização do objeto Prato.

public class PratoProxy {

    private final PratoRepository pratoRepository;

    public PratoProxy(PratoRepository pratoRepository) {
        this.pratoRepository = pratoRepository;
    }

    public ResponseEntity<?> atualizarPrato(Integer cod, DadosAtualizaPrato dados) {
        Optional<Prato> pratoOptional = pratoRepository.findByCod(cod);
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
            return ResponseEntity.ok(prato);
        }
        return ResponseEntity.status(404).build();
    }
}

