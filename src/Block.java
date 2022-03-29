import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends GameObject
{
	Handler handler;
	SpriteSheet ss;
	
	static boolean pl1, pl2, pl3, pl4, pl5, pl6, pl7, pl8, pl9, pl10, pl11, pl12, pl13, pl14, pl15, pl16, pl17, pl18= false;

	public Block(int x, int y, ID id, IDspecial ids, Handler handler, SpriteSheet ss) {
		super(x, y, id, ids);
		this.handler = handler;
		this.ss = ss;
		
		if(Game.sekcja == 1)
		{
			pl1 = true;
			pl2 = true;
			pl3 = true;
			pl4 = true;
		}
		if(Game.sekcja == 2)
		{
			pl5 = true;
			pl6 = true;
			pl7 = true;
			pl8 = true;
			pl9 = true;
			pl10 = true;
			pl11 = true;
		}
		
		if(Game.sekcja == 3)
		{
			pl12 = true;
			pl13 = true;
			pl14 = true;
			pl15 = true;
			pl16 = true;
			pl17 = true;
			pl18 = true;
		}
		
	}

	@Override
	public void tick()
	{
		
	}

	@Override
	public void render(Graphics g) {
	//	g.setColor(Color.red);
	//	g.fillRect(x, y, 32, 32);
	
		
		g.drawImage(ss.grabImage(18, 2, 32, 32), x, y, null);

		

	//	g.setColor(Color.green);
	//	g.drawRect(x, y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
}
