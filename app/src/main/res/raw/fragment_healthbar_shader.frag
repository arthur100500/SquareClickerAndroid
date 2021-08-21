precision mediump float;

varying vec2 v_Texture;
uniform float health;

void main()
{
	if (v_Texture.x < health){
		gl_FragColor = vec4(1.0 - health + 0.5, health, 0, 1.0);
	}
	else{
		gl_FragColor = vec4(0.0);
	}
}