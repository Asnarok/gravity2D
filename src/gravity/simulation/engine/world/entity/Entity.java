package gravity.simulation.engine.world.entity;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

import gravity.simulation.display.Rendering;
import gravity.simulation.display.graphics.shader.EntityMassShader;
import gravity.simulation.display.graphics.shader.EntitySizeShader;
import gravity.simulation.math.Vector2f;

public class Entity {

	private Vector2f pos, speed;
	private float mass, size;
	
	public static EntityMassShader massShader;
	public static EntitySizeShader sizeShader;
	
	public Entity(Vector2f pos, float mass, float size, Vector2f speed) {
		setMass(mass);
		setSize(size);
		setPos(pos);
		setSpeed(speed);
	}

	/**
	 * @return the speed
	 */
	public Vector2f getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}

	/**
	 * @return the mass
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * @return the size
	 */
	public float getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(float size) {
		this.size = size;
	}
	private static float scaledSize, scaledFontSize;

	public static Font info = new Font("Arial", 20, 20);
	public static TrueTypeFont infoFont;
	
	public void render() {
		scaledFontSize = scaledSize/10;
		if(scaledFontSize > 0)infoFont = new TrueTypeFont(info.deriveFont(scaledSize/10), false);
//			Rendering.drawCircle(relativeX, relativeY, scaledSize*2, sizeColor);
//			Rendering.drawCircle(relativeMassX, relativeMassY, scaledMass*2, massColor);
		
		if(size > mass) {
			sizeShader.start();
			sizeShader.init(getPos(), size);
			Rendering.renderCircle(Rendering.circle);
			sizeShader.stop();
			
			massShader.start();
			massShader.init(getPos(), getMass());
			Rendering.renderCircle(Rendering.circle);
			massShader.stop();
		}else {
			massShader.start();
			massShader.init(getPos(), getMass());
			Rendering.renderCircle(Rendering.circle);
			massShader.stop();
			
			sizeShader.start();
			sizeShader.init(getPos(), size);
			Rendering.renderCircle(Rendering.circle);
			sizeShader.stop();
		}
	}

	/**
	 * @return the pos
	 */
	public Vector2f getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

}
