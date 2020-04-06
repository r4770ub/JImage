package dev.utility.jimage;

import java.util.Base64;
import java.util.Map;

public class JImageMetadataTools 
{


	
	
	// convertion from jmetadata to byte representation to image byte representastion\

	static byte[] encryptMetadata(JImageMetadata jImageMetadata)
	{
	  byte[]  srcBytes = jMetadataToSrcMeta(jImageMetadata); 
	  return srcMetaToencMeta(srcBytes); 
		
	}
	
	static byte[] jMetadataToSrcMeta(JImageMetadata jImageMetadata)
	{
		
		String metadataString = "";

		for (Map.Entry<String, String> entry : jImageMetadata.getmetadata().entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			metadataString = metadataString + "{" + key + ":" + value + "}";
		}

		return metadataString.getBytes();
	}
	
	
	static  byte[] srcMetaToencMeta(byte[] jsonBytes)
	{
		return Base64.getEncoder().encode(jsonBytes);
	}
	
	
	
	

	public static JImageMetadata decryptMetadata(byte [] encryptedMetadata)
	{
		byte[] srcMeta = encMetaToSrcMeta(encryptedMetadata);
		return srcMetaToJMeta(srcMeta); 
	}
	
	
	public static byte[] encMetaToSrcMeta(byte [] encryptedMetadata)
	{
		return  Base64.getDecoder().decode(encryptedMetadata);
	}

	public static JImageMetadata srcMetaToJMeta(byte [] srcMetadata)
	{
		String metadataString = new String(srcMetadata);
		String key = "";
		JImageMetadata jMetadata = new JImageMetadata();
		String value = "";
		String word = "";

		for (int i = 0; i < metadataString.length(); i++) {
			Character character = metadataString.charAt(i);
			// System.out.println(character);
			if (character == ':') {
				key = word.substring(1, word.length());
				word = "";

			} else if (character == '}') {
				value = word;
				word = "";
				jMetadata.putValue(key, value);
				System.out.println("Metadata found: " + key + " with  value of: " + value);
			} else {
				word = word + character;
			}

		}
		return jMetadata;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
