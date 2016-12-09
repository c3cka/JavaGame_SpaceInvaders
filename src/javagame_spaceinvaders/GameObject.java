package javagame_spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;


public abstract class GameObject {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public abstract void render(Graphics2D g);
	
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getCenterX(){
		return this.x + this.width/2;
	}
	public int getCenterY(){
		return this.y + this.height/2;
	}

}
