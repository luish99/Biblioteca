package com.biblioteca.service;

import com.biblioteca.model.*;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private LivroRepository livroRepo;
    private UsuarioRepository usuarioRepo;
    private List<Emprestimo> emprestimos;
    private int proximoIdEmprestimo;

    public Biblioteca() {
        this.livroRepo = new LivroRepository();
        this.usuarioRepo = new UsuarioRepository();
        this.emprestimos = new ArrayList<>();
        this.proximoIdEmprestimo = 1;
    }
    public void cadastrarLivro(Livro livro) {
        if(livro == null){
            throw new IllegalArgumentException("Livro não pode ser nulo");
        }
        Livro livroExistente = livroRepo.buscaPorTitulo(livro.getTitulo());
        if (livroExistente != null) {
            throw new IllegalArgumentException("Livro já cadastrado");
        }
        livroRepo.adicionarLivro(livro);
        System.out.println("Livro cadastrado com sucesso: " + livro.getTitulo());
    }   
    public String buscarLivroPorIsbn(String isbn) {
        Livro livro = livroRepo.buscaPorIsbn(isbn);
        if (livro != null) {
            return "Livro encontrado: " + livro.getTitulo();
        } else {
            return "Livro não encontrado com ISBN: " + isbn;
        }
    }
    
    public void cadastrarUsuario(Usuario usuario) {
        if(usuario == null){
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        Usuario usuarioExistente = usuarioRepo.buscaPorNome(usuario.getNome());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
        usuarioRepo.adicionarUsuario(usuario);
        System.out.println("Usuário cadastrado com sucesso: " + usuario.getNome());
    }
    public int buscaUsuarioPorId(int id) {
        Usuario usuario = usuarioRepo.buscarPorId(id);
        if (usuario != null) {
            return usuario.getId();
        } else {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
    }

}