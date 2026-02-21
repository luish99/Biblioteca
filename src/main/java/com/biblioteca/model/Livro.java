package com.biblioteca.model;

public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String isbn;
    private int quantidadeTotalEstoque;
    private int quantidadeDisponivelEstoque;

    public Livro(String titulo, String autor, int anoPublicacao, String isbn, int quantidadeTotalEstoque, int quantidadeDisponivelEstoque) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
        this.quantidadeTotalEstoque = quantidadeTotalEstoque;
        this.quantidadeDisponivelEstoque = quantidadeDisponivelEstoque;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantidadeTotalEstoque() {
        return quantidadeTotalEstoque;
    }

    public int getQuantidadeDisponivelEstoque() {
        return quantidadeDisponivelEstoque;
    }

    // Setters
    

    public void setQuantidadeTotalEstoque(int quantidadeTotalEstoque) {
        this.quantidadeTotalEstoque = quantidadeTotalEstoque;
    }

    public void setQuantidadeDisponivelEstoque(int quantidadeDisponivelEstoque) {
        this.quantidadeDisponivelEstoque = quantidadeDisponivelEstoque;
    }

    // Métodos para atualizar o estoque
    public boolean  emprestar(){
        if(quantidadeDisponivelEstoque > 0){
            quantidadeDisponivelEstoque -= 1;
            return true;
        }
        else{
            System.out.println("Livro indisponível para empréstimo.");
            return false;
        }
    }
    public boolean  devolver(){
        if(quantidadeDisponivelEstoque < quantidadeTotalEstoque){
            quantidadeDisponivelEstoque += 1;
            return true;
        }
        else{
            System.out.println("Erro ao devolver o livro. Estoque já está completo.");
            return false;
        }
    }
    public boolean isDisponivel(){
        return quantidadeDisponivelEstoque > 0;
    }


}

