package com.squareclicker.app;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import java.util.HashMap;

public class SoundManagement {


    static SoundPool mSoundPool;
    static HashMap<Integer, Integer> mSoundMap;

    public static void init(Context context){
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        mSoundMap = new HashMap<Integer, Integer>();

        if(mSoundPool != null){
            //squares
            mSoundMap.put(R.raw.greensound, mSoundPool.load(context, R.raw.greensound, 1));
            mSoundMap.put(R.raw.redsound, mSoundPool.load(context, R.raw.redsound, 1));
            mSoundMap.put(R.raw.bluesound, mSoundPool.load(context, R.raw.bluesound, 1));
            mSoundMap.put(R.raw.missclick, mSoundPool.load(context, R.raw.missclick, 1));
            mSoundMap.put(R.raw.orange1, mSoundPool.load(context, R.raw.orange1, 1));
            mSoundMap.put(R.raw.orange2, mSoundPool.load(context, R.raw.orange2, 1));
            mSoundMap.put(R.raw.orange3, mSoundPool.load(context, R.raw.orange3, 1));
            //ui and effects
            mSoundMap.put(R.raw.zvukiprajeniya, mSoundPool.load(context, R.raw.zvukiprajeniya, 1));
            mSoundMap.put(R.raw.startsound, mSoundPool.load(context, R.raw.startsound, 1));
        }
    }

    public static void playSound(Context context, int sound) {
        AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;

        if(mSoundPool != null){
            mSoundPool.play(mSoundMap.get(sound), volume, volume, 1, 0, 1.0f);
        }
    }
}
