package gravity.simulation.engine.world;

import java.util.List;

import gravity.simulation.engine.world.entity.Entity;

public class World {
	
	private List<Entity> entities;

	public World(List<Entity> entities) {
		setEntities(entities);
	}

	/**
	 * @return the entities
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

}
