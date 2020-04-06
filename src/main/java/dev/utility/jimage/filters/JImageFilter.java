package dev.utility.jimage.filters;

import dev.utility.jimage.JImage;

public class JImageFilter {

	public static TransparencyFilter getTransparencyFilter(JImage image, float level) {
		return new TransparencyFilter(image, level);
	}

	public static OverlayFilter getOverlayFilter(JImage image1, float image1Trans, JImage image2, float image2Trans) {
		return new OverlayFilter(image1, image1Trans, image2, image2Trans);
	}
}
