package de.woody64k.services.pdf.creation;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.woody64k.services.pdf.data.FileHolder;
import de.woody64k.services.pdf.file.FileUtils;

class PdfBuilderTest {

    @Test
    void test() {
	Map<String, String> text = new HashMap<>();
	text.put("vorname", "Günther");
	FileUtils fileUtils = new FileUtils();
	FileHolder image = fileUtils.readFileOnClasspath("signature.png");
	PdfBuilder.fromTemplate("base-template").replaceText(text).insertImage("nachname", image)
		.save("C:/temp/test.pdf");
    }
}
