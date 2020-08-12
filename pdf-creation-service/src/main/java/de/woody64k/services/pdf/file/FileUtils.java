package de.woody64k.services.pdf.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

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

    public byte[] readFileOnClasspath(String name) {
	return readFileFromUrl(findFileOnClasspath(name));
    }

    public byte[] readFileFromUrl(URL url) {
	byte[] bytes = null;
	try (InputStream is = url.openStream()) {
	    bytes = IOUtils.toByteArray(is);
	} catch (IOException e) {
	    throw new RuntimeException(String.format("File could not read from path:", url.getPath()));
	}
	return bytes;
    }
}
