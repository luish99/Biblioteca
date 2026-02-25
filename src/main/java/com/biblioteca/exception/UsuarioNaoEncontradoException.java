package com.biblioteca.exception;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public UsuarioNaoEncontradoException(int id) {
        super("Usuario com ID " + id + " não encontrado");
    }
    public UsuarioNaoEncontradoException(String nome, int id){
        super("Usuário '" + nome + "' (ID: " + id + ") não encontrado");
    }
}
