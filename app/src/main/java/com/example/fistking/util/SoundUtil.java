package com.example.fistking.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by Wachsbeere on 2018/9/18.
 */


public class SoundUtil {

    private Context context;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;     //添加的声音资源参数

    private int soundVolType = 3;                   //声音类型，默认为多媒体
    public static final int INFINITE_PLAY = -1;     //无限循环播放
    public static final int SINGLE_PLAY = 0;        //单次播放
    public static final int RING_SOUND = 2;         //铃声音量
    public static final int MEDIA_SOUND = 3;        // 媒体音量



    /**
     * 初始化
     *
     * @param context
     * @param soundVolType 声音音量类型，默认为多媒体
     */
    public SoundUtil(Context context, int soundVolType) {
        this.context = context;
        this.soundVolType = soundVolType;
        // 初始化声音池和声音参数map
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap<>();
    }

    /**
     * 添加声音文件进声音池
     *
     * @param order 所添加声音的编号，播放的时候指定
     * @param soundRes 添加声音资源的id
     */
    public void putSound(int order, int soundRes) {
        // 上下文，声音资源id，优先级
        soundPoolMap.put(order, soundPool.load(context, soundRes, 1));
    }

    /**
     * 播放声音
     *
     * @param order 所添加声音的编号
     * @param times 循环次数，0不循环，-1循环
     */
    public void playSound(int order, int times) {
        // 实例化AudioManager对象
        AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        // 返回当前AudioManager对象播放所选声音的类型的最大音量值
        float maxVolumn = am.getStreamMaxVolume(soundVolType);
        // 返回当前AudioManager对象的音量值
        float currentVolumn = am.getStreamVolume(soundVolType);
        // 比值
        float volumnRatio = currentVolumn / maxVolumn;
//        soundPool.play(soundPoolMap.get(order), volumnRatio, volumnRatio, 1,
//                times, 1);

        soundPool.play(soundPoolMap.get(order), 1, 1, 0, 0, 1);
    }

    /**
     * 设置 soundVolType 的值
     *
     * @param soundVolType
     */
    public void setSoundVolType(int soundVolType) {
        this.soundVolType = soundVolType;
    }
}
