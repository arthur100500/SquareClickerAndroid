package com.squareclicker.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class EmptySquare extends Square {
    public void OnClick(){
        game.time -= game.time_mult;
        game.combo = 0;
        // game.grid.remove_square;
        SoundManagement.playSound(game.context, R.raw.missclick);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public EmptySquare(Context context, Game game){
        this.game = game;
        super.texture = R.drawable.transparentpixel;
        super.name = "empty";
        super.short_name = "e";
        OnLoad(context);
    }
}