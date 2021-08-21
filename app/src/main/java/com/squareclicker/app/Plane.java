package com.squareclicker.app;

import android.content.Context;
import android.opengl.GLES30;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_FRAGMENT_SHADER;
import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.GL_TRIANGLE_STRIP;
import static android.opengl.GLES30.GL_VERTEX_SHADER;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glDrawArrays;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGetActiveUniform;
import static android.opengl.GLES30.glGetAttribLocation;
import static android.opengl.GLES30.glGetProgramiv;
import static android.opengl.GLES30.glGetShaderiv;
import static android.opengl.GLES30.glUniform1f;
import static android.opengl.GLES30.glUniform2f;
import static android.opengl.GLES30.glUniform2fv;
import static android.opengl.GLES30.glUseProgram;
import static android.opengl.GLES30.glVertexAttribPointer;

public class Plane {

    float[] vertices = Misc.fullscreenverticies;

    private final static int POSITION_COUNT = 3;
    private static final int TEXTURE_COUNT = 2;
    private static final int STRIDE = (POSITION_COUNT
            + TEXTURE_COUNT) * 4;

    Context context;
    private FloatBuffer vertexData;
    private int aPositionLocation;
    private int aTextureLocation;
    public int programId;
    public int texture = 0;

    public int vertexShaderId;
    public int fragmentShaderId;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Plane(Context context, int vert_shader, int frag_shader, int texture_loc) {
        this.context = context;

        if (texture_loc != 0){
            texture = TextureUtils.loadTexture(context, texture_loc);
        }

        createProgram(vert_shader, frag_shader);
        getLocations();
        load();
        bindData();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Plane(Context context, int vert_shader, int frag_shader, int texture_loc, int it_is_raw_type) {
        this.context = context;


        if (texture_loc != 0){
            texture = TextureUtils.loadTexture(context, texture_loc);
        }

        programId = ShaderUtils.createProgram(vert_shader, frag_shader);
        //glUseProgram(programId);
        vertexShaderId = vert_shader;
        fragmentShaderId = frag_shader;

        int[] buf = new int[1];
        int[] junk = new int[1];
        glGetProgramiv(programId, GLES30.GL_ACTIVE_UNIFORMS, buf, 0);
        for (int i = 0; i < buf[0]; i++){
            String key = glGetActiveUniform(programId, i, junk, 0, junk, 0);
            int location = GLES30.glGetUniformLocation(programId, key);
            System.out.println("Uniform: " + key + "  " + location + "\n");
        }
        getLocations();
        load();
        bindData();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Plane(Context context, int programID, int texture_loc) {
        this.context = context;


        if (texture_loc != 0){
            texture = TextureUtils.loadTexture(context, texture_loc);
        }

        programId = programID;

        getLocations();
        load();
        bindData();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Plane(Context context, int programID) {
        this.context = context;
        texture = -1;

        programId = programID;

        getLocations();
        load();
        bindData();
    }


    public void setlayer(float layer){
        vertices[2] = layer;
        vertices[7] = layer;
        vertices[12] = layer;
        vertices[17] = layer;
        load();
    }

    public void reshape(float top_x, float top_y, float bottom_x, float bottom_y){
        vertices = new float[]{
                -bottom_x, bottom_y, 0f, 0.0f, 0.0f, // bottom left
                -bottom_x,  top_y, 0f, 0.0f, 1.0f,  // top left
                -top_x, bottom_y, 0f, 1.0f, 0.0f, // bottom right
                -top_x,  top_y, 0f, 1.0f, 1.0f // top right
                };
        load();
    }

    public void load() {
        vertexData = ByteBuffer
                .allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(vertices);
        UseProgram();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createProgram(int vertex_shader, int fragment_shader) {
        vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, vertex_shader);
        fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, fragment_shader);
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        //glUseProgram(programId);
        int[] buf = new int[1];
        int[] junk = new int[1];
        glGetProgramiv(programId, GLES30.GL_ACTIVE_UNIFORMS, buf, 0);
        for (int i = 0; i < buf[0]; i++){
            String key = glGetActiveUniform(programId, i, junk, 0, junk, 0);
            int location = GLES30.glGetUniformLocation(programId, key);
            System.out.println("Uniform: " + key + "  " + location + "\n");
        }
    }

    private void UseProgram(){
        glUseProgram(programId);
    }

    private void getLocations() {
        aPositionLocation = glGetAttribLocation(programId, "a_Position");
        aTextureLocation = glGetAttribLocation(programId, "a_Texture");
    }
    private int getLocation(String name){
        return glGetAttribLocation(programId, name);
    }

    public void uniform(int uniform_location, float[] value){
        //System.out.println("Setting " + uniform_location + " in " + programId + " to values: " + value[0] + " " + value[1]);
        glUniform2fv(uniform_location, 1, value, 0);
    }
    public void uniform(int uniform_location, float value){
        //System.out.println("Setting " + uniform_location + " in " + programId + " to values: " + value);
        glUseProgram(programId);
        glUniform1f(uniform_location, value);
    }

    private void bindData() {
        // координаты вершин
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aPositionLocation);


        // координаты текстур
        vertexData.position(POSITION_COUNT);
        glVertexAttribPointer(aTextureLocation, TEXTURE_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aTextureLocation);
    }



    public void resize(int width, int height){

    }

    public void UseTexture(){
        if (texture != -1) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture);
        }
    }
    public void draw() {
        bindData();
        UseProgram();
        UseTexture();
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }
    public void clear(){
        //GLES30.glDeleteProgram(programId);
        vertexData.clear();
    }
}
