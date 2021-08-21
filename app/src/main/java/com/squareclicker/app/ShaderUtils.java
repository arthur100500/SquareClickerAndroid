package com.squareclicker.app;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import java.util.HashMap;

import static android.opengl.GLES30.GL_COMPILE_STATUS;
import static android.opengl.GLES30.GL_LINK_STATUS;
import static android.opengl.GLES30.glAttachShader;
import static android.opengl.GLES30.glCompileShader;
import static android.opengl.GLES30.glCreateProgram;
import static android.opengl.GLES30.glCreateShader;
import static android.opengl.GLES30.glDeleteProgram;
import static android.opengl.GLES30.glDeleteShader;
import static android.opengl.GLES30.glGetProgramiv;
import static android.opengl.GLES30.glGetShaderiv;
import static android.opengl.GLES30.glLinkProgram;
import static android.opengl.GLES30.glShaderSource;


public class ShaderUtils {
    //useless private static HashMap<Integer, String> all_shader_text = new HashMap<Integer, String>();
    public static int createProgram(int vertexShaderId, int fragmentShaderId) {

        final int programId = glCreateProgram();

        if (programId == 0) {
            return 0;
        }

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);

        glLinkProgram(programId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(programId);
            return 0;
        }
        // GLES30.glDetachShader(programId, vertexShaderId);
        // GLES30.glDetachShader(programId, fragmentShaderId);
        // GLES30.glDeleteShader(vertexShaderId);
        // GLES30.glDeleteShader(fragmentShaderId);
        return programId;

    }

    static int createShader(Context context, int type, int shaderRawId) {
        String shaderText = FileUtils
                .readTextFromRaw(context, shaderRawId);
        return ShaderUtils.createShader(type, shaderText);
    }

    static int createShader(int type, String shaderText) {
        final int shaderId = glCreateShader(type);
        if (shaderId == 0) {
            return 0;
        }
        glShaderSource(shaderId, shaderText);
        glCompileShader(shaderId);
        int[] a = new int[1];
        GLES30.glGetShaderiv(shaderId, GL_COMPILE_STATUS, a, 0);
        String compilation_status = GLES30.glGetShaderInfoLog(shaderId);
        if (a[0] == 1){
            System.out.println("Shader " + shaderId + " compiled with no errors");
        }
        else {
            System.out.println("SHADER_LOG");
            System.out.println("Shader " + shaderId + " compiled with errors");
            System.out.println(shaderId + "    " + a[0]);
            System.out.println(shaderText);
            System.out.println(compilation_status);
            System.out.println("SHADER_END");
        }

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderId);
            return 0;
        }

        return shaderId;
    }

}