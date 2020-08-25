package de.woody64k.services.pdf.creation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import de.woody64k.services.pdf.data.FileHolder;
import de.woody64k.services.pdf.data.ImageSize;
import de.woody64k.services.pdf.file.FileUtils;

public class PdfBuilder implements AutoCloseable {
    private final PDDocument pdfDocument;
    private final Map<String, PDField> pdfFields = new HashMap<>();

    public PdfBuilder(PDDocument pdfFile) {
	super();
	this.pdfDocument = pdfFile;
	List<PDField> fields = pdfFile.getDocumentCatalog().getAcroForm().getFields();
	for (PDField field : fields) {
	    pdfFields.put(field.getFullyQualifiedName(), field);
	}
    }

    public PdfBuilder insertImage(String string, FileHolder imageFile) {
	if (pdfFields.containsKey(string)) {
	    PDField field = pdfFields.get(string);
	    PDPage page = getPageOfField(field);
	    PDRectangle imagePlaceholder = getPlaceholderFromField(field);
	    try {
		ImageSize size = ImageSize.fromContent(imageFile);
		size.scaleToFit(imagePlaceholder.getWidth(), imagePlaceholder.getHeight());
		PDImageXObject pdImage = PDImageXObject.createFromByteArray(pdfDocument, imageFile.getContent(),
			imageFile.getName());
		PDPageContentStream pageStream = new PDPageContentStream(pdfDocument, page);
		pageStream.drawImage(pdImage, imagePlaceholder.getLowerLeftX(), imagePlaceholder.getLowerLeftY(),
			size.getWidth(), size.getHeight());
		pageStream.close();
		field.setReadOnly(true);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	    return this;
	} else

	{
	    throw new RuntimeException(String.format("Es wurde kein feld mit dem Namen %s gefunden", string));
	}
    }

    public PdfBuilder replaceText(Map<String, String> replacements) {
	try {
	    for (String fieldName : replacements.keySet()) {
		if (pdfFields.containsKey(fieldName)) {
		    PDField field = pdfFields.get(fieldName);
		    field.setValue(replacements.get(fieldName));
		    field.setReadOnly(true);
		}
	    }
	    return this;

	} catch (Exception e) {
	    throw new RuntimeException("Fehler beim ersetzen der Felder.", e);
	}
    }

    public FileHolder build(String filename) {
	try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
	    pdfDocument.save(byteArrayOutputStream);
	    try (InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
		return new FileHolder(filename, IOUtils.toByteArray(inputStream));
	    } catch (IOException e) {
		throw new RuntimeException("Byte[] konnte mit PDF-Box nicht erzeugt werden.", e);
	    }
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public File save(String filename) {
	try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
	    File pdfFile = new File(filename);
	    pdfDocument.save(pdfFile);
	    return pdfFile;
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    // ================================================================================================================
    // Private Helper
    // ================================================================================================================

    private PDRectangle getPlaceholderFromField(PDField field) {
	COSDictionary fieldDict = field.getCOSObject();
	COSArray fieldAreaArray = (COSArray) fieldDict.getDictionaryObject(COSName.RECT);
	PDRectangle result = new PDRectangle(fieldAreaArray);
	return result;
    }

    private PDPage getPageOfField(PDField field) {
	for (PDAnnotationWidget widget : field.getWidgets()) {
	    PDPage page = widget.getPage();
	    if (page == null) {
		// incorrect PDF. Plan B: try all pages to check the annotations.
		for (int p = 0; p < pdfDocument.getNumberOfPages(); ++p) {
		    List<PDAnnotation> annotations;
		    try {
			annotations = pdfDocument.getPage(p).getAnnotations();

			for (PDAnnotation ann : annotations) {
			    if (ann.getCOSObject() == widget.getCOSObject()) {
				return pdfDocument.getPage(p);
			    }
			}
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		continue;
	    } else {
		return page;
	    }
	}
	throw new RuntimeException(
		String.format("Seite für Feld %s konnte nicht ermittelt werden.", field.getFullyQualifiedName()));
    }

    // ===============================s=================================================================================
    // Creation Helper
    // ================================================================================================================

    public static PdfBuilder fromTemplate(String templateName) {
	FileUtils fileUtils = new FileUtils();
	URL template = fileUtils.findFileOnClasspath(String.format("templates/%s.pdf", templateName));
	return fromUrl(template);
    }

    public static PdfBuilder fromUrl(URL documentLink) {
	try {
	    PDDocument document = PDDocument.load(documentLink.openStream());
	    return new PdfBuilder(document);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void close() throws Exception {
	System.out.println("CLOSE Stream.");
	pdfDocument.close();
    }
}
