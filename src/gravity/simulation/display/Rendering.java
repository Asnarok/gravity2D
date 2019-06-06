package gravity.simulation.display;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.TrueTypeFont;

import gravity.simulation.Simulator;
import gravity.simulation.display.graphics.GPUManager;
import gravity.simulation.display.graphics.model.RawModel;
import gravity.simulation.display.graphics.model.StaticObject;
import gravity.simulation.display.graphics.shader.EntityMassShader;
import gravity.simulation.display.graphics.shader.EntitySizeShader;
import gravity.simulation.display.graphics.shader.GridShader;
import gravity.simulation.display.graphics.shader.HUDShader;
import gravity.simulation.display.graphics.shader.ObjectShader;
import gravity.simulation.display.graphics.shader.SpeedLineShader;
import gravity.simulation.engine.world.entity.Entity;
import gravity.simulation.math.EngineMath;
import gravity.simulation.math.Vector2f;

public class Rendering {

	public static float[] HUDFloatColor = new float[] {0.58f, 0.58f, 0.58f, 1};
	
	public static org.newdawn.slick.Color HUDColorC = new org.newdawn.slick.Color(HUDFloatColor[0], HUDFloatColor[1], HUDFloatColor[2], HUDFloatColor[3]);
	public static org.newdawn.slick.Color blackC = new org.newdawn.slick.Color(0, 0, 0, 1f);
	
	public static HUDShader HUDShader;
	public static GridShader gridShader;
	public static SpeedLineShader speedLineShader;
	public static ObjectShader objectShader;
	
	public static TrueTypeFont HUDFont;
	public static TrueTypeFont scaleFont;
	
	public static GPUManager gpu;
	
	private static RawModel play;
	private static RawModel pause1;
	private static RawModel pause2;
	private static List<RawModel> grid;
	private static RawModel line;
	private static List<RawModel> scaleLines;
	
	public static RawModel circle;
	
	public static StaticObject helpPanel;
	
	
	public static void initFeatures() {
		gpu = new GPUManager();
		HUDShader = new HUDShader();
		gridShader = new GridShader();
		speedLineShader = new SpeedLineShader();
		objectShader = new ObjectShader();
		Entity.massShader = new EntityMassShader();
		Entity.sizeShader = new EntitySizeShader();
		generateCircles();
		
		play = gpu.loadToVAO(new float[] {20, 20, 45, 35, 20, 50}, new int[] {0, 1, 2});
		pause1 = gpu.loadToVAO(new float[] {20, 20, 30, 20, 20, 50, 30, 50}, new int[] {0, 1, 2, 1, 3, 2});
		pause2 = gpu.loadToVAO(new float[] {40, 20, 50, 20, 40, 50, 50, 50}, new int[] {0, 1, 2, 1, 3, 2});
		List<float[]> lines = generateGrid();
		grid = new ArrayList<RawModel>();
		for(float[] f : lines) {
			grid.add(gpu.loadToVAO(f, new int[] {0, 1}));
		}
		
		line = gpu.loadToVAO(new float[] {0, 0, 1, 1}, new int[] {0, 1});
		
		scaleLines = new ArrayList<RawModel>();
		scaleLines.add(gpu.loadToVAO(new float[] {500, 30, 550, 30}, new int[] {0, 1}));
		scaleLines.add(gpu.loadToVAO(new float[] {500, 25, 500, 36}, new int[] {0, 1}));
		scaleLines.add(gpu.loadToVAO(new float[] {550, 25, 550, 36}, new int[] {0, 1}));
		
		helpPanel = gpu.loadToVAO("help_panel", new Vector2f(-3.5f, -2), 100, new float[] {0.7f, 0.7f, 0.7f, 1.0f}); //problème : 
	}
	
	public static void render() {
		renderGrid();
		renderAddingMode();
		renderWorld();
		renderHUD();
		if(Simulator.showingHelp)renderHelpPanel();
	}
	
	private static void renderGrid() {
		gridShader.start();
		gridShader.loadOffset(Simulator.gridOffsetX, Simulator.gridOffsetY);
		for(RawModel m : grid) {
			renderLine(m);
		}
		gridShader.stop();
	}
	
	public static void renderModel(RawModel model){
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
	
	public static void renderLine(RawModel model){
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_LINES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
	
	public static void renderCircle(RawModel model){
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLE_FAN, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
	
	
	private static List<float[]> generateGrid() {
		int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		List<float[]> lines = new ArrayList<float[]>();
		for(int x = 50; x < screenWidth+100; x+=50) {
			lines.add(new float[] {x, 0, x, screenHeight+50});
		}
		for(int y = 50; y < screenHeight+50; y+=50) {
			lines.add(new float[] {0, y, screenWidth+100, y});
		}
		return lines;
	}
	
	private static void renderAddingMode() {
		if(Simulator.addingEntity) {
			if(Simulator.addingState == Simulator.POSITION_STATE) {
				Simulator.temporary.setPos(EngineMath.convertToAbsolute(DisplayManager.mouseX, DisplayManager.mouseY));
			}else if(Simulator.addingState == Simulator.SIZE_STATE) {
				Simulator.temporary.setSize(EngineMath.computePositiveDistance(Simulator.temporary.getPos(), EngineMath.convertToAbsolute(DisplayManager.mouseX, DisplayManager.mouseY)));
			}else if(Simulator.addingState == Simulator.MASS_STATE) {
				Simulator.temporary.setMass((float)(EngineMath.computePositiveDistance(Simulator.temporary.getPos(), EngineMath.convertToAbsolute(DisplayManager.mouseX, DisplayManager.mouseY))));
			}else if(Simulator.addingState == Simulator.SPEED_STATE) {
				Simulator.temporary.setSpeed(new Vector2f(EngineMath.convertToAbsoluteX(DisplayManager.mouseX)-Simulator.temporary.getPos().getX(), EngineMath.convertToAbsoluteY(DisplayManager.mouseY)-Simulator.temporary.getPos().getY()).mult(0.01f));
				speedLineShader.start();
				speedLineShader.init(new Vector2f(Simulator.temporary.getPos().getX(), Simulator.temporary.getPos().getY()), new Vector2f(DisplayManager.mouseX, DisplayManager.mouseY));
				renderLine(line);
				speedLineShader.stop();
			}
			Simulator.temporary.render();
		}
	}
	
	private static void renderHelpPanel() {
		helpPanel.render(objectShader);
	}
	private static void renderWorld() {
		synchronized(Simulator.world) {
			for(Entity e : Simulator.world.getEntities()) {
				e.render();
			}
		}
	}
	private static void renderHUD() {
		HUDShader.start();
		HUDShader.loadColor(new float[] {0.58f, 0.58f, 0.58f, 1.0f});
		if(Simulator.running) {
			renderModel(play);
		}else {
			renderModel(pause1);
			renderModel(pause2);
		}
		HUDShader.stop();
		
		renderText(HUDFont, 60, 20, "Update speed: ", HUDColorC);
		renderText(HUDFont, 210, 20, "One real second = "+(Simulator.speed)+"s", HUDColorC);
		
		HUDShader.start();
		HUDShader.loadColor(new float[] {0, 0, 0, 1});
		for(RawModel m : scaleLines) {
			renderLine(m);
		}
		HUDShader.stop();
		
		renderText(scaleFont, 500, 43, (float)(int)((50/Simulator.scale*100))/100+"m", blackC);
		
		renderText(HUDFont, 600, 20, "Update duration: "+Simulator.currentUpdateDuration+"ms", HUDColorC);
		
		renderText(HUDFont, 600, 40, "Time: ", HUDColorC);
		renderText(HUDFont, 600, 60, Simulator.years+"y", HUDColorC);
		renderText(HUDFont, 600, 80, Simulator.days+"d", HUDColorC);
		renderText(HUDFont, 600, 100, Simulator.hours+":"+Simulator.min+", "+Simulator.s+"s "+Simulator.ms+"ms", HUDColorC);
	}
	
	public static void renderText(TrueTypeFont font, float x, float y, String chars, org.newdawn.slick.Color c) {
        font.drawString(x, y, chars, c);
	}
	
	public static void drawLine(float x1, float y1, float x2, float y2, float[] rgba) {
		glDisable(GL_BLEND);
		glBegin(GL_LINES);
		glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glEnd();
		glEnable(GL_BLEND);
	}
	
	
	private static void generateCircles() {
		int resolution = 128;
		float[] vertices = new float[resolution*2];
		int[] indices = new int[resolution];
		
		double angle;
		for(int i = 0; i < vertices.length; i+=2){
		    angle = Math.PI * i / resolution;
		    vertices[i] = (float)Math.cos(angle);
		    vertices[i+1] = (float)Math.sin(angle);
		}
		for(int i = 0; i < indices.length; i++) {
			if(i == indices.length-1)indices[i] = 0; 
			else indices[i] = i; 
		}
		circle = gpu.loadToVAO(vertices, indices);
	}
}
