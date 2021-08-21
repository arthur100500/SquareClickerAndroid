package com.squareclicker.app;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class OrangeSquare extends Square {
    int type = 3;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void OnClick(){
        game.time += game.time_mult * 3;
        game.combo ++;
        game.score += (int)Math.sqrt(game.combo);
        if (type == 1) {
            game.score += (int)Math.sqrt(game.combo) * 3;
            SoundManagement.playSound(game.context, R.raw.orange1);
            super.destroyable = true;
            return;
        }
        if (type == 2) {
            SoundManagement.playSound(game.context, R.raw.orange2);
            type -= 1;

            super.texture = R.drawable.orangesquare1;
            super.OnLoad(game.context);
            return;
        }
        if (type == 3) {
            SoundManagement.playSound(game.context, R.raw.orange3);
            type -= 1;
            super.texture = R.drawable.orangesquare2;
            super.OnLoad(game.context);
            return;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public OrangeSquare(Context context, Game game){
        this.game = game;
        super.texture = R.drawable.orangesquare3;
        super.name = "orange";
        super.short_name = "o";
        super.OnLoad(context);
        super.destroyable = false;
    }
}
