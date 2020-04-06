package dev.utility.jimage;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawUtility {

	private JImage sourceImage;
	private JPanel sourcePanel;
 
	private JImage resultImage;
	private JPanel resultPanel; 

	public DrawUtility(JImage sourceImage) throws IOException {
		this.sourceImage = sourceImage;
		this.sourcePanel = drawPanel(sourceImage);
		this.resultImage = new JImage(sourceImage.getBufferedImage());
		this.resultPanel = drawPanel(resultImage);
	}

	public DrawUtility(JPanel sourcePanel) throws IOException {
		this.sourcePanel = sourcePanel;
		this.resultPanel = sourcePanel;
		this.sourceImage = drawImage(sourcePanel);
		this.resultImage = drawImage(resultPanel);
	}
 
	public void drawRectangle(Rectangle rect, int thickness, Color color) {
		int x1 = rect.x;
		int y1 = rect.y;
		int x2 = rect.width - x1;
		int y2 = rect.height - y1;

		BufferedImage bimage = resultImage.getBufferedImage();
		Graphics2D g2d = bimage.createGraphics();
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawRect(x1, y1, x2 - x1, y2 - y1);
		g2d.dispose();
		resultImage = new JImage(bimage);
		this.resultPanel = drawPanel(resultImage);

	}

	public void drawLine(int x1, int y1, int x2, int y2, int thickness, Color color) {
		Graphics2D g2d = resultImage.getBufferedImage().createGraphics();
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(4));
		g2d.drawLine(x1, y1, x2, y2);
		g2d.dispose();
		this.resultPanel = drawPanel(resultImage);

	}

	private JPanel drawPanel(JImage image) {
		JPanel panel = new JPanel();
		panel.setSize(image.getWidth(), image.getHeight());
		panel.add(new JLabel(image.getImageIcon()));
		return panel;

	}

	public void drawCenterText(String textField, Font font, Color color) {

		Graphics2D g2d = (Graphics2D) resultImage.getBufferedImage().getGraphics();
		FontMetrics fontMetrics = resultPanel.getFontMetrics(font);

		g2d.setFont(font);

		TextLayout textLayout = new TextLayout(textField, g2d.getFont(), g2d.getFontRenderContext());
		double textWidth = fontMetrics.stringWidth(textField);
		double textHeight = textLayout.getBounds().getHeight();

		int P1X = resultImage.getWidth() / 2 - (int) textWidth / 2;
		int P1Y = resultImage.getHeight() / 2 - (int) textHeight / 2;
		int P2X = P1X + fontMetrics.stringWidth(textField);
		int P2Y = P1Y + fontMetrics.getHeight();
		g2d.setColor(color);
		// Draw the text in the center of the image
		g2d.drawString(textField, P1X, P1Y);
		g2d.dispose();
		this.resultPanel = drawPanel(resultImage);
	}
	
	public void drawCenterText(String textField, Font font, Color color,int y1) {

		Graphics2D g2d = (Graphics2D) resultImage.getBufferedImage().getGraphics();
		FontMetrics fontMetrics = resultPanel.getFontMetrics(font);

		g2d.setFont(font);

		TextLayout textLayout = new TextLayout(textField, g2d.getFont(), g2d.getFontRenderContext());
		double textWidth = fontMetrics.stringWidth(textField);
		double textHeight = textLayout.getBounds().getHeight();

		int P1X = resultImage.getWidth() / 2 - (int) textWidth / 2;
		int P1Y = resultImage.getHeight() / 2 - (int) textHeight / 2;
		int P2X = P1X + fontMetrics.stringWidth(textField);
		int P2Y = P1Y + fontMetrics.getHeight();
		g2d.setColor(color);
		// Draw the text in the center of the image
		g2d.drawString(textField, P1X, y1);
		g2d.dispose(); 
		this.resultPanel = drawPanel(resultImage);
	}
//	public void paintTextWithOutline(Graphics g) {
//	    String text = "some text";
//
//	    Color outlineColor = Color.white;
//	    Color fillColor = Color.black;
//	    BasicStroke outlineStroke = new BasicStroke(2.0f);
//
//	    if (g instanceof Graphics2D) {
//	        Graphics2D g2 = (Graphics2D) g;
//
//	        // remember original settings
//	        Color originalColor = g2.getColor();
//	        Stroke originalStroke = g2.getStroke();
//	        RenderingHints originalHints = g2.getRenderingHints();
//
//
//	        // create a glyph vector from your text
//	        GlyphVector glyphVector = font.createGlyphVector(g2.getFontRenderContext(), text);
//	        // get the shape object
//	        Shape textShape = glyphVector.getOutline();
//
//	        // activate anti aliasing for text rendering (if you want it to look nice)
//	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//	                RenderingHints.VALUE_ANTIALIAS_ON);
//	        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
//	                RenderingHints.VALUE_RENDER_QUALITY);
//
//	        g2.setColor(outlineColor);
//	        g2.setStroke(outlineStroke);
//	        g2.draw(textShape); // draw outline
//
//	        g2.setColor(fillColor);
//	        g2.fill(textShape); // fill the shape
//
//	        // reset to original settings after painting
//	        g2.setColor(originalColor);
//	        g2.setStroke(originalStroke);
//	        g2.setRenderingHints(originalHints);
//	    }
//	}
	
	public void drawText(String textField, Font font,Color color, int x1,int y1) {

		Graphics2D g2d = (Graphics2D) resultImage.getBufferedImage().getGraphics();
		FontMetrics fontMetrics = resultPanel.getFontMetrics(font);

		g2d.setFont(font);

		TextLayout textLayout = new TextLayout(textField, g2d.getFont(), g2d.getFontRenderContext());
		double textWidth = fontMetrics.stringWidth(textField);
		double textHeight = textLayout.getBounds().getHeight();

		int P1X = resultImage.getWidth() / 2 - (int) textWidth / 2;
		int P1Y = resultImage.getHeight() / 2 - (int) textHeight / 2;
		int P2X = P1X + fontMetrics.stringWidth(textField);
		int P2Y = P1Y + fontMetrics.getHeight();
		g2d.setColor(color);
		// Draw the text in the center of the image
		g2d.drawString(textField, x1, y1);
		g2d.dispose();
		this.resultPanel = drawPanel(resultImage);
	} 

	public void drawOverlayImage(JImage image, float transparency)
	{
		
		try
		{
		BufferedImage imgA = this.resultImage.getBufferedImage();
	  
		BufferedImage imgB = image.getBufferedImage();
	   
	     
	        int compositeRule = AlphaComposite.SRC_OVER;
	        AlphaComposite ac;
	        int imgW = imgA.getWidth();
	        int imgH = imgA.getHeight();
	        BufferedImage overlay = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = overlay.createGraphics();
	        ac = AlphaComposite.getInstance(compositeRule, transparency);
	        g.drawImage(imgA,0,0,null);
	        g.setComposite(ac);
	        g.drawImage(imgB,0,0,null);
	        g.setComposite(ac);
	        resultImage = new JImage(overlay);
	        g.dispose();
	        resultPanel=drawPanel(resultImage);
		}
		catch(Exception e)
		{
			System.err.println("Unable to load overlay image in jimage");
		}  
	        
	      
		 
		
	}
	private JImage drawImage(JPanel panel) {
		BufferedImage bImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		panel.paint(bImage.getGraphics());
		return new JImage(bImage);
	}

	public JImage getSourceImage() {
		return sourceImage;
	}

	public JPanel getSourcePanel() {
		return sourcePanel;
	}

	public JPanel getResultPanel() {
		return resultPanel;
	}

	public JImage getResultImage() {
		return resultImage;
	}

}
