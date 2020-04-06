package dev.utility.jimage.transforms;

import java.io.IOException;
import java.util.List;

import dev.utility.jimage.JImage;
import dev.utility.jimage.transforms.base.BaseTransform;

public class JImageTransform extends BaseTransform{



	public static ScaleCropTransform getScaleCropper(JImage image, int width, int height) throws IOException {
		return new ScaleCropTransform(image, width, height);
	}

	public static CombineListTransform getCombineManyImages(List<JImage> images) throws IOException {
		return new CombineListTransform(images);
	}
	
	public static AutoCropVTransform getAutoCropper(JImage sourceImage, int ANALYSIS_OPTION) throws IOException, InterruptedException, Exception {
		return new AutoCropVTransform(sourceImage, ANALYSIS_OPTION);
	}
	public static ManualCropTransform getManualCropper(JImage images, int ANALYSIS_OPTION) throws IOException {
		return new ManualCropTransform(images, ANALYSIS_OPTION);
	}
 
}