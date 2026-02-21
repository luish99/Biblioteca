package com.biblioteca.model.usuario;

import com.biblioteca.model.Usuario;
import com.biblioteca.enums.TipoUsuario;
import java.time.LocalDate;


public class UsuarioComum extends Usuario{
    private String cpf;
    private String profissao;
    public UsuarioComum(String nome, int id, String email, String telefone, 
                        String endereco, String cpf, String profissao) {
        
        super(nome, id, email, telefone, endereco, LocalDate.now(), 0.0, TipoUsuario.COMUM);
        this.cpf = cpf;
        this.profissao = profissao;
    }
    //getters e setters
    public String getCpf() {
        return cpf;
    }   
    public String getProfissao() {
        return profissao;
    }
    @Override
    public int getLimiteLivros(){
        return 2;
    }
    @Override
    public int getDiasEmprestimo(){
        return 5;
    }
    @Override
    public String getTipo(){
        return "COMUM";
    }
}