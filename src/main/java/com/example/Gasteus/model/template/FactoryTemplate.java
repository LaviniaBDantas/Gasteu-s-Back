package com.example.Gasteus.model.template;

import com.example.Gasteus.model.Role;
import com.example.Gasteus.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FactoryTemplate<T,D> {

    @Autowired
    protected RoleRepository roleRepository;
    protected abstract Role obterRole();
    protected abstract void atribuirRole (T usuario, Role role);
    protected abstract T criarInstanciaUsuario(D dadosCadastro);
    protected abstract void processarDadosEspecificos(D dadosCadastro,T usuario);
    public T criarUsuario(D dadosCadastro){
        Role role= obterRole();
        if (role == null){
            throw new IllegalArgumentException("Role não encontrada");
        }
        T usuario = criarInstanciaUsuario(dadosCadastro);
        atribuirRole(usuario,role);
        processarDadosEspecificos(dadosCadastro, usuario);
        return usuario;
    }

}
