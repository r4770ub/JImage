package dev.utility.jimage.functions;

import java.util.ArrayList;
import java.util.List;

import dev.utility.jimage.JImage;

public class OutlineExtractor {

	JImage sourceImage;
	List<Outliner> rectangles;
	List<SubImage> subImages;

	public OutlineExtractor(JImage sourceImage, List<Outliner> rectangles) {
		this.sourceImage = sourceImage;
		this.rectangles = rectangles;
		subImages = new ArrayList<SubImage>();
	}

	public void process() {
 
		for (Outliner rect : rectangles) {
			System.out.println(rect.getX1() + "," + rect.getY1() + "," + rect.getX2() + "," + rect.getY2());
 
			try {
				JImage jImage = new JImage(sourceImage.getBufferedImage().getSubimage(rect.getX1(), rect.getY1(),
						rect.getWidth(), rect.getLength()));
				SubImage subImage = new SubImage(jImage, rect.isBaseImage());

				subImages.add(subImage);
			} catch (Exception e) {

			}

		}

	}

	public List<SubImage> extractSubImages() {
		return subImages;
	}

	public class SubImage {

		private JImage image;
		private Boolean isBaseImage;

		public SubImage(JImage image, Boolean isBaseImage) {
			this.setImage(image);
			this.setIsBaseImage(isBaseImage);

		}

		public JImage getImage() {
			return image;
		}

		public void setImage(JImage image) {
			this.image = image;
		}

		public Boolean getIsBaseImage() {
			return isBaseImage;
		}

		public void setIsBaseImage(Boolean isBaseImage) {
			this.isBaseImage = isBaseImage;
		}

	}

}
