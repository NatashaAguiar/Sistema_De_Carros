package Projeto.DAC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "carros")
public class Carro {
	
	@Id
	@Column(name = "carro_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carroId;
	
	@Column(nullable = false, length = 50)
    @NotEmpty
    @NotNull
    private String modelo;
	
	@Column(nullable = false, length = 50)
    @NotEmpty
    @NotNull
    private String fabricante;

	@Column(nullable = false)
    @NotNull
    private int ano;
	
	@Column(nullable = false)
    @NotNull
    @Min(0)
    private double preco;

	public Long getCarroId() {
		return carroId;
	}

	public void setCarroId(Long carroId) {
		this.carroId = carroId;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
}
