package Projeto.DAC.service;

public class ValidacaoDeSenha extends RuntimeException{
    public ValidacaoDeSenha(String mensagem) {
        super(mensagem);
    }
    
    public class ValidarSenha{
    	public static void validar(String senha) {
    	       if (senha.length() < 8) {
    	            throw new ValidacaoDeSenha("A senha deve ter pelo menos 8 caracteres.");
    	        }
    	        if (!senha.matches(".*[A-Z].*")) {
    	            throw new ValidacaoDeSenha("A senha deve conter pelo menos uma letra maiúscula.");
    	        }
    	        if (!senha.matches(".*[a-z].*")) {
    	            throw new ValidacaoDeSenha("A senha deve conter pelo menos uma letra minúscula.");
    	        }
    	        if (!senha.matches(".*\\d.*")) {
    	            throw new ValidacaoDeSenha("A senha deve conter pelo menos um dígito.");
    	        }
    	        if (!senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
    	            throw new ValidacaoDeSenha("A senha deve conter pelo menos um caractere especial.");
    	        }
    	}
    }
}
