package com.biblioteca.repository;
import com.biblioteca.model.Usuario;
import java.util.ArrayList;
import java.util.List;



public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();
    
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    public List<Usuario> listarTodos(){
        return new ArrayList<>(usuarios);
    }
    public void listarUsuarios() {
        listarTodos().forEach(System.out::println );
    }
    public Usuario buscarPorId(int id){
        for(Usuario usuario : usuarios){
            if(usuario.getId() == id){
                return usuario;
            }
        }
        return null;
    }
    public Usuario buscaPorNome(String nome){
        for(Usuario usuario : usuarios){
            if(usuario.getNome().equals(nome)){
                return usuario;
            }
        }
        return null;
    }
    public void removerPorId(int id){
        Usuario usuario = buscarPorId(id);
        if(usuario != null){
            usuarios.remove(usuario);
        }
    }
}
