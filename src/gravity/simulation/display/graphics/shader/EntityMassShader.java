package gravity.simulation.display.graphics.shader;

import gravity.simulation.Simulator;
import gravity.simulation.math.Vector2f;

public class EntityMassShader extends ShaderProgram {

	private int location_scale;
	private int location_pos;
	private int location_offset;
	private int location_mass;
	
	public EntityMassShader() {
		super("entityMassVertexShader.glsl", "entityMassFragmentShader.glsl");
	}

	@Override
	protected void getAllUniformLocations() {
		location_scale = super.getUniformLocation("scale");
		location_pos = super.getUniformLocation("pos");
		location_offset = super.getUniformLocation("offset");
		location_mass = super.getUniformLocation("mass");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	private void loadScale() {
		super.loadFloat(location_scale, Simulator.scale);
	}
	
	private void loadPos(Vector2f pos) {
		super.loadVector(location_pos, pos);
	}
	
	private void loadOffset() {
		super.loadVector(location_offset, Simulator.worldOffsetX, Simulator.worldOffsetY);
	}
	
	private void loadMass(float mass) {
		super.loadFloat(location_mass, mass);
	}
	
	public void init(Vector2f pos, float mass) {
		loadScale();
		loadPos(pos);
		loadOffset();
		loadMass(mass);
	}


}
