package com.squareclicker.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.os.Build;

import androidx.annotation.RequiresApi;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_RGB;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetActiveUniform;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES31.GL_COMPUTE_SHADER;
import static android.opengl.GLES31.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT;
import static android.opengl.GLES31.glDispatchCompute;
import static android.opengl.GLES31.glMemoryBarrier;

public class ParticleEffectAnimation {
    int texture;
    int width = 256;
    int height = 256;
    int programId;
    int shader;
    Plane plane;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ParticleEffectAnimation(Context context){
        // создание объекта текстуры
        final int[] textureIds = new int[1];
        glGenTextures(1, textureIds, 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureIds[0]);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, null);
        glBindTexture(GL_TEXTURE_2D, 0);
        texture = textureIds[0];

        // создание шейдера
        int vertexShaderId = ShaderUtils.createShader(context, GL_COMPUTE_SHADER, R.raw.compute_shader_anim);
        programId = glCreateProgram();

        glAttachShader(programId, vertexShaderId);
        glLinkProgram(programId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(programId);
        }
        int[] buf = new int[1];
        int[] junk = new int[1];
        glGetProgramiv(programId, GLES30.GL_ACTIVE_UNIFORMS, buf, 0);
        for (int i = 0; i < buf[0]; i++){
            String key = glGetActiveUniform(programId, i, junk, 0, junk, 0);
            int location = GLES30.glGetUniformLocation(programId, key);
            System.out.println("Uniform: " + key + "  " + location + "\n");
        }
        // создание плоскости
        plane = new Plane(context, ShaderStorage.usual_shader, -1);
        plane.texture = texture;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void recompute(){
        glUseProgram(programId);
        glDispatchCompute(width * 2, height, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);
        }
    }
    public void draw(){
        plane.draw();
    }
}
