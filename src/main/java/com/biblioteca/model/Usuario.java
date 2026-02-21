package com.biblioteca.model;

import com.biblioteca.enums.TipoUsuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String nome;
    private int id;
    private String email;
    private String telefone;
    private String endereco;
    private LocalDate dataCadastro;
    private double valorMulta;
    private List<Emprestimo> emprestimos;
    private TipoUsuario tipoUsuario;

    public Usuario(String nome, int id, String email, String telefone, String endereco, LocalDate dataCadastro, double valorMulta, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
        this.valorMulta = valorMulta;
        this.emprestimos = new ArrayList<>();
        this.tipoUsuario = tipoUsuario;
    }

    public String getNome() {
        return nome;
    }
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getEndereco() {
        return endereco;
    }
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
    public double getValorMulta() {
        return valorMulta;
    }
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public abstract int getLimiteLivros();
    public abstract int getDiasEmprestimo();
    public abstract String getTipo();

    public boolean podePegarLivro(){
        return (this.valorMulta <= 0 && this.getQuantidadeEmprestimosAtivos() < this.getLimiteLivros());
            
    }
    public void adicionarMulta(double valor){
        this.valorMulta += valor;
    }
    public void pagarMulta(){
        this.valorMulta = 0;
    }
    public void adicionarEmprestimo(Emprestimo e) {
        this.emprestimos.add(e);
    }
    public List<Emprestimo> getEmprestimosAtivos(){
        List<Emprestimo> ativos = new ArrayList<>();
        for(Emprestimo e : emprestimos){
            if(e.getDataDevolucao() == null){
                ativos.add(e);
            }
        }
        return ativos;
    }
    public int getQuantidadeEmprestimosAtivos(){
        return this.getEmprestimosAtivos().size();
    }

}
