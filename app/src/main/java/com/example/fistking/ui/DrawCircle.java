package com.example.fistking.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Wachsbeere on 2018/9/11.
 */

public class DrawCircle extends View {

    //圆的初始位置
    private int x = 100;
    private int y = 100;
    Context context;

    /**
     * 有style资源文件时调用
     * @param context
     * @param attrs
     * @param defStyle
     */
    public DrawCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }
    /**
     * xml创建时调用
     * @param context
     * @param attrs
     */
    public DrawCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    /**
     * java代码创建时调用
     * @param context
     */
    public DrawCircle(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        //绘制圆
        //cx :圆心的x坐标
        //cy :圆心的y坐标
        //radius ：圆的半径
        //paint ：画笔
        canvas.drawCircle(x, y, 20, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:
                // 获取当前触摸点的x,y坐标

                x = (int) event.getX();
                y = (int) event.getY();
                break;
        }
        //获取屏幕宽高
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        int heigh = manager.getDefaultDisplay().getHeight();

        //重新绘制圆 ,控制小球不会被移出屏幕
        if(x>=20 && y>=20 && x<=width-20 && y<=heigh-90){
            invalidate();
        }
        // 自己处理触摸事件
        return true;
    }
}
