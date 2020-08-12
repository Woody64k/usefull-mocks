package de.woody64k.services.pdf.data;

import java.util.Map;

public class PdfFormulaContent {
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
	return variables;
    }

    public void setVariables(Map<String, Object> variables) {
	this.variables = variables;
    }
}
