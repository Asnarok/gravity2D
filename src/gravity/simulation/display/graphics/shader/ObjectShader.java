package gravity.simulation.display.graphics.shader;

import gravity.simulation.display.DisplayManager;

public class ObjectShader extends ShaderProgram{

	private int location_color;
	private int location_windowSize;
	private int location_offset;
	private int location_scale;
	
	public ObjectShader() {
		super("objectVertexShader.glsl", "objectFragmentShader.glsl");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "scale");
		super.bindAttribute(2, "offset");
		super.bindAttribute(3, "color");
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = super.getUniformLocation("color");
		location_windowSize = super.getUniformLocation("windowSize");
		location_offset = super.getUniformLocation("offset");
		location_scale = super.getUniformLocation("scale");
	}
	
	public void loadColor(float[] rgba) {
		super.loadColor(location_color, rgba);
	}
	
	public void loadWindowSize() {
		super.loadVector(location_windowSize, DisplayManager.width, DisplayManager.height);
	}
	
	public void loadOffset(float x, float y) {
		super.loadVector(location_offset, x, y);
	}
	
	public void loadScale(float scale) {
		super.loadFloat(location_scale, scale);
	}


}
