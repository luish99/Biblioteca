package com.biblioteca;

import com.biblioteca.model.Livro;
import com.biblioteca.service.Biblioteca;

public class Main {
    public static void main(String[] args) {
        // Exemplo de teste:
        Biblioteca biblioteca = new Biblioteca();

        Livro livro1 = new Livro("Dom Casmurro", "Machado de Assis", 1899, "123", 5, 5);
        biblioteca.cadastrarLivro(livro1);

        String resultado = biblioteca.buscarLivroPorIsbn("123");
        System.out.println(resultado);
    }
}