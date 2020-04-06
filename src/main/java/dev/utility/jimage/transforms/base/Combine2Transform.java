package dev.utility.jimage.transforms.base;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import dev.utility.jimage.JImage;


public class Combine2Transform {

	public static final int SCALE_DIALATE = 0;
	public static final int SCALE_ERODE = 1;
	public static final int SCALE_AUTO = 2;
	
	public static final int COMBINE_HORIZONTAL = 0;
	public static final int COMBINE_VERTICAL = 1;
	public static final int COMBINE_AUTO = 2;
	
	


	 JImage img1; 
	 JImage img2; 
	Combine utils; 
	JImage combined;
	
	public Combine2Transform(JImage img1, JImage img2) throws IOException
	{
		this.img1=img1; 
		this.img2=img2; 
		
		utils= new Combine(Combine2Transform.class.getName());
		
	}
	

	
	
	public JImage combine2VerticalEnlarge() throws IOException
	{
		if(img1.getHeight() > img2.getHeight())
			img2.scaleHeight(img1.getHeight());
		else
			img1.scaleHeight(img2.getHeight());
		return combineX();
		 
	}
	
	public JImage combine2VerticalShrink() throws IOException
	{
		if(img1.getHeight() > img2.getHeight())
			img1.scaleHeight(img2.getHeight());
		else
			img2.scaleHeight(img1.getHeight());
		return combineX();

	}
	
	public JImage combine2HorizontalEnlarge() throws IOException
	{
		if(img1.getHeight() > img2.getHeight())
			img2.scaleHeight(img1.getHeight());
		else
			img1.scaleHeight(img2.getHeight());
		return combineY();
		 
	}
	
	public JImage combine2HorizontalShrink() throws IOException
	{
		if(img1.getHeight() > img2.getHeight())
			img1.scaleHeight(img2.getHeight());
		else
			img2.scaleHeight(img1.getHeight());
		return combineY();

	}
	
	public JImage combineX() throws IOException
	{
		combined=utils.combineXDirection(img1, img2);
		return combined;
	}
	
	public JImage combineY() throws IOException
	{
		combined=utils.combineYDirection(img1, img2);
		return combined;
	}
	public JImage autoCombine() throws IOException
	{
		return combined=utils.autoCombine(img1, img2);
	}
	



}
