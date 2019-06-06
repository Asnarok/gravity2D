package gravity.simulation.display.graphics.model;

public class GPolygon {

	private float[] vertices;
	private int[] indices;
	
	public GPolygon(float[] vertices, int[] indices) {
		setIndices(indices);
		setVertices(vertices);
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

}
