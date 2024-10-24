package Projeto.DAC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "login")
public class Login {

	@Id
	@Column(name = "login_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loginId;
	
	@Column(nullable = false, length = 50)
    @NotEmpty
    @NotNull
    private String usuario;
	
	@Column(nullable = false, length = 50)
    @NotEmpty
    @NotNull
    private String senha;
	
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", unique = true, nullable = false)
    private Usuario usuarioEntity;
	
	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
    public Usuario getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(Usuario usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

} 
