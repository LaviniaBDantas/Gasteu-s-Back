package com.example.Gasteus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ControllerRelatorio {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Apenas usuários com role 'ADMIN' podem acessar essa rota
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/relatorioReservasClientes")
    public List<Map<String, Object>> getRelatorioReservasClientes() {
        String sql = """
            SELECT EXTRACT(YEAR FROM data_hora_reserva) AS ano,
                   EXTRACT(MONTH FROM data_hora_reserva) AS mes,
                   SUM(valor_pedido) AS valor_total_reservas_mes
            FROM relatorio_reservas_clientes
            GROUP BY ano, mes
            ORDER BY ano, mes;
        """;
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/relatorioAvaliacao")
    public List<Map<String, Object>> getRelatorioAvaliacao() {
        String sql = """
            SELECT * FROM relatorio_avaliacoes_prato;
        """;
        return jdbcTemplate.queryForList(sql);
    }
}
