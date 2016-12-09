package javagame_spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;

public class Missile extends GameObject {
	private int speed = 3;
	private int counter=0;
	
	boolean direction;
	
	Missile(int x, int y, boolean dir){
		this.x = x;
		this.y = y;
		this.direction = dir;
		this.height = 1;
		this.width = 1;
	}
	
	public boolean missileExpired(){
		if(this.y < 0 || this.y > 600){
			return true;
		}
		return false;
	}
	
	public void updatePosition(){
		if(direction){
			this.y -= this.speed;
		}
		else{
			this.y += this.speed;
		}
	}
	
	public void render(Graphics2D g){
		
		if(counter == 1){
			g.setColor(Color.RED);
			counter = 0;
		}
		else{
			g.setColor(Color.BLACK);
			counter++;
		}
		g.drawLine(x-1, y, x-1, y+2); 
		g.drawLine(x, y-1, x, y+4);		// srednja
		g.drawLine(x+1, y, x+1, y+2);

	}
}
