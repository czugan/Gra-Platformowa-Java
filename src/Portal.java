import java.awt.Graphics;
import java.awt.Rectangle;

public class Portal extends GameObject
{

	public Portal(int x, int y, ID id, IDspecial ids) {
		super(x, y, id, ids);

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, 32, 32);
	}
	
}
