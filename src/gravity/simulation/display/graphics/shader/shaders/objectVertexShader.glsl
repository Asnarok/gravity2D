#version 120

in vec2 offset;
in float scale;
in vec2 position;

uniform vec2 windowSize;

void main(void){

	vec2 newPos = vec2(windowSize.x/2+offset.x+(position.x*scale), windowSize.y/2+offset.y+(position.y*scale));
	gl_Position = gl_ModelViewProjectionMatrix* vec4(newPos, 0.0, 1.0);
}
