package de.woody64k.services.pdf.creation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import de.woody64k.services.pdf.data.PdfFile;
import de.woody64k.services.pdf.file.FileUtils;

public class PdfHandler {

    /**
     * Füllt die Formularfelder entsprechend des Templates aus.
     * 
     * @param processVariables
     * @return byte[] als PDF-Binary.
     */
    public PdfFile replaceInTemplate(String templateName, Map<String, Object> processVaiables) {
	Map<String, String> replacements = new HashMap<>();
	for (String mapVar : processVaiables.keySet()) {
	    if (processVaiables.get(mapVar) != null) {
		replacements.put(mapVar, processVaiables.get(mapVar).toString());
	    }
	}

	FileUtils fileUtils = new FileUtils();
	URL template = fileUtils.findFileOnClasspath(String.format("templates/%s.pdf", templateName));
	PdfFile pdfFile = replaceAll(template, replacements);
	pdfFile.setName(templateName);
	return pdfFile;
    }

    private PdfFile replaceAll(URL documentLink, Map<String, String> replacements) {
	try (PDDocument document = PDDocument.load(documentLink.openStream())) {
	    replaceTextV2(document, replacements);
	    PdfFile pdfFile = documentToByteArry(document);
	    document.close();
	    return pdfFile;
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private PdfFile documentToByteArry(PDDocument document) {
	try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
	    document.save(byteArrayOutputStream);
	    try (InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
		return new PdfFile("document", IOUtils.toByteArray(inputStream));
	    } catch (IOException e) {
		throw new RuntimeException("Byte[] konnte mit PDF-Box nicht erzeugt werden.", e);
	    }
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private PDDocument replaceTextV2(PDDocument document, Map<String, String> replacements) throws IOException {
	List<PDField> fields = document.getDocumentCatalog().getAcroForm().getFields();
	for (PDField field : fields) {
	    if (replacements.containsKey(field.getFullyQualifiedName())) {
		field.setValue(replacements.get(field.getFullyQualifiedName()));
		field.setReadOnly(true);
	    }
	}
	return document;
    }
}
