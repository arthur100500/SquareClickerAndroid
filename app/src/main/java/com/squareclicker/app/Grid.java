package com.squareclicker.app;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    public int[] size = new int[]{5, 6};
    public final Square[][] grid = new Square[][]{new Square[]{null, null, null, null},
                                                new Square[]{null, null, null, null},
                                                new Square[]{null, null, null, null},
                                                new Square[]{null, null, null, null},
                                                new Square[]{null, null, null, null},
                                                new Square[]{null, null, null, null}};
    public Game game;
    public ArrayList<Plane> drawable = new ArrayList<>();
    public Grid(Game game){
        this.game = game;
    }

    public int debug_draw_int = 0;


    final Random random = new Random();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void click_cell(int[] cell_position){



        if (cell_position[0] == -1 || cell_position[1] == -1){
            if (game.state == "r" && !game.changing){
                game.combo = 0;
                game.time -= 2 * game.time_mult;
                SoundManagement.playSound(game.context, R.raw.missclick);
            }
        }

        else{
            if (grid[cell_position[1]][cell_position[0]] != null){
                grid[cell_position[1]][cell_position[0]].OnClick();
                if (grid[cell_position[1]][cell_position[0]].destroyable){
                    grid[cell_position[1]][cell_position[0]].plane.clear();
                    grid[cell_position[1]][cell_position[0]] = null;
                }
            }
            else{
                game.combo = 0;
                game.time -= 2 * game.time_mult;
                SoundManagement.playSound(game.context, R.raw.missclick);
            }
            if (game.state == "a" && !game.changing)
                game.time = 5500;
                game.prepare_change_scene("r");
        }

        refresh();
        game.score_r.assemble(game.score);
        System.out.println(game.time);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void generate_new_gray_pattern(){
        int[] next_coord;
        for(int i = 0; i < 3; i++)
        while (true){
            next_coord = new int[]{random.nextInt(6), random.nextInt(4)};
            if (grid[next_coord[0]][next_coord[1]] == null){
                grid[next_coord[0]][next_coord[1]] = new GraySquare(game.context, game);
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void refresh(){

        resize_all_squares_to_cells();
        for (int i = 0; i < 6; i ++){
            for (int j = 0; j < 4; j ++){
                if (grid[i][j] != null){
                    if (!grid[i][j].name.equals("gray") && !grid[i][j].name.equals("red")) {
                        return;
                    }
                }
            }
        }
        destroy_reds();
        replace_gray_by_random();
        generate_new_gray_pattern();
        resize_all_squares_to_cells();

    }

    public void destroy_reds(){
        for (int i = 0; i < 6; i ++){
            for (int j = 0; j < 4; j ++){
                if (grid[i][j] != null){
                    if (grid[i][j].name.equals("red")) {
                        grid[i][j].plane.clear();
                        grid[i][j] = null;
                    }
                }
            }
        }
    }

    public void print_grid_string(){
        for (int i = 0; i < 6; i ++){
            for (int j = 0; j < 4; j ++){
                if (grid[i][j] != null){
                    System.out.print(grid[i][j].short_name + " (" + (grid[i][j].plane.vertices[0] - grid[i][j].plane.vertices[10]) + ", " + grid[i][j].plane.vertices[1] + ", " + grid[i][j].plane.programId+ ")");
                }
                else{
                    System.out.print("n");
                }
            }
            System.out.println();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void replace_gray_by_random(){
        int rnd = 0;
        int red_rnd = 1; // var to ensure only 1 red spawns
        for (int i = 0; i < 6; i ++){
            for (int j = 0; j < 4; j ++){
                if (grid[i][j] != null){
                    if (grid[i][j].name.equals("gray")) {
                        if (game.time > 2000) {
                            // normal-gen
                            // 70% green
                            // 10% red
                            // 12% orange
                            // 8% blue
                            rnd = random.nextInt(100);
                            if (rnd < 70) {
                                grid[i][j] = new GreenSquare(game.context, game);
                                continue;
                            }
                            if (rnd < 80 * red_rnd) {
                                grid[i][j] = new RedSquare(game.context, game);
                                red_rnd = 0;
                                continue;
                            }
                            if (rnd < 92) {
                                grid[i][j] = new BlueSquare(game.context, game);
                                continue;
                            }
                            if (rnd < 100) {
                                grid[i][j] = new OrangeSquare(game.context, game);
                                continue;
                            }
                        }
                        if (game.time <= 2000){
                            // low time-gen
                            // 30% green
                            // 20 - 0% red
                            // 25 - 45% orange
                            // 25% blue
                            rnd = random.nextInt(100);
                            if (rnd < 30) {grid[i][j] = new GreenSquare(game.context, game); continue;}
                            if (rnd < 50 * red_rnd) {grid[i][j] = new RedSquare(game.context, game); red_rnd = 0; continue; }
                            if (rnd < 75) {grid[i][j] = new OrangeSquare(game.context, game); continue;}
                            if (rnd < 100) {grid[i][j] = new BlueSquare(game.context, game); continue;}
                        }
                    }
                }
            }
        }
    }

    public void resize_all_squares_to_cells(){
        for (int i = 0; i < 6; i ++){
            for (int j = 0; j < 4; j ++){
                if (grid[i][j] != null){
                    resize_square_to_cell(grid[i][j], new int[]{j, i});
                }
            }
        }
    }

    public void resize_square_to_cell(Square square, int[] coords){
        float x = 3f/95f + 23f/95f * coords[0];
        float y = 3f/168f + 23f/168f * coords[1];
        x = -x * 2 + 1;
        y = y * 2 - 1;
        square.plane.reshape(x - 40f/95f, y, x, y + 40f/168f);
    }

    
    public void draw(){
        for (int i = 0; i < 6; i ++){
            for (int j = 0; j < 4; j ++){
                if (grid[i][j] != null){
                    grid[i][j].draw();
                }
            }
        }

    }
}
