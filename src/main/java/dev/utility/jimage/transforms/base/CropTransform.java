package dev.utility.jimage.transforms.base;

import java.awt.image.BufferedImage;

import dev.utility.jimage.JImage;

public class CropTransform {
	private JImage sourceImage;
	private JImage croppedImage;

	public static CropTransform getCropper(JImage image) {
		return new CropTransform(image);
	}

	CropTransform(JImage sourceImage) {
		this.sourceImage = sourceImage;
	}

	public void proessImage(int x1, int y1, int x2, int y2) {
		int width = x2 - x1;
		int height = y2 - y1;

		System.out.println("Cropping image information");
		System.out.println("Source Image Width: " + sourceImage.getWidth());
		System.out.println("Source Image Height: " + sourceImage.getHeight());
		System.out.println(" New Width: " + width);
		System.out.println("New Height: " + height);

		BufferedImage bImage = sourceImage.getBufferedImage().getSubimage(x1, y1, width, height);
		croppedImage = new JImage(bImage);
	}

	public JImage getSourceImage() {
		return sourceImage;
	}

	public JImage getCroppedImage() {
		return croppedImage;
	}

	public void setSourceImage(JImage image) {
		this.sourceImage = image;
	}

}
