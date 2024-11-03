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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
//@RequestMapping("/reserva")
public class ControllerReserva {

    @Autowired
    private ReservaRepository reservaRepositorio;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/reserva")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroReserva dados,
                                       UriComponentsBuilder uriBuilder,
                                       Authentication autenticado) {
        System.out.println("Fazendo reserva...");

        // Pega o CPF do cliente autenticado
        String cpfCliente = autenticado.getName();

        // Busca o cliente no repositório usando o CPF
        Optional<Cliente> optionalCliente = clienteRepository.findByCpf(cpfCliente);
        if (optionalCliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Cliente cliente = optionalCliente.get();
        var reserva = new Reserva(dados, cliente);
        reservaRepositorio.save(reserva);

        var uri = uriBuilder.path("/reserva/{id}").buildAndExpand(reserva.getCod()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoReserva(reserva));
    }
}
