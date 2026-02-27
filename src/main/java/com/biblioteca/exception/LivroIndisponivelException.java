package com.biblioteca.exception;

public class LivroIndisponivelException extends Exception {
    public LivroIndisponivelException(String mensagem) {
        super(mensagem);
    }
    public LivroIndisponivelException(int id) {
        super("Livro com ID " + id + " está indisponível.");
    }
}
