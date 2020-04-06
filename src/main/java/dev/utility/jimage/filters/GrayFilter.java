package dev.utility.jimage.filters;

import java.awt.image.BufferedImage;
import dev.utility.jimage.JImage;

public class GrayFilter {

	JImage sourceImage;
	JImage grayImage;
	int width;
	int height;

	public GrayFilter(JImage img) {

		// get image width and height
		this.sourceImage = img;
		this.width = img.getWidth();
		this.height = img.getHeight();

		grayImage = new JImage(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY));

	}

	public void process() {

		// convert to grayscale
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = sourceImage.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate average
				int avg = (r + g + b) / 3;

				// replace RGB value with avg
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;

				this.grayImage.setRGB(x, y, p);
			}
		}

	}

	public JImage getGrayImage() {
		return grayImage;
	}
}// class ends here