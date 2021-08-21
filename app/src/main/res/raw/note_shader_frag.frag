

precision mediump float;

uniform vec2 current_pos;
varying vec2 v_Texture;

float len(vec2 arg){
	return sqrt(arg.x * arg.x + 4.0 * arg.y * arg.y);
}

float bet1and0(float val){
	return min(max(0.0, val), 1.0);
}


void main()
{
	vec2 screen_coord = vec2(v_Texture.x, 1.0 - v_Texture.y); 
	float dist = len(current_pos - screen_coord);
	float dist_blur = 1.0 - bet1and0(dist * 8.0);
	if (dist > 0.0 && screen_coord.y > 0.135)
		gl_FragColor = vec4(vec3(1.0), dist_blur);
	dist = len(current_pos - screen_coord);
	dist = 1.0 - bet1and0(dist * 13.0);
	if (dist > 0.0 && screen_coord.y > 0.135)
		gl_FragColor = vec4(vec3(1.0), 1.0);
		
		
}