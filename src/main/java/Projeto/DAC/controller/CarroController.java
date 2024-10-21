package Projeto.DAC.controller;

import java.io.IOException;
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

import Projeto.DAC.model.Carro;
import Projeto.DAC.service.CarroService;
import Projeto.DAC.service.PDFService;
import Projeto.DAC.service.QrCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Carro", description = "Carro APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/carro")
public class CarroController {
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	private QrCodeService qrCodeService;
	
	@Autowired
	private PDFService pdfService;
	
	public CarroController(PDFService pdfService) {
		this.pdfService = pdfService;
	}
	
	@Operation(summary = "Todos os Carros", tags = { "carros", "get", "filter" })
	@GetMapping
	public List<Carro> listar(){
		return carroService.listarTodos();
	}
	
	@GetMapping("/pdf")
	public void generatePDF(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Carro_Relatório.pdf";
	    response.setHeader(headerKey, headerValue);
		
		this.pdfService.export(response);
	}
	
	@GetMapping("/qrcode/{id}")
	public ResponseEntity<byte[]> generateQRCode(@PathVariable Long id) throws Exception {
	    Carro carro = carroService.listarPorId(id);
	    String qrContent = "Carro: " + carro.getModelo() + ", Fabricante: " + carro.getFabricante() + ", Ano: " + carro.getAno() + ", Preço: " + carro.getPreco();
	    
	    byte[] qrCodeImage = qrCodeService.generateQRCodeImage(qrContent, 200, 200);
	    
	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=carro_qrcode.png")
	    		.contentType(new MediaType("image", "png"))
	    		.body(qrCodeImage);
	}
	
	@PostMapping
	@ApiResponses({
	      @ApiResponse(responseCode = "201", content = {
	          @Content(schema = @Schema(implementation = Carro.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public Carro salvar( @RequestBody @Valid Carro carro ){
        return carroService.salvar(carro);
    }
	
	@GetMapping("{id}")
	public Carro listarPorId(@PathVariable Long id) {
		return carroService.listarPorId(id);
	}
	
	@DeleteMapping("{id}")
	@ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	public void excluir(@PathVariable Long id) {
		carroService.excluir(id);
	}
	
	@PutMapping("{id}")
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = {
	          @Content(schema = @Schema(implementation = Carro.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public void editar(@PathVariable Long id, @RequestBody @Valid Carro carroAtualizado) {
		carroService.editar(id, carroAtualizado);
	}
}

