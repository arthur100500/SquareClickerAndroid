package com.squareclicker.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class RedSquare extends Square {
    public void OnClick(){
        game.time -= game.time_mult * 666;
        game.combo = 0;
        // game.grid.remove_square;
        SoundManagement.playSound(game.context, R.raw.redsound);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RedSquare(Context context, Game game){
        this.game = game;
        super.texture = R.drawable.redsquare;
        super.name = "red";
        super.short_name = "r";
        OnLoad(context);
    }
}