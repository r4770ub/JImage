package dev.utility.jimage;
import java.io.*;
import java.util.Base64;

public class JCodec
{


	 static File f; 
	 static FileInputStream fis;
	 static FileOutputStream writer;
	public static void main(String [] args) throws IOException
	{
		if(args[0].equalsIgnoreCase("d"))
			D(args[1]);
		else if(args[0].equalsIgnoreCase("e"))
			E(args[1]);
		
	}
	public static void E(String input) throws IOException
	{
		
	   f = new File(input);
	   byte[] bA = new byte[(int) f.length()]; 
       fis = new FileInputStream(f);
       fis.read(bA);
       fis.close();
	   byte[] eB = Base64.getEncoder().encode(bA);
	   String fileName =input.substring(0,input.length()-3);
	   if(input.contains(".png"))
		   fileName = fileName + "P01";
	   else if(input.contains(".mp4") || input.contains(".avi") || input.contains(".flv"))
		   fileName = fileName + "V01";
	   else if(input.contains(".odt"))
		   fileName = fileName + "O01";
	   else
		   fileName = fileName + "O1";
	  
	   
	   FileOutputStream writer = new FileOutputStream(new File(fileName));
       writer.write(eB);
	    writer.close();
	}
	
	public static void D(String input) throws IOException
	{
	     File f = new File(input);
	     fis = new FileInputStream(f);
		byte[] fc = new byte[(int) f.length()];
		fis.read(fc);
		byte[] db = Base64.getDecoder().decode(fc); 
        
		 String fileName =input.substring(0,input.length()-3);
		   if(input.contains("P01"))
			   fileName = fileName + ".png";
		   else if(input.contains(".V01"))
			   fileName = fileName + "mp4";
		   else
		   		fileName = fileName +".unknown";
		OutputStream os = new FileOutputStream(fileName); 
        os.write(db); 
        os.close(); 	  
	}	
}

