package dev.utility.jimage.transforms;

import java.io.IOException;

import dev.utility.jimage.JImage;
import dev.utility.jimage.transforms.base.BaseTransform;
import dev.utility.jimage.transforms.base.CropTransform;
import dev.utility.jimage.transforms.base.ScaleTransform;

public class ScaleCropTransform {

	CropTransform sourceData;
	CropTransform scaledData;
	ScaleTransform scaler;
	Double sourceToScaleFactor;
	Double scaleToSourceFactor; 


	ScaleCropTransform(JImage image, int newWidth, int newHeight) throws IOException {

		sourceData = BaseTransform.getCropper(image);
		scaler = BaseTransform.getScaler(image,ScaleTransform.SCALE_AUTO);
		scaler.process(newWidth, newHeight);
		scaledData = BaseTransform.getCropper(scaler.process(newWidth, newHeight));
		sourceToScaleFactor = scaler.getScaleFactor();
		scaleToSourceFactor = scaler.getReverseScaleFactot();

	}

	public void scaleToSource(int x1, int y1, int x2, int y2) {
		scaledData.proessImage(x1, y1, x2, y2);

		int newX1 = (int) (x1 * scaleToSourceFactor);
		int newY1 = (int) (y1 * scaleToSourceFactor);
		int newX2 = (int) (x2 * scaleToSourceFactor);
		int newY2 = (int) (y2 * scaleToSourceFactor);

		sourceData.proessImage(newX1, newY1, newX2, newY2);
	}

	public void sourceToScale(int x1, int y1, int x2, int y2) {
		sourceData.proessImage(x1, y1, x2, y2);

		int newX1 = (int) (x1 * sourceToScaleFactor);
		int newY1 = (int) (y1 * sourceToScaleFactor);
		int newX2 = (int) (x2 * sourceToScaleFactor);
		int newY2 = (int) (y2 * sourceToScaleFactor);

		scaledData.proessImage(newX1, newY1, newX2, newY2);
	}

	public JImage getSourceOriginal() {
		return sourceData.getSourceImage();
	}

	public JImage getScaledOriginal() {
		return scaledData.getSourceImage();
	}

	public JImage getSourceCrop() {
		return sourceData.getCroppedImage();
	}

	public JImage getScaledCrop() {
		return scaledData.getCroppedImage();
	}

	public void setSourceOriginal(JImage image) {
		this.sourceData.setSourceImage(image);
	}

	public void setScaledOriginal(JImage image) {
		this.scaledData.setSourceImage(image);
	}
}
