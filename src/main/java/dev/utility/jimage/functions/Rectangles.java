package dev.utility.jimage.functions;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Rectangles {

	List<Outliner> rectangles;
	List<Integer> verticalLines;
	List<Integer> horizontalLines;
	int width;
	int height;
	boolean isBaseImage = false;

	public Rectangles(List<Integer> verticalLines, List<Integer> horizontalLines, int width, int height)
			throws FileNotFoundException {

		this.width = width;
		this.height = height;
		this.verticalLines = verticalLines;
		this.horizontalLines = horizontalLines;
  		this.rectangles = new ArrayList<Outliner>();

	}

	public void process() {
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		Outliner rectangle = null;
		// Only vertical images
		if (horizontalLines.size() == 2 && verticalLines.size() > 2) {
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = height - 1;
			for (Integer verticalLine : verticalLines) {
				x2 = verticalLine;
				rectangle = new Outliner(x1, y1, x2, y2, false);
				if (rectangle.getWidth() > 100)
					rectangles.add(rectangle);
				x1 = x2;

			}

		}

		// Only horizontal Images
		else if (verticalLines.size() == 2 && horizontalLines.size() > 2) {
			x1 = 0;
			y1 = 0;
			x2 = width - 1;
			y2 = 0;
			for (Integer horizontalLine : horizontalLines) {
				y2 = horizontalLine;
				rectangle = new Outliner(x1, y1, x2, y2, false);
				if (rectangle.getLength() > 100 && rectangle.getWidth() > 100)
					rectangles.add(rectangle);
				y1 = y2;

			}

		}
		// have no sub images
		else if (verticalLines.size() == 2 && horizontalLines.size() == 2) {
			rectangle = new Outliner(0, 0, width, height, true);
			rectangles.add(rectangle);

		}
		// we have multipel vericala and horizontal images.
		else if (verticalLines.size() > 2 && horizontalLines.size() > 2) {
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
			for (Integer verticalLine : verticalLines) {
				for (Integer horizontalLine : horizontalLines) {
					x2 = verticalLine;
					y2 = horizontalLine;
					rectangle = new Outliner(x1, y1, x2, y2, false);
					if (rectangle.getWidth() > 100 && rectangle.getLength() > 100)
						rectangles.add(rectangle);
					y1 = y2;
				}
				x1 = x2;

			}

		}

	}

	public boolean isBaseImage() {
		return isBaseImage;
	}

	public List<Outliner> extractRectangles() {
		return rectangles;
	}

}
