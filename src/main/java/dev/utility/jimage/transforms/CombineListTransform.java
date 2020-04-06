package dev.utility.jimage.transforms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dev.utility.jbase.constants.JConstants;
import dev.utility.jimage.JImage;
import dev.utility.jimage.transforms.base.BaseTransform;
import dev.utility.jimage.transforms.base.Combine;
import dev.utility.jimage.transforms.base.Combine2Transform;
import dev.utility.jimage.transforms.base.ScaleTransform;

public class CombineListTransform {

	private Queue images;
	private Queue images2 ;
	int totalWidth=0; 
	int totalHeight=0;
	int idealWidth; 
	

	public CombineListTransform(List<JImage> images) {
	
		this.images = new LinkedList();
		this.images2= new LinkedList();
		System.out.println("num imgs: " + images.size());
		for(JImage image: images)
		{
			this.images.add(image);
		}
		
	}

	public JImage combineTwoImages(JImage image1, JImage image2) throws IOException
	{
		Combine2Transform combiner = new Combine2Transform(image1,image2);
		JImage result = combiner.autoCombine();
		result.scaleAuto(1000,1000);
		return result;
		
		
	}
	public JImage combineLists() throws IOException {
		
		JImage image1;
		JImage image2;
		JImage combineImage;  
		Combine2Transform combineFilter;
		Combine imageUtils = new Combine(this.getClass().getName());

		while (images.size() > 1 || images2.size() >1) 
		{
			if(images.size() > 1)
			{
				reducePass1List(); 

			}
			if(images2.size() >1)
			{
			reducePass2List(); 
			}
		}
		
		
		if(images.size() == 1)
		{
			return (JImage) images.element(); 
		}
		else if(images2.size() ==1)
		{
			return (JImage) images2.element();
		}
		else
		{
			return null; 
		}
	

	}
	
	public void reducePass1List() throws IOException
	{
		JImage image1;
		JImage image2;
		JImage combineImage;  
		System.out.println("At beginning of pass1");
		System.out.println("Images size :" +images.size());
		System.out.println("Images2size :" +images2.size());
		while(images.size() >1)
		{
			image1 = (JImage) images.element();
			images.remove();
			image2 = (JImage) images.element();
			images.remove();
			combineImage=combineTwoImages(image1,image2);
			images2.add(combineImage);
		}
		if(images.size() == 1)
		{
	       images2.add(images.element());
	       images.remove();
		}
		
		System.out.println("At end of pass1");
		System.out.println("Images size :" +images.size());
		System.out.println("Images2size :" +images2.size());
	}
	
	private void reducePass2List() throws IOException
	{
		
			JImage image1;
			JImage image2;
			JImage combineImage;  
			System.out.println("At beginf pass2");
			System.out.println("Images size :" +images.size());
			System.out.println("Images2size :" +images2.size());
			while(images2.size() >1)
			{
				image1 = (JImage) images2.element();
				images2.remove();
				image2 = (JImage) images2.element();
				images2.remove();
				combineImage=combineTwoImages(image1,image2);
				images.add(combineImage);
			}
			if(images2.size() == 1)
			{
		       images.add(images2.element());
		       images2.remove();
			}
			System.out.println("At end of pass2");
			System.out.println("Images size :" +images.size());
			System.out.println("Images2size :" +images2.size());
		
		
		
	}

}
