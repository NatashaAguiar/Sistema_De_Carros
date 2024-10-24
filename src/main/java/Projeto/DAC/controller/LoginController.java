package Projeto.DAC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Projeto.DAC.model.Login;
import Projeto.DAC.model.Usuario;
import Projeto.DAC.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RestController
@RequestMapping("/api/login")

public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Operation(summary = "Realizar Login")
	@PostMapping 
	public ResponseEntity<Usuario> login(@RequestBody LoginRequest request){
		Usuario usuario = loginService.autenticar(request.getIdentificador(), request.getSenha());
        return ResponseEntity.ok(usuario);
	}
	
	public static class LoginRequest {
        private String identificador;
        private String senha;

        public String getIdentificador() {
            return identificador;
        }

        public void setIdentificador(String identificador) {
            this.identificador = identificador;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
