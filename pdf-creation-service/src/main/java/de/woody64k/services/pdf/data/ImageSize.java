package de.woody64k.services.pdf.data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSize {
    private float width;
    private float height;

    public ImageSize(float width, float height) {
	super();
	this.width = width;
	this.height = height;
    }

    public void scaleToFit(float fitWidth, float fitHeight) {
	float ratioImg = width / height;
	float ratioFit = fitWidth / fitHeight;
	if (ratioImg > ratioFit) {
	    scaleToWith(fitWidth);
	} else {
	    scaleToHeight(fitHeight);
	}
    }

    public void scaleToWith(float maxWidth) {
	float scale = maxWidth / width;
	width *= scale;
	height *= scale;
    }

    public void scaleToHeight(float maxHeight) {
	float scale = maxHeight / height;
	width *= scale;
	height *= scale;
    }

    public float getWidth() {
	return width;
    }

    public float getHeight() {
	return height;
    }

    public static ImageSize fromContent(FileHolder file) {
	try {
	    BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(file.getContent()));
	    return new ImageSize(bimg.getWidth(), bimg.getHeight());
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}
