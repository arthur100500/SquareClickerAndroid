precision mediump float;


uniform vec2 touch_coords;

uniform sampler2D u_TextureUnit;
varying vec2 v_Texture;

void main()
{
    gl_FragColor = vec4(texture2D(u_TextureUnit, v_Texture).xyz * 0.2, 1.0);
}