package gravity.simulation.engine;

import java.util.ArrayList;
import java.util.List;

import gravity.simulation.Simulator;
import gravity.simulation.engine.world.entity.Entity;
import gravity.simulation.math.EngineMath;
import gravity.simulation.math.Vector2f;

public class Engine {
	
	public static void updateWorld() {
		List<Entity> entities = new ArrayList<Entity>();
		synchronized(Simulator.world) {
			for(Entity e : Simulator.world.getEntities()) {
				entities.add(gravToEntityInList(e, Simulator.world.getEntities()));
			}
			applySpeedToEntities(entities);
			mergeEntities(entities);
			Simulator.world.setEntities(entities);
		}
		updateTime();
	}
	
	private static void applySpeedToEntities(List<Entity> list) {
		for(Entity e : list) {
			e.setPos(e.getPos().addVec(e.getSpeed().mult(Simulator.speed/1000000000)));
		}
	}
	
	private static void mergeEntities(List<Entity> list) {
		Entity newOne;
		Vector2f newSpeed;
		Vector2f newPos;
		Entity e = null;
		Entity en = null;
		List<Entity> done = new ArrayList<Entity>();
		for(int i = 0; i < list.size(); i++) {
			if(i < list.size())e = list.get(i);
			for(int j = 0; j < list.size(); j++) {
				if(j < list.size())en = list.get(j);
				if(colliding(e, en) && e != en && !done.contains(e) && !done.contains(en)) {
					
					if(e.getMass() > en.getMass()) {
						newPos = e.getPos();
						newSpeed = transferSpeed(en.getSpeed(), e.getSpeed(), en.getMass(), e.getMass());
					}
					else {
						newPos = en.getPos();
						newSpeed = transferSpeed(e.getSpeed(), en.getSpeed(), e.getMass(), en.getMass());
					}
					newOne = new Entity(newPos, e.getMass()+en.getMass(), e.getSize()+en.getSize(), newSpeed);
					list.remove(e);
					list.remove(en);
					done.add(e);
					done.add(en);
					list.add(newOne);
				}
			}
		}
	}
	
	private static Vector2f transferSpeed(Vector2f a, Vector2f toB, float massA, float massB) {
		float factor = massA/massB;
		return toB.addVec(a.mult(factor));
		
	}
	
	private static boolean colliding(Entity e1, Entity e2) {
		float f = EngineMath.computePositiveDistance(e1.getPos(), e2.getPos());
		return f <= e1.getSize()+e2.getSize();
	}
	
	
	private static Entity gravToEntityInList(final Entity en, final List<Entity> list) {
		float massA, massB;
		float distance;
		float F;
		Vector2f addSpeed;
		Vector2f newSpeed = en.getSpeed();
		Vector2f pos = en.getPos();
		for(Entity e : list) {
			if(e != en) {
				distance = EngineMath.computePositiveDistance(en.getPos(), e.getPos());
				massA = en.getMass();
				massB = e.getMass();
				F = EngineMath.computeGravityForce(massA, massB, distance);
				addSpeed = EngineMath.computeSegmentVector(en.getPos(), e.getPos());
				addSpeed = addSpeed.mult(EngineMath.computeAcceleration(massA, F));
				newSpeed = EngineMath.applyAcceleration(newSpeed, addSpeed, (Simulator.speed*Simulator.oneUpdateTime*1000));
			}
		}
		
		return new Entity(pos, en.getMass(), en.getSize(), newSpeed);
	}
	
	private static long dividedMs, dividedS, dividedMin, dividedHour, dividedDays;
	
	public static void updateTime() {
		Simulator.ms+=(Simulator.speed*Simulator.oneUpdateTime*1000)+Simulator.currentUpdateDuration;
		dividedMs = Simulator.ms/1000;
		if(dividedMs > 0) {
			Simulator.ms-=dividedMs*1000;
			Simulator.s+=dividedMs;
			dividedS = Simulator.s/60;
			if(dividedS > 0) {
				Simulator.s-=dividedS*60;
				Simulator.min+=dividedS;
				dividedMin = Simulator.min/60;
				if(dividedMin > 0) {
					Simulator.min-=dividedMin*60;
					Simulator.hours+=dividedMin;
					dividedHour = Simulator.hours/24;
					if(dividedHour > 0) {
						Simulator.hours-=dividedHour*24;
						Simulator.days+=dividedHour;
						dividedDays = (int)(Simulator.days/365.25f);
						if(dividedDays > 0) {
							Simulator.days-=dividedDays*365.25f;
							Simulator.years+=dividedDays;
						}
					}
				}
			}
		}
	}

}
