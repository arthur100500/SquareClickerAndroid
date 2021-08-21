package com.squareclicker.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import static android.opengl.GLES30.GL_TEXTURE0;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.glActiveTexture;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glDeleteTextures;
import static android.opengl.GLES30.glGenTextures;

public class TextureUtils {
    public static int loadTexture(Context context, int resourceId) {
        // создание объекта текстуры
        final int[] textureIds = new int[1];


        //создаем пустой массив из одного элемента
        //в этот массив OpenGL ES запишет свободный номер текстуры,
        // получаем свободное имя текстуры, которое будет записано в names[0]
        glGenTextures(1, textureIds, 0);
        if (textureIds[0] == 0) {
            return 0;
        }

        //This flag is turned on by default and should be turned off if you need a non-scaled version of the bitmap.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        // получение Bitmap
        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resourceId, options);

        if (bitmap == null) {
            glDeleteTextures(1, textureIds, 0);
            return 0;
        }

        //glActiveTexture — select active texture unit
        glActiveTexture(GL_TEXTURE0);
        //делаем текстуру с именем textureIds[0] текущей
        glBindTexture(GL_TEXTURE_2D, textureIds[0]);

        //учитываем прозрачность текстуры
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_BLEND);
        //включаем фильтры
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
        //переписываем Bitmap в память видеокарты
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        // удаляем Bitmap из памяти, т.к. картинка уже переписана в видеопамять
        bitmap.recycle();

        // сброс приязки объекта текстуры к блоку текстуры
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureIds[0];
    }
}