//********************************************************************************************************
//									Filename: JImageTranslator.java 
//                                     Author: Ryan Kulkarni 
//                                   JDK Version - 11.01 
//                                     Date: 04/20/2019 
//********************************************************************************************************

//********************************************************************************************************
//Program Description:
//	This program is a utility helper function to the JImage Class. It allows conversion of the wrapper 
//of JImage which is the bufferedImage. This is the dirty work that needs to be done for writing external 
// internal and encrypted images. All methods of this class are protected as only the JImage class should 
//be accessing these. 
//********************************************************************************************************

package dev.utility.jimage;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import dev.utility.jbase.constants.JConstants;

public class JImageTranslator {

	static Graphics2D g2d; 
	static BufferedImage bImage;
	static byte[] byteImage; 
	
	
	
	//====================================================================================================
	//							       	EXTERNAL IMAGE TYPE CONVERSIONs 
	//====================================================================================================
	
	
	/**
	*drawPngImage will take a buffered image that is is in any image format and record the alpha value
	*making it a png image which when ImageIO writes to file it will output a image that a png format 
	*would recognize.
	*@param the buffered image
	*@return returns thr bufferedimage with a alpha value for a png image. 
	*@see BufferedImage  
	 */
	 public static BufferedImage drawPngImage(BufferedImage source) {
	    bImage = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) bImage.getGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(source, 0, 0, source.getWidth(), source.getHeight(), null);
		g2d.dispose();
		return bImage;
	}

	//------------------------------------------------------------------------------------------------------
	//drawJpgImage - 
	//Function that will draw an bufferedimage into a Jpg format 
	//
	//Parameters - 
	//	Image in a buffered mage that is a source 
	//Post Condition - 
	// 	A BufferedImage written in a jpg format 
	//--------------------------------------------------------------------------------------------------------
	 public static BufferedImage drawJpgImage(BufferedImage source) {
	    bImage = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) bImage.getGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(source, 0, 0, source.getWidth(), source.getHeight(), null);
		g2d.dispose();
		return bImage;
	}

	//======================================================================================================
	// 							internal java conversion types 
	//======================================================================================================
	
	
	
	//------------------------------------------------------------------------------------------------------
	//bufferedImageToImage - 
	//Converts a buffered image which are usually read in by the file systemm and converts it into an Image 
	// which can allow for java's default scaling. 
	//
	//Parameters: 
	//	BufferedImage - image in the buffered image format does not matter what kind of image it is 
	//Post Condition - 
	// returns the Image class variation
	//-------------------------------------------------------------------------------------------------------
	 protected static Image bufferedImageToImage(BufferedImage bufferedImage) {
		ImageIcon iconTemp = new ImageIcon(bufferedImage);
		Image imageTemp = iconTemp.getImage();
		return imageTemp;

	}

	//------------------------------------------------------------------------------------------------------
	//imageIconToImage - 
	//Converts a imageIconwhich are usually used for displaying images in GUIS and converts it into an Image 
	// which can allow for java's default scaling. 
	//
	//Parameters: 
	//	ImageIcon - image that is used in a display  
	//Post Condition - 
	// returns the Image class variation
	//------------------------------s-------------------------------------------------------------------------
	 public static Image imageIconToImage(ImageIcon imageIcon) 
	{
		Image imageTemp = imageIcon.getImage();
		return imageTemp;
	}

	 public static ImageIcon imageToImageIcon(Image image) 
	{
		ImageIcon imageTemp = new ImageIcon(image);
		return imageTemp;
	}

	 public static ImageIcon bufferedImageToImageIcon(BufferedImage bufferedImage) {
		ImageIcon imageTemp = new ImageIcon(bufferedImage);
		return imageTemp;
	}

	 public static BufferedImage imageToBufferedImage(Image image) {
	    bImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		g2d = bImage.createGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return bImage;
	}

	 public static BufferedImage imageIconToBufferedImage(ImageIcon imageIcon) 
	{
		return imageToBufferedImage(imageIcon.getImage());

	}
	

	
	//------------------------------JImage to encrupted bytre image conversions --------------------------------------
	 public static byte[] encryptJImage(JImage jImage) throws IOException
	{
		byte [] sourceBytes = jImageToByteImage(jImage); 
		byte []  encruptedBytes = byteImageToEncImage(sourceBytes); 
		return encruptedBytes;
	}
		

	 public static byte[] jImageToByteImage(JImage jImage) throws IOException
	{
	    bImage = jImage.getBufferedImage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(jImage.getMetadata().getValue(JImageMetadata.FILE_EXTENSTION).equalsIgnoreCase(JConstants.JPG))
			ImageIO.write( bImage, "jpg", baos );
		else if(jImage.getMetadata().getValue(JImageMetadata.FILE_EXTENSTION).equalsIgnoreCase(JConstants.PNG))
			ImageIO.write( bImage, "png", baos );
		baos.flush();
	
		byte[] byteImage  = baos.toByteArray();
		baos.close();
		return byteImage; 
	}
	//------------------------------JImage to encrupted bytre image conversions --------------------------------------

	
	
	 public static  byte[]  byteImageToEncyptImage(byte[] byteImage) throws IOException
	{
				byte[] encJMetaB =  Base64.getEncoder().encode(byteImage);
				return encJMetaB;
	}


		
	 public  static byte[]  byteImageToEncImage(byte[] sourceBytes) throws IOException
		{
					byte[] encruptedImage =  Base64.getEncoder().encode(sourceBytes);
					return encruptedImage;
		}

	 public static JImage decryptJImage(byte[] byteImage) throws IOException {
			byte[] imageBytes = Base64.getDecoder().decode(byteImage);
			InputStream in = new ByteArrayInputStream(imageBytes);
			BufferedImage bimage = ImageIO.read(in); 
			JImage returnImage = new JImage(bimage); 
			return returnImage;
		}
	 
	 public static void writeEncryptedBytes(byte[] byteImage,String fileName) throws IOException
	 {
		 FileOutputStream writer = new FileOutputStream(new File(fileName));
	      writer.write(byteImage);
		  writer.close();
	 }



}