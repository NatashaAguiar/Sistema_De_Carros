package Projeto.DAC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Projeto.DAC.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
		
	Optional<Usuario> findByCpf(String cpf);
	Optional<Usuario> findByMatricula(String matricula);
}