package de.woody64k.services.pdf.data;

public class FileHolder {
    public FileHolder(String name, byte[] content) {
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
	if (name.endsWith(".pdf")) {
	    this.name = name;
	} else {
	    this.name = name + ".pdf";
	}
    }

    public byte[] getContent() {
	return content;
    }
}
