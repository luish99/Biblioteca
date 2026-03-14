package com.biblioteca;

import com.biblioteca.exception.*;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.Biblioteca;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        
        // POPULAR DADOS DE TESTE!
        biblioteca.popularDadosTeste();
        
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        
        while (opcao != 9) {
            System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
            System.out.println("1. Listar todos os livros");
            System.out.println("2. Listar todos os usuários");
            System.out.println("3. Buscar livro por ISBN");
            System.out.println("4. Buscar usuário por ID");
            System.out.println("5. Realizar empréstimo");
            System.out.println("6. Realizar devolução");
            System.out.println("7. Listar livros disponíveis");
            System.out.println("8. Listar empréstimos ativos");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        System.out.println("\n=== LIVROS CADASTRADOS ===");
                        biblioteca.getLivroRepo().listarLivros();
                        break;
                        
                    case 2:
                        System.out.println("\n=== USUÁRIOS CADASTRADOS ===");
                        biblioteca.getUsuarioRepo().listarUsuarios();
                        break;
                        
                    case 3:
                        System.out.print("Digite o ISBN do livro: ");
                        String isbn = scanner.nextLine();
                        try {
                            Livro livro = biblioteca.getLivroRepo().buscaPorIsbn(isbn);
                            if (livro != null) {
                                System.out.println("📖 Título: " + livro.getTitulo());
                                System.out.println("   Autor: " + livro.getAutor());
                                System.out.println("   ISBN: " + livro.getIsbn());
                                System.out.println("   Disponível: " + (livro.isDisponivel() ? "Sim" : "Não"));
                            } else {
                                System.out.println("❌ Livro não encontrado!");
                            }
                        } catch (Exception e) {
                            System.out.println("❌ Erro: " + e.getMessage());
                        }
                        break;
                        
                    case 4:
                        System.out.print("Digite o ID do usuário: ");
                        try {
                            int id = Integer.parseInt(scanner.nextLine());
                            Usuario usuario = biblioteca.getUsuarioRepo().buscarPorId(id);
                            if (usuario != null) {
                                System.out.println("👤 Nome: " + usuario.getNome());
                                System.out.println("   ID: " + usuario.getId());
                                System.out.println("   Email: " + usuario.getEmail());
                                System.out.println("   Tipo: " + usuario.getTipo());
                                System.out.println("   Multa: R$ " + String.format("%.2f", usuario.getValorMulta()));
                            } else {
                                System.out.println("❌ Usuário não encontrado!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ ID deve ser um número");
                        }
                        break;
                        
                    case 5:
                        System.out.println("\n=== REALIZAR EMPRÉSTIMO ===");
                        System.out.print("ID do usuário: ");
                        int idUser = Integer.parseInt(scanner.nextLine());
                        System.out.print("ISBN do livro: ");
                        String isbnLivro = scanner.nextLine();
                        
                        try {
                            biblioteca.realizarEmprestimo(idUser, isbnLivro);
                        } catch (UsuarioNaoEncontradoException e) {
                            System.out.println("❌ " + e.getMessage());
                        } catch (LivroNaoEncontradoException e) {
                            System.out.println("❌ " + e.getMessage());
                        } catch (LivroIndisponivelException e) {
                            System.out.println("❌ " + e.getMessage());
                        } catch (LimiteEmprestimosException e) {
                            System.out.println("❌ " + e.getMessage());
                        } catch (UsuarioInadimplenteException e) {
                            System.out.println("❌ " + e.getMessage());
                        }
                        break;
                        
                    case 6:
                        System.out.println("\n=== REALIZAR DEVOLUÇÃO ===");
                        System.out.print("ID do empréstimo: ");
                        int idEmprestimo = Integer.parseInt(scanner.nextLine());
                        
                        try {
                            biblioteca.realizarDevolucao(idEmprestimo);
                        } catch (EmprestimoNaoEncontradoException e) {
                            System.out.println("❌ " + e.getMessage());
                        }
                        break;
                        
                    case 7:
                        System.out.println("\n=== LIVROS DISPONÍVEIS ===");
                        for (Livro l : biblioteca.getLivroRepo().listarTodos()) {
                            if (l.isDisponivel()) {
                                System.out.println("📖 " + l.getTitulo() + " - " + 
                                                   l.getAutor() + " (" + 
                                                   l.getQuantidadeDisponivelEstoque() + " disponíveis)");
                            }
                        }
                        break;
                        
                    case 8:
                        System.out.println("\n=== EMPRÉSTIMOS ATIVOS ===");
                        // Você precisará de um getter para a lista de empréstimos na Biblioteca
                        // Por enquanto, vamos pular ou implementar depois
                        System.out.println("Funcionalidade em desenvolvimento...");
                        break;
                        
                    case 9:
                        System.out.println("Saindo do sistema...");
                        break;
                        
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
            }
        }
        
        scanner.close();
    }
}