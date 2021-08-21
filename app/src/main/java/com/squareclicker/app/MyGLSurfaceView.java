package com.squareclicker.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class MyGLSurfaceView extends GLSurfaceView
{
    private GLRenderer mRenderer;
    int screenWidth;
    int screenHeight;
    int recalc_val = 0;
    Context context;

    public MyGLSurfaceView(Context context)
    {
        super(context);
        this.context = context;
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
        screenHeight += getNavigationBarHeight();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event != null)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                mRenderer.game.touch_coord = new float[]{event.getX() / screenWidth, 1.0f - event.getY() / screenHeight};
                mRenderer.game.touch_update = true;
                InfoExchanger.touch_number += 1;
                System.out.println(screenWidth + " " + screenHeight);
            }
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                mRenderer.game.touch_coord = new float[]{event.getX() / screenWidth, event.getY() / screenHeight};
                InfoExchanger.touch_number -= 1;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE)
            {
                mRenderer.game.touch_coord = new float[]{event.getX() / screenWidth, event.getY() / screenHeight};
                InfoExchanger.touch_number += 1;
            }
        }
        return true;
    }

    // Hides superclass method.
    public void setRenderer(GLRenderer renderer)
    {
        mRenderer = renderer;
        super.setRenderer(renderer);
    }


    public boolean isShowNavigationBar(Resources resources)
    {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    private int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }
}