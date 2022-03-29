import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Enemy extends GameObject 
{
	Handler handler;
	SpriteSheet ss;
	
	int time;
	boolean left = false, right = true, stop = false;
	
	private BufferedImage[] standR = new BufferedImage[3];
	private BufferedImage[] standL = new BufferedImage[3];
	private BufferedImage[] runR = new BufferedImage[5];
	private BufferedImage[] runL = new BufferedImage[5];
	
	Animation animSR;
	Animation animSL;
	Animation runningR;
	Animation runningL;
	
	public Enemy(int x, int y, ID id, IDspecial ids, Handler handler, SpriteSheet ss)
	{
		super(x, y, id, ids);
		this.handler = handler;
		this.ss = ss;
		setVelX(-2);
		time = 200;
		
		standR[0] = ss.grabImage(1, 11, 32, 64);
		standR[1] = ss.grabImage(2, 11, 32, 64);
		standR[2] = ss.grabImage(3, 11, 32, 64);
		animSR = new Animation(6, standR);
		
		standL[0] = ss.grabImage(6, 11, 32, 64);
		standL[1] = ss.grabImage(5, 11, 32, 64);
		standL[2] = ss.grabImage(4, 11, 32, 64);
		animSL = new Animation(6, standL);
		
		runR[0] = ss.grabImage(1, 13, 32, 64);
		runR[1] = ss.grabImage(2, 13, 32, 64);
		runR[2] = ss.grabImage(3, 13, 32, 64);
		runR[3] = ss.grabImage(4, 13, 32, 64);
		runR[4] = ss.grabImage(5, 13, 32, 64);
		runningR = new Animation(6, runR);
		
		runL[0] = ss.grabImage(10, 13, 32, 64);
		runL[1] = ss.grabImage(9, 13, 32, 64);
		runL[2] = ss.grabImage(8, 13, 32, 64);
		runL[3] = ss.grabImage(7, 13, 32, 64);
		runL[4] = ss.grabImage(6, 13, 32, 64);
		runningL = new Animation(6, runL);
			
	}
	
	public void tick()
	{
		
		x += velX;
		y += velY;
		
		if(handler.isFall())
		{
			if(velY < 10)velY += 0.3;
		}
	
		try{collision();}catch(Exception E) {};
	
		
		animSR.runAnimation();
		animSL.runAnimation();
		runningR.runAnimation();
		runningL.runAnimation();
	}
	
	public void collision()
	{
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(y > 600)
			{
				if(tempObject.getIds() == IDspecial.Mon1)
				{
					handler.removeObject(this);
					Game.mon1 = false;
					Game.killed++;
					Player.playSound(3);
				}
				if(tempObject.getIds() == IDspecial.Mon2)
				{
					handler.removeObject(this);
					Game.mon1 = false;
					Game.killed++;
					Player.playSound(3);
				}
				if(tempObject.getIds() == IDspecial.Mon7)
				{
					handler.removeObject(this);
					Game.mon7 = false;
					Game.killed++;
					Player.playSound(3);
				}
				
			}
			
			if(tempObject.getId() == ID.Block)
			{
				if(bD().intersects(tempObject.getBounds()))
				{
					velY = 0;
					handler.setFall(false);
					y = tempObject.getY() - 64;
		
				}
				else handler.setFall(true);
				
				if(bU().intersects(tempObject.getBounds()))
				{
					if(velY < 0)velY *= -1;
					if(velY == 0)velY = 1; 
					
					handler.setFall(true);
				}
				if(bL().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() + 32; 
					handler.setFall(true);
					
				}
				if(bR().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() - 20; 
					handler.setFall(true);	
				}
			}
			
			if(tempObject.getId() == ID.Empty)
			{
				if(fallR().intersects(tempObject.getBounds()))
				{
					if(time > 0)
					{
						time--;
						setVelX(0);
					}
					else if(time == 0)
					{
						right = false;
						left = true;
						setVelX(2);
						time = 200;
					}
				}
				
				if(fallL().intersects(tempObject.getBounds()))
				{
					if(time > 0)
					{
						time--;
						setVelX(0);
					}
					else if(time == 0)
					{
						right = true;
						left = false;
						setVelX(-2);
						time = 200;
					}
				}		
			}
		}
	}
	
	public void render(Graphics g)
	{
		
		if(velX == 0 && right)animSL.drawAnimation(g, x, y, 0);
		if(velX == 0 && left)animSR.drawAnimation(g, x, y, 0);
		
		if(velX < 0 && right)runningL.drawAnimation(g, x, y, 0);
		if(velX > 0 && left)runningR.drawAnimation(g, x, y, 0);
		
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y + 24, 20, 40);
	}
	public Rectangle bR()
	{
		return new Rectangle(x + 20, y + 26, 4, 34);
	}
	
	public Rectangle bL()
	{
		return new Rectangle(x, y + 26, 4, 34);	
	}
	
	public Rectangle bU()
	{
		return new Rectangle(x + 4, y+20, 16, 20);	
	}
	
	public Rectangle bD()
	{
		return new Rectangle(x + 4, y+40, 16, 24);	
	}
	
	public Rectangle fallR()
	{
		return new Rectangle(x-10, y+64, 10, 10);	
	}
	public Rectangle fallL()
	{
		return new Rectangle(x+20, y+64, 10, 10);	
	}
}
