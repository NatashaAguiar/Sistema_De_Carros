package Projeto.DAC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Projeto.DAC.model.Usuario;
import Projeto.DAC.service.QrCodeService;
import Projeto.DAC.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuario", description = "Usuario APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private QrCodeService qrCodeService;
	
	@GetMapping("/qrcode/{cpf}")
	public ResponseEntity<byte[]> generateQRCode(@PathVariable String cpf) throws Exception {
	    Usuario usuario = usuarioService.listarPorCpf(cpf);
	    String qrContent = "Nome: " + usuario.getNome() + ", CPF: " + usuario.getCpf() +
	                       ", Data de Nascimento: " + usuario.getData_nascimento() +
	                       ", Endereço: " + usuario.getEndereco() +
	                       ", Telefone: " + usuario.getTelefone() +
	                       ", Email: " + usuario.getEmail();
	    
	    byte[] qrCodeImage = qrCodeService.generateQRCodeImage(qrContent, 200, 200);
	    
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "attachment; filename=usuario_qrcode.png")
	            .contentType(new MediaType("image", "png"))
	            .body(qrCodeImage);
	}
	
	@Operation(summary = "Todos os Usuarios", tags = { "usuarios", "get", "filter" })
	@GetMapping
	public List<Usuario> listar(){
		return usuarioService.listarTodos();
	}
	
	@PostMapping
	@ApiResponses({
	      @ApiResponse(responseCode = "201", content = {
	          @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public Usuario salvar( @RequestBody @Valid Usuario usuario ){
        return usuarioService.salvar(usuario);
    }
	
	@GetMapping("{id}")
	public Usuario listarPorId(@PathVariable Long id) {
		return usuarioService.listarPorId(id);
	}
	
	@DeleteMapping("{id}")
	@ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	public void excluir(@PathVariable Long id) {
		usuarioService.excluir(id);
	}
	
	@PutMapping("{id}")
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = {
	          @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public void editar(@PathVariable Long id, @RequestBody @Valid Usuario usuarioAtualizado) {
		usuarioService.editar(id, usuarioAtualizado);
	}
	
}