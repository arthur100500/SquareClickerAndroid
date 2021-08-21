package com.squareclicker.app;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

/*
Currently only numbers :(
 */
public class ScoreFontRenderer {
    public ArrayList<Plane> drawable = new ArrayList<>();
    float f_height = 0.6f;
    float f_width = 0.7f;
    float f_dist = 0.17f;
    float p_x = -1.13f;
    float p_y = 0.55f;
    Game game;

    public ScoreFontRenderer(Game game){
        this.game = game;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void assemble(int number){
        drawable.clear();
        int x = -1;
        // create planes
        if (number == 0) drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_0));
        while (number != 0){
            x = number % 10;
            if (x == 0) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_0));
            }
            if (x == 1) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_1));
            }
            if (x == 2) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_2));
            }
            if (x == 3) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_3));
            }
            if (x == 4) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_4));
            }
            if (x == 5) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_5));
            }
            if (x == 6) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_6));
            }
            if (x == 7) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_7));
            }
            if (x == 8) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_8));
            }
            if (x == 9) {
                drawable.add(new Plane(game.context, ShaderStorage.usual_shader, R.drawable.default_9));
            }
            number /= 10;
        }
        // reshape planes
        for (int i = 0; i < drawable.size(); i++){
            //topx topy bottomx bottomy
            drawable.get(i).reshape(p_x + f_dist * i, p_y, p_x + f_width + f_dist * i, p_y + f_height);
        }
    }

    public void draw(){
        for (Plane i : drawable){
            i.draw();
        }
    }
}
