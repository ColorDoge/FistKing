package com.example.fistking.logic;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fistking.R;

/**
 * Created by Wachsbeere on 2018/9/16.
 */

public class Cooling {

    public static int coolingBarHeight = 90;        //冷却条高度

    public static int coolingTimerG1 = 0;             //冷却计数器
    public static int coolingTimerG2 = 0;
    public static int coolingTimerG3 = 0;

    public static int coolingTimerB1 = 0;
    public static int coolingTimerB2 = 0;
    public static int coolingTimerB3 = 0;


    public static void decreaseCooling(TextView coolingBar) {
        ViewGroup.LayoutParams lp = coolingBar.getLayoutParams();
        lp.height = 0;
        coolingBar.setLayoutParams(lp);
        coolingBar.setBackgroundColor(coolingBar.getResources().getColor(R.color.grey));
    }

    public static int increaseCooling(int type, TextView coolingBar, int coolingTimer) {
        ViewGroup.LayoutParams lp = coolingBar.getLayoutParams();
        if (lp.height < coolingBarHeight) {
            if (type == 1 && coolingTimer % 2 == 0) {
                lp.height += 3;
            }else if (type == 2 && coolingTimer % 3 == 0) {
                lp.height += 2;
            }else if (type == 3 && coolingTimer % 5 == 0){
                lp.height += 2;
            }
        }else {
            if (type == 1) {
                coolingBar.setBackgroundColor(coolingBar.getResources().getColor(R.color.dodgerblue));
            }else if (type == 2) {
                coolingBar.setBackgroundColor(coolingBar.getResources().getColor(R.color.colorAccent));
            }else {
                coolingBar.setBackgroundColor(coolingBar.getResources().getColor(R.color.mediumspringgreen));
            }
        }
        coolingBar.setLayoutParams(lp);
        coolingTimer++;
        if (coolingTimer == 100) {
            coolingTimer = 0;
        }
        return coolingTimer;
    }
}
