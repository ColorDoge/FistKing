package com.example.fistking.logic;

import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Wachsbeere on 2018/9/13.
 *
 * 战斗相关类
 *
 * fightingTimer的顺序：技能2-技能1-技能3
 *
 */

public class Fighting {

    private static int fightSpeedTwo = 3;  //技能2战斗动作的速度
    private static int fightSpeedOne = 3;  //技能1战斗动作的速度
    private static int fightSpeedThree = 3;  //技能3战斗动作的速度
    private static int injuredSpeed = 30;       //受伤动作持续时间

    public static int timeDivideAB = fightSpeedTwo * 8;               //fightingTimer的判断不同技能的分割线
    public static int timeDivideBC = fightSpeedOne * 8 + timeDivideAB;
//    public static int timeDivideCD = fightSpeedThree * 8 + timeDivideBC;
    public static int timeDivideCD = fightSpeedThree * 16 + timeDivideBC;
    public static int timeDivideDE = timeDivideCD + injuredSpeed;
    
    public static int setFightingMovementsTwo(ImageView viewToMove, int fightingTimer, int[] fightArray) {

        //线程切换人物位置
        RelativeLayout.LayoutParams paramsA = (RelativeLayout.LayoutParams) viewToMove.getLayoutParams();
        viewToMove.setLayoutParams(paramsA);

        if (fightingTimer > 0 && fightingTimer < fightSpeedTwo) {
            viewToMove.setImageResource(fightArray[0]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedTwo && fightingTimer < fightSpeedTwo * 2) {
            viewToMove.setImageResource(fightArray[1]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedTwo * 2 && fightingTimer < fightSpeedTwo * 3) {
            viewToMove.setImageResource(fightArray[2]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedTwo * 3 && fightingTimer < fightSpeedTwo * 4) {
            viewToMove.setImageResource(fightArray[3]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedTwo * 4 && fightingTimer < fightSpeedTwo * 5) {
            viewToMove.setImageResource(fightArray[4]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedTwo * 5 && fightingTimer < fightSpeedTwo * 6) {
            viewToMove.setImageResource(fightArray[5]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedTwo * 6 && fightingTimer < fightSpeedTwo * 7) {
            viewToMove.setImageResource(fightArray[6]);
            fightingTimer++;
        }else {
            viewToMove.setImageResource(fightArray[7]);
            fightingTimer++;
            if (fightingTimer == timeDivideAB) fightingTimer = 0;
        }
        return fightingTimer;
    }

    public static int setFightingMovementsOne(ImageView viewToMove, int fightingTimer, int direction, int[] actionOneArray) {

        //线程切换人物位置
        RelativeLayout.LayoutParams paramsA = (RelativeLayout.LayoutParams) viewToMove.getLayoutParams();
        viewToMove.setLayoutParams(paramsA);

        if (direction == 1) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[0]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[1]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[2]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[3]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[4]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[5]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[6]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[7]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 2) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[8]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[9]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[10]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[11]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[12]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[13]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[14]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[15]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 3) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[16]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[17]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[18]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[19]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[20]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[21]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[22]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[23]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 4) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[24]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[25]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[26]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[27]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[28]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[29]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[30]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[31]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 5) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[32]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[33]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[34]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[35]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[36]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[37]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[38]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[39]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 6) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[40]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[41]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[42]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[43]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[44]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[45]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[46]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[47]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 7) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[48]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[49]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[50]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[51]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[52]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[53]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[54]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[55]);
                fightingTimer++;
                if (fightingTimer == fightSpeedOne * 8 + timeDivideAB) fightingTimer = 0;
            }
        }else if (direction == 8) {
            if (fightingTimer > timeDivideAB && fightingTimer < fightSpeedOne + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[56]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne + timeDivideAB && fightingTimer < fightSpeedOne * 2 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[57]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 2 + timeDivideAB && fightingTimer < fightSpeedOne * 3 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[58]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 3 + timeDivideAB && fightingTimer < fightSpeedOne * 4 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[59]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 4 + timeDivideAB && fightingTimer < fightSpeedOne * 5 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[60]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 5 + timeDivideAB && fightingTimer < fightSpeedOne * 6 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[61]);
                fightingTimer++;
            }else if (fightingTimer >= fightSpeedOne * 6 + timeDivideAB && fightingTimer < fightSpeedOne * 7 + timeDivideAB) {
                viewToMove.setImageResource(actionOneArray[62]);
                fightingTimer++;
            }else {
                viewToMove.setImageResource(actionOneArray[63]);
                fightingTimer++;
                if (fightingTimer == timeDivideBC) fightingTimer = 0;
            }
        }else {
            fightingTimer = 0;
        }
        return fightingTimer;
    }

    public static int setFightingMovementsThree(ImageView viewToMove, int fightingTimer, int[] fightArray) {

        //线程实时切换人物位置
        RelativeLayout.LayoutParams paramsA = (RelativeLayout.LayoutParams) viewToMove.getLayoutParams();
        viewToMove.setLayoutParams(paramsA);

        if (fightingTimer > timeDivideBC && fightingTimer < fightSpeedThree + timeDivideBC) {
            viewToMove.setImageResource(fightArray[0]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree + timeDivideBC && fightingTimer < fightSpeedThree * 2 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[1]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 2 + timeDivideBC && fightingTimer < fightSpeedThree * 3 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[2]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 3 + timeDivideBC && fightingTimer < fightSpeedThree * 4 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[3]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 4 + timeDivideBC && fightingTimer < fightSpeedThree * 5 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[4]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 5 + timeDivideBC && fightingTimer < fightSpeedThree * 6 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[5]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 6 + timeDivideBC && fightingTimer < fightSpeedThree * 7 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[6]);
            fightingTimer++;
        }

//        else {
//            viewToMove.setImageResource(fightArray[7]);
//            fightingTimer++;
//            if (fightingTimer == timeDivideCD) fightingTimer = 0;
//        }

        else if (fightingTimer >= fightSpeedThree * 7 + timeDivideBC && fightingTimer < fightSpeedThree * 8 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[7]);
            fightingTimer++;
        }

        //第二次
        else if (fightingTimer >= fightSpeedThree * 8 + timeDivideBC && fightingTimer < fightSpeedThree * 9 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[0]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 9 + timeDivideBC && fightingTimer < fightSpeedThree * 10 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[1]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 10 + timeDivideBC && fightingTimer < fightSpeedThree * 11 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[2]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 11 + timeDivideBC && fightingTimer < fightSpeedThree * 12 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[3]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 12 + timeDivideBC && fightingTimer < fightSpeedThree * 13 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[4]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 13 + timeDivideBC && fightingTimer < fightSpeedThree * 14 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[5]);
            fightingTimer++;
        }else if (fightingTimer >= fightSpeedThree * 14 + timeDivideBC && fightingTimer < fightSpeedThree * 15 + timeDivideBC) {
            viewToMove.setImageResource(fightArray[6]);
            fightingTimer++;
        }
        else {
            viewToMove.setImageResource(fightArray[7]);
            fightingTimer++;
            if (fightingTimer == timeDivideCD) fightingTimer = 0;
        }
        return fightingTimer;
    }
}
