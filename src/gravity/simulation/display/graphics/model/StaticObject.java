package gravity.simulation.display.graphics.model;

import gravity.simulation.display.Rendering;
import gravity.simulation.display.graphics.shader.ObjectShader;
import gravity.simulation.math.Vector2f;

public class StaticObject {
	
	private RawModel model;
	private Vector2f offset;
	private float[] color;
	private float scale;

	public StaticObject(RawModel model, Vector2f offset, float[] color, float scale) {
		setModel(model);
		setOffset(offset);
		setColor(color);
		setScale(scale);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public RawModel getModel() {
		return model;
	}

	public void setModel(RawModel model) {
		this.model = model;
	}

	public Vector2f getOffset() {
		return offset;
	}

	public void setOffset(Vector2f offset) {
		this.offset = offset;
	}
	
	public void render(ObjectShader shader) {
		shader.start();
		shader.loadColor(color);
		shader.loadWindowSize();
		shader.loadOffset(offset.getX(), offset.getY());
		shader.loadScale(scale);
		Rendering.renderModel(model);
		shader.stop();
	}

}
