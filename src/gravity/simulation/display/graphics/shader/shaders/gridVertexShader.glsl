#version 120

in vec2 position;

uniform vec2 offset;

void main(void){
//	float dividedFloatX = floor(offset.x/100)*100;
//	float dividedFloatY = floor(offset.y/100)*100;
//
//	float deltaX = offset.x-dividedFloatX;
//	float deltaY = offset.y-dividedFloatY;
//
//	vec2 compiledOffset = offset;
//
//	if(deltaX >= 50)compiledOffset.x = 0;
//	if(deltaY >= 50)compiledOffset.y = 0;

	gl_Position = gl_ModelViewProjectionMatrix* vec4(position+offset, 0.0, 1.0);
}
