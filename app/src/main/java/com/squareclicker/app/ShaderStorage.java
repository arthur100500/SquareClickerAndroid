package com.squareclicker.app;

import android.content.Context;
import android.opengl.GLES30;
import android.os.Build;

import androidx.annotation.RequiresApi;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glGetActiveUniform;
import static android.opengl.GLES20.glGetProgramiv;

public class ShaderStorage{
    public static int usual_shader;
    public static int rainbow_shader;
    public static int healthbar_shader;
    public static int button_shader;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void create_shaders(Context context){
        usual_shader = createProgram(context, R.raw.vertex_shader, R.raw.fragment_shader);
        healthbar_shader = createProgram(context, R.raw.vertex_shader, R.raw.fragment_healthbar_shader);
        button_shader = createProgram(context, R.raw.vertex_shader, R.raw.fragment_button_shader);
        rainbow_shader = -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int createProgram(Context context, int vertex_shader, int fragment_shader) {
        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, vertex_shader);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, fragment_shader);
        int programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        //glUseProgram(programId);
        int[] buf = new int[1];
        int[] junk = new int[1];
        glGetProgramiv(programId, GLES30.GL_ACTIVE_UNIFORMS, buf, 0);
        for (int i = 0; i < buf[0]; i++){
            String key = glGetActiveUniform(programId, i, junk, 0, junk, 0);
            int location = GLES30.glGetUniformLocation(programId, key);
            System.out.println("Uniform: " + key + "  " + location + "\n");
        }
        return programId;
    }
}