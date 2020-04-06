package dev.utility.jimage;

import java.io.IOException;

import dev.utility.jbase.constants.JConstants;
import dev.utility.jfile.JFileSorter;
import dev.utility.jfile.JFileTools;

public class JImageEncryptorTester {
	
	public static void main(String [] args) throws IOException
	{
		JFileSorter sorter = JFileTools.getFileSorter(JConstants.INPUT_IMAGE_DIRECTORY, JFileSorter.SORT_FILES_ALPHANUMERIC);
		String[] fileNames = sorter.getFileNames();
		JImage image = new JImage(fileNames[0]);
		image.writeEncryptdImage("/home/r4770/Desktop/img");

		JImage image2=new JImage("/home/r4770/Desktop/img.IP01");
		image2.writeJImageAsJPG("/home/r4770/Desktop/final.jpg");
	
	
	}}
