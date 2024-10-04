package com.example.Gasteus.controller;

import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.reserva.DadosCadastroReserva;
import com.example.Gasteus.model.reserva.DadosDetalhamentoReserva;
import com.example.Gasteus.model.reserva.Reserva;
import com.example.Gasteus.repository.ClienteRepository;
import com.example.Gasteus.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Importação correta
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reserva")
public class ControllerReserva {

    @Autowired
    private ReservaRepository reservaRepositorio;
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroReserva dados,
                                    UriComponentsBuilder uriBuilder,
                                    Authentication autenticado) {
        // Verifica se o usuário n está autenticado
        if (!autenticado.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // pegar o CPF do cliente autenticado
        String cpfCliente = autenticado.getName();

        //buscar o cliente no repositório usando o CPF
        Cliente cliente = clienteRepository.findByCpf(cpfCliente);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var reserva = new Reserva(dados, cliente); // Passa o cliente para a reserva
        reservaRepositorio.save(reserva);

        var uri = uriBuilder.path("/reserva/{id}").buildAndExpand(reserva.getCod()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoReserva(reserva));
    }
}
