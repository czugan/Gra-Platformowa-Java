import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Game extends Canvas implements Runnable
{
	
    static BufferedImageLoader loader = new BufferedImageLoader();
  
    boolean LeTsGoo = true;
    
    static boolean stop = false; 
    
	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
    private Thread thread;
    private static Handler handler;
    private static Camera camera;
    static SpriteSheet ss;
    
    static BufferedImage level = null;
    static BufferedImage sprites = null;
    static BufferedImage back = null;
    static BufferedImage background = null;
    static BufferedImage effectTV = null;
    static BufferedImage cutscene = null;
     
    static boolean zak1, zak2, zak3, zak4, zak5 = false;
    AnimationEffects anim1;
    private BufferedImage[] an1 = new BufferedImage[10];
    
    public static int text = 0;
    
    public String[] texts = {"", "Nie mam pojêcia kim on by³...", "Nie... Nie pamiêtam...", "Da³ mi do rêki tabletkê i powiedzia³...", "''Przecie¿ jesteœmy przyjació³mi...''", "''...zrób to dla mnie...''", "''...kumple sobie pomagaj¹.''", "Dlaczego mia³bym mu odmówiæ?",
  /*zak1*/  		"Ci ludzie nie ¿yj¹ przeze mnie...","To wszystko moja wina...","Mam ich krew na rêkach...","Przecie¿ byli moimi przyjació³mi...","Mimo to ten dzieciak nie jest zadowolony...","Na zewn¹trz s³ychaæ d¿wiêki syren policyjnych...","Ktoœ nareszcie zajmie siê tym psychopat¹...","Jak mog³em mu pomóc... to wszystko przez te tabletki...","''Nie ruszaæ siê! Rêce do góry'' - us³ysza³em za plecami...","Zanim nas z³apali... ten ch³opak powiedzia³...","''By³o zabiæ ich wszystkich.''",
  /*zak2*/          "Ten cz³owiek to wariat...","Zwyczajny psychopata...","Przyjêcie od niego tabletki to by³ b³¹d...","Na szczêœcie nikomu nie sta³a siê krzywda...","Tacy ludzie jak Alex powinni zostaæ zamkniêci...","Policja znalaz³a w jego szafce pistolet...","Dziêki mnie wszystko skoñczy³o siê dobrze...",
  /*zak3*/          "Wszyscy martwi...","Wype³ni³em swoje zadanie...","Przyjaciele powinni sobie pomagaæ...","...w tym beznadziejnym œwiecie...","...nie mo¿na na innych liczyæ...","Dobrze ¿e mogê mu zaufaæ...","''¯adnych œwiadków'' - powiedzia³ z uœmiechem.","Podszed³ do szafki... i wyj¹³ z niej pistolet...","''Ktoœ chowa siê w ³azience...''","''Idziesz przyjacielu?'' - doda³ z uœmiechem.","Jak tu odmówiæ?","To mo¿e byæ pocz¹tek piêknej przyjaŸni.",
  /*zak4*/          "Chcia³ we mnie wmusiæ te prochy...","... ale ja ich nie potrzebujê aby zabijaæ...","Ci ludzie nic dla mnie nie znaczyli...","Móg³ mi od razu powiedzieæ, ¿e o to chodzi...","Nie mogê mu zaufaæ...","Ten idiota myœli, ¿e zrobi³em to dla niego...","G³upiec...","Teraz jego kolej...",
  /*zak5*/          "Nie wiem sk¹d on ma te prochy...","..ale... polubi³em je...","...chcia³em poprosiæ o wiêcej ale...","On jest na mnie z³y...","''Zawiod³eœ mnie...'' - warkn¹³.","''Nie jesteœ mi potrzebny...''", "",""};
    
    static boolean mon1, mon2, mon3, mon4, mon5, mon6, mon7 = false;//czy ¿yje potwór
    static boolean coin1, coin2, coin3, coin4, coin5, coin6, coin7, coin8, coin9, coin10, coin11 = false;//czy zebrano monetê
    
    public static int poziom = 1; //od 1 do 5
    public static int sekcja = 0;
    
    public static int collected = 0;
    public static int killed = 0;
    
    public static int collected_ealier = 0;
    public static int killed_ealier = 0;
    
    public static boolean talk = false;
    
    
    public Game()
    {
        new Window(1030, 563, "Pills", this);
        start();
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        camera = new Camera(0, 0, handler);
        sprites = loader.loadImage("/sprites1.png");
        ss = new SpriteSheet(sprites);
        level = loader.loadImage("/scena1.png");       
        back = loader.loadImage("/scenka1.png");
        background = loader.loadImage("/scenka1back.png");
        effectTV = loader.loadImage("/effect1.png");
     
        an1[0] = loader.loadImage("/dead1.png");
        an1[1] = loader.loadImage("/dead1.png");
        an1[2] = loader.loadImage("/dead1.png");
        an1[3] = loader.loadImage("/dead1.png");
        an1[4] = loader.loadImage("/dead1.png");
        an1[5] = loader.loadImage("/dead2.png");
        an1[6] = loader.loadImage("/dead3.png");
        an1[7] = loader.loadImage("/dead4.png");
        an1[8] = loader.loadImage("/dead5.png");
        
        cutscene = loader.loadImage("/text.png");
           
        loadLevel(level);
        
        Player.playMusic(1);
        
    }

    private void start()
    {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop()
    {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                try{tick();}catch(Exception x) {};
                updates++;
                delta--;
            }
            try{render();;}catch(Exception x) {};
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                System.out.println("Killed: " + killed + " Collected: " + collected);
                System.out.println("Sekcja: " + sekcja + " Fragment: " + poziom);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public void tick()
    {
    	for(int i = 0; i < handler.object.size(); i++)
    	{
    		if(handler.object.get(i).getId() == ID.Player)
    		{
    			camera.tick(handler.object.get(i));
    		}
    	}
  
    	handler.tick();
    	
    	anim1.runAnimation();
    }

    public void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        
        
        if(sekcja == 1 || sekcja == 2)g.drawImage(background, (int) (Camera.xX-32), -32, null);//t³o
        if(sekcja == 4)g.drawImage(background, (int) 0, -32, null);//t³o
        if(sekcja == 0 || sekcja == 3) g.drawImage(background, (int) (Camera.xX), -32, null);//t³o
        
        
        g2d.translate(-camera.getX(), -camera.getY());      
       
        handler.render(g);
        
        g2d.translate(camera.getX(), camera.getY());
        
        if(sekcja != 4)g.drawImage(back, (int) (Camera.xX), -32, null);//bloki
        if(sekcja == 4)g.drawImage(back, (int) 0, -32, null);//bloki
        
        
        if(sekcja == 0) g.drawImage(an1[6], 0, 0, null);//efekty
        if(sekcja == 2 || sekcja == 3 || sekcja == 4) g.drawImage(effectTV, 0, 0, null);//efekty
        if(sekcja == 3 || sekcja == 4) g.drawImage(an1[6], 0, 0, null);//efekty
          
        
       
          
        //tekst
       if(sekcja == 0 && text != 0)
       {
    	   g.drawImage(cutscene, 0, -47, null);//efekty
    	      
    	   g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 25);
           g2d.setFont(font);
           
           g2d.drawString(texts[text], 225, 440);
           
           g.setColor(Color.DARK_GRAY); 
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 25);
           g2d.setFont(font2);
   
           g2d.drawString("[spacja]", 825, 510);
           
           
       }
       if(sekcja == 0 && text == 0)
       {
    	   
           g.setColor(Color.BLACK);
           g.fillRect(0, 0, 1200, 1200);
         
           
           g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 40);
           g2d.setFont(font);
              
           g2d.drawString("[spacja]", 430, 255);
           
       }
       
       if(sekcja == 4 && Player.now == true)
       {
    	  
    	   g.drawImage(cutscene, 0, -47, null);//efekty
 	      
    	   g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 25);
           g2d.setFont(font);
           
           g2d.drawString(texts[text], 225, 440);
           
           g.setColor(Color.DARK_GRAY);
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 25);
           g2d.setFont(font2);
   
           g2d.drawString("[spacja]", 825, 510);
           
       }
    	
       if(zak1 && text == 19)
       {
    	   stop = true;
    	   g.drawImage(an1[0],0, 0, null);
    	    
        
           g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 20);
           g2d.setFont(font);
           
           g2d.drawString("Twoje zakoñczenie:", 390, 225);
           
           g2d.drawString("1/5", 465, 380);   
           
           g.setColor(Color.DARK_GRAY);
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 60);
           g2d.setFont(font2);
           g2d.drawString("''Marionetka''", 310, 300);
           
    	   
       }
       if(zak2 && text == 26)
       {
    	   stop = true;
    	   g.drawImage(an1[0],0, 0, null);
    	   
    	   g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 20);
           g2d.setFont(font);
           
           g2d.drawString("Twoje zakoñczenie:", 390, 225);
           
           g2d.drawString("2/5", 465, 380);   

           
           g.setColor(Color.DARK_GRAY);
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 60);
           g2d.setFont(font2);
           g2d.drawString("''Œwiadomy''", 310, 300);
    	  
       }
       if(zak3 && text == 38)
       {
    	   stop = true;
    	   g.drawImage(an1[0],0, 0, null);
    	   
    	   g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 20);
           g2d.setFont(font);
           
           g2d.drawString("Twoje zakoñczenie:", 390, 225);
           
           g2d.drawString("3/5", 465, 380);   

           
           g.setColor(Color.DARK_GRAY);
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 60);
           g2d.setFont(font2);
           g2d.drawString("''Zaœlepiony''", 310, 300);
    	   
       }
       if(zak4 && text == 46)
       {
    	   stop = true;
    	   g.drawImage(an1[0],0, 0, null);
    	   
    	   g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 20);
           g2d.setFont(font);
           
           g2d.drawString("Twoje zakoñczenie:", 390, 225);
           
           g2d.drawString("4/5", 465, 380);   

           
           g.setColor(Color.DARK_GRAY);
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 60);
           g2d.setFont(font2);
           g2d.drawString("''Psychopata''", 310, 300);
       }
       if(zak5 && text >= 52 )
       {
    	   stop = true;
    	   g.drawImage(an1[0],0, 0, null);
      	    
           g.setColor(Color.DARK_GRAY);
           Font font = new Font("PixelFJVerdana12pt", Font.PLAIN, 20);
           g2d.setFont(font);
           
           g2d.drawString("Twoje zakoñczenie:", 390, 225);
           
           g2d.drawString("5/5", 465, 380);   

           
           g.setColor(Color.DARK_GRAY);
           Font font2 = new Font("PixelFJVerdana12pt", Font.PLAIN, 60);
           g2d.setFont(font2);
           g2d.drawString("''Narkoman''", 310, 300);
       }
        
         
        if(dead) {
        	g.drawImage(an1[0],0, 0, null);
        	
        }
        
        g.dispose();
        bs.show();
        
    }
    
    int times = 0;
    static boolean dead = false;
    
    public static void died()
	{
    	dead = true;
    	Game.killed = Game.killed_ealier;
    	Game.collected = Game.collected_ealier;
		loadLevel(level);		
	
	}
    
    public static void stopMusic()
	{
	     Player.clips.stop();	
	}
    
    public static void nextLevel()
	{
    	stopMusic();
    	
    	if(Game.sekcja == 1)
    	{
    		Player.playMusic(2);
    	}
    	if(Game.sekcja == 2)
    	{
    		Player.playMusic(3);
    	}
    	if(Game.sekcja == 3)
    	{
    		Player.playMusic(4);
    	}
    	if(Game.sekcja == 4)
    	{
    		Player.playMusic(1);
    	}
		loadLevel(level);
		camera.setX(0);	
		
	}
   
    static void loadLevel(BufferedImage image)
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
    			//przejœcia
    			if(red == 255 && green == 255 && blue ==0)
    			{
    				handler.addObject(new Portal(xx*32, yy*32, ID.Portal, IDspecial.None));
    			}
    			//gracz
    			if(red == 0 && green == 0 && blue == 255 && poziom == 1)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 254 && poziom == 2)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 253 && poziom == 3)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 252 && poziom == 4)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			if(red == 0 && green == 0 && blue == 251 && poziom == 5)
    			{
    				handler.addObject(new Player(xx*32, yy*32, ID.Player, IDspecial.None, handler, ss));
    			}
    			//
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
    				if(sekcja == 0 || sekcja == 4)
    				{
    					BadGuy.left = true;
    					BadGuy.right = false;
    				}
    				if(sekcja == 1)
    				{
    					BadGuy.right = true;
    					BadGuy.left = false;
    				}
    			}
    			//pills
    			
    			//sekcja1
    			if(red == 255 && green == 0 && blue == 110)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.None, handler, ss));//podstawowa tabletka
    			}
    			if(red == 255 && green == 0 && blue == 111)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin1, handler, ss));
    				coin1 = true;
    			}
    			if(red == 255 && green == 0 && blue == 112)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin2, handler, ss));
    				coin2 = true;
    			}
    			
    			//sekcja2
    			if(red == 255 && green == 0 && blue == 113)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin3, handler, ss));
    				coin3 = true;
    			}
    			if(red == 255 && green == 0 && blue == 114)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin4, handler, ss));
    				coin4 = true;
    			}
    			if(red == 255 && green == 0 && blue == 115)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin5, handler, ss));
    				coin5 = true;
    			}
    			if(red == 255 && green == 0 && blue == 116)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin6, handler, ss));
    				coin6 = true;
    			}
    			if(red == 255 && green == 0 && blue == 117)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin7, handler, ss));
    				coin7 = true;
    			}
    			//sekcja3
    			if(red == 255 && green == 0 && blue == 119)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin8, handler, ss));
    				coin8 = true;
    			}
    			if(red == 255 && green == 0 && blue == 120)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin9, handler, ss));
    				coin9 = true;
    			}
    			if(red == 255 && green == 0 && blue == 121)
    			{
    				handler.addObject(new Pill(xx*32, yy*32, ID.Pill, IDspecial.Coin10, handler, ss));
    				coin10 = true;
    			}
    			
    			
    			
    			
    			
    			
    			
    			//monsters
    			if(red == 0 && green == 0 && blue == 127)//podstawowy przeciwnik bez funkcji
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.None, handler, ss));
    			}		
    			
    			if(red == 0 && green == 0 && blue == 128)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon1, handler, ss));
    				mon1 = true;
    			}
    			
    			if(red == 0 && green == 0 && blue == 129)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon2, handler, ss));
    				mon2 = true;
    			}
    			
    			if(red == 0 && green == 0 && blue == 130)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon3, handler, ss));
    				mon3 = true;
    			}
    			
    			if(red == 0 && green == 0 && blue == 131)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon4, handler, ss));
    				mon4 = true;
    			}
    			if(red == 0 && green == 0 && blue == 132)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon5, handler, ss));
    				mon5 = true;
    			}
    			if(red == 0 && green == 0 && blue == 133)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon6, handler, ss));
    				mon6 = true;
    			}
    			if(red == 0 && green == 0 && blue == 134)
    			{
    				handler.addObject(new Enemy(xx*32, yy*32, ID.Monster, IDspecial.Mon7, handler, ss));
    				mon7 = true;
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
    			
    			if(red == 250 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat5, handler, ss));
    			}
    			
    			if(red == 249 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat6, handler, ss));
    			}
    			
    			if(red == 248 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat7, handler, ss));
    			}
    			
    			if(red == 247 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat8, handler, ss));
    			}
    			
    			if(red == 246 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat9, handler, ss));
    			}
    			
    			if(red == 245 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat10, handler, ss));
    			}
    			
    			if(red == 244 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat11, handler, ss));
    			}
    			
    			if(red == 243 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat12, handler, ss));
    			}
    			
    			if(red == 242 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat13, handler, ss));
    			}
    			
    			if(red == 241 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat14, handler, ss));
    			}
    			
    			if(red == 240 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat15, handler, ss));
    			}
    			
    			if(red == 239 && green == 0 && blue == 0)
    			{
    				handler.addObject(new Block(xx*32, yy*32, ID.Block, IDspecial.Plat16, handler, ss));
    			}
    		}
    	}
    }

    public static void main(String[]args)
    {
        new Game();
    }
    
}