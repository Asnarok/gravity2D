package gravity.simulation.display;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import gravity.simulation.Simulator;

public class DisplayManager {
	
	
	public static float width = 1280, height = 720;
	
	public static float mouseX, mouseY;
	
	public static void initDisplay() {
		init();
	}
	
	private static void init() {
		try {
			Display.setDisplayMode(new DisplayMode((int)width, (int)height));
			Display.setVSyncEnabled(true);
			Display.create(new PixelFormat(/*Alpha Bits*/8, /*Depth bits*/ 8, /*Stencil bits*/ 0, /*samples*/8));
			Display.setResizable(true);
			Display.setTitle("Gravity Simulator");
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initGL();
	}
	
	private static void initGL() {
		glClearColor(1, 1, 1, 1);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glEnable(GL_POLYGON_SMOOTH);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
	}
	
	private static void updateView() {
		Display.update();
		InputManager.update();
		width = Display.getWidth();
		height = Display.getHeight();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glViewport(0, 0, (int)width, (int)height);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static void start() {
		loop();
	}
	
	@SuppressWarnings("deprecation")
	private static void loop() {
		long millis = System.currentTimeMillis();
		long delta;
		float fpsCap = 1000f/60f;
		
		while(!Display.isCloseRequested()) {
			delta = System.currentTimeMillis()-millis;
			if(delta >= fpsCap) {
				updateView();
				Rendering.render();
				millis = System.currentTimeMillis();
			}else {
				try {
					Thread.sleep((long)fpsCap-delta);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		Rendering.gpu.cleanUp();
		Simulator.updateThread.stop();
		Rendering.gpu.cleanUp();
		Display.destroy();
		System.exit(0);
	}
	

}
