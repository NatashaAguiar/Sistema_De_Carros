package Projeto.DAC.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import Projeto.DAC.model.Usuario;
import Projeto.DAC.repository.UsuarioRepository;
import Projeto.DAC.service.ValidacaoDeSenha.ValidarSenha;

@Service
public class UsuarioService /*implements UserDetailsService*/{
		
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	PasswordEncoder encoder;
	
	public Usuario salvar(Usuario usuario) {
		ValidarSenha.validar(usuario.getSenha());
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		String assunto = "Cadastro realizado com sucesso!!";
		String mensagem = String.format("Olá, %s!\n\nSeu cadastro foi realizado com sucesso.\n", usuarioSalvo.getNome());
		
		emailService.enviarEmail(usuarioSalvo.getEmail(), assunto, mensagem);
		return usuarioSalvo;
	}
	
	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<Usuario> usuarioOpt;

	    if (username.matches("\\d{11}")) { 
	        usuarioOpt = usuarioRepository.findByCpf(username);
	    } else { 
	        usuarioOpt = usuarioRepository.findByMatricula(username);
	    }

	    Usuario usuario = usuarioOpt.orElseThrow(() -> 
	        new UsernameNotFoundException("Usuário não encontrado: " + username));

	    String role = (usuario.getMatricula() != null) ? "ADMIN" : "USER"; 
	    return org.springframework.security.core.userdetails.User.builder()
	        .username(username)
	        .password(usuario.getSenha()) 
	        .authorities(role) 
	        .build();
	}*/
	
	public ResponseEntity<Boolean> validarSenha(@RequestParam String cpf, @RequestParam String senha){
		
		Optional<Usuario> optUsuario = usuarioRepository.findByCpf(cpf);
		if(optUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		Usuario usuario = optUsuario.get();
		boolean valid = encoder.matches(senha, usuario.getSenha());
		
		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

		return ResponseEntity.status(status).body(valid);
	}
	
	public void alterarSenha(Long id, String novaSenha) {
	    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

	    ValidarSenha.validar(novaSenha);
	    usuario.setSenha(encoder.encode(novaSenha));
	    usuarioRepository.save(usuario);
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