attribute vec4 a_Position;
attribute vec2 a_Texture;
varying vec2 v_Texture;

void main()
{
    gl_Position = a_Position;
    v_Texture = a_Texture;
}