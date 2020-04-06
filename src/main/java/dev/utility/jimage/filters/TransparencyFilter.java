package dev.utility.jimage.filters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import dev.utility.jimage.JImage;

public class TransparencyFilter {

	private JImage sourceImage;
	private JImage transImage;
	private float level;

	TransparencyFilter(JImage sourceImage, float level) {
		this.setLevel(level);
		this.setSourceImage(sourceImage);
		process();
	}

	private void process() {
		BufferedImage image = sourceImage.getBufferedImage();
		BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) transparentImage.getGraphics();
		g2d.setComposite(AlphaComposite.SrcOver.derive(level));
		g2d.drawImage(image, 0, 0, null);
		transImage = new JImage(transparentImage);

	}

	public JImage getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(JImage sourceImage) {
		this.sourceImage = sourceImage;
	}

	public JImage getTransImage() {
		return transImage;
	}

	public void setTransImage(JImage transImage) {
		this.transImage = transImage;
	}

	public float getLevel() {
		return level;
	}

	public void setLevel(float level) {
		this.level = level;
	}
}