package com.example.fistking.logic;

import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.fistking.logic.Fighting.timeDivideCD;
import static com.example.fistking.logic.Fighting.timeDivideDE;
import static com.example.fistking.logic.Rocker.screenSizeLimit;

/**
 * Created by Wachsbeere on 2018/9/16.
 */

public class HpMp {

    private static int damageFab1 = 20;  //技能1的伤害
    private static int damageFab2 = 40;  //技能2的伤害

    private static int verticalDamageRange = 250;   //纵向杀伤距离
    private static int horizontalDamageRange = 250;   //纵向杀伤距离
    private static int visionTolerance = 50;        //横纵视觉误差

    private static int repelDistance = 7;           //击退距离

    public static int mpWidth = 600;                //蓝条长度（200dp）

    public static int mpTimerG = 0;             //蓝条计数器
    public static int mpTimerB = 0;

    public static int manicTime = 1000;                  //大招持续时间

    public static void decreaseMp(TextView mpBar) {
        ViewGroup.LayoutParams lp = mpBar.getLayoutParams();
        lp.width = 0;
        mpBar.setLayoutParams(lp);
    }

    /**
     * 增加蓝量
     *
     * @param type 1：自然增加 2： 攻击增加
     * @param mpBar
     * @param mpTimer
     * @return
     */

    public static int increaseMp(int type, TextView mpBar, int mpTimer) {
        ViewGroup.LayoutParams lp = mpBar.getLayoutParams();
        if (lp.width < mpWidth) {
            if (type == 1 && mpTimer % 3 == 0) {
                lp.width += 1;
            }else if (type == 2) {
                lp.width += 50;
            }
        }
        mpBar.setLayoutParams(lp);
        mpTimer++;
        if (mpTimer == 100) {
            mpTimer = 0;
        }
        return mpTimer;
    }

    /**
     * 计算伤害值
     *
     * @param attacker
     * @param victim
     * @param direction
     * @param isProtecting
     * @param victimTextView
     *
     * @return 0：没有造成伤害 1：造成伤害未死亡 2：造成伤害并死亡
     */
    public static int computeDamage(ImageView attacker, ImageView victim, int direction, boolean isProtecting, TextView victimTextView, boolean isAttackerHot, boolean isVictimHot) {
        RelativeLayout.LayoutParams paramsAttacker = (RelativeLayout.LayoutParams) attacker.getLayoutParams();
        RelativeLayout.LayoutParams paramsVictim = (RelativeLayout.LayoutParams) victim.getLayoutParams();

        int damageFab1Arg = damageFab1;
        int damageFab2Arg = damageFab2;
        if (isAttackerHot && !isVictimHot) {
            damageFab1Arg *= 2;
            damageFab2Arg *= 2;
        }else if (!isAttackerHot && isVictimHot) {
            damageFab1Arg /= 2;
            damageFab2Arg /= 2;
        }

        //技能2（正方形范围伤害）
        if (direction == 0 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < horizontalDamageRange && paramsAttacker.leftMargin - paramsVictim.leftMargin > -horizontalDamageRange)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < verticalDamageRange && paramsAttacker.topMargin - paramsVictim.topMargin > -verticalDamageRange)) {
                return updateMp(damageFab2Arg, victimTextView);
            }
        }

        //技能1
        if (direction == 1 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < 0 && paramsAttacker.leftMargin - paramsVictim.leftMargin > -horizontalDamageRange)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < visionTolerance && paramsAttacker.topMargin - paramsVictim.topMargin > -visionTolerance)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 2 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < -visionTolerance && paramsAttacker.leftMargin - paramsVictim.leftMargin > -horizontalDamageRange)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < -visionTolerance && paramsAttacker.topMargin - paramsVictim.topMargin > -verticalDamageRange)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 3 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < visionTolerance && paramsAttacker.leftMargin - paramsVictim.leftMargin > -visionTolerance)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < 0 && paramsAttacker.topMargin - paramsVictim.topMargin > -verticalDamageRange)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 4 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < horizontalDamageRange && paramsAttacker.leftMargin - paramsVictim.leftMargin > visionTolerance)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < -visionTolerance && paramsAttacker.topMargin - paramsVictim.topMargin > -verticalDamageRange)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 5 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < horizontalDamageRange && paramsAttacker.leftMargin - paramsVictim.leftMargin > 0)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < visionTolerance && paramsAttacker.topMargin - paramsVictim.topMargin > -visionTolerance)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 6 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < horizontalDamageRange && paramsAttacker.leftMargin - paramsVictim.leftMargin > visionTolerance)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < verticalDamageRange && paramsAttacker.topMargin - paramsVictim.topMargin > visionTolerance)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 7 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < visionTolerance && paramsAttacker.leftMargin - paramsVictim.leftMargin > -visionTolerance)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < verticalDamageRange && paramsAttacker.topMargin - paramsVictim.topMargin > 0)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }else if (direction == 8 && !isProtecting) {
            if ((paramsAttacker.leftMargin - paramsVictim.leftMargin < -visionTolerance && paramsAttacker.leftMargin - paramsVictim.leftMargin > -horizontalDamageRange)
                    && (paramsAttacker.topMargin - paramsVictim.topMargin < verticalDamageRange && paramsAttacker.topMargin - paramsVictim.topMargin > visionTolerance)) {
                return updateMp(damageFab1Arg, victimTextView);
            }
        }
        return 0;
    }

    /**
     * 设置受伤的动作
     *
     * @param viewToMove
     * @param fightingTimer
     * @param direction
     * @param injuredArray
     * @return
     */
    public static int setInjuredAction(ImageView viewToMove, int fightingTimer, int direction, int[] injuredArray) {

        WindowManager manager = (WindowManager) viewToMove.getContext().getSystemService(Context.WINDOW_SERVICE);
        final int width = manager.getDefaultDisplay().getWidth();
        final int heigh = manager.getDefaultDisplay().getHeight();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewToMove.getLayoutParams();
        float imgWidth = viewToMove.getWidth();
        float imgHeight = viewToMove.getHeight();
        int x = 0;
        int y = 0;
        if (direction == 1) {
            x = -repelDistance;
            y = 0;
        }else if (direction == 2) {
            x = -repelDistance;
            y = -repelDistance;
        }else if (direction == 3) {
            x = 0;
            y = -repelDistance;
        }else if (direction == 4) {
            x = repelDistance;
            y = -repelDistance;
        }else if (direction == 5) {
            x = repelDistance;
            y = 0;
        }else if (direction == 6) {
            x = repelDistance;
            y = repelDistance;
        }else if (direction == 7) {
            x = 0;
            y = repelDistance;
        }else if (direction == 8) {
            x = -repelDistance;
            y = repelDistance;
        }
        //线程切换人物位置
        params.leftMargin += x;
        params.topMargin += y;
        if (params.leftMargin <= 0) {
            params.leftMargin = 0;
        }else if (params.leftMargin >= width - imgWidth) {
            params.leftMargin = (int)(width - imgWidth);
        }

        if (params.topMargin <= screenSizeLimit) {
            params.topMargin = screenSizeLimit;
        }else if (params.topMargin >= heigh - imgHeight - screenSizeLimit) {
            params.topMargin = (int)(heigh - imgHeight - screenSizeLimit);
        }
        viewToMove.setLayoutParams(params);
        if (direction == 1) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[0]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else if (direction == 2) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[1]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else if (direction == 3) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[2]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else if (direction == 4) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[3]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else if (direction == 5) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[4]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else if (direction == 6) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[5]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else if (direction == 7) {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[6]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }else {
            if (fightingTimer > timeDivideCD && fightingTimer <= timeDivideDE) {
                viewToMove.setImageResource(injuredArray[7]);
                fightingTimer++;
                if (fightingTimer == timeDivideDE) fightingTimer = 0;
            }
        }
        return fightingTimer;
    }

    private static int updateMp(int lostMp, TextView victimTextView) {
        final ViewGroup.LayoutParams lp = victimTextView.getLayoutParams();
        lp.width -= lostMp;
        victimTextView.setLayoutParams(lp);
        return lp.width <= 0 ? 2 : 1;
    }

    public static void restartHp(TextView hpBar) {
        final ViewGroup.LayoutParams lp = hpBar.getLayoutParams();
        lp.width = mpWidth;
        hpBar.setLayoutParams(lp);
    }
}
