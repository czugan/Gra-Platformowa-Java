import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BadGuy extends GameObject
{
	private BufferedImage[] standR = new BufferedImage[3];
	private BufferedImage[] standL = new BufferedImage[3];
	Animation animSR;
	Animation animSL;
	
	SpriteSheet ss;
	Handler handler;
	static boolean left, right;
	
	public BadGuy(int x, int y, ID id, IDspecial ids,Handler handler, SpriteSheet ss) {
		super(x, y, id, ids);
		this.handler = handler;
		
		y += velY;
		
		standR[0] = ss.grabImage(1, 9, 32, 64);
		standR[1] = ss.grabImage(2, 9, 32, 64);
		standR[2] = ss.grabImage(3, 9, 32, 64);
		animSR = new Animation(6, standR);
		
		standL[0] = ss.grabImage(6, 9, 32, 64);
		standL[1] = ss.grabImage(5, 9, 32, 64);
		standL[2] = ss.grabImage(4, 9, 32, 64);
		animSL = new Animation(6, standL);
	}

	@Override
	public void tick() {
		animSR.runAnimation();
		animSL.runAnimation();	
		
		try{collision();}catch(Exception E) {};

	}
	public void collision()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block)
			{	
				velY = 0;
			}
		}
	}
	@Override
	public void render(Graphics g) {
		if(right)animSR.drawAnimation(g, x, y-32, 0);
		if(left)animSL.drawAnimation(g, x, y-32, 0);
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x, y, 32, 64);
	}
	
}
