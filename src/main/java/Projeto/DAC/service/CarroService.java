package Projeto.DAC.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Projeto.DAC.model.Carro;
import Projeto.DAC.model.Usuario;
import Projeto.DAC.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	public UsuarioService usuarioService;
	
	public Carro salvar(Carro carro, Long Id) {
		
		Usuario usuario = usuarioService.listarPorId(Id);
		
		String loginType = (usuario != null && usuario.getMatricula() != null) ? "ADMIN" : "USER";
		if(loginType.equals("USER")) 
		{
			throw new IllegalStateException("Usuários comum não têm permissão para inserir carros.");
		}
		
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
	
	public void excluir(Long id, Long usuarioId) {
		
		Usuario usuario = usuarioService.listarPorId(usuarioId);
		
		String loginType = (usuario != null && usuario.getMatricula() != null) ? "ADMIN" : "USER";
		if(loginType.equals("USER")) 
		{
			throw new IllegalStateException("Usuários comum não têm permissão para inserir carros.");
		}
		
		carroRepository.findById(id).map(carro -> {
			carroRepository.delete(carro);
	            return Void.TYPE;
	        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
	}
	
	public void editar(Long id, Carro carroAtualizado, Long usuarioId) {
		
		Usuario usuario = usuarioService.listarPorId(usuarioId);
		
		String loginType = (usuario != null && usuario.getMatricula() != null) ? "ADMIN" : "USER";
		if(loginType.equals("USER")) 
		{
			throw new IllegalStateException("Usuários comum não têm permissão para inserir carros.");
		}
		carroRepository.findById(id)
        .map( carro -> {
        	carro.setModelo(carroAtualizado.getModelo());
        	carro.setFabricante(carroAtualizado.getFabricante());
        	carro.setAno(carroAtualizado.getAno());
        	carro.setPreco(carroAtualizado.getPreco());
            return carroRepository.save(carro);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado") );
	}

}
