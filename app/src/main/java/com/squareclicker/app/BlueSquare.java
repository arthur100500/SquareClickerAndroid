package com.squareclicker.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class BlueSquare extends Square {
    public void OnClick(){
        game.time += game.time_mult * 666;
        game.combo ++;
        game.score += (int)Math.sqrt(game.combo);
        // game.grid.remove_square;
        SoundManagement.playSound(game.context, R.raw.bluesound);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public BlueSquare(Context context, Game game){
        this.game = game;
        super.texture = R.drawable.bluesquare;
        super.name = "blue";
        super.short_name = "b";
        OnLoad(context);
    }
}
