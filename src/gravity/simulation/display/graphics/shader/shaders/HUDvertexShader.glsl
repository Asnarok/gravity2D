#version 120

in vec2 position;

void main(void){
	gl_Position = gl_ModelViewProjectionMatrix* vec4(position, 0.0, 1.0);
}