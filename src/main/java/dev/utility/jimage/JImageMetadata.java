package dev.utility.jimage;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JImageMetadata implements Serializable {

	/** 
		 * 
		 */
	private static final long serialVersionUID = 8481654772234530135L;

	public static final String IMAGE_ID = "IMAGE_ID";
	public static final String IMAGE_TYPE = "IMAGE_TYPE";
	public static final String IMAGE_CREATION_TIME = "IMAGE_CREATION_TIME";

	public static final String ABSOLUTE_PATH = "ABSOLUTE_PATH";
	public static final String FILE_NAME = "FILE_NAME";
	public static final String ABSOLUTE_FILE_NAME = "ABSOLUTE_FILE_NAME";
	public static final String PARENT_PATH = "PARENT_PATH";
	public static final String FILE_EXTENSTION = "FILE_EXTENSTION";
	public static final String WIDTH = "WIDTH";
	public static final String HEIGHT = "HEIGHT";



	public static final String CODE = "CODE";
	public static final String DATE = "DATE";
	public static final String LOCATION = "LOCATION";
	public static final String DESCRIPTION = "DESCRIPTION";

	private Map<String, String> metadata = new HashMap<String, String>();
 

	
	public JImageMetadata() {
		
	}
	public JImageMetadata(BufferedImage image) {
		initialize(image);

	}

	public JImageMetadata(BufferedImage image, String fileName) {
		initialize(image);
	}

	public void initialize(BufferedImage image) {
		
		putValue(IMAGE_ID, createID(image));
		putValue(IMAGE_TYPE, "SOURCE_IMAGE");
		putValue(ABSOLUTE_PATH, null);
		putValue(FILE_NAME, null);
		putValue(PARENT_PATH, null);
		putValue(FILE_EXTENSTION, null);
		putValue(ABSOLUTE_FILE_NAME, null);
		putValue(WIDTH, null);
		putValue(HEIGHT, null);

		putValue(CODE, null);
		putValue(DATE, null);
		putValue(LOCATION, null);
		putValue(DESCRIPTION, null);

	}

	public void setFileInformation(String absolutePath) {
		String fileName = absolutePath.substring(absolutePath.lastIndexOf('/') + 1, absolutePath.lastIndexOf('.'));
		String absoluteFileName = absolutePath.substring(absolutePath.lastIndexOf('/') + 1, absolutePath.length());
		String fileExtenstion = absolutePath.substring(absolutePath.lastIndexOf('.') + 1, absolutePath.length());
		String parentPath = absolutePath.substring(0, absolutePath.lastIndexOf("/") + 1);

		putValue(ABSOLUTE_PATH, absolutePath);
		putValue(PARENT_PATH, parentPath);
		putValue(ABSOLUTE_FILE_NAME, absoluteFileName);
		putValue(FILE_NAME, fileName);
		putValue(FILE_EXTENSTION, fileExtenstion);

	}



	public void putValue(String key, String value) {
		metadata.put(key, value);
	}

	public String getValue(String key) {
		String data = metadata.get(key);
		return data;

	}

	public Map<String, String> getmetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getJSONString() {
		String ans = "{MetadataElement: { " + "\t" + CODE + ":" + getValue(CODE) + "\t" + DATE + ":" + getValue(DATE)
				+ "\t" + LOCATION + ":" + "\t" + DESCRIPTION + ":" + "}";

		return ans;
	}

	public void printMetadata() {

		System.out
				.println("\t==========================================IMAGE METADATA=================================");
		System.out
				.println("\tJImage metadata is programatically set. If NULL program is being developed still        ");

		System.out.println(
				"\t-------------------------------------------Metadata from Read------------------------------------");

		System.out.println("\tImageID : " + getValue(IMAGE_ID));
		System.out.println("\tImagetype : " + getValue(IMAGE_TYPE));

		System.out.println("\tParent Path : " + getValue(PARENT_PATH));
		System.out.println("\tAbsolute File Path : " + getValue(ABSOLUTE_PATH));
		System.out.println("\tParent Path : " + getValue(PARENT_PATH));
		System.out.println("\tAbsolute File Name : " + getValue(ABSOLUTE_FILE_NAME));
		System.out.println("\tFile name: " + getValue(FILE_NAME));
		System.out.println("\tFile id: " + getValue(IMAGE_ID));

		System.out.println("\tFile Extension: " + getValue(FILE_EXTENSTION));
		System.out.println("\tImage width: " + getValue(WIDTH));
		System.out.println("\tImage Height " + getValue(HEIGHT));
		System.out
				.println("\t---------------------------------------------end-----------------------------------------");

		System.out.println(
				"\t-------------------------------------------JImage Metadata------------------------------------");

		System.out.println("\tJImage Code: " + " " + getValue(CODE));
		System.out.println("\tJImage Date: " + " " + getValue(DATE));
		System.out.println("\tJImage Location" + " " + getValue(LOCATION));
		System.out.println("\tJImage Description" + " " + getValue(DESCRIPTION));
		System.out
				.println("\t---------------------------------------------end-----------------------------------------");
		System.out.println("\t==========================================END METADATA=================================");

	}

	private static String createID(BufferedImage sourceImage) {
		List<Point> pts = new ArrayList<Point>();
		String ID = "";

		pts.add(new Point(10, 10));
		pts.add(new Point(10, sourceImage.getHeight() - 1));
		pts.add(new Point(sourceImage.getWidth() - 1, 10));
		pts.add(new Point(sourceImage.getWidth() - 1, sourceImage.getHeight() - 1));
		pts.add(new Point(sourceImage.getWidth() / 2, sourceImage.getHeight() / 2));

		for (Point point : pts) {

			int p1Color = sourceImage.getRGB(point.x, point.y);
			ID = ID + Integer.toHexString(p1Color)+".";
			

		}
		return ID;

	}
	
	public String getJSONRepresentation()
	{
		String metadataString = "";

		for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			metadataString = metadataString + "{" + key + ":" + value + "}";
		}
		return metadataString; 

	}

	

}
