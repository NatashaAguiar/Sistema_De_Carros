package Projeto.DAC.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import Projeto.DAC.model.Carro;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PDFService {
	
	@Autowired
	CarroService carroService;
	
	public void export(HttpServletResponse response) throws IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);
		
		Paragraph title = new Paragraph("Relatório de Carros", fontTitle);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		
		document.add(new Paragraph(" "));
		
		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(12);
		
		List<Carro> carros = carroService.listarTop10();
	    for (Carro carro : carros) {
	        Paragraph paragraph = new Paragraph(
	            "Modelo: " + carro.getModelo() + 
	            ", Fabricante: " + carro.getFabricante() + 
	            ", Ano: " + carro.getAno() + 
	            ", Preço: " + carro.getPreco(),
	            fontParagraph
	        );
	        document.add(paragraph);
	    }
		
		document.close();
		
	}
}

