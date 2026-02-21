package com.biblioteca.model.usuario;

import java.time.LocalDate;

import com.biblioteca.model.Usuario;
import com.biblioteca.enums.TipoUsuario;


public class Professor extends Usuario{
    private String departamento;
    private String formacaoAcademica;
    private LocalDate dataContratacao;

    public Professor(String nome, int id, String email, String telefone, 
                     String endereco, String departamento, 
                     String formacaoAcademica, LocalDate dataContratacao) {
        
        super(nome, id, email, telefone, endereco, LocalDate.now(), 0.0, TipoUsuario.PROFESSOR);
        this.departamento = departamento;
        this.formacaoAcademica = formacaoAcademica;
        this.dataContratacao = dataContratacao;
    }
    //getters e setters
    public String getDepartamento() {
        return departamento;
    }
    public String getFormacaoAcademica() {
        return formacaoAcademica;
    }
    public LocalDate getDataContratacao() {
        return dataContratacao;
    }  
    @Override
    public int getLimiteLivros(){
        return 5;
    }   
    @Override
    public int getDiasEmprestimo(){
        return 14;
    }
    @Override
    public String getTipo(){
        return "PROFESSOR";
    }

}