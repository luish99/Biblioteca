package com.biblioteca.exception;

public class UsuarioInadimplenteException extends Exception {
    public UsuarioInadimplenteException(String mensagem) {
        super(mensagem);
    }
    public UsuarioInadimplenteException(int id) {
        super("Usuário com ID " + id + " está inadimplente.");
    }
}
