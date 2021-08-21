package com.squareclicker.app;

import android.content.Context;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES30.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES30.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES30.glClear;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class Game {
    // Game attributes
    private Plane background;
    private Plane background_blank;
    public Context context;
    private Date date = new Date();
    private long time_initial;
    private long time_prev;

    public Game(Context context){
        this.context = context;
        grid = new Grid(this);
    }

    // Game attributes sent to change from another classes
    public int score = 0;
    public int combo = 0;
    public int time = 5000;
    public int time_mult = 400;
    public String state;

    // Custom game elements
    public Grid grid;
    public Healthbar hb;
    public ScoreFontRenderer score_r;

    // Touch tracking
    public float[] touch_coord = new float[2];
    public boolean touch_update = false;

    // Stop screen
    mButton startb;
    public boolean changing;
    float darkener;
    String new_state;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void load(){
        // generate shader programs
        ShaderStorage.create_shaders(this.context);
        // generate grid
        grid.generate_new_gray_pattern();
        grid.replace_gray_by_random();
        grid.generate_new_gray_pattern();
        grid.resize_all_squares_to_cells();
        // generate drawables
        background = new Plane(context, ShaderStorage.usual_shader, R.drawable.playfield);
        background_blank = new Plane(context, ShaderStorage.usual_shader, R.drawable.playfield_blank);
        hb = new Healthbar(this);
        // time management
        time = 5000;
        time_initial = new Date().getTime();
        time_prev = new Date().getTime();
        score_r = new ScoreFontRenderer(this);

        // start game render and update loop
        prepare_change_scene("s");
        // vars for s state
        startb = new mButton(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pass_click_to_grid(float[] click_coords){
        int x = -1;
        int y = -1;
        for (int i = 0; i < 4; i++){
            if (3f/95f + i * 23f/95f <= click_coords[0] && click_coords[0] <= 3f/95f + i * 23f/95f + 20f/95f){
                x = i;
            }
        }
        for (int i = 0; i < 6; i++){
            if (3f/168f + i * 23f/168f <= click_coords[1] && click_coords[1] <= 3f/168f + i * 23f/168f + 20f/168f){
                y = i;
            }
        }
        grid.click_cell(new int[] {x, y});
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void update(){
        if (touch_update){
            pass_click_to_grid(touch_coord);
            touch_update = false;
        }
        time = Math.min(time, 5500);
        if (state == "r" && !changing)
            // speeding things up if score is HIGH
            time -= (new Date().getTime() - time_prev) * Math.log(score) / 3.4;
        hb.update(time);
        time_prev = new Date().getTime();

        // loosing
        if (time <= 0 && !changing){
            SoundManagement.playSound(context, R.raw.zvukiprajeniya);
            prepare_change_scene("s");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void draw(){
        // Running state
        if (state == "r" || state == "a") {
            update();
            background.draw();
            grid.draw();
            score_r.draw();
            hb.draw();
        }
        if (state == "s") {
            if (touch_update){
                startb.pass_click(touch_coord[0], touch_coord[1]);
                touch_update = false;
            }
            background_blank.draw();
            score_r.draw();
            startb.draw();
        }
        if (changing){
            update_scene_changing();
        }
    }

    public void prepare_change_scene(String new_state){
        if (state == "r" && new_state == "a" || state == "a" && new_state == "r" || new_state == state) {
            state = new_state;
            return;
        }
        darkener = 0.0f;
        changing = true;
        this.new_state = new_state;
    }
    public void update_scene_changing(){
        if (state != new_state) {
            darkener += 0.06f;
            if (darkener > 1.1) {
                state = new_state;
            }
        }
        else{
            darkener -= 0.06f;
            if (darkener < 0.0f){
                darkener = 0f;
                changing = false;
            }
        }
        // give darkener to all shaders
        glUseProgram(ShaderStorage.usual_shader);
        glUniform1f(0, darkener);
    }


}
