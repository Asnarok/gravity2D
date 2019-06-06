package gravity.simulation.display.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gravity.simulation.display.graphics.model.GPolygon;
import gravity.simulation.display.graphics.model.RawModel;
import gravity.simulation.display.graphics.model.StaticObject;
import gravity.simulation.math.Vector2f;

public class GPUManager {

	private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
     
    public RawModel loadToVAO(float[] positions,int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,positions);
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }
    
    public StaticObject loadToVAO(String fileName, Vector2f offset, float scale, float[] color) {
    	GPolygon p = OBJLoader.loadSimpeOBJ(fileName);
    	int vaoID = createVAO();
    	int[] colorIndices = new int[p.getIndices().length];
    	for(int i = 0; i < colorIndices.length; i++) {
    		colorIndices[i] = 0;
    	}
    	bindIndicesBuffer(p.getIndices());
    	storeDataInAttributeList(0, p.getVertices());
    	bindIndicesBuffer(colorIndices);
    	storeDataInAttributeList(1, scale);
    	bindIndicesBuffer(colorIndices);
    	storeDataInAttributeList(2, new float[] {offset.getX(), offset.getY()});
    	bindIndicesBuffer(colorIndices);
    	storeDataInAttributeList(3, color);
    	
    	unbindVAO();
    	return new StaticObject(new RawModel(vaoID, p.getIndices().length), offset, color, scale);
    }
     
    public void cleanUp(){
        for(int vao:vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos){
            GL15.glDeleteBuffers(vbo);
        }
    }
     
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
     
    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    
    private void storeDataInAttributeList(int attributeNumber, float data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(new float[] {data});
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, 1, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
     
     
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }
     
    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }
     
    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
     
    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
