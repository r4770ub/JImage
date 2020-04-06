package dev.utility.jimage.functions;

import java.util.List;

import dev.utility.jimage.JImage;

public class JImageSet {
	
	
	private List<JImage> images; 
	int size; 
	int totalWidth=0; 
	int totalHeight=0; 
	
	 
	public JImageSet(List<JImage>  images)
	{
		this.images = images; 
		this.images.size(); 
		this.totalHeight = getHeight(); 
		this.totalWidth = getWidth(); 
	}
	
	
	
	private int getHeight() 
	{
		this.totalHeight = 0;
        for(JImage image: images)
        {
        	this.totalHeight = this.totalHeight + image.getHeight(); 
        }
        return totalHeight;
	}
	
	
	private int getWidth()
	{
		this.totalWidth = 0;
        for(JImage image: images)
        {
        	this.totalWidth = this.totalWidth + image.getWidth(); 
        }
        return totalWidth ;
	}
	
	
	

}
