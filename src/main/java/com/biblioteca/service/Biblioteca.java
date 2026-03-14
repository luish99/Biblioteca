package com.biblioteca.service;

import com.biblioteca.exception.EmprestimoNaoEncontradoException;
import com.biblioteca.exception.EntidadeNulaException;
import com.biblioteca.exception.LimiteEmprestimosException;
import com.biblioteca.exception.LivroIndisponivelException;
import com.biblioteca.exception.LivroJaCadastradoException;
import com.biblioteca.exception.LivroNaoEncontradoException;
import com.biblioteca.exception.UsuarioInadimplenteException;
import com.biblioteca.exception.UsuarioJaCadastradoException;
import com.biblioteca.exception.UsuarioNaoEncontradoException;
import com.biblioteca.model.*;
import com.biblioteca.model.usuario.Aluno;
import com.biblioteca.model.usuario.Professor;
import com.biblioteca.model.usuario.UsuarioComum;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import java.time.LocalDate;
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

    public LivroRepository getLivroRepo() {
        return livroRepo;
    }

    public UsuarioRepository getUsuarioRepo() {
        return usuarioRepo;
    }
    public void cadastrarLivro(Livro livro) throws EntidadeNulaException, LivroJaCadastradoException {
        if(livro == null){
            throw new EntidadeNulaException("Livro não pode ser nulo");
        }
        Livro livroExistente = livroRepo.buscaPorTitulo(livro.getTitulo());
        if (livroExistente != null) {
            throw new LivroJaCadastradoException("Livro já cadastrado");
        }
        livroRepo.adicionarLivro(livro);
        System.out.println("Livro cadastrado com sucesso: " + livro.getTitulo());
    }   
    public String buscarLivroPorIsbn (String isbn) throws LivroNaoEncontradoException{
        Livro livro = livroRepo.buscaPorIsbn(isbn);
        if (livro != null) {
            return "Livro encontrado: " + livro.getTitulo();
        } else {
            throw new LivroNaoEncontradoException("Livro não encontrado com ISBN: " + isbn);
        }
    }
    
    public void cadastrarUsuario(Usuario usuario) throws EntidadeNulaException, UsuarioJaCadastradoException {
        if(usuario == null){
            throw new EntidadeNulaException("Usuário não pode ser nulo");
        }
        Usuario usuarioExistente = usuarioRepo.buscaPorNome(usuario.getNome());
        if (usuarioExistente != null) {
            throw new UsuarioJaCadastradoException("Usuário já cadastrado");
        }
        usuarioRepo.adicionarUsuario(usuario);
        System.out.println("Usuário cadastrado com sucesso: " + usuario.getNome());
    }
    public int buscaUsuarioPorId(int id) throws UsuarioNaoEncontradoException {
        Usuario usuario = usuarioRepo.buscarPorId(id);
        if (usuario != null) {
            return usuario.getId();
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + id);
        }
    }
    public Emprestimo realizarEmprestimo(int idUsuario, String isbn) 
        throws UsuarioNaoEncontradoException, 
               LivroNaoEncontradoException, 
               LivroIndisponivelException,
               LimiteEmprestimosException,
               UsuarioInadimplenteException {
    
    Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
    if (usuario == null) {
        throw new UsuarioNaoEncontradoException(idUsuario);  // Usa o construtor com ID
    }
    
    // Verificar se usuário pode pegar livro
    if (!usuario.podePegarLivro()) {
        if (usuario.getValorMulta() > 0) {
            throw new UsuarioInadimplenteException(usuario.getNome(), usuario.getId());
        } else {
            throw new LimiteEmprestimosException(usuario.getNome(), usuario.getId());
        }
    }
    
    Livro livro = livroRepo.buscaPorIsbn(isbn);
    if (livro == null) {
        throw new LivroNaoEncontradoException(isbn);  // Usa o construtor com ISBN
    }
    
    if (!livro.emprestar()) {  // Usa o método emprestar()
        throw new LivroIndisponivelException(livro.getTitulo() + " está indisponível para empréstimo");
    }
    
    LocalDate hoje = LocalDate.now();
    LocalDate dataPrevista = hoje.plusDays(usuario.getDiasEmprestimo());  // Prazo correto
    
    Emprestimo emprestimo = new Emprestimo(
        proximoIdEmprestimo++, 
        livro, 
        usuario, 
        hoje,
        dataPrevista,
        null, 
        0.0
    );
    
    emprestimos.add(emprestimo);
    usuario.adicionarEmprestimo(emprestimo);  // Adiciona ao histórico do usuário
    
    System.out.println("✅ Empréstimo realizado: " + livro.getTitulo() + 
                       " para " + usuario.getNome() + 
                       " - Devolução: " + dataPrevista);
    
    return emprestimo;
}
    public Emprestimo realizarDevolucao(int idEmprestimo) 
        throws EmprestimoNaoEncontradoException {
    
    // Busca o empréstimo 
    Emprestimo emprestimo = emprestimos.stream().filter(e -> e.getIdEmprestimo() == idEmprestimo).findFirst().orElseThrow(() -> new EmprestimoNaoEncontradoException(idEmprestimo));
    
    // Verifica se já foi devolvido
    if (emprestimo.getDataDevolucao() != null) {
        throw new IllegalStateException("Livro já devolvido em: " + emprestimo.getDataDevolucao());
    }
    
    // Processa a devolução (já calcula multa, atualiza estoque)
    emprestimo.processarDevolucao();
    
    System.out.println("✅ Devolução realizada: " + emprestimo.getLivro().getTitulo() + " por " + emprestimo.getUsuario().getNome());
    
    return emprestimo;
}
public void popularDadosTeste() {
    System.out.println("=== POPULANDO DADOS DE TESTE ===");
    
    // ===========================================
    // LIVROS - AGORA COM 6 PARÂMETROS!
    // ===========================================
    System.out.println("\n📚 Cadastrando livros...");

    try {
        // Machado de Assis
        cadastrarLivro(new Livro("Dom Casmurro", "Machado de Assis", 1899, "978-85-333-0227-3", 5, 5));
        cadastrarLivro(new Livro("O Alienista", "Machado de Assis", 1882, "978-85-333-1456-8", 3, 3));
        cadastrarLivro(new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881, "978-85-333-2457-4", 2, 2));
        cadastrarLivro(new Livro("Quincas Borba", "Machado de Assis", 1891, "978-85-333-3678-0", 4, 4));
        cadastrarLivro(new Livro("Helena", "Machado de Assis", 1876, "978-85-333-4789-1", 3, 3));

        // George Orwell
        cadastrarLivro(new Livro("1984", "George Orwell", 1949, "978-85-111-0227-3", 4, 4));
        cadastrarLivro(new Livro("A Revolução dos Bichos", "George Orwell", 1945, "978-85-111-0333-3", 6, 6));
        cadastrarLivro(new Livro("Dentro da Baleia", "George Orwell", 1936, "978-85-111-0444-4", 2, 2));

        // J.R.R. Tolkien
        cadastrarLivro(new Livro("O Senhor dos Anéis: A Sociedade do Anel", "J.R.R. Tolkien", 1954, "978-85-222-1111-1", 3, 3));
        cadastrarLivro(new Livro("O Senhor dos Anéis: As Duas Torres", "J.R.R. Tolkien", 1954, "978-85-222-2222-2", 3, 3));
        cadastrarLivro(new Livro("O Senhor dos Anéis: O Retorno do Rei", "J.R.R. Tolkien", 1955, "978-85-222-3333-3", 3, 3));
        cadastrarLivro(new Livro("O Hobbit", "J.R.R. Tolkien", 1937, "978-85-222-4444-4", 5, 5));
        cadastrarLivro(new Livro("Silmarillion", "J.R.R. Tolkien", 1977, "978-85-222-5555-5", 2, 2));

        // J.K. Rowling
        cadastrarLivro(new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", 1997, "978-85-333-6666-6", 7, 7));
        cadastrarLivro(new Livro("Harry Potter e a Câmara Secreta", "J.K. Rowling", 1998, "978-85-333-7777-7", 5, 5));
        cadastrarLivro(new Livro("Harry Potter e o Prisioneiro de Azkaban", "J.K. Rowling", 1999, "978-85-333-8888-8", 4, 4));
        cadastrarLivro(new Livro("Harry Potter e o Cálice de Fogo", "J.K. Rowling", 2000, "978-85-333-9999-9", 3, 3));

        // Autores diversos
        cadastrarLivro(new Livro("Cem Anos de Solidão", "Gabriel García Márquez", 1967, "978-85-444-1111-1", 4, 4));
        cadastrarLivro(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943, "978-85-555-1111-1", 8, 8));
        cadastrarLivro(new Livro("O Alquimista", "Paulo Coelho", 1988, "978-85-666-1111-1", 6, 6));
        cadastrarLivro(new Livro("A Culpa é das Estrelas", "John Green", 2012, "978-85-777-1111-1", 5, 5));
        cadastrarLivro(new Livro("O Diário de Anne Frank", "Anne Frank", 1947, "978-85-888-1111-1", 3, 3));
        cadastrarLivro(new Livro("A Arte da Guerra", "Sun Tzu", 500, "978-85-999-1111-1", 10, 10));
    } catch (EntidadeNulaException | LivroJaCadastradoException e) {
        System.out.println("⚠️ Erro ao cadastrar livro: " + e.getMessage());
    }

    System.out.println("✅ " + livroRepo.listarTodos().size() + " livros cadastrados!");
    
    // ===========================================
    // USUÁRIOS (continuam iguais)
    // ===========================================
    System.out.println("\n👥 Cadastrando usuários...");

    try {
        // Alunos
        cadastrarUsuario(new Aluno("João Silva", 101, "joao.silva@email.com", "11911111111",
                                  "Rua das Flores, 123 - São Paulo", 2023001, "Engenharia da Computação", 3, 2023));
        cadastrarUsuario(new Aluno("Maria Oliveira", 102, "maria.oliveira@email.com", "11922222222",
                                  "Av. Paulista, 456 - São Paulo", 2023002, "Medicina", 5, 2022));
        cadastrarUsuario(new Aluno("Pedro Santos", 103, "pedro.santos@email.com", "11933333333",
                                  "Rua Augusta, 789 - São Paulo", 2023003, "Direito", 2, 2024));
        cadastrarUsuario(new Aluno("Ana Costa", 104, "ana.costa@email.com", "11944444444",
                                  "Rua Oscar Freire, 321 - São Paulo", 2023004, "Arquitetura", 4, 2021));
        cadastrarUsuario(new Aluno("Carlos Souza", 105, "carlos.souza@email.com", "11955555555",
                                  "Rua Haddock Lobo, 654 - São Paulo", 2023005, "Administração", 6, 2020));

        // Professores
        cadastrarUsuario(new Professor("Dra. Mariana Lima", 201, "mariana.lima@email.com", "11811111111",
                                      "Rua dos Professores, 111 - São Paulo", "Ciência da Computação",
                                      "Doutorado em Inteligência Artificial", LocalDate.of(2015, 3, 10)));
        cadastrarUsuario(new Professor("Dr. Roberto Alves", 202, "roberto.alves@email.com", "11822222222",
                                      "Av. dos Educadores, 222 - São Paulo", "Medicina",
                                      "Doutorado em Cardiologia", LocalDate.of(2010, 8, 15)));
        cadastrarUsuario(new Professor("Dra. Fernanda Rocha", 203, "fernanda.rocha@email.com", "11833333333",
                                      "Rua da Universidade, 333 - São Paulo", "Direito",
                                      "Mestrado em Direito Civil", LocalDate.of(2018, 5, 20)));
        cadastrarUsuario(new Professor("Dr. Ricardo Mendes", 204, "ricardo.mendes@email.com", "11844444444",
                                      "Rua do Conhecimento, 444 - São Paulo", "Física",
                                      "Doutorado em Física Quântica", LocalDate.of(2012, 11, 5)));

        // Usuários Comuns
        cadastrarUsuario(new UsuarioComum("Luciana Ferreira", 301, "luciana.ferreira@email.com", "11711111111",
                                         "Rua dos Comuns, 111 - São Paulo", "123.456.789-01", "Advogada"));
        cadastrarUsuario(new UsuarioComum("Marcos Paulo", 302, "marcos.paulo@email.com", "11722222222",
                                         "Av. dos Anônimos, 222 - São Paulo", "987.654.321-01", "Engenheiro"));
        cadastrarUsuario(new UsuarioComum("Patrícia Gomes", 303, "patricia.gomes@email.com", "11733333333",
                                         "Rua dos Cidadãos, 333 - São Paulo", "456.789.123-01", "Médica"));
        cadastrarUsuario(new UsuarioComum("Fernando Dias", 304, "fernando.dias@email.com", "11744444444",
                                         "Rua do Povo, 444 - São Paulo", "789.123.456-01", "Professor"));
    } catch (EntidadeNulaException | UsuarioJaCadastradoException e) {
        System.out.println("⚠️ Erro ao cadastrar usuário: " + e.getMessage());
    }

    System.out.println("✅ " + usuarioRepo.listarTodos().size() + " usuários cadastrados!\n");
    
    // ===========================================
    // EMPRÉSTIMOS DE EXEMPLO
    // ===========================================
    System.out.println("📖 Realizando alguns empréstimos de exemplo...");
    
    try {
        // Aluno pegando livro
        realizarEmprestimo(101, "978-85-333-0227-3"); // Dom Casmurro
        realizarEmprestimo(102, "978-85-111-0227-3"); // 1984
        realizarEmprestimo(103, "978-85-222-4444-4"); // O Hobbit
        realizarEmprestimo(104, "978-85-333-6666-6"); // Harry Potter 1
        
        // Professor pegando livros
        realizarEmprestimo(201, "978-85-333-1456-8"); // O Alienista
        realizarEmprestimo(201, "978-85-333-2457-4"); // Memórias Póstumas
        realizarEmprestimo(202, "978-85-222-1111-1"); // Senhor dos Anéis 1
        
        // Usuário comum pegando livro
        realizarEmprestimo(301, "978-85-555-1111-1"); // O Pequeno Príncipe
        
        System.out.println("✅ Empréstimos realizados com sucesso!");
    } catch (Exception e) {
        System.out.println("⚠️ Erro em empréstimo de exemplo: " + e.getMessage());
    }
}
}