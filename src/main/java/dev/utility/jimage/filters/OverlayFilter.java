package dev.utility.jimage.filters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import dev.utility.jimage.JImage;

public class OverlayFilter {

	private JImage sourceImage1;
	private JImage sourceImage2;
	private JImage resultImage;

	OverlayFilter(JImage sourceImage1, float transLevel1, JImage sourceImage2, float transLevel2) {
		this.sourceImage1 = sourceImage1; 
		this.sourceImage2 = sourceImage2;

		TransparencyFilter transFilter = JImageFilter.getTransparencyFilter(sourceImage1, transLevel1);
		BufferedImage image1 = transFilter.getTransImage().getBufferedImage();
		transFilter = JImageFilter.getTransparencyFilter(sourceImage2, transLevel2);
		BufferedImage image2 = transFilter.getTransImage().getBufferedImage();

		int compositeRule = AlphaComposite.SRC_OVER;
		AlphaComposite ac;
		int imgW = image1.getWidth();
		int imgH = image2.getHeight();
		BufferedImage overlay = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = overlay.createGraphics();
		ac = AlphaComposite.getInstance(compositeRule, transLevel1);
		g.drawImage(image1, 0, 0, null);
		g.setComposite(ac);
		g.drawImage(image2, 0, 0, null);
		g.setComposite(ac);
		g.dispose();

		resultImage = new JImage(overlay);

	}

	public JImage getSourceImage1() {
		return sourceImage1;
	}

	public JImage getSourceImage2() {
		return sourceImage2;
	}

	public JImage getResultImage() {
		return resultImage;
	}

}
