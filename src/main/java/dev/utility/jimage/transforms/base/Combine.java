package dev.utility.jimage.transforms.base;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import dev.utility.jimage.JImage;
import dev.utility.jimage.JImageMetadata;

public class Combine {

	public static final int COMBINE_HORIZONTAL_DIALATE = 0;
	public static final int COMBINE_HORIZONTAL_ERODE = 1;
	
	public static final int COMBINE_VERTICAL_DIALATE = 0;
	public static final int COMBINE_VERTICAL_ERODE = 1;
	
 
	public static final int AUTO_COMBINE = 2;

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(Combine.class.getName());

	private List<JImage> images;
	List<JImage> croppedImages;
	private String utilityName;
	private Date startTime;

	public Combine(String utilityName) throws IOException {
	
		log.info("JImageUtils.constructor");
		this.setUtilityName(utilityName);
		this.setStartTime(new Date());
		images = new ArrayList<JImage>();

	}

	 JImage autoScale(JImage image, int targetWidth, int targetHeight) {
		log.info("Auto Scale: Scaling image with dimensions: " + image.getWidth() + "," + image.getHeight()
				+ " to best fit dimensions " + targetWidth + "," + targetHeight);

		Double scalex = (double) targetWidth / image.getWidth();
		Double scaley = (double) targetHeight / image.getHeight();
		Double scaleFactor = Math.min(scalex, scaley);
		log.info("Calculated Scale factor: " + scaleFactor);

		image = scale(image, scaleFactor);
		log.info("Resulting image size is: " + image.getWidth() + "," + image.getHeight() + "\n");
		return image;
	}

	private JImage scale(JImage image, Double scaleFactor) {

		Double exactWidth = image.getWidth() * scaleFactor;
		Double exactHeight = image.getHeight() * scaleFactor;

		Integer roundedHeight = exactHeight.intValue();
		Integer roundedWidth = exactWidth.intValue();
		log.info("Calculated new Height: " + roundedHeight);
		log.info("Calculated new width: " + roundedWidth);

		Image scaledImage = image.getImage().getScaledInstance(roundedWidth, roundedHeight, Image.SCALE_SMOOTH);

		JImage returnImage = new JImage(scaledImage);
		return returnImage;
	}

	 JImage scaleByX(JImage image, int targetWidth) {
		log.info("Scale by X: Scaling image with dimensions: " + image.getWidth() + "," + image.getHeight()
				+ " to best fit dimensions " + targetWidth);

		Double scalex = (double) targetWidth / image.getWidth();
		log.info("Calculated Scale factor: " + scalex);
		image = scale(image, scalex);
		log.info("Resulting image size is: " + image.getWidth() + "," + image.getHeight() + "\n");

		return image;

	}

	 JImage scaleByY(JImage image, int targetHeight) throws IOException {

		
		Double scaley = (double) targetHeight / image.getHeight();
	

		image = scale(image, scaley);

		return image;

	}



	 JImage autoCombine(JImage imageA, JImage imageB) throws IOException {
		int widthDelta;
		int heightDelta;
		boolean concatByX = false;
		boolean concatByY = false;
		boolean scaleImageA = false;
		boolean scaleImageB = false;
		JImage concatImage = null;

		// determine the differences in widths
		if (imageA.getWidth() > imageB.getWidth())
			widthDelta = imageA.getWidth() - imageB.getWidth();
		else
			widthDelta = imageB.getWidth() - imageA.getWidth();

		// determine the difference in heights.
		if (imageA.isPortrait && imageB.isPortrait)
			heightDelta = imageA.getHeight() - imageB.getHeight();
		else
			heightDelta = imageB.getHeight() - imageA.getHeight();


		// determine which to scale to.
		if (imageA.isPortrait && imageB.isPortrait) {
			concatByY = true;
			concatByX = false;

			if (imageA.getHeight() > imageB.getHeight()) {

				scaleImageB = true;
				scaleImageA = false;
			} else {

				scaleImageB = false;
				scaleImageA = true;
			}

		} else if (imageA.isLandscape && imageB.isLandscape) {
			concatByY = false;
			concatByX = true;
			if (imageA.getHeight() > imageB.getHeight()) {
				scaleImageB = true;
				scaleImageA = false;
			} else {

				scaleImageB = false;
				scaleImageA = true;
			}
		} else {
			if (imageA.getHeight() > imageB.getHeight()) {
				scaleImageB = true;
				scaleImageA = true;
			} else {

				scaleImageB = true;
				scaleImageA = true;
			}

		}

		if (scaleImageB && scaleImageA) {
			// scale vertically
			JImage imageAHorizotal = scaleByX(imageA, imageA.getWidth());
			JImage imageBHoriztonal = scaleByY(imageB, imageA.getHeight());

			// scale horizontally
			JImage imageAVertical = scaleByX(imageA, imageB.getWidth());
			JImage imageBVertical = scaleByY(imageB, imageB.getHeight());

			

		}

		else if (scaleImageB && concatByX) {
			imageB = scaleByX(imageB, imageA.getWidth());
		}

		else if (scaleImageB && concatByY) {
			imageB = scaleByY(imageB, imageA.getHeight());

		}

		else if (scaleImageA && concatByX) {
			imageA = scaleByX(imageA, imageB.getWidth());

		}

		else if (scaleImageA && concatByY) {
			imageA = scaleByY(imageA, imageB.getHeight());

		}

		else {

		}

		if (imageA.getWidth() + imageB.getWidth() < imageA.getHeight() + imageB.getHeight()) {
			concatImage = concatByX(imageA, imageB);

		} else {
			concatImage = concatByY(imageA, imageB);
		}


		return concatImage;

	}

	private List<JImage> getConcatOptions(JImage imageA, JImage imageB) throws IOException {
		croppedImages = new ArrayList<JImage>();

		imageA = autoScale(imageA,(imageA.getWidth() + imageB.getWidth()/2), (imageA.getHeight() + imageB.getHeight())/2);
		imageB = autoScale(imageB,(imageA.getWidth() + imageB.getWidth()/2), (imageA.getHeight() + imageB.getHeight())/2);

		
		JImage imageb2 = scaleByX(imageB, imageA.getWidth());
		croppedImages.add(concatByY(imageA, imageb2));

		imageA = scaleByX(imageA, imageB.getWidth());
		imageA = scaleByY(imageA, imageB.getHeight());
		croppedImages.add(concatByX(imageA, imageB));

		return croppedImages;

	}

	 JImage combineXDirection(JImage imageA, JImage imageB) throws IOException
	{
		List<JImage> images = getConcatOptions(imageA,imageB);
		return images.get(0);	
	}
	
	 JImage combineYDirection(JImage imageA, JImage imageB) throws IOException
	{
		List<JImage> images = getConcatOptions(imageA,imageB);
		return images.get(1);	
	}
	
	
	private JImage concatByX(JImage imageA, JImage imageB) throws IOException {

		BufferedImage concatImage = null;

		int widthTotal = imageA.getWidth() + imageB.getWidth();
		concatImage = new BufferedImage(widthTotal, imageA.getHeight(), BufferedImage.TYPE_INT_RGB);
		int widthCurrent = 0;

		Graphics2D g2d = concatImage.createGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(imageA.getBufferedImage(), widthCurrent, 0, null);
		widthCurrent += imageA.getWidth();
		g2d.drawImage(imageB.getBufferedImage(), widthCurrent, 0, null);
		g2d.dispose();
		JImage returnImage = new JImage(concatImage);
		return returnImage;

	}

	private JImage concatByY(JImage imageA, JImage imageB) throws IOException {

		BufferedImage concatImage = null;

		int heightTotal = imageA.getHeight() + imageB.getHeight();

		concatImage = new BufferedImage(imageA.getWidth(), heightTotal, BufferedImage.TYPE_INT_RGB);
		int heightCurr = 0;
		Graphics2D g2d = concatImage.createGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(imageA.getBufferedImage(), 0, heightCurr, null);
		heightCurr += imageA.getHeight();
		g2d.drawImage(imageB.getBufferedImage(), 0, heightCurr, null);
		g2d.dispose();

		JImage returnImage = new JImage(concatImage);
		return returnImage;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getUtilityName() {
		return utilityName;
	}

	public void setUtilityName(String utilityName) {
		this.utilityName = utilityName;
	}

}