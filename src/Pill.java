import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Pill extends GameObject
{
	private BufferedImage[] animation = new BufferedImage[6];
	Animation pill;
	Handler handler;
	SpriteSheet ss;

	public Pill(int x, int y, ID id, IDspecial ids, Handler handler, SpriteSheet ss) {
		super(x, y, id, ids);
		
		animation[0] = ss.grabImage(1, 15, 32, 32);
		animation[1] = ss.grabImage(2, 15, 32, 32);
		animation[2] = ss.grabImage(3, 15, 32, 32);
		animation[3] = ss.grabImage(4, 15, 32, 32);
		animation[4] = ss.grabImage(5, 15, 32, 32);
		animation[5] = ss.grabImage(6, 15, 32, 32);
		
		pill = new Animation(6, animation);

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
		pill.runAnimation();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		pill.drawAnimation(g, x, y, 0);
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x+8, y, 16, 16);
	}
	
}
