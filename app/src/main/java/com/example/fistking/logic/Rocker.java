package com.example.fistking.logic;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Wachsbeere on 2018/9/13.
 *
 * 摇杆相关类
 *
 */

public class Rocker {

    public static int screenSizeLimit = 220;       //屏幕移动范围限制
    private static double paiDivideEight = 0.3925;      //pai 除以8的结果
    private static int direction;                       //人物方向（8个角度）
    private static int speed = 10;                       //控制人物走动速度
    private static int stepSpeed = 7;                 //步伐速度
    private static int directionStop = 10;              //停止标识符，表示摇杆没动

    /**
     * 控制人物在屏幕范围内
     * @param x
     * @param y
     * @param tempRad
     * @param context
     * @param imageView
     * @return
     */
    public static int controlInsideScreen(float x, float y, double tempRad, Context context, final View imageView) {

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final int width = manager.getDefaultDisplay().getWidth();
        final int heigh = manager.getDefaultDisplay().getHeight();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        float imgWidth = imageView.getWidth();
        float imgHeight = imageView.getHeight();

        //控制不会被移出屏幕
        if (params.leftMargin <= 0) {
            if (x <= 0) {
                if (!((params.topMargin <= screenSizeLimit && y <= 0) || (params.topMargin >= heigh - imgHeight - screenSizeLimit && y >= 0))) {
                    computeDirection(0, y, tempRad, params);
                }
            }else {
                if ((x >= 0 && params.topMargin <= screenSizeLimit) || (x >= 0 && params.topMargin >= heigh - imgHeight - screenSizeLimit)) {
                    computeDirection(x, 0, tempRad, params);
                }else {
                    computeDirection(x, y, tempRad, params);
                }
            }
        }else if (params.leftMargin >= width - imgWidth) {
            if (x >= 0) {
                if (!((params.topMargin <= screenSizeLimit && y <= 0) || (params.topMargin >= heigh - imgHeight - screenSizeLimit && y >= 0))) {
                    computeDirection(0, y, tempRad, params);
                }
            }else {
                if ((x <= 0 && params.topMargin <= screenSizeLimit) || (x <= 0 && params.topMargin >= heigh - imgHeight - screenSizeLimit)) {
                    computeDirection(x, 0, tempRad, params);
                }else {
                    computeDirection(x, y, tempRad, params);
                }
            }
        }else if (params.topMargin <= screenSizeLimit) {
            if (y <= 0) {
                if (!((params.leftMargin <= 0 && x <= 0) || (params.leftMargin >= width - imgWidth && x >= 0))) {
                    computeDirection(x, 0, tempRad, params);
                }
            }else {
                computeDirection(x, y, tempRad, params);
            }
        }else if (params.topMargin >= heigh - imgHeight - screenSizeLimit) {
            if (y >= 0) {
                if (!((params.leftMargin <= 0 && x <= 0) || (params.leftMargin >= width - imgWidth && x >= 0))) {
                    computeDirection(x, 0, tempRad, params);
                }
            }else {
                computeDirection(x, y, tempRad, params);
            }
        }
        else if (params.leftMargin >= 0 && params.leftMargin <= width - imgWidth &&
                params.topMargin >= screenSizeLimit && params.topMargin <= heigh - imgHeight - screenSizeLimit) {
            computeDirection(x, y, tempRad, params);
        }
        return direction;
    }

    /**
     * 计算不同方向的direction
     *
     * @param x
     * @param y
     * @param tempRad
     * @param params
     */
    private static void computeDirection(float x, float y, double tempRad, RelativeLayout.LayoutParams params) {
        params.leftMargin += x / speed;
        params.topMargin += y / speed;

        if (x == 0 && y > 0) {
            direction = 3;
        }else if (x == 0 && y < 0) {
            direction = 7;
        }else if (y == 0 && x < 0) {
            direction = 5;
        }else if (y == 0 && x > 0) {
            direction = 1;
        }else if (x > 0 && y > 0) {
            if (tempRad < paiDivideEight && tempRad >= -paiDivideEight) {
                direction = 1;
            }else if (tempRad > paiDivideEight && tempRad <= paiDivideEight * 3) {
                direction = 2;
            }else if (tempRad > paiDivideEight * 3 && tempRad <= paiDivideEight * 5) {
                direction = 3;
            }
        }else if (x > 0 && y < 0) {
            if (tempRad < paiDivideEight && tempRad >= -paiDivideEight) {
                direction = 1;
            }else if (tempRad < -paiDivideEight && tempRad >= -paiDivideEight * 3) {
                direction = 8;
            }else if (tempRad < -paiDivideEight * 3 && tempRad >= -paiDivideEight * 5) {
                direction = 7;
            }
        }else if (x < 0 && y > 0) {
            if (tempRad < paiDivideEight * 5 && tempRad >= paiDivideEight * 3) {
                direction = 3;
            }else if (tempRad < paiDivideEight * 7 && tempRad >= paiDivideEight * 5) {
                direction = 4;
            }else {
                direction = 5;
            }
        }else if (x < 0 && y < 0) {
            if (tempRad > -paiDivideEight * 5 && tempRad <= -paiDivideEight * 3) {
                direction = 7;
            }else if (tempRad > -paiDivideEight * 7 && tempRad <= -paiDivideEight * 5) {
                direction = 6;
            }else {
                direction = 5;
            }
        }
    }

    /**
     * 人物不同方向下动作切换
     *
     * @param direction
     * @param viewToMove
     * @param currentStep
     * @param array
     * @return
     */
    public static int setMovements(int direction, ImageView viewToMove, int currentStep, int[] array) {

        //线程实时切换人物位置
        RelativeLayout.LayoutParams paramsA = (RelativeLayout.LayoutParams) viewToMove.getLayoutParams();
        viewToMove.setLayoutParams(paramsA);

        if (direction != directionStop) {
            if (direction == 1) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[0]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[1]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[2]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[3]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[4]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[5]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[6]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[7]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 2) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[8]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[9]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[10]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[11]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[12]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[13]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[14]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[15]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 3) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[16]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[17]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[18]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[19]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[20]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[21]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[22]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[23]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 4) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[24]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[25]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[26]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[27]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[28]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[29]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[30]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[31]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 5) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[32]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[33]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[34]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[35]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[36]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[37]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[38]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[39]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 6) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[40]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[41]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[42]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[43]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[44]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[45]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[46]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[47]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 7) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[48]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[49]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[50]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[51]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[52]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[53]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[54]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[55]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }else if (direction == 8) {
                if (currentStep >= 0 && currentStep < stepSpeed) {
                    viewToMove.setImageResource(array[56]);
                    currentStep++;
                }else if (currentStep >= stepSpeed && currentStep < stepSpeed * 2) {
                    viewToMove.setImageResource(array[57]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 2 && currentStep < stepSpeed * 3) {
                    viewToMove.setImageResource(array[58]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 3 && currentStep < stepSpeed * 4) {
                    viewToMove.setImageResource(array[59]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 4 && currentStep < stepSpeed * 5) {
                    viewToMove.setImageResource(array[60]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 5 && currentStep < stepSpeed * 6) {
                    viewToMove.setImageResource(array[61]);
                    currentStep++;
                }else if (currentStep >= stepSpeed * 6 && currentStep < stepSpeed * 7) {
                    viewToMove.setImageResource(array[62]);
                    currentStep++;
                }else {
                    viewToMove.setImageResource(array[63]);
                    currentStep++;
                    if (currentStep == stepSpeed * 8) currentStep = 0;
                }
            }
        }
        return currentStep;
    }
}
