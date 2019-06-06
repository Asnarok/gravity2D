package gravity.simulation.display;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import gravity.simulation.Simulator;
import gravity.simulation.engine.world.entity.Entity;
import gravity.simulation.math.EngineMath;
import gravity.simulation.math.Vector2f;

public class InputManager {
	
	
	private static int pauseKey = Keyboard.KEY_SPACE, increaseSpeedKey = Keyboard.KEY_UP, decreaseSpeedKey = Keyboard.KEY_DOWN, addEntityKey = Keyboard.KEY_N, clearEntitesKey = Keyboard.KEY_C, generateRandom = Keyboard.KEY_R, resetTimeKey = Keyboard.KEY_T, showHelp = Keyboard.KEY_H;
	
	
	public static void update() {
		updateKeyboard();
		updateMouse();
	}
	
	private static void updateKeyboard() {

		if(Keyboard.next()) {
			if(Keyboard.isKeyDown(pauseKey)) {
				Simulator.running = !Simulator.running;
			}else if(Keyboard.isKeyDown(increaseSpeedKey)) {
				if(Simulator.speed < Simulator.maxSpeed)Simulator.speed*=2;
			}else if(Keyboard.isKeyDown(decreaseSpeedKey)) {
				if(Simulator.speed > Simulator.minSpeed)Simulator.speed/=2;
			}else if(Keyboard.isKeyDown(addEntityKey)) {
				Simulator.addingEntity = true;
			}else if(Keyboard.isKeyDown(clearEntitesKey)) {
				synchronized(Simulator.world) {
					Simulator.world.getEntities().clear();
				}
			}else if(Keyboard.isKeyDown(generateRandom)) {
				Simulator.randomEntities.newRandom(EngineMath.convertToAbsoluteX(0), EngineMath.convertToAbsoluteX(DisplayManager.width), EngineMath.convertToAbsoluteY(0), EngineMath.convertToAbsoluteY(DisplayManager.height));
			}else if(Keyboard.isKeyDown(resetTimeKey)) {
				Simulator.ms = 0;
				Simulator.s = 0;
				Simulator.min = 0;
				Simulator.hours = 0;
				Simulator.days = 0;
				Simulator.years = 0;
			}else if(Keyboard.isKeyDown(showHelp)) {
				Simulator.showingHelp = !Simulator.showingHelp;
			}
		}
	}
	
	private static int buttonLeft = 0, buttonRight = 1;
	private static float lastX, lastY;
	private static float toAddX, toAddY;
	
	private static boolean mouseLeft = false;
	private static boolean mouseRight = false;
	
	
	private static void updateMouse() {
		updateMouseButton();
		updateMouseWheel();
		updateMouseMotion();
	}
	
	private static void updateMouseMotion() {
		DisplayManager.mouseX = Mouse.getX();
		DisplayManager.mouseY = DisplayManager.height-Mouse.getY();
		if(Mouse.isButtonDown(buttonLeft)) {
			if(lastX != Integer.MIN_VALUE && lastY != Integer.MIN_VALUE) {
				toAddX = DisplayManager.mouseX-lastX;
				toAddY = DisplayManager.mouseY-lastY;
				addMouseMotionToWorldOffset(toAddX, toAddY);
			}
			lastX = DisplayManager.mouseX;
			lastY = DisplayManager.mouseY;
		}else {
			lastX = Integer.MIN_VALUE;
			lastY = Integer.MIN_VALUE;
		}
		
	}
	
	private static void addMouseMotionToWorldOffset(float x, float y) {
		Simulator.worldOffsetX-=x/Simulator.scale;
		Simulator.worldOffsetY-=y/Simulator.scale;
		Simulator.gridOffsetX+=x;
		Simulator.gridOffsetY+=y;
		if(Simulator.gridOffsetX > 0 || Simulator.gridOffsetX < -100)Simulator.gridOffsetX = -50;
		if(Simulator.gridOffsetY > 0 || Simulator.gridOffsetY < -100)Simulator.gridOffsetY = -50;
	}
	private static void updateMouseButton() {
		if((Mouse.isButtonDown(buttonLeft) || Mouse.isButtonDown(buttonRight)) && !mouseLeft && !mouseRight) {
				if(Simulator.addingEntity) {
					if(Simulator.addingState < Simulator.SPEED_STATE) {
						Simulator.addingState++;
					}else {
						synchronized(Simulator.world) {
							List<Entity> en = Simulator.world.getEntities();
							if(Mouse.isButtonDown(buttonRight))Simulator.temporary.setSpeed(new Vector2f(0, 0));
							en.add(Simulator.temporary);
							Simulator.world.setEntities(en);
						}
						Simulator.temporary = new Entity(null, 0, 0, null);
						Simulator.addingEntity = false;
						Simulator.addingState = 0;
				}
			}
		}
		mouseLeft = Mouse.isButtonDown(buttonLeft);
		mouseRight = Mouse.isButtonDown(buttonRight);
	}
	
	private static void updateMouseWheel() {
		if(Mouse.hasWheel()) {
			int w = Mouse.getDWheel();
			if(w < 0) {
				Simulator.scale/=1.3;
//				addMouseMotionToWorldOffset((Display.getWidth()/2-Mouse.getX()+Display.getWidth()/4)/2, (Display.getHeight()/2-Mouse.getY()+Display.getHeight()/4)/2);
				addMouseMotionToWorldOffset(Display.getWidth()/8.75f, Display.getHeight()/8.75f);
			}
			else if(w > 0) {
				Simulator.scale*=1.3;
//				addMouseMotionToWorldOffset(Mouse.getX()-Display.getWidth()/2-Display.getWidth()/2, Mouse.getY()-Display.getHeight()/2);
				addMouseMotionToWorldOffset(-Display.getWidth()/6.25f, -Display.getHeight()/6.25f);
			}
		}
	}
}
