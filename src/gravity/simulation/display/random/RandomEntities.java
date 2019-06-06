package gravity.simulation.display.random;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gravity.simulation.Simulator;
import gravity.simulation.engine.world.entity.Entity;
import gravity.simulation.math.EngineMath;
import gravity.simulation.math.Vector2f;

@SuppressWarnings("serial")
public class RandomEntities extends JFrame {

	public static JLabel maxNumOfEntitiesLabel;
	public static JTextField maxNumberOfEntities;
	public static JCheckBox freeSpacing;
	public static JLabel minSpacingLabel;
	public static JTextField minSpacing;
	public static JLabel maxSizeLabel;
	public static JTextField maxSize;
	public static JLabel minSizeLabel;
	public static JTextField minSize;
	
	public static JLabel minMassLabel;
	public static JTextField minMass;
	public static JLabel maxMassLabel;
	public static JTextField maxMass;
	
	public static JCheckBox speed;
	public static JLabel minSpeedLabel;
	public static JTextField minSpeed;
	public static JLabel maxSpeedLabel;
	public static JTextField maxSpeed;
	
	public static JButton validate;
	
	private Container container;
	
	private RandomEntitiesListener l;
	
	private static float x1, x2, y1, y2;
	
	public RandomEntities(float x1, float x2, float y1, float y2) {
		RandomEntities.x1 = x1;
		RandomEntities.x2 = x2;
		RandomEntities.y1 = y1;
		RandomEntities.y2 = y2;
		container = new Container();
		this.setContentPane(container);
		this.setLayout(new FlowLayout(FlowLayout.LEADING,  3,  3));
		
		l = new RandomEntitiesListener();
		
		maxNumOfEntitiesLabel = new JLabel("Maximum number of entities: ");
		maxNumberOfEntities = new JTextField(20);
		maxNumberOfEntities.setText("1000");
		
		freeSpacing = new JCheckBox("Free spacing");
		freeSpacing.setSelected(true);
		freeSpacing.addItemListener(l);
		
		minSpacingLabel = new JLabel("Minimum spacing: ");
		minSpacing = new JTextField(20);
		minSpacing.setEnabled(false);
		
		maxSizeLabel = new JLabel("Maximum entity size: ");
		maxSize = new JTextField(20);
		maxSize.setText("100000");
		
		minSizeLabel = new JLabel("Minimum entity size: ");
		minSize = new JTextField(20);
		minSize.setText("10000");
		
		minMassLabel = new JLabel("Minimum entity mass: ");
		minMass = new JTextField(20);
		minMass.setText("7500");
		
		maxMassLabel = new JLabel("Maximum entity mass: ");
		maxMass = new JTextField(20);
		maxMass.setText("75000");
		
		speed = new JCheckBox("Initial speed");
		speed.addItemListener(l);
		minSpeedLabel = new JLabel("Minimum entity speed: ");
		minSpeed = new JTextField(20);
		minSpeed.setEnabled(false);
		
		maxSpeedLabel = new JLabel("Maximum entity speed: ");
		maxSpeed = new JTextField(20);
		maxSpeed.setEnabled(false);
		
		validate = new JButton("Generate");
		validate.addActionListener(l);
		
		container.add(maxNumOfEntitiesLabel);
		container.add(maxNumberOfEntities);
		
		container.add(new JLabel("                                                                                           "));
		
		container.add(freeSpacing);
		container.add(new JLabel("                                                          "));
		container.add(minSpacingLabel);
		container.add(minSpacing);
		container.add(new JLabel("                                                                                           "));

		container.add(minMassLabel);
		container.add(minMass);
		container.add(maxMassLabel);
		container.add(maxMass);
		container.add(new JLabel("                                                                                           "));
		
		container.add(minSizeLabel);
		container.add(minSize);
		container.add(maxSizeLabel);
		container.add(maxSize);
		container.add(new JLabel("                                                                                           "));
		
		container.add(speed);
		container.add(new JLabel("                                                          "));
		container.add(minSpeedLabel);
		container.add(minSpeed);
		container.add(maxSpeedLabel);
		container.add(maxSpeed);
		
		container.add(validate);
		
		this.setTitle("Generate Random Entities");
		this.setSize(470, 380);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		
	}
	
	public void newRandom(float x1, float x2, float y1, float y2) {
		RandomEntities.x1 = x1;
		RandomEntities.x2 = x2;
		RandomEntities.y1 = y1;
		RandomEntities.y2 = y2;
		this.setVisible(true);
		this.setAlwaysOnTop(false);
	}
	
	public static void generate(boolean freeSpacing, boolean initialSpeed) {
		int numOfEntities = Integer.parseInt(maxNumberOfEntities.getText());
		float minSize = Float.parseFloat(RandomEntities.minSize.getText());
		float maxSize = Float.parseFloat(RandomEntities.maxSize.getText());
		float minMass = Float.parseFloat(RandomEntities.minMass.getText());
		float maxMass = Float.parseFloat(RandomEntities.maxMass.getText());
		float minSpeed = 0;
		float maxSpeed = 0;
		float minSpacing = 0;
		if(RandomEntities.minSpacing.getText().length() > 0)minSpacing = Float.parseFloat(RandomEntities.minSpacing.getText());
		if(RandomEntities.minSpeed.getText().length() > 0)minSpeed = Float.parseFloat(RandomEntities.minSpeed.getText());
		if(RandomEntities.maxSpeed.getText().length() > 0)maxSpeed = Float.parseFloat(RandomEntities.maxSpeed.getText());
		
		List<Entity> entities = new ArrayList<Entity>();
		Vector2f pos;
		Vector2f speed;
		float size, mass;
		if(freeSpacing) {
			for(int i = 0; i < numOfEntities; i++) {
				pos = generatePos(x1, x2, y1, y2);
				size = EngineMath.generateRandomNumber(minSize, maxSize);
				mass = EngineMath.generateRandomNumber(minMass, maxMass);
				speed  = generateSpeed(initialSpeed, minSpeed, maxSpeed);
				entities.add(new Entity(pos, mass, size, speed));
			}
		}else {
			for(float x = x1; x < x2; x+=minSpacing) {
				for(float y = y1; y < y2; y+=minSpacing) {
					if(EngineMath.generateRandomNumber(0, 1) >= 0.5) {
						pos = new Vector2f(x, y);
						size = EngineMath.generateRandomNumber(minSize, maxSize);
						mass = EngineMath.generateRandomNumber(minMass, maxMass);
						speed  = generateSpeed(initialSpeed, minSpeed, maxSpeed);
						entities.add(new Entity(pos, mass, size, speed));
					}
				}
			}
		}
		synchronized(Simulator.world) {
			Simulator.world.getEntities().addAll(entities);
		}
	}
	
	private static Vector2f generateSpeed(boolean initialSpeed, float minSpeed, float maxSpeed) {
		if(initialSpeed) {
			float speed = EngineMath.generateRandomNumber(minSpeed, maxSpeed);
			float x = EngineMath.generateRandomNumber(-1, 1);
			float y = EngineMath.generateRandomNumber(-1, 1);
			x*=speed;
			y*=speed;
			return new Vector2f(x, y);
			
		}else return new Vector2f(0, 0);
	}
	
	private static Vector2f generatePos(float xMin, float xMax, float yMin, float yMax) {
		float x = EngineMath.generateRandomNumber(xMin, xMax);
		float y = EngineMath.generateRandomNumber(yMin, yMax);
		return new Vector2f(x, y);
	}

}
