package com.squareclicker.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class GraySquare extends Square {
    public void OnClick(){

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public GraySquare(Context context, Game game){
        this.game = game;
        super.texture = R.drawable.graysquare;
        super.destroyable = false;
        super.name = "gray";
        super.short_name = "g";
        OnLoad(context);
    }
}