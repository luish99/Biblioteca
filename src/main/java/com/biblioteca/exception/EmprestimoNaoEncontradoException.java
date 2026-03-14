package com.biblioteca.exception;

public class EmprestimoNaoEncontradoException extends Exception {
    public EmprestimoNaoEncontradoException(int id) {
        super("Empréstimo com ID " + id + " não encontrado");
    }
}