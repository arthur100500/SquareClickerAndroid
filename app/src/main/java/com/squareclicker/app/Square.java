package com.squareclicker.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Square {
    public Plane plane;
    public boolean destroyable = true;
    public String name = "basic";
    public String short_name = "b";
    public int texture = R.drawable.s_playfield;
    public Game game;
    public void OnClick(){

    }
    public void draw(){
        plane.draw();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void OnLoad(Context context){
        plane = new Plane(context, ShaderStorage.usual_shader, texture);
    }
}

