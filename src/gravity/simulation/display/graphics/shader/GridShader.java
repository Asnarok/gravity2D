package gravity.simulation.display.graphics.shader;

public class GridShader extends ShaderProgram {
	
	private int location_offset;
	
	public GridShader() {
		super("gridVertexShader.glsl", "gridFragmentShader.glsl");
	}

	@Override
	protected void getAllUniformLocations() {
		super.getUniformLocation("offset");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void loadOffset(float x, float y) {
		super.loadVector(location_offset, x, y);
	}

}
