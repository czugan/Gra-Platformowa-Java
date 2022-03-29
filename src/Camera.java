
public class Camera
{
	float x, y;
	
	Handler handler;
	
	static float xX, yY;
	
	static float changeX, changeY;
	
	static int granica = 992;
	
	public Camera(float x, float y, Handler handler)
	{
		this.x = x;
		this.y = y;
		this.handler = handler;
	}

	public void tick(GameObject object)
	{
		xX = -x;
		yY = -y;
		
		x += changeX;
		y += changeY;
		
		//ruch kamery przy dojœciu do odpowiedniego miejsca
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player)
			{
				
				if(Game.poziom == 1)
				{
					granica = 992;
				}
				
				if(Game.poziom == 2)
				{
					granica = 1984;
				}
				
				if(Game.poziom == 3)
				{
					granica = 2976;
				}
				
				if(Game.poziom == 4)
				{
					granica = 3968;
				}
				
				if(Game.poziom == 5)
				{
					granica = 4960;
				}
				

				if(Player.X >= granica)//992
				{
					
					if(x >= 0 && x < 992 && Game.poziom == 1)
					{
						x += 31;
						
					}if(x == 992 && Game.poziom == 1)
					{
						Game.poziom = 2;
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
					}
					if(x >= 992 && x < 1984 && Game.poziom == 2)
					{
						x += 31;
					}if(x == 1984 && Game.poziom == 2)
					{
						Game.poziom = 3;
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
					}
					if(x >= 1984 && x < 2976 && Game.poziom == 3)
					{
						x += 31;
					}if(x == 2976 && Game.poziom == 3)
					{
						Game.poziom = 4;
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
					}
					if(x >= 2976 && x < 3968 && Game.poziom == 4)
					{
						x += 31;
					}if(x == 3968 && Game.poziom == 4)
					{
						Game.poziom = 5;	
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
					}
					if(x >= 3968 && x < 4960 && Game.poziom == 5)
					{
						x += 31;
					}
					if(x >= 3968) x = 3904-16;//3936
					
				}
			}
		}
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
