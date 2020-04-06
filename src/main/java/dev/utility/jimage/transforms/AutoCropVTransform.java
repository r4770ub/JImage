package dev.utility.jimage.transforms;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import dev.utility.jbase.constants.JConstants;
import dev.utility.jimage.JImage;
import dev.utility.jimage.filters.CannyEdgeFilter;
import dev.utility.jimage.filters.GrayFilter;
import dev.utility.jimage.functions.Line;
import dev.utility.jimage.functions.OutlineExtractor;
import dev.utility.jimage.functions.OutlineExtractor.SubImage;
import dev.utility.jimage.functions.Outliner;
import dev.utility.jimage.functions.Rectangles;

public class AutoCropVTransform
{

	 int imageCounter = 0;
	 String imageDirectory;
	private JImage sourceImage;
	int width;
	int height;
	private JImage grayImage;
	private JImage edgeImage;
	private JImage lineImage;

	List<Outliner> rectangles;
	List<OutlineExtractor.SubImage> subImages;
	float lowerThreshold = 1.2f;
	float upperThreshold = 3f;
	static List<JImage> finalImages;
	public String saveDirectory = JConstants.OUTPUT_DESKTOP_DIRECTORY;
	boolean save; 
	boolean display;
	boolean horizontal ;
	boolean vertical;
	boolean diagnoal;
    
	public static final int NO_ACTION =0; 
	public static final int SAVE_ONLY = 1; 
	public static final int DISPLAY_ONLY = 2;
	public static final int SAVE_AND_DISPLAY = 3; 
	
	public static final int HORIZONTAL_ANALYSIS =4; 
	public static final int VERTICAL_ANALYSIS = 5; 
	public static final int DIAGNOAL_ANALYSIS = 6;
	

	
	Logger log = Logger.getLogger(AutoCropVTransform.class.getName());

	public AutoCropVTransform(JImage sourceImage, int analysisType)
			throws IOException, InterruptedException, Exception {
		log.info("Constructing image");

		if(analysisType == HORIZONTAL_ANALYSIS)
		{
			horizontal = true; 
			vertical=false;
			diagnoal =false;
		}
		else if(analysisType == VERTICAL_ANALYSIS)
			{
				horizontal = false; 
				vertical=true; 
				diagnoal =false;
			}
		else if(analysisType == DIAGNOAL_ANALYSIS)
		{
			horizontal = false; 
			vertical=false;
			diagnoal =true;
		}
		else
		{
			throw new IOException("Inaalid lass");
		}
		
		this.sourceImage = sourceImage;
		this.width = sourceImage.getWidth();
		this.height = sourceImage.getHeight();
	}

	public void process() throws InterruptedException, Exception {
		finalImages = new ArrayList<JImage>();

		// convert to grayscale
		GrayFilter grayConverter = new GrayFilter(sourceImage);
		grayConverter.process();
		this.grayImage = grayConverter.getGrayImage();

		// Detect edges
		CannyEdgeFilter detector = new CannyEdgeFilter(grayImage, lowerThreshold, upperThreshold);
		detector.process();
		this.edgeImage = new JImage(detector.getEdgesImage());

		// Find lines from edge images.
		Line lineCreator = new Line(edgeImage,Line.VERTICAL_ANALYSIS);
		lineCreator.process();
		this.lineImage = lineCreator.getLineImage();

		// Find rectangles from the lines
		Rectangles rectangleCreator = new Rectangles(lineCreator.extractVerticalLines(),
				lineCreator.extractHorizontalLines(), lineImage.getWidth(), lineImage.getHeight());
		rectangleCreator.process();
		rectangles = rectangleCreator.extractRectangles();

		// Extract subImages
		OutlineExtractor subImageCreator = new OutlineExtractor(sourceImage, rectangles);
		subImageCreator.process();
		this.subImages = subImageCreator.extractSubImages();
 
		
		    


	}

	public List<JImage> getSubImages() 
	{
		for(SubImage subimage: this.subImages)
		{
			finalImages.add(subimage.getImage());
		}
		return this.finalImages;
		
	}
	
	public JImage getSourceImage()
	{
		return this.sourceImage; 
	}
}
