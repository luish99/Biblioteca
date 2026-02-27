package com.biblioteca.exception;

public class LivroNaoEncontradoException extends Exception {
    public LivroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public LivroNaoEncontradoException(String isbn, String autor) {
        super("Livro com ISBN '" + isbn + "' do autor '" + autor + "' não encontrado.");
    }
}
