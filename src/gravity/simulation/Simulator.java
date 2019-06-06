package gravity.simulation;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.TrueTypeFont;

import gravity.simulation.display.DisplayManager;
import gravity.simulation.display.Rendering;
import gravity.simulation.display.random.RandomEntities;
import gravity.simulation.engine.UpdateThread;
import gravity.simulation.engine.world.World;
import gravity.simulation.engine.world.entity.Entity;

public class Simulator {

	
	public static boolean running = true;
	public static boolean addingEntity = false;
	public static boolean showingHelp = false;
	
	public static float speed = 1;
	public static long maxSpeed = 1000000000000000000l;
	public static long years = 0;
	public static long days = 0, hours = 0, min = 0, s = 0, ms = 0;
	public static float minSpeed = 0.01f;
	public static float scale = 1f;
	public static List<Entity> temp;
	public static World world;
	
	public static float gridOffsetX = 50, gridOffsetY = 50;
	
	public static int currentUpdateDuration;
	
	public static float oneUpdateTime = 0.01f;
	
	public static int POSITION_STATE = 0, SIZE_STATE = 1, MASS_STATE = 2, SPEED_STATE = 3;
	public static int addingState = 0;
	public static Entity temporary = new Entity(null, 0, 0, null);
	
	public static int worldOffsetX, worldOffsetY;
	
	public static UpdateThread updater;
	public static Thread updateThread;
	
	public static RandomEntities randomEntities;
	
	public static void main(String[] args) {
		world = new World(new ArrayList<Entity>());
		updater = new UpdateThread();
		updateThread = new Thread(updater);
		updateThread.start();
		randomEntities = new RandomEntities(0, 0, 0, 0);
		
		DisplayManager.initDisplay();
		initFonts();
		Rendering.initFeatures();
		DisplayManager.start();
	}
	
	
	private static void initFonts() {
		Entity.infoFont = new TrueTypeFont(Entity.info, false);
		Rendering.HUDFont = new TrueTypeFont(Entity.info, false);
		Rendering.scaleFont = new TrueTypeFont(new Font("Arial", 10, 10), false);
	}

}
