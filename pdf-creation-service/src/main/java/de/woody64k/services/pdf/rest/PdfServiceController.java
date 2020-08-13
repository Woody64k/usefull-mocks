package de.woody64k.services.pdf.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.woody64k.services.pdf.creation.PdfHandler;
import de.woody64k.services.pdf.data.PdfFile;
import de.woody64k.services.pdf.data.PdfFormulaContent;

@RestController
@RequestMapping("/pdf")
public class PdfServiceController {

    @PostMapping(value = "/create/{template}")
    public ResponseEntity<byte[]> createPdf(@PathVariable String template, @RequestBody PdfFormulaContent request) {
	PdfHandler handler = new PdfHandler();
	PdfFile pdfFileInfo = handler.replaceInTemplate(template, request.getVariables());

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_PDF);
	// Here you have to set the actual filename of your pdf
	String filename = pdfFileInfo.getName();
	headers.setContentDispositionFormData(filename, filename);
	headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	ResponseEntity<byte[]> response = new ResponseEntity<>(pdfFileInfo.getContent(), headers, HttpStatus.OK);
	return response;
    }
}
