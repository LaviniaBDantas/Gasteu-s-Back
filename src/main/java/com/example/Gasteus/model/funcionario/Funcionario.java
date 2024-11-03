package com.example.Gasteus.model.funcionario;

import com.example.Gasteus.model.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "funcionario")
public class Funcionario implements UserDetails {
    @Id
    private Long nroCarteira;
    private String nome;
    private String funcao;
    private Double salario;
    private LocalDate dataContratacao;
    private String senha;
    private String telefone;

    @Column(name = "curriculo", columnDefinition = "bytea")
    private byte[] curriculo;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;


    public Funcionario(DadosCadastroFuncionario dados) throws IOException {
        this.nroCarteira = dados.nroCarteira();
        this.nome = dados.nome();
        this.funcao = dados.funcao();
        this.telefone = dados.telefone();
        this.dataContratacao = dados.dataContratacao(); // Adicionado aqui
        this.senha = new BCryptPasswordEncoder().encode(dados.senha());
    }


    public Funcionario() {

    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return java.util.List.of(new SimpleGrantedAuthority("ROLE_" + role.getNome()));
//    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNome()));
        }
        return authorities;
    }


    // Implementação do método UserDetails
    @Override
    public String getPassword() {return senha;}

    @Override
    public String getUsername() {return String.valueOf(nroCarteira);}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getNroCarteira() {
        return nroCarteira;
    }

    public void setNroCarteira(Long nroCarteira) {
        this.nroCarteira = nroCarteira;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }
}
