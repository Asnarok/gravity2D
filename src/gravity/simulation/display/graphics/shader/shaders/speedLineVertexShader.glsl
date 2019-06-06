#version 120

in vec2 position;
uniform float scale;
uniform vec2 origin;
uniform vec2 target;
uniform vec2 offset;

void main(void){
	vec2 relative;
	if(position.x == 0.0 && position.y == 0.0) {
		relative = vec2((position.x+origin.x-offset.x)*scale, (position.y+origin.y-offset.y)*scale);
	}else {
		relative = target;
	}
	gl_Position = gl_ModelViewProjectionMatrix* vec4(relative, 0.0, 1.0);
}
