package dev.utility.jimage.transforms.base;

import java.io.IOException;

import dev.utility.jimage.JImage;


public class BaseTransform {
	public static CropTransform getCropper(JImage image) {
		return new CropTransform(image);

	}

	public static ScaleTransform getScaler(JImage image) {
		return new ScaleTransform(image);

	}
	public static ScaleTransform getScaler(JImage image, int option) {
		return new ScaleTransform(image, option);
	}
	public static Combine2Transform getCombiner(JImage image1, JImage image2) throws IOException {
		return new Combine2Transform(image1, image2);
	}



}
