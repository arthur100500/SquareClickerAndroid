package com.squareclicker.app;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class Healthbar {
    public Plane plane;
    public Game game;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    public Healthbar(Game game){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            plane = new Plane(game.context, ShaderStorage.healthbar_shader);
        }
        plane.reshape(4f/95f - 1, 2 - 52f/168f - 1, 184f/95f - 1, 2 - 56f/168f - 1);
        plane.uniform(0, 1.0f);
        System.out.println(plane.programId);
    }

    public void update(int time){
        plane.uniform(0, (float)time / 5000f);
    }

    public void draw(){
        plane.draw();
    }
}
