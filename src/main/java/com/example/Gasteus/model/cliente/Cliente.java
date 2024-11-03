package com.example.Gasteus.model.cliente;

import com.example.Gasteus.model.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente implements UserDetails {
    @Id
    private String cpf;
    private String nome;
    private String telefone;
    private String senha;

    @Column(name = "foto_perfil", columnDefinition = "bytea")
    private byte[] fotoPerfil;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;


    public Cliente (DadosCadastroCliente dados){
        this.cpf=dados.cpf();
        this.nome=dados.nome();
        this.telefone=dados.telefone();
        this.senha=new BCryptPasswordEncoder().encode(dados.senha());
    }

    public Cliente() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getNome()));
    }


    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    public void setRole(Role userRole) {
        role=userRole;
    }

    public Role getRole() {
        return role;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
