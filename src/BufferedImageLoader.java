import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader 
{
	private BufferedImage image;
	
	public BufferedImage loadImage(String path)
	{
		try {
			image = ImageIO.read(getClass().getResource(path));
			
		} catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}
	/*
	public BufferedImage scaleImage(String path, int width, int height)
	{
		try {
			image = ImageIO.read(getClass().getResource(path));
			image = (BufferedImage) image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
			
		} catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}
	*///prawie dzia³a
}
