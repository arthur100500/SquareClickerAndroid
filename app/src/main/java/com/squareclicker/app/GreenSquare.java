package com.squareclicker.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class GreenSquare extends Square {
    public void OnClick(){
        game.time += game.time_mult;
        game.combo ++;
        game.score += (int)Math.sqrt(game.combo);
        // game.grid.remove_square;
        SoundManagement.playSound(game.context, R.raw.greensound);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public GreenSquare(Context context, Game game){
        this.game = game;
        super.texture = R.drawable.greensquare;
        super.name = "green";
        super.short_name = "gr";
        OnLoad(context);
    }
}
