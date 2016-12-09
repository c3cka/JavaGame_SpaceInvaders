package javagame_spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;


public class EnemyShip extends GameObject {

	private int pathMaxLeft;
	private int pathMaxRight;
	private int speed = 1;
	private int hp = 3;
	private int hit = 0;
	
	private Image shipImage;
	private Image shipImageL;
	private Image shipImageR;
	
	private ArrayList<Missile> missiles;
	
	private int rotation = 0;
	private boolean direction;
	Random rand;
	private short counter001=0;
	
	EnemyShip(int x, int y, int pathSizeLR, ArrayList<Missile> missiles){
		rand = new Random();
		this.missiles = missiles;
		this.x = x;
		this.y = y;
		this.pathMaxLeft = x - pathSizeLR;
		this.pathMaxRight = x + pathSizeLR;
		this.direction = rand.nextBoolean();
		try {
			/*this.shipImage = ImageIO.read(new URL("http://testsranica.net63.net/eship1.png"));
			this.shipImageL = ImageIO.read(new URL("http://testsranica.net63.net/eship1L.png"));
			this.shipImageR = ImageIO.read(new URL("http://testsranica.net63.net/eship1R.png"));*/
			this.shipImage = ImageIO.read(new File("bin/images/eship1.png"));
			this.shipImageL = ImageIO.read(new File("bin/images/eship1L.png"));
			this.shipImageR = ImageIO.read(new File("bin/images/eship1R.png"));
		} 
		catch (IOException e){
			e.printStackTrace();
		}
		this.height = this.shipImage.getHeight(null);
		this.width = this.shipImage.getWidth(null);
	}
	
	public int getHP(){
		return this.hp;
	}
	
	public void updateHP(int update){
		this.hp += update;
	}
	
	
	public ArrayList<Missile> getMissiles(){
		return this.missiles;
	}
	
	public void updates(){

		if(this.rand.nextInt(1000) <= 5){
			this.direction = !this.direction;
		}
		
		if(this.rand.nextInt(1000) <= 5){
			this.missiles.add(new Missile(this.x+23,this.y + this.height, false));
		}
		
		if(direction){
			this.rotation = 1;
			this.x += speed;
			if(this.x >= pathMaxRight){
				this.direction = false;
			}
		}
		else{
			this.rotation = -1;
			this.x -= speed;
			if(this.x <= pathMaxLeft){
				this.direction = true;
			}
		}
	}
	
	public void gotHit(){
		this.hit = 25;
	}
	
        @Override
	public void render(Graphics2D g){
	
		if(this.hit > 0){
			this.hit --;
			if(this.hit % 2 == 1){
				g.setXORMode(Color.WHITE);
			}
		}
		
		switch(rotation){
			case  1:g.drawImage(this.shipImageR, this.x, this.y+39, 47, -39, null);break;
			case -1:g.drawImage(this.shipImageL, this.x, this.y+39, 47, -39, null);break;
			default:g.drawImage(this.shipImage, this.x, this.y+39, 47, -39, null); break;
		}
		
		g.setPaintMode();
		
		g.setColor(Color.ORANGE);
		
		if(counter001 == 1){
			counter001 = 0;	
		}
		else{
			g.drawLine(this.x+14, this.y+3, this.x+18, this.y+3);
			g.drawLine(this.x+28, this.y+3, this.x+32, this.y+3);
			
			g.drawLine(this.x+14, this.y+2, this.x+18, this.y+2);
			g.drawLine(this.x+28, this.y+2, this.x+32, this.y+2);
			
			g.drawLine(this.x+14, this.y+1, this.x+18, this.y+1);
			g.drawLine(this.x+28, this.y+1, this.x+32, this.y+1);
			
			g.drawLine(this.x+15, this.y, this.x+17, this.y);
			g.drawLine(this.x+29, this.y, this.x+31, this.y);
			
			g.drawLine(this.x+15, this.y-1, this.x+17, this.y-1);
			g.drawLine(this.x+29, this.y-1, this.x+31, this.y-1);
			counter001 ++;
		}
	}
}
