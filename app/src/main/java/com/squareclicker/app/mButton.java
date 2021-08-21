package com.squareclicker.app;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;

public class mButton {
    public Game game;
    public float top_x = -0.9f;
    public float top_y = -0.9f;
    public float bottom_x = 0.9f;
    public float bottom_y = -0.65f;
    private Plane plane;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public mButton(Game game){
        this.game = game;
        this.plane = new Plane(game.context, ShaderStorage.button_shader, R.drawable.start);
        resize();
    }
    public void resize(){
        plane.reshape(top_x, top_y, bottom_x, bottom_y);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void pass_click(float x, float y){
        x = 2 * x - 1;
        y = 2 * y - 1;

        if (top_x < x && bottom_x > x && top_y < y && bottom_y > y){
            OnClick();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void OnClick(){
        SoundManagement.playSound(game.context, R.raw.startsound);
        game.time = 5500;
        game.state = "a";
        game.score = 0;
        game.score_r.assemble(0);
    }
    public void draw(){
        plane.draw();
        plane.uniform(0, (float)(new Date().getTime() % 9600) / 1500f);
    }
}
