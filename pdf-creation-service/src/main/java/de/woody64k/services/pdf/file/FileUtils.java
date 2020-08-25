package de.woody64k.services.pdf.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import de.woody64k.services.pdf.data.FileHolder;

public class FileUtils {

    public URL findFileOnClasspath(String name) {
	URL url = ClassLoader.getSystemResource(name);
	if (url != null) {
	    return url;
	} else {
	    ClassLoader classLoader = this.getClass().getClassLoader();
	    return classLoader.getResource(name);
	}

    }

    public FileHolder readFileOnClasspath(String name) {
	return readFileFromUrl(name, findFileOnClasspath(name));
    }

    public FileHolder readFileFromUrl(String name, URL url) {
	try (InputStream is = url.openStream()) {
	    return new FileHolder(name, IOUtils.toByteArray(is));
	} catch (IOException e) {
	    throw new RuntimeException(String.format("File could not read from path:", url.getPath()));
	}
    }
}
