package Projeto.DAC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import Projeto.DAC.model.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long>{
	
	List<Carro> findTop10ByOrderByCarroIdAsc();
}
