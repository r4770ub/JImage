package dev.utility.jimage.functions;

public class Outliner {

	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private boolean isBaseImage;

	private int width;
	private int length;
 
	public Outliner() {
		this.x1 = 0; 
		this.x2 = 0;
		this.y1 = 0;
		this.y2 = 0;
		this.width = 0;
		this.length = 0;
 
	}

	public Outliner(int x1, int y1, int x2, int y2, boolean isBaseImage) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		this.setBaseImage(isBaseImage);
		this.width = x2 - x1;
		this.length = y2 - y1;

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public boolean isBaseImage() {
		return isBaseImage;
	}

	public void setBaseImage(boolean isBaseImage) {
		this.isBaseImage = isBaseImage;
	}

	@Override
	public String toString() {
		String ans = "";
		ans = "(" + x1 + "," + y1 + ")" + "<----->" + "(" + x1 + "," + x1 + width + ")\n" + "(" + x2 + "," + y1 + ")"
				+ "<----->" + "(" + x2 + "," + y2 + ")";

		return ans;
	}

}