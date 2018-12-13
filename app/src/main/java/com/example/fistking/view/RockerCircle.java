package com.example.fistking.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.fistking.control.RockerMove;

/**
 * Created by Wachsbeere on 2018/9/11.
 */

public class RockerCircle extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private String TAG = "RockerCircle";

    private Thread th;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint paint;
    private boolean flag;
    //固定摇杆背景圆形的X,Y坐标以及半径
    private int RockerCircleX = 130;
    private int RockerCircleY = 130;
    private int RockerCircleR = 100;
    //摇杆的X,Y坐标以及摇杆的半径
    private float SmallRockerCircleX = 130;
    private float SmallRockerCircleY = 130;
    private float SmallRockerCircleR = 30;
    public RockerMove rockerMove;
    private double tempRad;

    public RockerCircle(Context context, RockerMove rockerMove) {
        super(context);
        Log.v("Himi", "MySurfaceView");
        this.setKeepScreenOn(true);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.rockerMove = rockerMove;


        setZOrderOnTop(true);//使surfaceview放到最顶层
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度

    }
    public void surfaceCreated(SurfaceHolder holder) {
        th = new Thread(this);
        flag = true;
        th.start();
    }
    /***
     * 得到两点之间的弧度
     */
    public double getRad(float px1, float py1, float px2, float py2) {
        //得到两点X的距离
        float x = px2 - px1;
        //得到两点Y的距离
        float y = py1 - py2;
        //算出斜边长
        float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
        float cosAngle = x / xie;
        //通过反余弦定理获取到其角度的弧度
        float rad = (float) Math.acos(cosAngle);
        //注意：当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE) {
            // 当触屏区域不在活动范围内
            if (Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2)
                    + Math.pow((RockerCircleY - (int) event.getY()), 2)) >= RockerCircleR) {
                //得到摇杆与触屏点所形成的角度
//                double tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
                tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
                //保证内部小圆运动的长度限制
                getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
            } else {//如果小球中心点小于活动区域则随着用户触屏点移动即可
                SmallRockerCircleX = (int) event.getX();
                SmallRockerCircleY = (int) event.getY();
                tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());  //同样要计算角度
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {        //三角形triangle
            //当释放按键时摇杆要恢复摇杆的位置为初始位置
            SmallRockerCircleX = 130;
            SmallRockerCircleY = 130;
        }
        return true;
    }
    /**
     *
     * @param R
     *            圆周运动的旋转点
     * @param centerX
     *            旋转点X
     * @param centerY
     *            旋转点Y
     * @param rad
     *            旋转的弧度
     */
    public void getXY(float centerX, float centerY, float R, double rad) {
        //获取圆周运动的X坐标
        SmallRockerCircleX = (float) (R * Math.cos(rad)) + centerX;
        //获取圆周运动的Y坐标
        SmallRockerCircleY = (float) (R * Math.sin(rad)) + centerY;
    }

    public void draw() {
        try {
            canvas = sfh.lockCanvas();
//            canvas.drawColor(Color.WHITE);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
//            canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
//            canvas.drawColor(Color.alpha(0x10000000));
//            canvas.drawColor(Color.TRANSPARENT);
//            canvas.drawColor(Color.TRANSPARENT);
            //设置透明度
//            paint.setColor(0x10000000);
            paint.setColor(0x10000000);
            //绘制摇杆背景
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
            paint.setColor(0x70ff0000);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(0);
//            paint.setColor(getResources().getColor(R.color.yellow));
//            paint.setColor(0x20000000);
            //绘制摇杆
            canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY,
                    SmallRockerCircleR, paint);
            //回调
            rockerMove.move(SmallRockerCircleX - RockerCircleX, SmallRockerCircleY - RockerCircleY, tempRad);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                if (canvas != null)
                    sfh.unlockCanvasAndPost(canvas);
            } catch (Exception e2) {
            }
        }
    }
    public void run() {
        // TODO Auto-generated method stub
        while (flag) {
            draw();
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
            }
        }
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v("Himi", "surfaceChanged");
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        Log.v("Himi", "surfaceDestroyed");
    }
}

