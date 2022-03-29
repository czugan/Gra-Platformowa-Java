import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler
{
    LinkedList<GameObject> object = new LinkedList<GameObject>();
    SpriteSheet ss;
    Handler handler; 
    private boolean left = false, right = false, jump = false, hit = false, fall = true;

    public void loadLevel(BufferedImage image)
    {
    	int w = image.getWidth();
    	int h = image.getHeight();
    	
    	for(int xx = 0; xx < w; xx++) {
    		for(int yy = 0; yy < h; yy++)
    		{
    			int pixel = image.getRGB(xx, yy);
    			int red = (pixel >> 16) & 0xff;
    			int green = (pixel >> 8) & 0xff;
    			int blue = (pixel) & 0xff;
    			
    			if(red == 0 && green == 0 && blue == 255 && Game.poziom == 1)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 254 && Game.poziom == 2)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 253 && Game.poziom == 3)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 252 && Game.poziom == 4)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 251 && Game.poziom == 5)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			
    			if(red == 255 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.None, handler, ss));
    			}
    			if(red == 182 && green == 255 && blue == 0)
    			{
    				handler.addObject(new Empty(xx*32, yy*32, ID.Empty, IDspecial.None));
    			}
    			if(red == 255 && green == 106 && blue == 0)
    			{
    				handler.addObject(new BadGuy(xx*32, yy*32, ID.BadGuy, IDspecial.None, handler, ss));
    			}
    			//pills
    			if(red == 255 && green == 0 && blue == 110)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.None, handler, ss));//podstawowa tabletka
    			}
    			if(red == 255 && green == 0 && blue == 111)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin1, handler, ss));
    				Game.coin1 = true;
    			}
    			if(red == 255 && green == 0 && blue == 112)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin2, handler, ss));
    				Game.coin2 = true;
    			}
    			
    			//monsters
    			if(red == 0 && green == 0 && blue == 127)//podstawowy przeciwnik bez funkcji
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.None, handler, ss));
    			}			
    			if(red == 0 && green == 0 && blue == 128)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon1, handler, ss));
    				Game.mon1 = true;
    			}
    			if(red == 0 && green == 0 && blue == 129)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon2, handler, ss));
    				Game.mon2 = true;
    			}
    			//platforms
    			if(red == 255 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.None, handler, ss));// zwyk³a ziemia
    			}
    			
    			if(red == 254 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat1, handler, ss));
    			}
    			
    			if(red == 253 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat2, handler, ss));
    			}
    			
    			if(red == 252 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat3, handler, ss));
    			}
    			
    			if(red == 251 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat4, handler, ss));
    			}
    		}
    	}
    }
    public void ClearLevel()
    {
    	object.clear();
    }
    
    public boolean isFall() {
		return fall;
	}

	public void setFall(boolean fall) {
		this.fall = fall;
	}

	public  boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public void tick()
    {
    	for(int i = 0; i < object.size(); i++)
    	{
    		GameObject tempObject = object.get(i);
    		tempObject.tick();
    	}
    }
    
    public void render(Graphics g)
    {
    	for(int i = 0; i < object.size(); i++)
    	{
    		GameObject tempObject = object.get(i);
    		tempObject.render(g);
    	}
    }
    
    public void removeObject(GameObject tempObject)
    {
    	object.remove(tempObject);
    }
    
    public void addObject(GameObject tempObject)
    {
    	object.add(tempObject);
    }

}
