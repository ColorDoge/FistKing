package com.example.fistking.logic;

import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Wachsbeere on 2018/9/17.
 */

public class Standing {
    
    private static int standMovementSpeed = 10;     //站立动作的速度

    public static int setStandingMovement(ImageView viewStanding, int standingTimer, int[] standArray, int lastDirection) {

        //线程切换人物位置
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewStanding.getLayoutParams();
        viewStanding.setLayoutParams(params);

        if (lastDirection == 1) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[0]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[1]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[2]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[3]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[2]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[1]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 2) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[4]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[5]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[6]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[7]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[6]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[5]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 3) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[8]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[9]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[10]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[11]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[10]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[9]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 4) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[12]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[13]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[14]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[15]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[14]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[13]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 5) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[16]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[17]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[18]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[19]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[18]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[17]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 6) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[20]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[21]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[22]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[23]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[22]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[21]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 7) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[24]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[25]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[26]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[27]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[26]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[25]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else if (lastDirection == 8) {
            if (standingTimer >= 0 && standingTimer < standMovementSpeed) {
                viewStanding.setImageResource(standArray[28]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed && standingTimer < standMovementSpeed * 2) {
                viewStanding.setImageResource(standArray[29]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 2 && standingTimer < standMovementSpeed * 3) {
                viewStanding.setImageResource(standArray[30]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 3 && standingTimer < standMovementSpeed * 4) {
                viewStanding.setImageResource(standArray[31]);
                standingTimer++;
            }else if (standingTimer >= standMovementSpeed * 4 && standingTimer < standMovementSpeed * 5) {
                viewStanding.setImageResource(standArray[30]);
                standingTimer++;
            }else {
                viewStanding.setImageResource(standArray[29]);
                standingTimer++;
                if (standingTimer == standMovementSpeed * 6) standingTimer = 0;
            }
        }else {
            standingTimer = 0;
        }
        return standingTimer;
    }
}
