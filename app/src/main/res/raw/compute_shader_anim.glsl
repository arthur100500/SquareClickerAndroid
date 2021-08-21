precision mediump float;

uniform image2D img_output;



void main() {
  ivec2 pixel_coords = ivec2(gl_GlobalInvocationID.xy);
  vec4 pixel = imageLoad(img_output, pixel_coords);
  vec4 pix = vec4(0.5);
  imageStore(img_output, pixel_coords, pix);
}