package gravity.simulation.display.graphics.shader;

import gravity.simulation.Simulator;
import gravity.simulation.math.Vector2f;

public class SpeedLineShader extends ShaderProgram {

	private int location_scale;
	private int location_origin;
	private int location_offset;
	private int location_target;
	
	public SpeedLineShader() {
		super("speedLineVertexShader.glsl", "speedLineFragmentShader.glsl");
	}

	@Override
	protected void getAllUniformLocations() {
		location_scale = super.getUniformLocation("scale");
		location_origin = super.getUniformLocation("origin");
		location_offset = super.getUniformLocation("offset");
		location_target = super.getUniformLocation("target");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	private void loadScale() {
		super.loadFloat(location_scale, Simulator.scale);
	}
	
	private void loadOrigin(Vector2f origin) {
		super.loadVector(location_origin, origin);
	}
	
	private void loadOffset() {
		super.loadVector(location_offset, Simulator.worldOffsetX, Simulator.worldOffsetY);
	}
	
	private void loadTarget(Vector2f target) {
		super.loadVector(location_target, target);
	}
	
	public void init(Vector2f origin, Vector2f target) {
		loadScale();
		loadOrigin(origin);
		loadOffset();
		loadTarget(target);
	}


}
