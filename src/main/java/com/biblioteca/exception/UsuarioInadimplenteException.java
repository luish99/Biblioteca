package com.biblioteca.exception;

public class UsuarioInadimplenteException extends Exception {
    public UsuarioInadimplenteException(String mensagem) {
        super(mensagem);
    }
    public UsuarioInadimplenteException(int id) {
        super("Usuário com ID " + id + " é inadimplente e não pode realizar empréstimos.");
    }
    public UsuarioInadimplenteException(String nome, int id) {
        super("Usuário '" + nome + "' (ID: " + id + ") é inadimplente e não pode realizar empréstimos.");
    }
}
