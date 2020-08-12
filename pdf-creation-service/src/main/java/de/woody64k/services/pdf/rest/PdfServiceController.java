package de.woody64k.services.pdf.rest;

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

    @PostMapping(value = "/create/{formular}")
    public PdfFile findFirstPerson(@PathVariable String formular, @RequestBody PdfFormulaContent request) {
	PdfHandler handler = new PdfHandler();
	return handler.replaceInTemplate(formular, request.getVariables());
    }
}
