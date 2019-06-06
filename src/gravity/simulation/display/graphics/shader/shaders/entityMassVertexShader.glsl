#version 120

in vec2 position;
uniform float scale;
uniform vec2 pos;
uniform vec2 offset;
uniform float mass;

void main(void){
	float relativeX = (position.x*mass+pos.x-offset.x)*scale;
	float relativeY = (position.y*mass+pos.y-offset.y)*scale;
	vec2 newPos = vec2(relativeX, relativeY);
	gl_Position = gl_ModelViewProjectionMatrix* vec4(newPos, 0.0, 1.0);
}