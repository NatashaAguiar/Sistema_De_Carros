package Projeto.DAC.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Projeto.DAC.model.Usuario;
import Projeto.DAC.repository.UsuarioRepository;
import Projeto.DAC.service.ValidacaoDeSenha.ValidarSenha;

@Service
public class UsuarioService {
		
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EmailService emailService;
	
	public Usuario salvar(Usuario usuario) {
		ValidarSenha.validar(usuario.getSenha());
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		String assunto = "Cadastro realizado com sucesso!!";
		String mensagem = String.format("Olá, %s!\n\nSeu cadastro foi realizado com sucesso.\n", usuarioSalvo.getNome());
		
		emailService.enviarEmail(usuarioSalvo.getEmail(), assunto, mensagem);
		return usuarioSalvo;
	}
	
	public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }
	
	public Usuario listarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado") );
	}
	
	public Usuario listarPorCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado") );
	}
	
	public void excluir(Long id) {
		usuarioRepository.findById(id).map(usuario -> {
	        	usuarioRepository.delete(usuario);
	            return Void.TYPE;
	        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
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
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado") );
	}

}