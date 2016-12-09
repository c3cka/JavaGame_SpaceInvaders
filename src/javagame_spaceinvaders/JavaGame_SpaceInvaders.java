/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame_spaceinvaders;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Igor_Kos_Cecka
 */
public class JavaGame_SpaceInvaders extends Applet implements Runnable{

        Image img;										// Image za crtanje
	Image background;								// Refresh image 
	
	Player player;			// Igrac
	int hpBar;
	ArrayList<EnemyShip> eShipList = new ArrayList<>();
	ArrayList<Missile> eMissileList = new ArrayList<>();
	/*
	EnemyShip eShip1 = new EnemyShip(175,11,150,eMissileList);
	EnemyShip eShip2 = new EnemyShip(375,71,150,eMissileList);
	EnemyShip eShip3 = new EnemyShip(575,11,150,eMissileList);*/
	
	long fps = 0;
	
	Font mainFont = new Font("Consolas", Font.BOLD, 20);
	Font secondaryFont = new Font("AppleGothic", Font.PLAIN, 40);
	
	KeyAdapter keyAdapter = new KeyAdapter();		// Kontrole
	
	Graphics2D graph;								// Crtanje
	
	Thread nit;										// Nit za update i render
	
        @Override
	public void init(){
		try {
			//background = ImageIO.read(new URL("http://testsranica.net63.net/background.png"));
			//background = ImageIO.read(new File("background.png"));
                        background = ImageIO.read(new File("bin/images/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//setGame();
		/*eShipList.add(eShip1);
		eShipList.add(eShip2);
		eShipList.add(eShip3);*/
		this.addKeyListener(keyAdapter);
		setSize(700,600);
		nit = null;
		img = createImage(700,600);
		graph = (Graphics2D) img.getGraphics();
		//graph.setFont(mainFont);
	}
	
        @Override
	public void start(){
		if(nit == null){
			nit = new Thread(this);
			nit.start();
		}
	}
	
        @Override
	public void paint(Graphics g){		
		graph.drawImage(background,0,0,this);			// bg reset
		graph.setColor(new Color(200,229,255));			// 
		graph.fillRect(600, 0, 100, 600);				// 
		graph.setFont(mainFont);
		if(player != null){
		graph.setColor(Color.BLACK);					
		graph.drawString("FPS:" + fps, 615, 35);		
		graph.drawString("HP", 618, 578);
		graph.drawString("AMMO", 648, 578);
		
		if(player.getHP()>=7){graph.setColor(Color.GREEN);}
		else if(player.getHP()>=3){graph.setColor(Color.YELLOW);}
		else {graph.setColor(Color.RED);}	
		
		if(hpBar > 50 * player.getHP()){
			hpBar--;
		}
		graph.fillRect(615, 560 - hpBar, 30, hpBar);
		
		graph.setColor(Color.ORANGE);
		graph.fillRect(656, 560 - 5 * player.getAmmo(), 30, 5 * player.getAmmo());
		
		
                for (EnemyShip enemyShip : eShipList) {
			enemyShip.render(graph);
		}
		for(int i=0;i<eMissileList.size();i++){
			eMissileList.get(i).render(graph);
		}
		if(eShipList.isEmpty() && player.getHP() > 0){
			graph.setColor(Color.WHITE);
			graph.setFont(secondaryFont);
			graph.drawString("VICTORY", 175, 295);
		}
			if(player.getHP() > 0){
				player.render(graph);
			}
			else{
				graph.setColor(Color.WHITE);
				graph.setFont(secondaryFont);
				graph.drawString("DEFEAT", 175, 295);
			}
		graph.setFont(mainFont);
		}
		if(player == null || player.getHP()<=0 || eShipList.isEmpty()){
			graph.setColor(Color.WHITE);
			graph.setFont(mainFont);
			graph.drawString("SPACE TO START", 175, 350);
		}
		g.drawImage(img,0,0,this);	
	}
	
        @Override
	public void update(Graphics g){
		paint(g);
	}
	
        @Override
	public void stop(){
		nit.stop();
		nit = null;
		
	}
        @Override
	public void destroy(){
		
	}
	
	public void setGame(){
		player  = new Player(300,550);
		hpBar = 50 * player.getHP();
		eShipList.clear();
		for(int i=0;i<6;i++){
			eShipList.add(new EnemyShip(100 + (350 * (i%2)),11 + (100 *(i%3)),100,eMissileList));
			if(i <= 2)eShipList.add(new EnemyShip(200,61 +(100 * i),100,eMissileList));
		}
	}
	
	/*public static boolean colisionDetection(GameObject a, GameObject b){
		if( ((a.getX() >= b.getX()) && (a.getX() <= (b.getX() + b.getWidth()))) && 
			 ((a.getY() <= (b.getY() + b.getHeight())) && (a.getY() >= b.getY())) ){
			return true;
		}
		return false;
	}*/
	
	public static boolean colisionDetection2(GameObject a, GameObject b){
		if( a.getX() < b.getX() + b.getWidth() &&
			a.getX() + a.getWidth() > b.getX() &&
			a.getY() < b.getY() + b.getHeight() &&
			a.getY() + a.getHeight() > b.getY()			
			){
			return true;
		}
		return false;
	}
	
	public static boolean colisionDetection3(GameObject a, GameObject b, int precision){
		int x = Math.abs(a.getCenterX() - b.getCenterX());
		int y = Math.abs(a.getCenterY() - b.getCenterY());
		double z = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		if(((a.getHeight()+b.getHeight())/2)+precision > z){
			return true;
		}
		return false;
	}
	
        @Override
	public void run() {	
		
		long start, end, timeToUpdate, timeToSleep;
		long fps, fpsS;
                fps = 0;
		fpsS=System.currentTimeMillis();
		while(true){
		while(player == null || player.getHP()<=0 || eShipList.isEmpty()){
			if(keyAdapter.getSpace()){
				 setGame();
			}
			repaint();
			try {
				Thread.sleep(6);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		while(player != null && player.getHP()>0 && eShipList.size() > 0){
			try {
				
				start = System.currentTimeMillis();
/* ************************ */
/* Update igraca i shooteva */
/* ************************************************************************************************************* */
					player.keyUpdates(keyAdapter.getLeft(),
							keyAdapter.getRight(),
							keyAdapter.getSpace(),
							keyAdapter.getUp(),
							keyAdapter.getDown());
/* ***************** */
/* Update protivnika */
/* ************************************************************************************************************* */
					for(int i=0;i<eShipList.size();i++){
						eShipList.get(i).updates();
					}
/* ************************************* */
/* Update pozicije protivnickih shooteva */
/* ************************************************************************************************************* */
					for(int i=0;i<eMissileList.size();i++){
						eMissileList.get(i).updatePosition();
					}
/* *************** */
/* Kolizija igraca */
/* ************************************************************************************************************* */				
					for(int i=0;i < eMissileList.size();){
						if(colisionDetection2(eMissileList.get(i), player)){
							player.updateHP(-1);
							player.gotHit();
							eMissileList.remove(i);
						}
						else{
							i++;
						}
					}
/* ******************* */
/* Kolizija protivnika */
/* ************************************************************************************************************* */
					ArrayList<Missile> missiles = player.getMissiles();
					
					for(int i=0;i<eShipList.size();i++){
						for(int j=0;j<missiles.size();){
							if(colisionDetection2(missiles.get(j),eShipList.get(i))){
								eShipList.get(i).updateHP(-1);
								eShipList.get(i).gotHit();
								missiles.remove(j);
							}
							else{
								j++;
							}
						}
					}
/* **************************** */
/* Kolizija igraca i protivnika */
/* ************************************************************************************************************* */					
					for(int i=0;i<eShipList.size();i++){
							if(colisionDetection3(player,eShipList.get(i),-6)){
								eShipList.get(i).updateHP(-10);
								player.updateHP(-10);
							}
						}
/* ************************************ */
/* Izbacivanje protivnika ako nemaju HP */
/* ************************************************************************************************************* */					
					for(int i=0;i<eShipList.size();){
						if(eShipList.get(i).getHP() < 1){
							eShipList.remove(i);
						}
						else{
							i++;
						}
					}
/* ************************************************************************************************************* */										
					repaint();
				end = System.currentTimeMillis();
				
				timeToUpdate = end - start;
				timeToSleep = 6 - timeToUpdate;
				
				if(timeToSleep >= 0)Thread.sleep(timeToSleep);
				
				fps++;
				if((end - fpsS) > 1000){
					this.fps = fps;
					fpsS = System.currentTimeMillis();
					fps=0;
				}

			} 
			catch (Exception e){
				return;
			}
		}//while(player.getHP()>0)
		}//while(true)
	}

}
