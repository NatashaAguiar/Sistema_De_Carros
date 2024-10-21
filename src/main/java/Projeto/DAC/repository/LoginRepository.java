package Projeto.DAC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Projeto.DAC.model.Login;
import Projeto.DAC.model.Usuario;


public interface LoginRepository extends JpaRepository<Login, Long>{

	Optional<Login> findByUsuario(String usuario);
} 

