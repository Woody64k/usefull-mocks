package de.woody64k.services.pdf.data;

public class PdfFile {
    public PdfFile(String name, byte[] content) {
	super();
	this.name = name;
	this.content = content;
    }

    private String name;
    private byte[] content;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public byte[] getContent() {
	return content;
    }
}
