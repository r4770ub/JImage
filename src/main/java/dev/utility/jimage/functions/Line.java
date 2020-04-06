package dev.utility.jimage.functions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.utility.jimage.JImage;

public class Line {

	public static final int HORIZONTAL_ANALYSIS =1; 
	public static final int VERTICAL_ANALYSIS = 2; 
	public static final int DIAGNOAL_ANALYSIS = 3;

	private JImage sourceImage;
	private List<Integer> verticalLines;
	private List<Integer> horizontalLines;
	private List<Integer> verticalAnalysis;
	private List<Integer> horizontalAnalysis;
	private JImage lineImage;
	private int analysisType;
	public static int pixeldelta = 230; 
	public static float densityDelta = 2f;
 
	public Line(JImage sourceImage, int analysisType) {
		this.sourceImage = sourceImage;
		this.lineImage = new JImage(sourceImage);
		this.analysisType=analysisType;
		verticalLines = new ArrayList<Integer>();
		horizontalLines = new ArrayList<Integer>();
		verticalAnalysis = new ArrayList<Integer>();
		horizontalAnalysis = new ArrayList<Integer>();
	}

	public void process() throws IOException {

		System.out.println("Image Width: " + this.sourceImage.getWidth());
		System.out.println("Image Height: " + this.sourceImage.getHeight());

		System.out.println("analsysiType" + analysisType);
		drawBorder();
		if(analysisType ==HORIZONTAL_ANALYSIS)
		{
			getHorizontalLines();
			verticalLines.add(0);
			verticalLines.add(lineImage.getHeight());
		}
		else if(analysisType ==VERTICAL_ANALYSIS)
		{
			
			getVerticalLines();
			horizontalLines.add(0);
			horizontalLines.add(lineImage.getWidth());
		}
		else if(analysisType ==DIAGNOAL_ANALYSIS)
		{
		getVerticalLines();
		getHorizontalLines();
		}
		else
		{
			throw new IOException("error in input exiting");
		}
		eliminateDuplicates();
		drawVerticalLines();
		drawHorizontalLines();

	}

	private void drawBorder() {
		for (int i = 0; i < this.sourceImage.getWidth() - 1; i++)
			lineImage.setRGB(i, 0, Color.red.getRGB());
		for (int i = 0; i < this.sourceImage.getWidth() - 1; i++)
			lineImage.setRGB(i, this.sourceImage.getHeight() - 1, Color.red.getRGB());
		for (int i = 0; i < this.sourceImage.getHeight() - 1; i++)
			lineImage.setRGB(0, i, Color.red.getRGB());
		for (int i = 0; i < this.sourceImage.getHeight() - 1; i++)
			lineImage.setRGB(this.sourceImage.getWidth() - 1, i, Color.red.getRGB());

	}

	private void getHorizontalLines() throws IOException {

		System.out.println("Calculating horizontal lines");

		for (int i = 0; i < sourceImage.getHeight(); i++) {
			int edgePixals = 0;
			for (int j = 0; j < sourceImage.getWidth(); j++) {
				// check Current pixal
				if (checkActivePixal(j, i))
					edgePixals++;
				else if (checkActivePixal(j, i - 1))
					edgePixals++;
				else
				// check right pixal
				if (checkActivePixal(j, i + 1))
					edgePixals++;

			}

			// System.out.print("HL" + i +"-->" + edgePixals + "POE ||");
			// if(i%10 ==0)
			// System.out.println();
			horizontalAnalysis.add(edgePixals);

		}
		System.out.println();

		horizontalLines.add(0);
		System.out.println("Horizontal lines of 50% or greater threshold detected @ ");

		for (int i = 0; i < horizontalAnalysis.size(); i++) {
			Integer amount = horizontalAnalysis.get(i);
			

			if (amount > lineImage.getWidth() / densityDelta) {
				System.out.print(i + ",");

				horizontalLines.add(i);

			}

		}
		horizontalLines.add(lineImage.getHeight() - 1);
		System.out.println();

	}

	private void getVerticalLines() throws IOException {

		for (int i = 0; i < sourceImage.getWidth(); i++) {

			int edgePixals = 0;
			for (int j = 0; j < sourceImage.getHeight(); j++) {

				// check current pixal
				if (checkActivePixal(i, j) || checkActivePixal(i, j - 1) || checkActivePixal(i, j + 1) || checkActivePixal(i+1, j ) || checkActivePixal(i-1,j))
					edgePixals++;

			}

			// System.out.print("VL" + i +"-->" + edgePixals + "POE ||");
			// if(i%10 ==0)
			// System.out.println();
			verticalAnalysis.add(edgePixals);

		}
		System.out.println();

		verticalLines.add(9);
		System.out.println("Vertical lines of 50% or greater threshold detected @ ");

		for (int i = 0; i < verticalAnalysis.size(); i++) {
			Integer amount = verticalAnalysis.get(i);
			
			if (amount > lineImage.getHeight() / densityDelta) {
				System.out.print(i + ",");
				verticalLines.add(i);

			}

		}
		verticalLines.add(lineImage.getWidth());
		System.out.println();

	}

	private boolean checkActivePixal(int i, int j) {
		boolean active = false;

		try {
			int rawPixal = sourceImage.getRGB(i, j);
			Color pixalColor = new Color(rawPixal);

			if (pixalColor.getRed() > pixeldelta && pixalColor.getGreen() > pixeldelta && pixalColor.getBlue() > pixeldelta)
				active = true;

		} catch (Exception e) {
			// just means were out of bounds, this is bad coding style whoops
		}

		return active;

	}

	private void eliminateDuplicates() {
		int i = 0;
		while (i < verticalLines.size() - 2) {
			Integer line1 = verticalLines.get(i);
			Integer line2 = verticalLines.get(i + 1);
			if (line2 - line1 < 100) {
				System.out.println("Eliminating noise vertical line " + line1);
				verticalLines.remove(i);
			}

			i++;
		}

		i = 0;
		while (i < horizontalLines.size() - 1) {
			Integer line1 = horizontalLines.get(i);
			Integer line2 = horizontalLines.get(i + 1);
			if (line2 - line1 < 100) {
				System.out.println("Eliminating noise horizontal line " + line1);
				verticalLines.remove(line1);
			}

			i++;
		}

	}

	public void drawVerticalLines() throws IOException {

		for (Integer column : verticalLines) {

			Graphics2D g2d = (Graphics2D) lineImage.getBufferedImage().getGraphics();
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(1));
			g2d.drawLine(column, 0, column, lineImage.getHeight());
			g2d.dispose();
		}

	}

	public void drawHorizontalLines() throws IOException {

		for (Integer row : horizontalLines) {
			Graphics2D g2d = (Graphics2D) lineImage.getBufferedImage().getGraphics();
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(1));
			g2d.drawLine(0, row, lineImage.getWidth(), row);
			g2d.dispose();

		}
	}

	public JImage getLineImage() {
		return lineImage;
	}

	public List<Integer> extractVerticalLines() {
		return verticalLines;
	}

	public List<Integer> extractHorizontalLines() {
		return horizontalLines;
	}
}
