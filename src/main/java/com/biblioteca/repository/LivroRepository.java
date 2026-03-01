package com.biblioteca.repository;
import com.biblioteca.model.Livro;
import java.util.ArrayList;
import java.util.List;


public class LivroRepository  { 
    private List<Livro> livros = new ArrayList<>();

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public List<Livro> listarTodos(){
        return new ArrayList<>(livros);
    }
    public void listarLivros() {
        listarTodos().forEach(System.out::println );
    }
    public Livro buscaPorIsbn(String isbn)    
    {
        for(Livro livro : livros) { 
            if(livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }
    public Livro buscaPorTitulo(String titulo){
        for(Livro livro : livros){
            if(livro.getTitulo().equals(titulo)){
                return livro;
            }
        }
        return null;
    }
    public void removerPorIsbn(String isbn){
        Livro livro = buscaPorIsbn(isbn);
        if(livro != null){
            livros.remove(livro);
        }
    }
}   
