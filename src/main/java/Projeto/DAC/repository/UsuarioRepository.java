package Projeto.DAC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Projeto.DAC.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
}