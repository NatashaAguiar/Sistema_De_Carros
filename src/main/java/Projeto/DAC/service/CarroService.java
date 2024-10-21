package Projeto.DAC.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Projeto.DAC.model.Carro;
import Projeto.DAC.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public Carro salvar(Carro carro) {
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
		carroRepository.findById(id).map(carro -> {
			carroRepository.delete(carro);
	            return Void.TYPE;
	        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
	}
	
	public void editar(Long id, Carro carroAtualizado) {
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
