package javagame_spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Player extends GameObject {
	
	final int LEFT_BORDER = 1;
	final int RIGHT_BORDER = 550;
	final int TOP_BORDER = 1;
	final int BOTTOM_BORDER = 550;
	
	private int ammo = 100;
	private int hp = 10;
	private int speed = 1;
	private int hit = 0;
	
	private Image shipImage;
	private Image shipImageL;
	private Image shipImageR;
	
	private ArrayList<Missile> missiles;
	
	private int rotation = 0;
	
	private short counter001=0;
	
	Player(int x, int y){
        this.missiles = new ArrayList<>(10);
		this.x = x;
		this.y = y;
		try {
			/*this.shipImage = ImageIO.read(new URL("http://testsranica.net63.net/ship1.png"));
			this.shipImageL = ImageIO.read(new URL("http://testsranica.net63.net/ship1L.png"));
			this.shipImageR = ImageIO.read(new URL("http://testsranica.net63.net/ship1R.png"));*/
			this.shipImage = ImageIO.read(new File("bin/images/ship1.png"));
			this.shipImageL = ImageIO.read(new File("bin/images/ship1L.png"));
			this.shipImageR = ImageIO.read(new File("bin/images/ship1R.png"));
			//System.out.println(this.shipImage.getHeight(null));
		} 
		catch (IOException e){
			e.printStackTrace();
		}
		this.height = this.shipImage.getHeight(null);
		this.width = this.shipImage.getWidth(null);
	}
	
	public void gotHit(){
		this.hit = 25;
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
	public void keyUpdates(boolean left, boolean right, boolean space, boolean up, boolean down){
		if(left){
			this.x -= speed;
			if(this.x < LEFT_BORDER)this.x = LEFT_BORDER;
		}
		if(right){
			this.x += speed;
			if(this.x > RIGHT_BORDER)this.x = RIGHT_BORDER;
		}	
		if(up){
			this.y -= speed;
			if(this.y < TOP_BORDER)this.y = TOP_BORDER;
		}
		if(down){
			this.y += speed;
			if(this.y > BOTTOM_BORDER)this.y = BOTTOM_BORDER;
		}
		
		if(left && right)rotation=0;
		else if(left && !right)rotation=-1;
		else if(!left && right)rotation=1;
		else rotation=0;
		
		for(int i=0;i<this.missiles.size();i++){
			this.missiles.get(i).updatePosition();
		}
		
		if(space && this.ammo > 0){	
			this.ammo --;
			this.missiles.add(new Missile(this.x+23,this.y, true));
		}
	}
	
	public int getAmmo(){
		return this.ammo;
	}
	
        @Override
	public void render(Graphics2D g){
		
		if(this.hit > 0){
			this.hit --;
			if(this.hit % 2 == 1){
				g.setXORMode(Color.BLACK);
			}
		}
		
		switch(rotation){
		case  1:g.drawImage(this.shipImageR, this.x, this.y, null);break;
		case -1:g.drawImage(this.shipImageL, this.x, this.y, null);break;
		default:g.drawImage(this.shipImage, this.x, this.y, null); break;
		}
		
		g.setPaintMode();
		
		g.setColor(Color.ORANGE);
		
		if(counter001 == 1){
			counter001 = 0;	
		}
		else{
			g.drawLine(this.x+14, this.y+35, this.x+18, this.y+35);
			g.drawLine(this.x+28, this.y+35, this.x+32, this.y+35);
			
			g.drawLine(this.x+14, this.y+36, this.x+18, this.y+36);
			g.drawLine(this.x+28, this.y+36, this.x+32, this.y+36);
			
			g.drawLine(this.x+14, this.y+37, this.x+18, this.y+37);
			g.drawLine(this.x+28, this.y+37, this.x+32, this.y+37);
			
			g.drawLine(this.x+15, this.y+38, this.x+17, this.y+38);
			g.drawLine(this.x+29, this.y+38, this.x+31, this.y+38);
			
			g.drawLine(this.x+15, this.y+39, this.x+17, this.y+39);
			g.drawLine(this.x+29, this.y+39, this.x+31, this.y+39);
			counter001 ++;
		}
		
		for(int i=0;i<this.missiles.size();){
			this.missiles.get(i).render(g);
			if(this.missiles.get(i).missileExpired()){
				this.missiles.remove(i);
			}
			else{
				i++;
			}
		}
	}
}
