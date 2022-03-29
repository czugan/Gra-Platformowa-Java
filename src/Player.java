import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player extends GameObject
{
	Handler handler;
	SpriteSheet ss;
	
	static boolean now = false;
	static int jump = 0;
	static boolean look_right = true, look_left = false, ground = false;;
    BufferedImageLoader loader = new BufferedImageLoader();

	private BufferedImage[] standR = new BufferedImage[3];
	private BufferedImage[] standL = new BufferedImage[3];
	private BufferedImage[] runR = new BufferedImage[5];
	private BufferedImage[] runL = new BufferedImage[5];
	private BufferedImage[] attackR = new BufferedImage[3];
	private BufferedImage[] attackL = new BufferedImage[3];
	
	Animation animSR;
	Animation animSL;
	Animation runningR;
	Animation runningL;
	Animation attackingR;
	Animation attackingL;
	
	static int timer = 0;
	
	static int X;
	
	public static Clip clips;	
	
	public static Clip clip;	

	
	
	public static void playMusic(int i)
	{
		try {
			clips = AudioSystem.getClip();
	        
	        if(i == 1)clips.open(AudioSystem.getAudioInputStream(Game.class.getResource("scarry.wav")));
	        if(i == 2)clips.open(AudioSystem.getAudioInputStream(Game.class.getResource("song1.wav")));
	        if(i == 3)clips.open(AudioSystem.getAudioInputStream(Game.class.getResource("song2.wav")));
	        if(i == 4)clips.open(AudioSystem.getAudioInputStream(Game.class.getResource("song3.wav")));
	        
	        clips.start();
	        clips.loop(100);
	        
	
		}catch(Exception e)
		{
			
		}
	}
	
	public static void playSound(int i)
	{
		try {
			clip = AudioSystem.getClip();
	        if(i == 1)clip.open(AudioSystem.getAudioInputStream(Game.class.getResource("attack.wav")));
	        if(i == 2)clip.open(AudioSystem.getAudioInputStream(Game.class.getResource("coin.wav")));
	        if(i == 3)clip.open(AudioSystem.getAudioInputStream(Game.class.getResource("death.wav")));
	        if(i == 4)clip.open(AudioSystem.getAudioInputStream(Game.class.getResource("jump.wav")));
	        if(i == 5)clip.open(AudioSystem.getAudioInputStream(Game.class.getResource("shot.wav")));
	        if(i == 6)clip.open(AudioSystem.getAudioInputStream(Game.class.getResource("walk.wav")));
	        

	        clip.start();
	       
	        
	
		}catch(Exception e)
		{
			
		}
	}
	
	
	public Player(int x, int y, ID id, IDspecial ids, Handler handler, SpriteSheet ss)
	{
		super(x, y, id, ids);
		this.handler = handler;
		this.ss = ss;
		
		
		standR[0] = ss.grabImage(1, 1, 31, 64);
		standR[1] = ss.grabImage(2, 1, 31, 64);
		standR[2] = ss.grabImage(3, 1, 31, 64);
		animSR = new Animation(6, standR);
		
		standL[0] = ss.grabImage(6, 1, 31, 64);
		standL[1] = ss.grabImage(5, 1, 31, 64);
		standL[2] = ss.grabImage(4, 1, 31, 64);
		animSL = new Animation(6, standL);
		
		runR[0] = ss.grabImage(1, 3, 31, 64);
		runR[1] = ss.grabImage(2, 3, 31, 64);
		runR[2] = ss.grabImage(3, 3, 31, 64);
		runR[3] = ss.grabImage(4, 3, 31, 64);
		runR[4] = ss.grabImage(5, 3, 31, 64);
		runningR = new Animation(6, runR);
		
		runL[0] = ss.grabImage(10, 3, 31, 64);
		runL[1] = ss.grabImage(9, 3, 31, 64);
		runL[2] = ss.grabImage(8, 3, 31, 64);
		runL[3] = ss.grabImage(7, 3, 31, 64);
		runL[4] = ss.grabImage(6, 3, 31, 64);
		runningL = new Animation(6, runL);
		
		attackR[0] = ss.grabImage(1, 7, 64, 64);
		attackR[1] = ss.grabImage(3, 7, 64, 64);
		attackR[2] = ss.grabImage(5, 7, 32, 64);
		attackingR = new Animation(6, attackR);
		
		attackL[0] = ss.grabImage(10, 7, 64, 64);
		attackL[1] = ss.grabImage(8, 7, 64, 64);
		attackL[2] = ss.grabImage(6, 7, 64, 64);
		attackingL = new Animation(6, attackL);
	}
	
	public void dead()
	{
		
		handler.ClearLevel();
		Game.killed = Game.killed_ealier;
		Game.collected = Game.collected_ealier;
		Game.died();
		playSound(3);
		
	}
	int time = 15;

	public void tick()
	{
		
		if(Game.sekcja == 0)
		{
			look_right = true;
			look_left = false;
			
		}
		if(Game.sekcja == 0 )
		{
			
		}
		if(Game.sekcja > 0  && Game.sekcja < 4)
		{
			x += velX;
			y += velY;
		}
		if(Game.sekcja == 4 && now == false)
		{
			x += velX;
			y += velY;
		}
		X = x;
		
		
	//	System.out.println("x: " + x + " y: " + y);	
		try{collision();}catch(Exception E) {};

		
		if(y > 600)
		{
			dead();
		}
		
		if(handler.isFall())
		{
			if(velY < 10)velY += 0.3;
		}	
		
		if(handler.isHit())
		{	
		
			Attack();	
			if(time == 15)
			{
				
				playSound(1);
				time = 15;		
				
			}
			if(time > 0)
			{
				time--;
				
			}
			if(time == 0)time = 15;
		}
		
			//poruszanie
		if(handler.isLeft() && !handler.isRight())velX = -4;
		if((!handler.isLeft() && !handler.isRight()) || (handler.isLeft() && handler.isRight())) velX = 0;
		if(!handler.isLeft() && handler.isRight())velX = 4;
		
		animSR.runAnimation();
		animSL.runAnimation();
		runningR.runAnimation();
		runningL.runAnimation();
		attackingR.runAnimation();
		attackingL.runAnimation();
	
		
		if(Player.X < Camera.granica - 960 && Game.sekcja != 2) x = Camera.granica - 860 - 100;
		if(Player.X < 3968 - 96 && Game.sekcja == 2 && Game.poziom == 5) x = 3968 - 96;

		
		if(Game.text == 8 && Game.sekcja == 0)
		{
			
			Camera.granica = 992;
			handler.ClearLevel();
			Game.level = loader.loadImage("/pills1map.png");
			Game.back = loader.loadImage("/sekcja1.png");
			Game.background = loader.loadImage("/sekcja1back.png");
			Game.killed = Game.killed_ealier;
			Game.collected = Game.collected_ealier;
			Game.poziom = 1;
			Game.sekcja = 1;
			Game.nextLevel();
			Camera.xX = 0;
			
		}
	}
	
	
	
	public void Attack()
	{
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Monster)
			{
				if(attackL().intersects(tempObject.getBounds()) && look_left)
				{	playSound(3);
					handler.removeObject(tempObject);
					Game.killed++;
					if(tempObject.getIds() == IDspecial.Mon1)Game.mon1 = false; 
					if(tempObject.getIds() == IDspecial.Mon2)Game.mon2 = false;					
					if(tempObject.getIds() == IDspecial.Mon3)Game.mon3 = false;
					if(tempObject.getIds() == IDspecial.Mon4)Game.mon4 = false;
					if(tempObject.getIds() == IDspecial.Mon5)Game.mon5 = false;
					if(tempObject.getIds() == IDspecial.Mon6)Game.mon6 = false;
					if(tempObject.getIds() == IDspecial.Mon7)Game.mon7 = false;
				
				}
				
				if(attackR().intersects(tempObject.getBounds()) && look_right)
				{	playSound(3);
					handler.removeObject(tempObject);
					Game.killed++;
					if(tempObject.getIds() == IDspecial.Mon1)Game.mon1 = false;
					if(tempObject.getIds() == IDspecial.Mon2)Game.mon2 = false;
					if(tempObject.getIds() == IDspecial.Mon3)Game.mon3 = false;
					if(tempObject.getIds() == IDspecial.Mon4)Game.mon4 = false;
					if(tempObject.getIds() == IDspecial.Mon5)Game.mon5 = false;
					if(tempObject.getIds() == IDspecial.Mon6)Game.mon6 = false;
					if(tempObject.getIds() == IDspecial.Mon7)Game.mon7 = false;
				}
			}
		}
		
	}
	
	public void collision()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Empty && Game.sekcja == 4)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					now = true;
				}
			}

			if(tempObject.getId() == ID.Portal)
			{
				
				if(getBounds().intersects(tempObject.getBounds()))
				{
					Game.dead = true;
					Camera.granica = 992;
								
					if(Game.sekcja == 1 && x > 3936 && Game.poziom == 5)
					{
						
						handler.ClearLevel();
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
						Game.poziom = 1;
						Game.sekcja = 2;
						Game.level = loader.loadImage("/pills2map.png");
						Game.back = loader.loadImage("/sekcja2.png");
						Game.background = loader.loadImage("/sekcja2back.png");
						Game.nextLevel();
						Camera.xX = 0;
					}
					if(Game.sekcja == 2 && x > 3936 && Game.poziom == 5)
					{
						
						handler.ClearLevel();
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
						Game.poziom = 1;
						Game.sekcja = 3;
						Game.level = loader.loadImage("/pills3map.png");
						Game.back = loader.loadImage("/sekcja3.png");
						Game.background = loader.loadImage("/sekcja3back.png");
						Game.sprites = loader.loadImage("/sprites2.png");
						Game.ss = new SpriteSheet(Game.sprites);
						Game.nextLevel();
						Camera.xX = 0;
					}
					if(Game.sekcja == 3 && x > 3936 && Game.poziom == 5)
					{
						
						handler.ClearLevel();
						Game.killed_ealier = Game.killed;
						Game.collected_ealier = Game.collected;
						Game.poziom = 1;
						Game.sekcja = 4;
						Game.level = loader.loadImage("/scena2.png");
						if(Game.killed == 0)Game.background = loader.loadImage("/scenka1back.png");
						if(Game.killed != 0)Game.background = loader.loadImage("/scenka2back.png");
						Game.back = loader.loadImage("/scenka1.png");
						Game.sprites = loader.loadImage("/sprites1.png");
						Game.ss = new SpriteSheet(Game.sprites);
						Game.nextLevel();
						Camera.xX = 0;
						
						
						//zak2
					    	   if(Game.collected == 0 && Game.killed == 0)
					    	   {
					    		   Game.text = 19; 
					    		   Game.zak2 = true;
					    	   }
					    	   //zak3
					    	   else if(Game.collected == 27 && Game.killed == 18)
					    	   {
					    		   Game.text = 26; 
					    		   Game.zak3 = true;
					    	   }
					    	   //zak4
					    	   else if(Game.collected == 0 && Game.killed == 18)
					    	   {
					    		   Game.text = 38; 
					    		   Game.zak4 = true;
					    	   }
					    	   //zak5
					    	   else if(Game.collected == 27 && Game.killed == 0)
					    	   {
					    		   Game.text = 46;
					    		   Game.zak5 = true;
					    	   }
					    	 //zak1
					    	   else
					    	   {
					    		   Game.text = 8; 
					    		   Game.zak1 = true;
					    	   }
					    	   
					       
					}
					
				}
			}
					
			if(tempObject.getId() == ID.Monster)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					dead();	
				}
			}
			
			if(tempObject.getId() == ID.Pill)
			{
				
				if(getBounds().intersects(tempObject.getBounds()))
				{playSound(2);
					Game.collected++;
					if(tempObject.getIds() == IDspecial.Coin1)Game.coin1 = false;
					if(tempObject.getIds() == IDspecial.Coin2)Game.coin2 = false;
					if(tempObject.getIds() == IDspecial.Coin3)Game.coin3 = false;
					if(tempObject.getIds() == IDspecial.Coin4)Game.coin4 = false;
					if(tempObject.getIds() == IDspecial.Coin5)Game.coin5 = false;
					if(tempObject.getIds() == IDspecial.Coin6)Game.coin6 = false;
					if(tempObject.getIds() == IDspecial.Coin7)Game.coin7 = false;
					if(tempObject.getIds() == IDspecial.Coin8)Game.coin8 = false;
					if(tempObject.getIds() == IDspecial.Coin9)Game.coin9 = false;
					if(tempObject.getIds() == IDspecial.Coin10)Game.coin10 = false;
					
					
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ID.Block)
			{	
				if(bD().intersects(tempObject.getBounds()))
				{
					velY = 0;
					handler.setFall(false);
					handler.setJump(false);
					y = tempObject.getY() - 64;
					jump = 0;
					ground = true;
				}
				else
				{
					handler.setFall(true);			
				}
				
				if(bU().intersects(tempObject.getBounds()))
				{
					if(velY < 0)velY *= -1;
					if(velY == 0)velY = 1; 
					
					handler.setFall(true);
					handler.setJump(false);
				}
				if(bL().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() + 32; 
					handler.setFall(true);
					handler.setJump(false);
					
				}
				if(bR().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() - 24; 
					handler.setFall(true);
					handler.setJump(false);
				}
				
				
				
			}
			//sekcja1
			if(tempObject.getIds() == IDspecial.Plat1)
			{
				if(Game.mon2 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat2)
			{
				if(Game.mon1 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat3)
			{
				if(Game.coin2 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat4)
			{
				if(Game.coin1 == false)handler.removeObject(tempObject);
			}
			//sekcja2
			if(tempObject.getIds() == IDspecial.Plat5)
			{
				if(Game.mon3 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat6)
			{
				if(Game.coin3 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat7)
			{
				if(Game.coin4 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat8)
			{
				if(Game.coin5 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat9)
			{
				if(Game.coin6 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat10)
			{
				if(Game.coin7 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat11)
			{
				if(Game.mon4 == false)handler.removeObject(tempObject);
			}
			//sekcja 3
			if(tempObject.getIds() == IDspecial.Plat12)
			{
				if(Game.mon5 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat13)
			{
				if(Game.mon6 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat14)
			{
				if(Game.coin9 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat15)
			{
				if(Game.coin10 == false)handler.removeObject(tempObject);
			}
			if(tempObject.getIds() == IDspecial.Plat16)
			{
				if(Game.coin8 == false)handler.removeObject(tempObject);
			}
			
			
			
			
			//znikaj¹ce platformy
			if(tempObject.getIds() == IDspecial.Plat1 && !Block.pl1)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat2 && !Block.pl2)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat3 && !Block.pl3)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat4 && !Block.pl4)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat5 && !Block.pl5)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat6 && !Block.pl6)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat7 && !Block.pl7)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat8 && !Block.pl8)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat9 && !Block.pl9)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat10 && !Block.pl10)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat11 && !Block.pl11)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat12 && !Block.pl12)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat13 && !Block.pl13)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat14 && !Block.pl14)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat15 && !Block.pl15)handler.removeObject(tempObject);
			if(tempObject.getIds() == IDspecial.Plat16 && !Block.pl16)handler.removeObject(tempObject);
		
		}
	}
	
	public void render(Graphics g)
	{

		if(velX == 0 && look_right && ground && !handler.isHit() && !handler.isHit())animSR.drawAnimation(g, x, y, 0);
		if(velX == 0 && look_left && ground && !handler.isHit() && !handler.isHit())animSL.drawAnimation(g, x, y, 0);

		if(velX > 0 && look_right && !handler.isJump() && ground && !handler.isHit())runningR.drawAnimation(g, x, y, 0);
		if(velX < 0 && look_left && !handler.isJump() && ground && !handler.isHit())runningL.drawAnimation(g, x, y, 0);
		
		if(handler.isHit() && look_right)attackingR.drawAnimation(g, x, y, 0);
		if(handler.isHit() && look_left)attackingL.drawAnimation(g, x-32, y, 0);
	
		
		if(velY > 0 && look_right && !ground && !handler.isHit())g.drawImage(ss.grabImage(2, 5, 32, 64) ,x, y, null);
		if(velY < 0 && look_right && !ground && !handler.isHit())g.drawImage(ss.grabImage(1, 5, 32, 64) ,x, y, null);
		
		if(velY > 0 && look_left && !ground && !handler.isHit())g.drawImage(ss.grabImage(3, 5, 32, 64) ,x, y, null);
		if(velY < 0 && look_left && !ground && !handler.isHit())g.drawImage(ss.grabImage(4, 5, 32, 64) ,x, y, null);

	//	g.setColor(Color.yellow);
	//	g.drawRect(x + 20, y + 26, 4, 34);//bR
	//	g.drawRect(x, y + 26, 4, 34);//bL
	//	g.drawRect(x + 4, y+20, 16, 20);//bU
	//	g.drawRect(x + 4, y+40, 16, 24);//bD
	//	g.drawRect(x + 24, y+34, 26, 24);//attackR
	//	g.drawRect(x - 26, y+34, 26, 24);//attackL
	
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y + 20, 24, 44); // ca³oœæ dla obra¿eñ
	}
	// do ruchu
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
	
	public Rectangle attackR()
	{
		return new Rectangle(x + 24, y+34, 26, 24);	
	}
	
	public Rectangle attackL()
	{
		return new Rectangle(x - 26, y+34, 26, 24);	
	}
}
