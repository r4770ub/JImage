package dev.utility.jimage.transforms.base;

import java.awt.Image;
import java.io.IOException;

import dev.utility.jimage.JImage;
import dev.utility.jimage.JImageMetadata;

public class ScaleTransform 
{


	public final static int SCALE_HORIZONTAL = 0; 
	public final static int SCALE_VERTICAL = 1; 
	public final static int SCALE_AUTO = 2;  
		
	

	private int scale_direction; 
	private JImage sourceImage; 
	private JImage scaleImage; 
   
	private Double scaleFactor;
	
	
	
	//package backend ins. 
	static JImage scaleByX(JImage image, int target) 
	{
		ScaleTransform scalar = new ScaleTransform(image); 
		JImage resultImage = scalar.scaleByX(target); 
		return resultImage; 
	}
	static JImage scaleByY(JImage image, int target) throws IOException 
	{
		ScaleTransform scalar = new ScaleTransform(image); 
		JImage resultImage = scalar.scaleByY(target); 
		return resultImage; 
	}
	static JImage autoScale(JImage image, int targetX, int targetY) throws IOException 
	{
		ScaleTransform scalar = new ScaleTransform(image); 
		JImage resultImage = scalar.scaleAuto(targetX, targetY); 
		return resultImage; 
	}
	
	ScaleTransform(JImage sourceImage) 
	{
		this.sourceImage = sourceImage;
		this.scale_direction = ScaleTransform.SCALE_AUTO;
	}
	

	ScaleTransform(JImage sourceImage, int SCALE_DIRECTION) 
	{
		this.sourceImage = sourceImage;
		this.scale_direction = SCALE_DIRECTION; 
	}
	
	public JImage process(int targetXDimension,int targetYDimension) throws IOException
	{
			this.scaleImage = scaleAuto(targetXDimension, targetYDimension);
			return scaleImage;
	}

	public JImage process(int targetDimension) throws IOException
	{
	
		if(scale_direction == ScaleTransform.SCALE_HORIZONTAL)
			scaleImage = scaleByX(targetDimension);
		else if(scale_direction == ScaleTransform.SCALE_VERTICAL)
			scaleImage = scaleByY(targetDimension); 
		else if(scale_direction == ScaleTransform.SCALE_AUTO)
			scaleImage = scaleAuto(targetDimension, targetDimension); 
		else
			throw new IOException("Invalid scale type");
		return scaleImage;
		
	
	}
	
	public JImage scaleAuto(int targetWidth, int targetHeight) {

		Double scalex = (double) targetWidth / sourceImage.getWidth();
		Double scaley = (double) targetHeight / sourceImage.getHeight();
		
		scaleFactor = Math.min(scalex, scaley);
		this.scaleImage = scale(scaleFactor);
		return this.scaleImage;
	}
	
	/**
	 * @param scaleFactor
	 * @return
	 */
	private JImage scale(Double scaleFactor)
	{
		
		Double exactWidth = sourceImage.getWidth() * scaleFactor;
		Double exactHeight = sourceImage.getHeight() * scaleFactor;

		Integer roundedHeight = exactHeight.intValue();
		Integer roundedWidth = exactWidth.intValue();
		
		Image resizeImage = sourceImage.getImage().getScaledInstance(roundedWidth, roundedHeight, Image.SCALE_REPLICATE);
		this.scaleImage = new JImage(resizeImage); 
	
		return this.scaleImage;
	}



	public JImage scaleByX(int targetWidth) {

	
		Double scalex = (double) targetWidth / sourceImage.getWidth();
		System.out.println("\tCalculated Scale factor: " + scalex);
		scaleImage = scale(scalex);
		return scaleImage;

	}

	public JImage scaleByY(int targetHeight) throws IOException {

		Double scaley = (double) targetHeight / sourceImage.getHeight();
		System.out.println("\tCalculated Scale factor: " + scaley);

		scaleImage = scale(scaley);
		return scaleImage;

	}

	public JImage getsourceImage() {
		return sourceImage;
	}

	public JImage getscaleImage() {
		scaleImage.setMetadata(sourceImage.getMetadata());
	//	scaleImage.getMetadata().setFileInformation(scaleImage.getMetadata().getValue(JImageMetadata.ABSOLUTE_PATH));
		return scaleImage;
	}

	public Double getScaleFactor() {
		return scaleFactor;
	}

	public Double getReverseScaleFactot() {
		return 1 / scaleFactor;
	}

	public int getScale_direction() {
		return scale_direction;
	}


}
