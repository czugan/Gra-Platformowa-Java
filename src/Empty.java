import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Empty extends GameObject
{

	public Empty(int x, int y, ID id, IDspecial ids) {
		super(x, y, id, ids);
	
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
	//	g.drawRect(x, y, 32, 32);
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x, y, 32, 32);
	}

}
