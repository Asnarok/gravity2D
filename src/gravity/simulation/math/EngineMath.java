package gravity.simulation.math;

import gravity.simulation.Simulator;

public class EngineMath {
	
	public static double G = 6.67408*Math.pow(10, -11);
	
	
	public static Vector2f computeSegmentVector(Vector2f pointA, Vector2f pointB) {
		float x = pointA.getX()-pointB.getX();
		float y = pointA.getY()-pointB.getY();
		
		float pX = onlyPositive(x), pY = onlyPositive(y);
		
		if(pY > pX) {
			pX/=pY;
			pY/=pY;
		}else {
			pY/=pX;
			pX/=pX;
		}
		if(x < 0)pX*=-1;
		if(y < 0)pY*=-1;
		
		return new Vector2f(pX, pY);
	}
	
	public static float onlyPositive(float f) {
		if(f < 0) return -f;
		return f;
	}
	
	public static Vector2f computeAverage(Vector2f a, Vector2f b) {
		return new Vector2f((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
	}
	
	public static float computePositiveDistance(Vector2f pointA, Vector2f pointB) {
		float f = (float) Math.sqrt(Math.pow((pointB.getX()-pointA.getX()), 2)+Math.pow((pointB.getY()-pointA.getY()), 2));
		if (f < 0) f*=-1;
		return f;
	}
	
	public static float computeGravityForce(float massA, float massB, float d) {
		return (float) (G* (massA*massB)/(d*d));
	}
	
	public static float computeAcceleration(float m, float F) {
		return F/m;
	}
	
	public static float generateRandomNumber(float min, float max) {
		return (float)min+(float)Math.random()*((float)max-(float)min);
	}
	
	public static Vector2f applyAcceleration(Vector2f currentSpeed, Vector2f acceleration, float deltaTs) {
		return currentSpeed = currentSpeed.addVec(acceleration.mult(deltaTs*-1));
	}
	
	//relative and absolute positions
	
	public static Vector2f convertToRelative(float x, float y) {
		return new Vector2f((x)*Simulator.scale-Simulator.worldOffsetX, (y)*Simulator.scale-Simulator.worldOffsetY);
	}
	
	public static Vector2f convertToAbsolute(float x, float y) {
		return new Vector2f(x/Simulator.scale+Simulator.worldOffsetX, y/Simulator.scale+Simulator.worldOffsetY);
	}
	
	public static Vector2f convertToRelative(Vector2f pos) {
		return new Vector2f((pos.getX())*Simulator.scale+Simulator.worldOffsetX, (pos.getY())*Simulator.scale+Simulator.worldOffsetY);
	}
	
	public static Vector2f convertToAbsolute(Vector2f pos) {
		return new Vector2f((pos.getX())/Simulator.scale-Simulator.worldOffsetX, (pos.getY())/Simulator.scale-Simulator.worldOffsetY);
	}
	
	public static float convertToRelativeX(float x) {
		return (x-Simulator.worldOffsetX)*Simulator.scale;
	}
	
	public static float convertToRelativeY(float y) {
		return (y-Simulator.worldOffsetY)*Simulator.scale;
	}
	
	public static float convertToAbsoluteX(float x) {
		return (x)/Simulator.scale+Simulator.worldOffsetX;
	}
	
	public static float convertToAbsoluteY(float y) {
		return (y)/Simulator.scale+Simulator.worldOffsetY;
	}

}
