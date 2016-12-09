package javagame_spaceinvaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAdapter implements KeyListener {

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean space = false;
	
	private boolean spaceReady = true;
	
	public boolean getLeft(){
		return this.left;
	}
	
	public boolean getRight(){
		return this.right;
	}
	
	public boolean getUp(){
		return this.up;
	}
	
	public boolean getDown(){
		return this.down;
	}
	
	public boolean getSpace(){
		if(this.space){
			this.space = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// Lijevo stisnuto
		if(37 == e.getKeyCode()){
			this.left = true;
		}
		// Desno stisnuto
		if(39 == e.getKeyCode()){
			this.right = true;
		}
		// Gore stisnuto
		if(38 == e.getKeyCode()){
			this.up = true;
		}
		// Dolje stisnuto
		if(40 == e.getKeyCode()){
			this.down = true;
		}
		// Space stisnut
		if(32 == e.getKeyCode() && this.spaceReady){
			this.spaceReady = false;
			this.space = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Lijevo otpusteno
		if(37 == e.getKeyCode()){
			this.left = false;
		}
		// Desno otpusteno
		if(39 == e.getKeyCode()){
			this.right = false;
		}
		// Gore otpusteno
		if(38 == e.getKeyCode()){
			this.up = false;
		}
		// Dolje otpusteno
		if(40 == e.getKeyCode()){
			this.down = false;
		}
		if(32 == e.getKeyCode()){
			this.spaceReady = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
