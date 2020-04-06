package dev.utility.jimage.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import dev.utility.jimage.JImage;

public class ThresholdFilter {

	JImage sourceImage;
	JImage thresHoldImage;
	int thresHold;

	public ThresholdFilter(JImage sourceImage, int thresHold) {
		this.sourceImage = sourceImage;
		this.thresHold = thresHold;
	}

	public void process() {

		BufferedImage tempImage = new BufferedImage(this.sourceImage.getWidth(), this.sourceImage.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < sourceImage.getBufferedImage().getWidth(); i++) {
			for (int j = 0; j < sourceImage.getBufferedImage().getHeight(); j++) {
				int p = sourceImage.getBufferedImage().getRGB(i, j);

				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				int avg = (r + g + b) / 3;
				if (avg >= thresHold)
					tempImage.setRGB(i, j, Color.WHITE.getRGB());
				else
					tempImage.setRGB(i, j, Color.black.getRGB());
			}
		}
		this.thresHoldImage = new JImage(tempImage);
	}

	public JImage getThreshold() {
		return thresHoldImage;
	}

}
