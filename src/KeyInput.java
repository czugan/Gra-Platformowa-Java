import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter 
{
	Handler handler;
	
	public KeyInput(Handler handler)
	{
		this.handler = handler;
	}
	int time = 10;
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player)
			{
				
				
					
				if(key == KeyEvent.VK_A)
				{Game.dead = false;
					handler.setLeft(true);
					Player.look_right = false;
					Player.look_left = true;
					
					if(time == 10)
					{
						
						Player.playSound(6);
						
						time = 10;		
						
					}
					if(time > 0)
					{
						time--;
						
					}
					if(time == 0)time = 10;
				}
				if(key == KeyEvent.VK_D)
				{Game.dead = false;
					handler.setRight(true);
					Player.look_right = true;
					Player.look_left = false;
					if(time == 10)
					{
						
						Player.playSound(6);
						
						time = 10;		
						
					}
					if(time > 0)
					{
						time--;
						
					}
					if(time == 0)time = 10;
				}
				if(key == KeyEvent.VK_W && !handler.isJump() && Player.jump == 0)Player.playSound(4);
				if(key == KeyEvent.VK_W && !handler.isJump() && Player.jump == 0)
				{Game.dead = false;
					handler.setJump(true); 
					tempObject.setVelY(-6);
					Player.jump = 1;
					Player.ground = false;
				}
				if(key == KeyEvent.VK_H)
				{Game.dead = false;
					handler.setHit(true);
				}
				if(key == KeyEvent.VK_R)
				{
					try{
						Player.playSound(3);
					handler.ClearLevel();
					Game.killed = Game.killed_ealier;
					Game.collected = Game.collected_ealier;
					Game.died();
					}catch(Exception E) {};
				}
				
				if(key == KeyEvent.VK_SPACE)
				{
					if(!Game.stop && (Game.sekcja == 0 || Player.now == true) && (!Game.zak1 || !Game.zak2 || !Game.zak3 || !Game.zak4 || !Game.zak5))Game.text++;
					
					
					if(Game.text == 46)
					{
						Player.playSound(5);
						Game.text++;
					}
					
					if(Game.text == 52)
					{
						Player.playSound(5);
						Game.text++;
					}
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player)
			{
				if(key == KeyEvent.VK_A)handler.setLeft(false);
				if(key == KeyEvent.VK_D)handler.setRight(false);
				if(key == KeyEvent.VK_W)handler.setJump(false);
				if(key == KeyEvent.VK_H)handler.setHit(false);
			}
		}
 	}
}
