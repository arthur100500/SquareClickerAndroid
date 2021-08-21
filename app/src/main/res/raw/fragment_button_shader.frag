precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_Texture;
uniform float iTime;

void main()
{
    vec4 mask_color = texture2D(u_TextureUnit, v_Texture);
	
	if (mask_color.r > 0.5)
		gl_FragColor = mask_color;
	else{
		gl_FragColor = vec4((1.1 - vec3(mask_color)) * (0.5 + 0.5 * cos(iTime + v_Texture.xyx + vec3(0,2,4))), mask_color.a);
	}
}