package com.biblioteca.model.usuario;

import java.time.LocalDate;

import com.biblioteca.model.Usuario;
import com.biblioteca.enums.TipoUsuario;

public class Aluno extends Usuario{
    private int matricula;
    private String curso;
    private int periodo;
    private int anoIngresso;

    public Aluno(String nome, int id, String email, String telefone, 
                 String endereco, int matricula, String curso, 
                 int periodo, int anoIngresso) {
        
        super(nome, id, email, telefone, endereco, LocalDate.now(), 0.0, TipoUsuario.ALUNO);
        this.matricula = matricula;
        this.curso = curso;
        this.periodo = periodo;
        this.anoIngresso = anoIngresso;
    }
    //getters e setters
    public int getMatricula() {
        return matricula;
    }
    public String getCurso() {
        return curso;
    }
    public int getPeriodo() {
        return periodo;
    }
    public int getAnoIngresso() {
        return anoIngresso;
    }
    @Override
    public int getLimiteLivros(){
        return 3;
    }   
    @Override
    public int getDiasEmprestimo(){
        return 7;
    }
    @Override
    public String getTipo(){
        return "ALUNO";
    }



}