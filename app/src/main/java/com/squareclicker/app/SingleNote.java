package com.squareclicker.app;

public class SingleNote {
    public Plane plane;
    public int time_ms;
    public float screen_pos;

    public static SingleNote LoadFromText(String text){
        SingleNote result = new SingleNote();
        //256,324,5204,1,0,0:0:0:0:
        //X,n,Time,n,n,n,n,n
        String[] data = text.split(",");
        result.screen_pos = 0.1f + Integer.parseInt(data[0]) / 512f * 0.8f;
        result.time_ms = Integer.parseInt(data[2]);
        return result;
    }
}
