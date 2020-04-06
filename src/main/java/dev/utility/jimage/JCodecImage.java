package dev.utility.jimage;

import java.io.IOException;
import java.io.Serializable;

public class JCodecImage implements Serializable  {

	
	private static final long serialVersionUID = -7413179456419319204L;
	
	private byte[] encyptedImage; 
	private byte[] encryptedMetadata;

	
	public JCodecImage(JImage jImage) throws IOException 
	{
		codecify(jImage); 
	} 
	
	public void codecify(JImage jImage) throws IOException
	{
		encyptedImage= JImageTranslator.encryptJImage(jImage); 
		encryptedMetadata = JImageMetadataTools.encryptMetadata(jImage.getMetadata());
	}
	
	
	public JImage deCodecify() throws IOException
	{
		JImage jImage = JImageTranslator.decryptJImage(encyptedImage); 
		JImageMetadata metadata = JImageMetadataTools.decryptMetadata(encryptedMetadata);
		jImage.setMetadata(metadata);
		return jImage;
	}
	
	public byte[] getEncrypedImage()
	{
		return encyptedImage; 
	}
	public byte[] getEncryptedMetadata()
	{
		return encryptedMetadata; 
	}
}


