package com.biblioteca.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private int idEmprestimo;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private double valorMulta;
    private static final double VALOR_MULTA_DIARIA = 1.50;
    
    public Emprestimo(int idEmprestimo, Livro livro, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao, double valorMulta) {
        this.idEmprestimo = idEmprestimo;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.valorMulta = valorMulta;
}

// getters e setters
public int getIdEmprestimo() {
    return idEmprestimo;
} 
public Livro getLivro() {
    return livro;
}
public Usuario getUsuario() {
    return usuario;
}

public LocalDate getDataEmprestimo() {
    return dataEmprestimo;
}

public LocalDate getDataPrevistaDevolucao() {
    return dataPrevistaDevolucao;
}

public LocalDate getDataDevolucao() {
    return dataDevolucao;
}

public double getValorMulta() {
    return valorMulta;
}



public void setDataDevolucao(LocalDate dataDevolucao) {
    this.dataDevolucao = dataDevolucao;
}

public void setValorMulta(double valorMulta) {
    this.valorMulta = valorMulta;
}

public boolean  estaAtrasado(){
    if (dataDevolucao != null) {
        // Já foi devolvido: verifica se devolveu DEPOIS do prazo
        return dataDevolucao.isAfter(dataPrevistaDevolucao);
    } else {
        // Ainda não devolveu: verifica se a data de HOJE passou do prazo
        return LocalDate.now().isAfter(dataPrevistaDevolucao);
    }
}

private long diasEmAtraso() {
    // 1. Escolhe qual data usar para comparação
    LocalDate dataComparacao;
    
    if (dataDevolucao != null) {
        // Se já devolveu, usa a data da devolução
        dataComparacao = dataDevolucao;
    } else {
        // Se não devolveu, usa a data atual
        dataComparacao = LocalDate.now();
    }
    
    // 2. Se não passou do prazo, retorna 0
    if (!dataComparacao.isAfter(dataPrevistaDevolucao)) {
        return 0;
    }
    
    // 3. Calcula a diferença em dias entre as datas
    return ChronoUnit.DAYS.between(dataPrevistaDevolucao, dataComparacao);
}
private double calcularValorMulta(){
    if(estaAtrasado()){
        long diasAtraso = diasEmAtraso();
        return diasAtraso * VALOR_MULTA_DIARIA;
    }
    return 0;
}

public void processarDevolucao() {
    
    this.dataDevolucao = LocalDate.now();
    this.valorMulta = calcularValorMulta();
    if(valorMulta > 0){
        usuario.adicionarMulta(valorMulta);
    }
    this.livro.devolver();
    System.out.println("Livro devolvido com sucesso em: " + dataDevolucao);
    }
}