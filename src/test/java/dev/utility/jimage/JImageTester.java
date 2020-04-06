package dev.utility.jimage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dev.utility.jbase.constants.JConstants;
import dev.utility.jimage.transforms.CombineListTransform;
import dev.utility.jimage.transforms.JImageTransform;
import dev.utility.jimage.transforms.base.Combine2Transform;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class JImageTester {

	public static void main(String[] args) throws InterruptedException, Exception {
		testWrite();
	}

	public static void testWrite() throws InterruptedException, Exception {
		
		List<JImage> pass1 = new ArrayList<JImage>();
		
		File directory = new File("/home/r4770/Pictures/inputs/");
		File[] files = directory.listFiles();
		
		for(File file:files)
		{
			pass1.add(new JImage(file.getAbsolutePath()));
		}
		JImage img1 = pass1.get(0);
		JImage img2= pass1.get(1);
		Combine2Transform combiner = JImageTransform.getCombiner(img1,img2);
		JImage finalImage = combiner.combine2HorizontalEnlarge();
		finalImage.displayImage("ENLARGE HORIZONTAL: " +finalImage.getWidth()+"  | "  + finalImage.getHeight());
		
		 finalImage = combiner.combine2HorizontalShrink();
		finalImage.displayImage("Shrnk HORIZONTAL: " +finalImage.getWidth()+"  | "  + finalImage.getHeight());
		
		 finalImage = combiner.combine2VerticalEnlarge();
		finalImage.displayImage("ENLARGE vertical : " +finalImage.getWidth()+"  | "  + finalImage.getHeight());
		
		 finalImage = combiner.combine2VerticalShrink();
		finalImage.displayImage("Shrnk vertical: " +finalImage.getWidth()+"  | "  + finalImage.getHeight());
		

	}

}
