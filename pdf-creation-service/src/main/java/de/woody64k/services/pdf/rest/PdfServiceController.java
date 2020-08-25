package de.woody64k.services.pdf.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.woody64k.services.pdf.creation.PdfBuilder;
import de.woody64k.services.pdf.data.FileHolder;
import de.woody64k.services.pdf.data.PdfFormulaContent;

@RestController
@RequestMapping("/pdf")
public class PdfServiceController {

    @PostMapping(value = "/create/{template}")
    public FileHolder createPdf(@PathVariable String template, @RequestBody PdfFormulaContent request) {
	FileHolder builder = PdfBuilder.fromTemplate(template).replaceText(request.getVariables()).build("file.pdf");
	return builder;
    }

    @PostMapping(value = "/merge")
    public FileHolder mergePdfs(@RequestBody List<FileHolder> request) throws IOException {
	ByteArrayOutputStream destStream = new ByteArrayOutputStream();
	PDFMergerUtility mergerUtil = new PDFMergerUtility();
	mergerUtil.setDestinationStream(destStream);
	for (FileHolder file : request) {
	    mergerUtil.addSource(new ByteArrayInputStream(file.getContent()));
	}
	mergerUtil.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
	return new FileHolder("Merged Document", destStream.toByteArray());
    }
}
