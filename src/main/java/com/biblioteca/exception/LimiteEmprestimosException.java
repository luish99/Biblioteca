package com.biblioteca.exception;

public class LimiteEmprestimosException extends Exception {
    
    public LimiteEmprestimosException(String mensagem) {
        super(mensagem);
    }
    public LimiteEmprestimosException(int id) {
        super("Usuário com ID " + id + " atingiu o limite de empréstimos permitidos.");
    }
    public LimiteEmprestimosException(String nome, int id) {
        super("Usuário '" + nome + "' (ID: " + id + ") atingiu o limite de empréstimos permitidos.");
    }

}
