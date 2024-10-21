package Projeto.DAC.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import Projeto.DAC.model.Usuario;
import Projeto.DAC.repository.UsuarioRepository;

@Service
public class LoginService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	public Usuario autenticar(String identificador, String senha) {
		Optional<Usuario> usuarioOpt;
		
		if(identificador != null && identificador.matches("\\d{11}")) {
			usuarioOpt =  usuarioRepository.findByCpf(identificador); 
		}else {
			usuarioOpt = usuarioRepository.findByMatricula(identificador);
		}
		
		 Usuario usuario  = usuarioOpt.orElseThrow(() -> 
				new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
		
        if (!usuario.getSenha().equals(senha)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha incorreta");
        }
		      
		return usuario;
	}	
}
