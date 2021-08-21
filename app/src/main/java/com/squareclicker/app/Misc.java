package com.squareclicker.app;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

//class for useful junk
public class Misc {

    static public float[] fullscreenverticies =
            {
                   -1,  1f, 0,   0, 0,
                   -1, -1,  0,   0, 1f,
                    1,  1f, 0,   1f, 0,
                    1, -1,  0,   1f, 1f,
            };
    static public float[] fullscreenverticies2D = {
            1f, -1f, 1f, 0f,
            -1f, -1f, 0f, 0f,
            -1f, 1f, 0f, 1f,

            1f, 1f, 1f, 1f,
            1f, -1f, 1f, 0f,
            -1f, 1f, 0f, 1f
    };
    public static void Log(Object message) {
        System.out.print(message);
    }

    private static boolean isdigit(char x) {
        if (x == '1' || x == '2' || x == '3' || x == '4' || x == '5' || x == '6' || x == '7' || x == '8' || x == '9' || x == '0') {
            return true;
        }
        return false;
    }

    public static void PrintShaderError(String shader, String error) {
        System.out.println("Shader compilation error!\n\n");
        String[] splitted = error.split("\n");
        String buffer = "";
        List<Integer> lines = new ArrayList<Integer>();
        for (int i = 0; i < splitted.length; i++) {
            buffer = "";
            if (splitted[i].length() > 10) {
                if (splitted[i].charAt(0) == 'E' && splitted[i].charAt(1) == 'R' && splitted[i].charAt(2) == 'R' && splitted[i].charAt(3) == 'O' && splitted[i].charAt(4) == 'R' && splitted[i].charAt(5) == ':') {
                    for (int j = 9; j < 100; j++) {
                        if (isdigit(splitted[i].charAt(i)))
                            buffer += splitted[i].charAt(i);
                        else {
                            break;
                        }
                    }
                    if (buffer != "")
                        lines.add(parseInt(buffer) - 1);
                }
            }
        }
        splitted = shader.split("\n");
        for (int i = 0; i < splitted.length; i++) {
            if (lines.contains(i)) {
                System.out.println("error! ");
            }
            System.out.println((i + 1) + " " + splitted[i]);
        }
        System.out.println(error);
    }
}

