package dev.utility.jimage.transforms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import dev.utility.jimage.DrawUtility;
import dev.utility.jimage.JImage;
import dev.utility.jimage.JImageMetadata;
import dev.utility.jimage.transforms.CombineListTransform;
import dev.utility.jimage.transforms.JImageTransform;
import dev.utility.jimage.transforms.ScaleCropTransform;;

public class ManualCropTransform {

	public static final  int DIAGNOAL_IMAGE_SET=0;
	public static final int HORIZONTAL_IMAGE_SET=1;
	
	public static final int SINGLE_CROP = 2; 
	public static final int MULTI_CROP = 3; 

	
	int counter;
	int imageSetType;
	JLabel imageLabel;

	public static boolean running = true;
	JFrame window;
	ScaleCropTransform converter;
	JImage originalImage;
	List<JImage> images = new ArrayList<JImage>();
	static boolean topPointSet = false;
	MouseListener windowListener;

	  ManualCropTransform(JImage originalImage, int imageSetType) throws IOException {

		this.originalImage = originalImage;
		this.imageSetType = imageSetType;
		counter = 0;

		if (imageSetType == HORIZONTAL_IMAGE_SET)
			windowListener = new HorizontalImageListener();
		else if (imageSetType == DIAGNOAL_IMAGE_SET)
			windowListener = new DiagnoalImageListener();
		else
			throw new IOException("Invalid class input variable: " + this.getClass().getName());

		converter = JImageTransform.getScaleCropper(originalImage, 1000, 1000);
		imageLabel = new JLabel(converter.getScaledOriginal().getImageIcon());
		window = new JFrame("cropper");
		window.add(imageLabel);
		window.addMouseListener(windowListener);
		window.setSize(converter.getScaledOriginal().getWidth(), converter.getScaledOriginal().getHeight());
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	public void process() {
		running = true;
		window.setVisible(true);
	}

	public List<JImage> getImages() {
		return this.images;
	}

	public class HorizontalImageListener implements MouseListener {
		int x1 = 0;
		int y1;
		int y2;
		int x2;
		int width;
		int height;

		public void mouseClicked(MouseEvent e) {

			if (e.getClickCount() == 1) {
				x2 = e.getX();
				y2 = converter.getScaledOriginal().getHeight() - 1;

				System.out.println("Bottom Right: " + x2 + " , " + y2);
				width = x2 - x1;
				height = y2 - 0;

				topPointSet = false;

				converter.scaleToSource(x1, y1, x2, y2);
				JImage img = converter.getSourceCrop();
				images.add(img);

				Graphics2D g2d = converter.getScaledOriginal().getBufferedImage().createGraphics();
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(3));
				g2d.drawRect(x1, y1, width, height);
				g2d.dispose();
				x1 = x2;

			} else if (e.getClickCount() == 2) {
				running = false;
			}

		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class DiagnoalImageListener implements MouseListener {
		int x1;
		int y1;
		int y2;
		int x2;
		int width;
		int height;

		public void mouseClicked(MouseEvent e) {

			if (e.getClickCount() == 1 && topPointSet == false) {
				x1 = e.getX();
				y1 = e.getY();
				System.out.println("Top Left: " + x1 + " , " + y1);
				topPointSet = true;
			} else if (e.getClickCount() == 1 && topPointSet == true) {
				x2 = e.getX();
				y2 = e.getY();
				System.out.println("Bottom Right: " + x2 + " , " + y2);
				width = x2 - x1;
				height = y2 - y1;

				topPointSet = false;

				try {
					DrawUtility drawer = new DrawUtility(converter.getScaledOriginal());
					drawer.drawRectangle(new Rectangle(x1, y1, width, height), 4, Color.black);
					converter.setScaledOriginal(drawer.getResultImage());
					converter.getScaledOriginal().displayImage();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				converter.scaleToSource(x1, y1, x2, y2);
				JImage img = converter.getSourceCrop();
				images.add(img);
				String filePath = originalImage.getMetadata().getValue(JImageMetadata.PARENT_PATH) + counter++
						+ ".png";
				try {
					img.writeJImageAsPNG(filePath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else if (e.getClickCount() == 2) {

				CombineListTransform combineList;
				try {
					combineList = JImageTransform.getCombineManyImages(images);
	
					JImage finalImg = combineList.combineLists();
					finalImg.writeJImageAsPNG(
							originalImage.getMetadata().getValue(JImageMetadata.PARENT_PATH) + "final.png");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
