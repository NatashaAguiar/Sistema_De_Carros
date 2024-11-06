package Projeto.DAC.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import Projeto.DAC.model.Carro;
import Projeto.DAC.repository.CarroRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	public UsuarioService usuarioService;
	
	public Carro salvar(Carro carro) {
		validarPermissaoAdmin();
		return carroRepository.save(carro);
	}
	
	public List<Carro> listarTop10() {
	    return carroRepository.findTop10ByOrderByCarroIdAsc();
	}
	
	public List<Carro> listarTodos(){
        return carroRepository.findAll();
    }
	
	public Carro listarPorId(Long id) {
		return carroRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado") );
	}
	
	public void excluir(Long id) {
		validarPermissaoAdmin();
		
		carroRepository.findById(id).map(carro -> {
			carroRepository.delete(carro);
	            return Void.TYPE;
	        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
	}
	
	public void editar(Long id, Carro carroAtualizado) {
		validarPermissaoAdmin();
		
		carroRepository.findById(id)
        .map( carro -> {
        	carro.setModelo(carroAtualizado.getModelo());
        	carro.setFabricante(carroAtualizado.getFabricante());
        	carro.setAno(carroAtualizado.getAno());
        	carro.setPreco(carroAtualizado.getPreco());
            return carroRepository.save(carro);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado") );
	}
	
	private void validarPermissaoAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new IllegalStateException("Permissão negada. Apenas administradores podem realizar esta operação.");
        }
    }

}
