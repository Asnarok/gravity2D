package gravity.simulation.display.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gravity.simulation.display.graphics.model.GPolygon;
import gravity.simulation.display.graphics.shader.ShaderProgram;

public class OBJLoader {
	
	public static GPolygon loadSimpeOBJ(String fileName) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderProgram.class.getResourceAsStream("/gravity/simulation/display/graphics/wavefront/"+fileName+".obj")));
		List<Float> floats = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		String[] split;
		String line;
		float x, y;
		String coords;
		try {
			while((line = reader.readLine()) != null) {

				if(line.startsWith("v")) {
					coords = line.substring(2, line.lastIndexOf(" "));
					x = Float.parseFloat(coords.substring(0, coords.lastIndexOf(" ")));
					y = Float.parseFloat(coords.substring(coords.lastIndexOf(" "), coords.length()));
					floats.add(x);
					floats.add(y);
				}else if(line.startsWith("f")) {
					coords = line.substring(2, line.length());
					split = coords.split(" ");
					for(int i = 0; i < split.length; i++) {
						indices.add(Integer.parseInt(split[i]));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		float[] floatArray = new float[floats.size()];
		int[] intArray = new int[indices.size()];
		int i = 0;
		for(Float f : floats) {
			floatArray[i] = f;
			i++;
		}
		i = 0;
		for(Integer integer : indices) {
			intArray[i] = integer;
			i++;
		}
		return new GPolygon(floatArray, intArray);
	}

}
