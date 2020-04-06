package dev.utility.jimage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import dev.utility.jbase.JHelper;
import dev.utility.jbase.JImageString;
import dev.utility.jbase.constants.JConstants;
import dev.utility.jbase.printer.JPrinter;
import dev.utility.jimage.transforms.base.BaseTransform;
import dev.utility.jimage.transforms.base.ScaleTransform;

/**
 * @author r4770
 *
 */
public class JImage {

	private BufferedImage bufferedImage;
	private static SimpleDateFormat sdf;
	private JImageMetadata metadata;
	public boolean isPortrait;
	public boolean isLandscape;
	private String image_id;
 
	// ========================================================================================================================================
	// Constructors
	// ========================================================================================================================================
	public JImage(ImageIcon imageIcon) {
		this.bufferedImage = JImageTranslator.imageIconToBufferedImage(imageIcon);
		this.metadata = new JImageMetadata(bufferedImage);
		this.image_id=this.metadata.getValue(this.metadata.IMAGE_ID);

		setSize();
	}

	public JImage(Image image) {

		this.bufferedImage = JImageTranslator.imageToBufferedImage(image);
		this.metadata = new JImageMetadata(bufferedImage);
		this.image_id=this.metadata.getValue(this.metadata.IMAGE_ID);
		setSize();
	}

	public JImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		this.metadata = new JImageMetadata(bufferedImage);
		this.image_id=this.metadata.getValue(this.metadata.IMAGE_ID);

		setSize();
	}

	public JImage(JImage jImage) {
		this.bufferedImage = jImage.bufferedImage;
		this.metadata = new JImageMetadata(bufferedImage);
		this.metadata.setMetadata(jImage.metadata.getmetadata());
		this.image_id=this.metadata.getValue(this.metadata.IMAGE_ID);

		setSize();

	}

	public JImage(String absolutePath) {

		String msg="Error message";

		try 
		{
			if (absolutePath.contains(JConstants.IP01_EXTENSION) || absolutePath.contains(JConstants.IJ01)) {
				msg=msg+"Attempting to read a codec image"; 
				byte[] array = Files.readAllBytes(Paths.get(absolutePath));
				JImage temp = JImageTranslator.decryptJImage(array);
				this.bufferedImage = temp.bufferedImage;
			} 
			else if (absolutePath.contains("www"))
			{
				msg=msg+"Attempting to read website image"; 
				URL url = new URL(absolutePath);
				this.bufferedImage = ImageIO.read(url);
			}
			else
			{
				msg=msg+"Attempting to read a disk image"; 
				this.bufferedImage = ImageIO.read(new File(absolutePath));

			}
			
			metadata = new JImageMetadata(bufferedImage);
			setSize();
			this.metadata.setFileInformation(absolutePath);
			this.metadata.putValue(JImageMetadata.HEIGHT, getHeight().toString());
			this.metadata.putValue(JImageMetadata.WIDTH, getWidth().toString());
		} 
		catch (Exception e) 
		{
			JHelper.exit_Error(this.getClass().getPackageName() + this.getClass().getName(), e,msg);

		}

	}

	// ==========================================================================================================================
	// Utiltity methods
	// ==========================================================================================================================

	public void writeJImageAsJPG() throws IOException {
		String filename = JConstants.OUTPUT_DESKTOP_DIRECTORY + JConstants.getMiliNumericTimeStamp() + "."
				+ JConstants.JPG;
		writeJImageAsJPG(filename);
	}

	public void writeJImageAsPNG() throws IOException {
		String filename = JConstants.OUTPUT_DESKTOP_DIRECTORY + JConstants.getMiliNumericTimeStamp() + ","
				+ JConstants.PNG;

		writeJImageAsPNG(filename);
	}

	public void writeJImageAsJPG(String outputPath) throws IOException {

		if (!outputPath.contains(JConstants.JPG))
			outputPath = outputPath + "." + JConstants.JPG;
		writeImageToFile(outputPath, JConstants.JPG);
	}

	public void writeJImageAsPNG(String outputPath) throws IOException {

		if (!outputPath.contains(JConstants.PNG))
			outputPath = outputPath + "." + JConstants.PNG;

		writeImageToFile(outputPath, JConstants.PNG);

	}

	private void writeImageToFile(String outputPath, String imageType) throws IOException {
		try {
			BufferedImage image = this.getBufferedImage();

			BufferedImage writeImage = null;
			if (JImageString.isJPG(imageType)) {
				writeImage = JImageTranslator.drawJpgImage(this.bufferedImage);
			} else if (JImageString.isPNG(imageType)) {
				writeImage = JImageTranslator.drawPngImage(this.bufferedImage);
			} else {
				System.out.println("unsupported image type...." + imageType);
				System.exit(1);
			}

			ImageIO.write(writeImage, imageType, new File(outputPath));
		} catch (IOException e) {
			System.out.println("JImage write error");
			e.printStackTrace();

		}

	}

	public void scaleWidth(int x) {
		ScaleTransform bt = BaseTransform.getScaler(this);
		this.bufferedImage = bt.scaleByX(x).getBufferedImage();
	}

	public void scaleHeight(int y) throws IOException {
		ScaleTransform bt = BaseTransform.getScaler(this);
		this.bufferedImage = bt.scaleByY(y).getBufferedImage();
	}

	public void scaleAuto(int x, int y) {
		ScaleTransform bt = BaseTransform.getScaler(this);
		this.bufferedImage = bt.scaleAuto(x, y).getBufferedImage();
	}

	public void printMetadata() {
		metadata.printMetadata();
	}

	public void displayImage(String title) {
		this.getMetadata().putValue(JImageMetadata.FILE_NAME, title);
		displayImage();
	}

	public void displayImage() {
		JLabel label = new JLabel();
		label.setIcon(this.getImageIcon());
		label.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
		label.setOpaque(false);
		JPanel labelHolder = new JPanel();
		labelHolder.add(label);
		labelHolder.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane(labelHolder, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setOpaque(false);
		JFrame frame = new JFrame(this.metadata.getValue(JImageMetadata.FILE_NAME));
		frame.add(scrollPane);
		frame.setSize(this.getWidth(), this.getHeight());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void setSize() {

		if (bufferedImage.getWidth() > bufferedImage.getHeight()) {
			this.isPortrait = false;
			this.isLandscape = true;
		} else {
			this.isPortrait = true;
			this.isLandscape = false;
		}
	}

	public int getRGB(int x, int y) {
		int result = bufferedImage.getRGB(x, y);
		return result;
	}

	public void setRGB(int x, int y, int rgb) {
		this.bufferedImage.setRGB(x, y, rgb);

	}

	//
	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}

	public Image getImage() {
		return JImageTranslator.bufferedImageToImage(bufferedImage);
	}

	public ImageIcon getImageIcon() {
		return JImageTranslator.bufferedImageToImageIcon(bufferedImage);
	}

	public Integer getWidth() {
		return bufferedImage.getWidth();
	}

	public Integer getHeight() {
		return bufferedImage.getHeight();
	}

	public JImageMetadata getMetadata() {

		return metadata;
	}

	/**
	 * @param metadata
	 */
	public void setMetadata(JImageMetadata metadata) {
		this.metadata = metadata;
	}

	public byte[] encryptImage() throws IOException {
		byte[] encryptedBytes;
		encryptedBytes = JImageTranslator.encryptJImage(this);
		return encryptedBytes;

	}

	public void writeEncryptdImage(String filePath) throws IOException {
		byte[] encyptedBytes = encryptImage();
		String finalPath = filePath + JConstants.IP01_EXTENSION;
		JImageTranslator.writeEncryptedBytes(encyptedBytes, finalPath);
	}

	public static class Helper {

	}

}
