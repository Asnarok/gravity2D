package gravity.simulation.display.graphics.shader;

public class HUDShader extends ShaderProgram{

	private int location_color;
	
	public HUDShader() {
		super("HUDvertexShader.glsl", "HUDfragmentShader.glsl");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = super.getUniformLocation("color");
	}
	
	public void loadColor(float[] rgba) {
		super.loadColor(location_color, rgba);
	}


}
