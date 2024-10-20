package Projeto.DAC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import Projeto.DAC.model.Usuario;
import Projeto.DAC.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }
	
	public Usuario listarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado") );
	}
	
	public String listarPorTelefone(Long id) {
		return usuarioRepository.findById(id).get().getEmail();            
	}
	
	public void excluir(Long id) {
		usuarioRepository.findById(id).map(usuario -> {
	        	usuarioRepository.delete(usuario);
	            return Void.TYPE;
	        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
	}
	
	public void editar(Long id, Usuario usuarioAtualizado) {
		usuarioRepository.findById(id)
        .map( usuario -> {
        	usuario.setNome(usuarioAtualizado.getNome());
        	usuario.setCpf(usuarioAtualizado.getCpf());
        	usuario.setData_nascimento(usuarioAtualizado.getData_nascimento());
        	usuario.setEndereco(usuarioAtualizado.getEndereco());
        	usuario.setTelefone(usuarioAtualizado.getTelefone());
        	usuario.setEmail(usuarioAtualizado.getEmail());
        	usuario.setSenha(usuarioAtualizado.getSenha());
            return usuarioRepository.save(usuario);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado") );
	}

}