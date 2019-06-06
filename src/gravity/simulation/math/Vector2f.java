package gravity.simulation.math;

public class Vector2f {

	public float x;
	public float y;
	
	public Vector2f(float x, float y) {
		setX(x);
		setY(y);
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2f mult(float factor) {
		return new Vector2f(x*factor, y*factor);
	}
	
	public Vector2f addVec(Vector2f toAdd) {
		return new Vector2f(this.getX()+toAdd.getX(), this.getY()+toAdd.getY());
	}
	
	public Vector2f div(float factor) {
		return new Vector2f(x/factor, y/factor);
	}
}
