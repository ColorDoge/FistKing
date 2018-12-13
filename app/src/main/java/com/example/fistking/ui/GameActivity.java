package com.example.fistking.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fistking.R;
import com.example.fistking.control.RockerMove;
import com.example.fistking.logic.Cooling;
import com.example.fistking.logic.Fighting;
import com.example.fistking.logic.HpMp;
import com.example.fistking.logic.Rocker;
import com.example.fistking.logic.Standing;
import com.example.fistking.util.SoundUtil;
import com.example.fistking.util.VibratorUtil;
import com.example.fistking.view.RockerCircle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.example.fistking.logic.Cooling.coolingBarHeight;
import static com.example.fistking.logic.Cooling.coolingTimerB1;
import static com.example.fistking.logic.Cooling.coolingTimerB2;
import static com.example.fistking.logic.Cooling.coolingTimerB3;
import static com.example.fistking.logic.Cooling.coolingTimerG1;
import static com.example.fistking.logic.Cooling.coolingTimerG2;
import static com.example.fistking.logic.Cooling.coolingTimerG3;
import static com.example.fistking.logic.Fighting.timeDivideAB;
import static com.example.fistking.logic.Fighting.timeDivideBC;
import static com.example.fistking.logic.Fighting.timeDivideCD;
import static com.example.fistking.logic.Fighting.timeDivideDE;
import static com.example.fistking.logic.HpMp.manicTime;
import static com.example.fistking.logic.HpMp.mpTimerB;
import static com.example.fistking.logic.HpMp.mpTimerG;
import static com.example.fistking.logic.HpMp.mpWidth;
import static com.example.fistking.logic.Rocker.controlInsideScreen;

public class GameActivity extends AppCompatActivity {

    private String TAG = "GameActivity";
    @BindView(R.id.fab_1) FloatingActionButton fab_1;
    @BindView(R.id.fab_2) FloatingActionButton fab_2;
    @BindView(R.id.fab_3) FloatingActionButton fab_3;
    @BindView(R.id.fab_4) FloatingActionButton fab_4;
    @BindView(R.id.fab_5) FloatingActionButton fab_5;
    @BindView(R.id.fab_6) FloatingActionButton fab_6;
    @BindView(R.id.image_a) ImageView image_a;
    @BindView(R.id.image_b) ImageView image_b;
    @BindView(R.id.ll_a) RelativeLayout ll_a;
    @BindView(R.id.ll_b) RelativeLayout ll_b;
    @BindView(R.id.tv_hp_a) TextView tv_hp_a;
    @BindView(R.id.tv_hp_b) TextView tv_hp_b;
    @BindView(R.id.tv_mp_a) TextView tv_mp_a;
    @BindView(R.id.tv_mp_b) TextView tv_mp_b;
    @BindView(R.id.tv_cool_b_1) TextView tv_cool_b_1;
    @BindView(R.id.tv_cool_b_2) TextView tv_cool_b_2;
    @BindView(R.id.tv_cool_b_3) TextView tv_cool_b_3;
    @BindView(R.id.tv_cool_g_1) TextView tv_cool_g_1;
    @BindView(R.id.tv_cool_g_2) TextView tv_cool_g_2;
    @BindView(R.id.tv_cool_g_3) TextView tv_cool_g_3;
    @BindView(R.id.iv_words) ImageView iv_words;
    @BindView(R.id.iv_k) ImageView iv_k;
    @BindView(R.id.iv_o) ImageView iv_o;
    @BindView(R.id.iv_quit) ImageView iv_quit;
    @BindView(R.id.iv_restart) ImageView iv_restart;
    @BindView(R.id.iv_pause) ImageView iv_pause;
    @BindView(R.id.iv_resume) ImageView iv_resume;
    @BindView(R.id.iv_menu) ImageView iv_menu;

    private Context context;
    public RockerMove rockerMoveA, rockerMoveB;

    public int direationA = 10;           //人物方向（8）
    public int direationB = 10;

    private int directionStop = 10;         //停止标识符，表示摇杆没动

    public int currentStepA = 1;         //控制人物动的效果（步伐）
    public int currentStepB = 1;

    private int fightingTimerA = 0;         //战斗动作计时器，0表示没有在战斗，1~30：技能2， 31~60：技能1
    private int fightingTimerB = 0;

    private int standingTimerA = 0;        //静止不动时的动作
    private int standingTimerB = 0;

    private int firstKickDirectionA = 0;    //攻击技能的释放时的第一个方向
    private int firstKickDirectionB = 0;

    private int lastDireationA = 1;     //人物静止前的最后一个方向（游戏起始方向）
    private int lastDireationB = 4;

    private boolean isGameOver = false;
    private boolean isGameStart = false;
    private boolean isGamePause = false;

    private int whoDied = 0;

    private boolean isGirlHot = false;      //是否处于狂躁状态
    private boolean isBoyHot = false;

    private int animTimer = 0;      //动画计时器

    private int aFirstLeft = 30;        //初始getLeft、getTop
    private int bFirstLeft = 720;
    private int aFirstTop = 1200;
    private int bFirstTop = 300;

    private static int[] girlWalkImgArray = {R.drawable.g_walk_right_1, R.drawable.g_walk_right_2, R.drawable.g_walk_right_3, R.drawable.g_walk_right_4,
            R.drawable.g_walk_right_5, R.drawable.g_walk_right_6, R.drawable.g_walk_right_7, R.drawable.g_walk_right_8, R.drawable.g_walk_front_right_1,
            R.drawable.g_walk_front_right_2, R.drawable.g_walk_front_right_3, R.drawable.g_walk_front_right_4, R.drawable.g_walk_front_right_5,
            R.drawable.g_walk_front_right_6, R.drawable.g_walk_front_right_7, R.drawable.g_walk_front_right_8, R.drawable.g_walk_front_1, R.drawable.g_walk_front_2,
            R.drawable.g_walk_front_3, R.drawable.g_walk_front_4, R.drawable.g_walk_front_5, R.drawable.g_walk_front_6, R.drawable.g_walk_front_7, R.drawable.g_walk_front_8,
            R.drawable.g_walk_front_left_1, R.drawable.g_walk_front_left_2, R.drawable.g_walk_front_left_3, R.drawable.g_walk_front_left_4,
            R.drawable.g_walk_front_left_5, R.drawable.g_walk_front_left_6, R.drawable.g_walk_front_left_7, R.drawable.g_walk_front_left_8, R.drawable.g_walk_left_1,
            R.drawable.g_walk_left_2, R.drawable.g_walk_left_3, R.drawable.g_walk_left_4, R.drawable.g_walk_left_5, R.drawable.g_walk_left_6, R.drawable.g_walk_left_7,
            R.drawable.g_walk_left_8, R.drawable.g_walk_back_left_1, R.drawable.g_walk_back_left_2, R.drawable.g_walk_back_left_3, R.drawable.g_walk_back_left_4,
            R.drawable.g_walk_back_left_5, R.drawable.g_walk_back_left_6, R.drawable.g_walk_back_left_7, R.drawable.g_walk_back_left_8, R.drawable.g_walk_back_1,
            R.drawable.g_walk_back_2, R.drawable.g_walk_back_3, R.drawable.g_walk_back_4, R.drawable.g_walk_back_5, R.drawable.g_walk_back_6, R.drawable.g_walk_back_7,
            R.drawable.g_walk_back_8, R.drawable.g_walk_back_right_1, R.drawable.g_walk_back_right_2, R.drawable.g_walk_back_right_3, R.drawable.g_walk_back_right_4,
            R.drawable.g_walk_back_right_5, R.drawable.g_walk_back_right_6, R.drawable.g_walk_back_right_7, R.drawable.g_walk_back_right_8};

    private static int[] girlFightOneArray = {R.drawable.g_fight_1_right_1, R.drawable.g_fight_1_right_2, R.drawable.g_fight_1_right_3, R.drawable.g_fight_1_right_4,
            R.drawable.g_fight_1_right_5, R.drawable.g_fight_1_right_6, R.drawable.g_fight_1_right_7, R.drawable.g_fight_1_right_8, R.drawable.g_fight_1_front_right_1,
            R.drawable.g_fight_1_front_right_2, R.drawable.g_fight_1_front_right_3, R.drawable.g_fight_1_front_right_4, R.drawable.g_fight_1_front_right_5,
            R.drawable.g_fight_1_front_right_6, R.drawable.g_fight_1_front_right_7, R.drawable.g_fight_1_front_right_8, R.drawable.g_fight_1_front_1, R.drawable.g_fight_1_front_2,
            R.drawable.g_fight_1_front_3, R.drawable.g_fight_1_front_4, R.drawable.g_fight_1_front_5, R.drawable.g_fight_1_front_6, R.drawable.g_fight_1_front_7,
            R.drawable.g_fight_1_front_8, R.drawable.g_fight_1_front_left_1, R.drawable.g_fight_1_front_left_2, R.drawable.g_fight_1_front_left_3, R.drawable.g_fight_1_front_left_4,
            R.drawable.g_fight_1_front_left_5, R.drawable.g_fight_1_front_left_6, R.drawable.g_fight_1_front_left_7, R.drawable.g_fight_1_front_left_8, R.drawable.g_fight_1_left_1,
            R.drawable.g_fight_1_left_2, R.drawable.g_fight_1_left_3, R.drawable.g_fight_1_left_4, R.drawable.g_fight_1_left_5, R.drawable.g_fight_1_left_6, R.drawable.g_fight_1_left_7,
            R.drawable.g_fight_1_left_8, R.drawable.g_fight_1_back_left_1, R.drawable.g_fight_1_back_left_2, R.drawable.g_fight_1_back_left_3, R.drawable.g_fight_1_back_left_4,
            R.drawable.g_fight_1_back_left_5, R.drawable.g_fight_1_back_left_6, R.drawable.g_fight_1_back_left_7, R.drawable.g_fight_1_back_left_8, R.drawable.g_fight_1_back_1,
            R.drawable.g_fight_1_back_2, R.drawable.g_fight_1_back_3, R.drawable.g_fight_1_back_4, R.drawable.g_fight_1_back_5, R.drawable.g_fight_1_back_6, R.drawable.g_fight_1_back_7,
            R.drawable.g_fight_1_back_8, R.drawable.g_fight_1_back_right_1, R.drawable.g_fight_1_back_right_2, R.drawable.g_fight_1_back_right_3, R.drawable.g_fight_1_back_right_4,
            R.drawable.g_fight_1_back_right_5, R.drawable.g_fight_1_back_right_6, R.drawable.g_fight_1_back_right_7, R.drawable.g_fight_1_back_right_8};

    private static int[] girlFightTwoArray = {R.drawable.g_fight_2_1, R.drawable.g_fight_2_2, R.drawable.g_fight_2_3, R.drawable.g_fight_2_4, R.drawable.g_fight_2_5,
            R.drawable.g_fight_2_6, R.drawable.g_fight_2_7, R.drawable.g_fight_2_8};

    private static int[] girlFightThreeArray = {R.drawable.g_fight_3_1, R.drawable.g_fight_3_2, R.drawable.g_fight_3_3, R.drawable.g_fight_3_4, R.drawable.g_fight_3_5,
            R.drawable.g_fight_3_6, R.drawable.g_fight_3_7, R.drawable.g_fight_3_8};

    private static int[] girlInjuredArray = {R.drawable.g_injured_right, R.drawable.g_injured_front_right, R.drawable.g_injured_front, R.drawable.g_injured_front_left,
            R.drawable.g_injured_left, R.drawable.g_injured_back_left, R.drawable.g_injured_back, R.drawable.g_injured_back_right};

    private static int[] girlStandArray = {R.drawable.g_stand_right_1, R.drawable.g_stand_right_2, R.drawable.g_stand_right_3, R.drawable.g_stand_right_4, R.drawable.g_stand_front_right_1,
            R.drawable.g_stand_front_right_2, R.drawable.g_stand_front_right_3, R.drawable.g_stand_front_right_4, R.drawable.g_stand_front_1, R.drawable.g_stand_front_2, R.drawable.g_stand_front_3, R.drawable.g_stand_front_4,
            R.drawable.g_stand_front_left_1, R.drawable.g_stand_front_left_2, R.drawable.g_stand_front_left_3, R.drawable.g_stand_front_left_4, R.drawable.g_stand_left_1, R.drawable.g_stand_left_2, R.drawable.g_stand_left_3,
            R.drawable.g_stand_left_4, R.drawable.g_stand_back_left_1, R.drawable.g_stand_back_left_2, R.drawable.g_stand_back_left_3, R.drawable.g_stand_back_left_4, R.drawable.g_stand_back_1, R.drawable.g_stand_back_2,
            R.drawable.g_stand_back_3, R.drawable.g_stand_back_4, R.drawable.g_stand_back_right_1, R.drawable.g_stand_back_right_2, R.drawable.g_stand_back_right_3, R.drawable.g_stand_back_right_4};

    private static int[] girlHotWalkImgArray = {R.drawable.g_hot_walk_right_1, R.drawable.g_hot_walk_right_2, R.drawable.g_hot_walk_right_3, R.drawable.g_hot_walk_right_4,
            R.drawable.g_hot_walk_right_5, R.drawable.g_hot_walk_right_6, R.drawable.g_hot_walk_right_7, R.drawable.g_hot_walk_right_8, R.drawable.g_hot_walk_front_right_1,
            R.drawable.g_hot_walk_front_right_2, R.drawable.g_hot_walk_front_right_3, R.drawable.g_hot_walk_front_right_4, R.drawable.g_hot_walk_front_right_5,
            R.drawable.g_hot_walk_front_right_6, R.drawable.g_hot_walk_front_right_7, R.drawable.g_hot_walk_front_right_8, R.drawable.g_hot_walk_front_1, R.drawable.g_hot_walk_front_2,
            R.drawable.g_hot_walk_front_3, R.drawable.g_hot_walk_front_4, R.drawable.g_hot_walk_front_5, R.drawable.g_hot_walk_front_6, R.drawable.g_hot_walk_front_7, R.drawable.g_hot_walk_front_8,
            R.drawable.g_hot_walk_front_left_1, R.drawable.g_hot_walk_front_left_2, R.drawable.g_hot_walk_front_left_3, R.drawable.g_hot_walk_front_left_4,
            R.drawable.g_hot_walk_front_left_5, R.drawable.g_hot_walk_front_left_6, R.drawable.g_hot_walk_front_left_7, R.drawable.g_hot_walk_front_left_8, R.drawable.g_hot_walk_left_1,
            R.drawable.g_hot_walk_left_2, R.drawable.g_hot_walk_left_3, R.drawable.g_hot_walk_left_4, R.drawable.g_hot_walk_left_5, R.drawable.g_hot_walk_left_6, R.drawable.g_hot_walk_left_7,
            R.drawable.g_hot_walk_left_8, R.drawable.g_hot_walk_back_left_1, R.drawable.g_hot_walk_back_left_2, R.drawable.g_hot_walk_back_left_3, R.drawable.g_hot_walk_back_left_4,
            R.drawable.g_hot_walk_back_left_5, R.drawable.g_hot_walk_back_left_6, R.drawable.g_hot_walk_back_left_7, R.drawable.g_hot_walk_back_left_8, R.drawable.g_hot_walk_back_1,
            R.drawable.g_hot_walk_back_2, R.drawable.g_hot_walk_back_3, R.drawable.g_hot_walk_back_4, R.drawable.g_hot_walk_back_5, R.drawable.g_hot_walk_back_6, R.drawable.g_hot_walk_back_7,
            R.drawable.g_hot_walk_back_8, R.drawable.g_hot_walk_back_right_1, R.drawable.g_hot_walk_back_right_2, R.drawable.g_hot_walk_back_right_3, R.drawable.g_hot_walk_back_right_4,
            R.drawable.g_hot_walk_back_right_5, R.drawable.g_hot_walk_back_right_6, R.drawable.g_hot_walk_back_right_7, R.drawable.g_hot_walk_back_right_8};

    private static int[] girlHotFightOneArray = {R.drawable.g_hot_fight_1_right_1, R.drawable.g_hot_fight_1_right_2, R.drawable.g_hot_fight_1_right_3, R.drawable.g_hot_fight_1_right_4,
            R.drawable.g_hot_fight_1_right_5, R.drawable.g_hot_fight_1_right_6, R.drawable.g_hot_fight_1_right_7, R.drawable.g_hot_fight_1_right_8, R.drawable.g_hot_fight_1_front_right_1,
            R.drawable.g_hot_fight_1_front_right_2, R.drawable.g_hot_fight_1_front_right_3, R.drawable.g_hot_fight_1_front_right_4, R.drawable.g_hot_fight_1_front_right_5,
            R.drawable.g_hot_fight_1_front_right_6, R.drawable.g_hot_fight_1_front_right_7, R.drawable.g_hot_fight_1_front_right_8, R.drawable.g_hot_fight_1_front_1, R.drawable.g_hot_fight_1_front_2,
            R.drawable.g_hot_fight_1_front_3, R.drawable.g_hot_fight_1_front_4, R.drawable.g_hot_fight_1_front_5, R.drawable.g_hot_fight_1_front_6, R.drawable.g_hot_fight_1_front_7,
            R.drawable.g_hot_fight_1_front_8, R.drawable.g_hot_fight_1_front_left_1, R.drawable.g_hot_fight_1_front_left_2, R.drawable.g_hot_fight_1_front_left_3, R.drawable.g_hot_fight_1_front_left_4,
            R.drawable.g_hot_fight_1_front_left_5, R.drawable.g_hot_fight_1_front_left_6, R.drawable.g_hot_fight_1_front_left_7, R.drawable.g_hot_fight_1_front_left_8, R.drawable.g_hot_fight_1_left_1,
            R.drawable.g_hot_fight_1_left_2, R.drawable.g_hot_fight_1_left_3, R.drawable.g_hot_fight_1_left_4, R.drawable.g_hot_fight_1_left_5, R.drawable.g_hot_fight_1_left_6, R.drawable.g_hot_fight_1_left_7,
            R.drawable.g_hot_fight_1_left_8, R.drawable.g_hot_fight_1_back_left_1, R.drawable.g_hot_fight_1_back_left_2, R.drawable.g_hot_fight_1_back_left_3, R.drawable.g_hot_fight_1_back_left_4,
            R.drawable.g_hot_fight_1_back_left_5, R.drawable.g_hot_fight_1_back_left_6, R.drawable.g_hot_fight_1_back_left_7, R.drawable.g_hot_fight_1_back_left_8, R.drawable.g_hot_fight_1_back_1,
            R.drawable.g_hot_fight_1_back_2, R.drawable.g_hot_fight_1_back_3, R.drawable.g_hot_fight_1_back_4, R.drawable.g_hot_fight_1_back_5, R.drawable.g_hot_fight_1_back_6, R.drawable.g_hot_fight_1_back_7,
            R.drawable.g_hot_fight_1_back_8, R.drawable.g_hot_fight_1_back_right_1, R.drawable.g_hot_fight_1_back_right_2, R.drawable.g_hot_fight_1_back_right_3, R.drawable.g_hot_fight_1_back_right_4,
            R.drawable.g_hot_fight_1_back_right_5, R.drawable.g_hot_fight_1_back_right_6, R.drawable.g_hot_fight_1_back_right_7, R.drawable.g_hot_fight_1_back_right_8};

    private static int[] girlHotFightTwoArray = {R.drawable.g_hot_fight_2_1, R.drawable.g_hot_fight_2_2, R.drawable.g_hot_fight_2_3, R.drawable.g_hot_fight_2_4, R.drawable.g_hot_fight_2_5,
            R.drawable.g_hot_fight_2_6, R.drawable.g_hot_fight_2_7, R.drawable.g_hot_fight_2_8};

    private static int[] girlHotFightThreeArray = {R.drawable.g_hot_fight_3_1, R.drawable.g_hot_fight_3_2, R.drawable.g_hot_fight_3_3, R.drawable.g_hot_fight_3_4, R.drawable.g_hot_fight_3_5,
            R.drawable.g_hot_fight_3_6, R.drawable.g_hot_fight_3_7, R.drawable.g_hot_fight_3_8};

    private static int[] girlHotInjuredArray = {R.drawable.g_hot_injured_right, R.drawable.g_hot_injured_front_right, R.drawable.g_hot_injured_front, R.drawable.g_hot_injured_front_left,
            R.drawable.g_hot_injured_left, R.drawable.g_hot_injured_back_left, R.drawable.g_hot_injured_back, R.drawable.g_hot_injured_back_right};

    private static int[] girlHotStandArray = {R.drawable.g_hot_stand_right_1, R.drawable.g_hot_stand_right_2, R.drawable.g_hot_stand_right_3, R.drawable.g_hot_stand_right_4, R.drawable.g_hot_stand_front_right_1,
            R.drawable.g_hot_stand_front_right_2, R.drawable.g_hot_stand_front_right_3, R.drawable.g_hot_stand_front_right_4, R.drawable.g_hot_stand_front_1, R.drawable.g_hot_stand_front_2, R.drawable.g_hot_stand_front_3, R.drawable.g_hot_stand_front_4,
            R.drawable.g_hot_stand_front_left_1, R.drawable.g_hot_stand_front_left_2, R.drawable.g_hot_stand_front_left_3, R.drawable.g_hot_stand_front_left_4, R.drawable.g_hot_stand_left_1, R.drawable.g_hot_stand_left_2, R.drawable.g_hot_stand_left_3,
            R.drawable.g_hot_stand_left_4, R.drawable.g_hot_stand_back_left_1, R.drawable.g_hot_stand_back_left_2, R.drawable.g_hot_stand_back_left_3, R.drawable.g_hot_stand_back_left_4, R.drawable.g_hot_stand_back_1, R.drawable.g_hot_stand_back_2,
            R.drawable.g_hot_stand_back_3, R.drawable.g_hot_stand_back_4, R.drawable.g_hot_stand_back_right_1, R.drawable.g_hot_stand_back_right_2, R.drawable.g_hot_stand_back_right_3, R.drawable.g_hot_stand_back_right_4};

    private static int[] boyWalkImgArray = {R.drawable.b_walk_right_1, R.drawable.b_walk_right_2, R.drawable.b_walk_right_3, R.drawable.b_walk_right_4,
            R.drawable.b_walk_right_5, R.drawable.b_walk_right_6, R.drawable.b_walk_right_7, R.drawable.b_walk_right_8, R.drawable.b_walk_front_right_1,
            R.drawable.b_walk_front_right_2, R.drawable.b_walk_front_right_3, R.drawable.b_walk_front_right_4, R.drawable.b_walk_front_right_5,
            R.drawable.b_walk_front_right_6, R.drawable.b_walk_front_right_7, R.drawable.b_walk_front_right_8, R.drawable.b_walk_front_1, R.drawable.b_walk_front_2,
            R.drawable.b_walk_front_3, R.drawable.b_walk_front_4, R.drawable.b_walk_front_5, R.drawable.b_walk_front_6, R.drawable.b_walk_front_7, R.drawable.b_walk_front_8,
            R.drawable.b_walk_front_left_1, R.drawable.b_walk_front_left_2, R.drawable.b_walk_front_left_3, R.drawable.b_walk_front_left_4,
            R.drawable.b_walk_front_left_5, R.drawable.b_walk_front_left_6, R.drawable.b_walk_front_left_7, R.drawable.b_walk_front_left_8, R.drawable.b_walk_left_1,
            R.drawable.b_walk_left_2, R.drawable.b_walk_left_3, R.drawable.b_walk_left_4, R.drawable.b_walk_left_5, R.drawable.b_walk_left_6, R.drawable.b_walk_left_7,
            R.drawable.b_walk_left_8, R.drawable.b_walk_back_left_1, R.drawable.b_walk_back_left_2, R.drawable.b_walk_back_left_3, R.drawable.b_walk_back_left_4,
            R.drawable.b_walk_back_left_5, R.drawable.b_walk_back_left_6, R.drawable.b_walk_back_left_7, R.drawable.b_walk_back_left_8, R.drawable.b_walk_back_1,
            R.drawable.b_walk_back_2, R.drawable.b_walk_back_3, R.drawable.b_walk_back_4, R.drawable.b_walk_back_5, R.drawable.b_walk_back_6, R.drawable.b_walk_back_7,
            R.drawable.b_walk_back_8, R.drawable.b_walk_back_right_1, R.drawable.b_walk_back_right_2, R.drawable.b_walk_back_right_3, R.drawable.b_walk_back_right_4,
            R.drawable.b_walk_back_right_5, R.drawable.b_walk_back_right_6, R.drawable.b_walk_back_right_7, R.drawable.b_walk_back_right_8};

    private static int[] boyFightOneArray = {R.drawable.b_fight_1_right_1, R.drawable.b_fight_1_right_2, R.drawable.b_fight_1_right_3, R.drawable.b_fight_1_right_4,
            R.drawable.b_fight_1_right_5, R.drawable.b_fight_1_right_6, R.drawable.b_fight_1_right_7, R.drawable.b_fight_1_right_8, R.drawable.b_fight_1_front_right_1,
            R.drawable.b_fight_1_front_right_2, R.drawable.b_fight_1_front_right_3, R.drawable.b_fight_1_front_right_4, R.drawable.b_fight_1_front_right_5,
            R.drawable.b_fight_1_front_right_6, R.drawable.b_fight_1_front_right_7, R.drawable.b_fight_1_front_right_8, R.drawable.b_fight_1_front_1, R.drawable.b_fight_1_front_2,
            R.drawable.b_fight_1_front_3, R.drawable.b_fight_1_front_4, R.drawable.b_fight_1_front_5, R.drawable.b_fight_1_front_6, R.drawable.b_fight_1_front_7,
            R.drawable.b_fight_1_front_8, R.drawable.b_fight_1_front_left_1, R.drawable.b_fight_1_front_left_2, R.drawable.b_fight_1_front_left_3, R.drawable.b_fight_1_front_left_4,
            R.drawable.b_fight_1_front_left_5, R.drawable.b_fight_1_front_left_6, R.drawable.b_fight_1_front_left_7, R.drawable.b_fight_1_front_left_8, R.drawable.b_fight_1_left_1,
            R.drawable.b_fight_1_left_2, R.drawable.b_fight_1_left_3, R.drawable.b_fight_1_left_4, R.drawable.b_fight_1_left_5, R.drawable.b_fight_1_left_6, R.drawable.b_fight_1_left_7,
            R.drawable.b_fight_1_left_8, R.drawable.b_fight_1_back_left_1, R.drawable.b_fight_1_back_left_2, R.drawable.b_fight_1_back_left_3, R.drawable.b_fight_1_back_left_4,
            R.drawable.b_fight_1_back_left_5, R.drawable.b_fight_1_back_left_6, R.drawable.b_fight_1_back_left_7, R.drawable.b_fight_1_back_left_8, R.drawable.b_fight_1_back_1,
            R.drawable.b_fight_1_back_2, R.drawable.b_fight_1_back_3, R.drawable.b_fight_1_back_4, R.drawable.b_fight_1_back_5, R.drawable.b_fight_1_back_6, R.drawable.b_fight_1_back_7,
            R.drawable.b_fight_1_back_8, R.drawable.b_fight_1_back_right_1, R.drawable.b_fight_1_back_right_2, R.drawable.b_fight_1_back_right_3, R.drawable.b_fight_1_back_right_4,
            R.drawable.b_fight_1_back_right_5, R.drawable.b_fight_1_back_right_6, R.drawable.b_fight_1_back_right_7, R.drawable.b_fight_1_back_right_8};

    private static int[] boyFightTwoArray = {R.drawable.b_fight_2_1, R.drawable.b_fight_2_2, R.drawable.b_fight_2_3, R.drawable.b_fight_2_4, R.drawable.b_fight_2_5,
            R.drawable.b_fight_2_6, R.drawable.b_fight_2_7, R.drawable.b_fight_2_8};

    private static int[] boyFightThreeArray = {R.drawable.b_fight_1_left_1, R.drawable.b_fight_1_back_left_1, R.drawable.b_fight_1_back_1, R.drawable.b_fight_1_back_right_1,
            R.drawable.b_fight_1_right_1, R.drawable.b_fight_1_front_right_1, R.drawable.b_fight_1_front_1, R.drawable.b_fight_1_front_left_1};

    private static int[] boyInjuredArray = {R.drawable.b_injured_right, R.drawable.b_injured_front_right, R.drawable.b_injured_front, R.drawable.b_injured_front_left,
            R.drawable.b_injured_left, R.drawable.b_injured_back_left, R.drawable.b_injured_back, R.drawable.b_injured_back_right};

    private static int[] boyStandArray = {R.drawable.b_stand_right_1, R.drawable.b_stand_right_2, R.drawable.b_stand_right_3, R.drawable.b_stand_right_4, R.drawable.b_stand_front_right_1,
            R.drawable.b_stand_front_right_2, R.drawable.b_stand_front_right_3, R.drawable.b_stand_front_right_4, R.drawable.b_stand_front_1, R.drawable.b_stand_front_2, R.drawable.b_stand_front_3, R.drawable.b_stand_front_4,
            R.drawable.b_stand_front_left_1, R.drawable.b_stand_front_left_2, R.drawable.b_stand_front_left_3, R.drawable.b_stand_front_left_4, R.drawable.b_stand_left_1, R.drawable.b_stand_left_2, R.drawable.b_stand_left_3,
            R.drawable.b_stand_left_4, R.drawable.b_stand_back_left_1, R.drawable.b_stand_back_left_2, R.drawable.b_stand_back_left_3, R.drawable.b_stand_back_left_4, R.drawable.b_stand_back_1, R.drawable.b_stand_back_2,
            R.drawable.b_stand_back_3, R.drawable.b_stand_back_4, R.drawable.b_stand_back_right_1, R.drawable.b_stand_back_right_2, R.drawable.b_stand_back_right_3, R.drawable.b_stand_back_right_4};

    private static int[] boyHotWalkImgArray = {R.drawable.b_hot_walk_right_1, R.drawable.b_hot_walk_right_2, R.drawable.b_hot_walk_right_3, R.drawable.b_hot_walk_right_4,
            R.drawable.b_hot_walk_right_5, R.drawable.b_hot_walk_right_6, R.drawable.b_hot_walk_right_7, R.drawable.b_hot_walk_right_8, R.drawable.b_hot_walk_front_right_1,
            R.drawable.b_hot_walk_front_right_2, R.drawable.b_hot_walk_front_right_3, R.drawable.b_hot_walk_front_right_4, R.drawable.b_hot_walk_front_right_5,
            R.drawable.b_hot_walk_front_right_6, R.drawable.b_hot_walk_front_right_7, R.drawable.b_hot_walk_front_right_8, R.drawable.b_hot_walk_front_1, R.drawable.b_hot_walk_front_2,
            R.drawable.b_hot_walk_front_3, R.drawable.b_hot_walk_front_4, R.drawable.b_hot_walk_front_5, R.drawable.b_hot_walk_front_6, R.drawable.b_hot_walk_front_7, R.drawable.b_hot_walk_front_8,
            R.drawable.b_hot_walk_front_left_1, R.drawable.b_hot_walk_front_left_2, R.drawable.b_hot_walk_front_left_3, R.drawable.b_hot_walk_front_left_4,
            R.drawable.b_hot_walk_front_left_5, R.drawable.b_hot_walk_front_left_6, R.drawable.b_hot_walk_front_left_7, R.drawable.b_hot_walk_front_left_8, R.drawable.b_hot_walk_left_1,
            R.drawable.b_hot_walk_left_2, R.drawable.b_hot_walk_left_3, R.drawable.b_hot_walk_left_4, R.drawable.b_hot_walk_left_5, R.drawable.b_hot_walk_left_6, R.drawable.b_hot_walk_left_7,
            R.drawable.b_hot_walk_left_8, R.drawable.b_hot_walk_back_left_1, R.drawable.b_hot_walk_back_left_2, R.drawable.b_hot_walk_back_left_3, R.drawable.b_hot_walk_back_left_4,
            R.drawable.b_hot_walk_back_left_5, R.drawable.b_hot_walk_back_left_6, R.drawable.b_hot_walk_back_left_7, R.drawable.b_hot_walk_back_left_8, R.drawable.b_hot_walk_back_1,
            R.drawable.b_hot_walk_back_2, R.drawable.b_hot_walk_back_3, R.drawable.b_hot_walk_back_4, R.drawable.b_hot_walk_back_5, R.drawable.b_hot_walk_back_6, R.drawable.b_hot_walk_back_7,
            R.drawable.b_hot_walk_back_8, R.drawable.b_hot_walk_back_right_1, R.drawable.b_hot_walk_back_right_2, R.drawable.b_hot_walk_back_right_3, R.drawable.b_hot_walk_back_right_4,
            R.drawable.b_hot_walk_back_right_5, R.drawable.b_hot_walk_back_right_6, R.drawable.b_hot_walk_back_right_7, R.drawable.b_hot_walk_back_right_8};

    private static int[] boyHotFightOneArray = {R.drawable.b_hot_fight_1_right_1, R.drawable.b_hot_fight_1_right_2, R.drawable.b_hot_fight_1_right_3, R.drawable.b_hot_fight_1_right_4,
            R.drawable.b_hot_fight_1_right_5, R.drawable.b_hot_fight_1_right_6, R.drawable.b_hot_fight_1_right_7, R.drawable.b_hot_fight_1_right_8, R.drawable.b_hot_fight_1_front_right_1,
            R.drawable.b_hot_fight_1_front_right_2, R.drawable.b_hot_fight_1_front_right_3, R.drawable.b_hot_fight_1_front_right_4, R.drawable.b_hot_fight_1_front_right_5,
            R.drawable.b_hot_fight_1_front_right_6, R.drawable.b_hot_fight_1_front_right_7, R.drawable.b_hot_fight_1_front_right_8, R.drawable.b_hot_fight_1_front_1, R.drawable.b_hot_fight_1_front_2,
            R.drawable.b_hot_fight_1_front_3, R.drawable.b_hot_fight_1_front_4, R.drawable.b_hot_fight_1_front_5, R.drawable.b_hot_fight_1_front_6, R.drawable.b_hot_fight_1_front_7,
            R.drawable.b_hot_fight_1_front_8, R.drawable.b_hot_fight_1_front_left_1, R.drawable.b_hot_fight_1_front_left_2, R.drawable.b_hot_fight_1_front_left_3, R.drawable.b_hot_fight_1_front_left_4,
            R.drawable.b_hot_fight_1_front_left_5, R.drawable.b_hot_fight_1_front_left_6, R.drawable.b_hot_fight_1_front_left_7, R.drawable.b_hot_fight_1_front_left_8, R.drawable.b_hot_fight_1_left_1,
            R.drawable.b_hot_fight_1_left_2, R.drawable.b_hot_fight_1_left_3, R.drawable.b_hot_fight_1_left_4, R.drawable.b_hot_fight_1_left_5, R.drawable.b_hot_fight_1_left_6, R.drawable.b_hot_fight_1_left_7,
            R.drawable.b_hot_fight_1_left_8, R.drawable.b_hot_fight_1_back_left_1, R.drawable.b_hot_fight_1_back_left_2, R.drawable.b_hot_fight_1_back_left_3, R.drawable.b_hot_fight_1_back_left_4,
            R.drawable.b_hot_fight_1_back_left_5, R.drawable.b_hot_fight_1_back_left_6, R.drawable.b_hot_fight_1_back_left_7, R.drawable.b_hot_fight_1_back_left_8, R.drawable.b_hot_fight_1_back_1,
            R.drawable.b_hot_fight_1_back_2, R.drawable.b_hot_fight_1_back_3, R.drawable.b_hot_fight_1_back_4, R.drawable.b_hot_fight_1_back_5, R.drawable.b_hot_fight_1_back_6, R.drawable.b_hot_fight_1_back_7,
            R.drawable.b_hot_fight_1_back_8, R.drawable.b_hot_fight_1_back_right_1, R.drawable.b_hot_fight_1_back_right_2, R.drawable.b_hot_fight_1_back_right_3, R.drawable.b_hot_fight_1_back_right_4,
            R.drawable.b_hot_fight_1_back_right_5, R.drawable.b_hot_fight_1_back_right_6, R.drawable.b_hot_fight_1_back_right_7, R.drawable.b_hot_fight_1_back_right_8};

    private static int[] boyHotFightTwoArray = {R.drawable.b_hot_fight_2_1, R.drawable.b_hot_fight_2_2, R.drawable.b_hot_fight_2_3, R.drawable.b_hot_fight_2_4, R.drawable.b_hot_fight_2_5,
            R.drawable.b_hot_fight_2_6, R.drawable.b_hot_fight_2_7, R.drawable.b_hot_fight_2_8};

    private static int[] boyHotFightThreeArray = {R.drawable.b_hot_fight_1_left_1, R.drawable.b_hot_fight_1_back_left_1, R.drawable.b_hot_fight_1_back_1, R.drawable.b_hot_fight_1_back_right_1,
            R.drawable.b_hot_fight_1_right_1, R.drawable.b_hot_fight_1_front_right_1, R.drawable.b_hot_fight_1_front_1, R.drawable.b_hot_fight_1_front_left_1};

    private static int[] boyHotInjuredArray = {R.drawable.b_hot_injured_right, R.drawable.b_hot_injured_front_right, R.drawable.b_hot_injured_front, R.drawable.b_hot_injured_front_left,
            R.drawable.b_hot_injured_left, R.drawable.b_hot_injured_back_left, R.drawable.b_hot_injured_back, R.drawable.b_hot_injured_back_right};

    private static int[] boyHotStandArray = {R.drawable.b_hot_stand_right_1, R.drawable.b_hot_stand_right_2, R.drawable.b_hot_stand_right_3, R.drawable.b_hot_stand_right_4, R.drawable.b_hot_stand_front_right_1,
            R.drawable.b_hot_stand_front_right_2, R.drawable.b_hot_stand_front_right_3, R.drawable.b_hot_stand_front_right_4, R.drawable.b_hot_stand_front_1, R.drawable.b_hot_stand_front_2, R.drawable.b_hot_stand_front_3, R.drawable.b_hot_stand_front_4,
            R.drawable.b_hot_stand_front_left_1, R.drawable.b_hot_stand_front_left_2, R.drawable.b_hot_stand_front_left_3, R.drawable.b_hot_stand_front_left_4, R.drawable.b_hot_stand_left_1, R.drawable.b_hot_stand_left_2, R.drawable.b_hot_stand_left_3,
            R.drawable.b_hot_stand_left_4, R.drawable.b_hot_stand_back_left_1, R.drawable.b_hot_stand_back_left_2, R.drawable.b_hot_stand_back_left_3, R.drawable.b_hot_stand_back_left_4, R.drawable.b_hot_stand_back_1, R.drawable.b_hot_stand_back_2,
            R.drawable.b_hot_stand_back_3, R.drawable.b_hot_stand_back_4, R.drawable.b_hot_stand_back_right_1, R.drawable.b_hot_stand_back_right_2, R.drawable.b_hot_stand_back_right_3, R.drawable.b_hot_stand_back_right_4};

//    private SoundUtil soundUtils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        //添加摇杆
        addRockers();
        //实时修改位置的线程A和B
        addPositionThread();
        context = this;

//        soundUtils = new SoundUtil(this, SoundUtil.MEDIA_SOUND);
//        soundUtils.putSound(0, R.raw.music1);

    }
    @OnClick(R.id.fab_1)
    public void setFab_1() {
        if (!isGamePause && !isGameOver) {
            firstKickDirectionA = direationA;
            if (direationA != directionStop) {      //静止时不可释放技能1
                ViewGroup.LayoutParams lp = tv_cool_g_1.getLayoutParams();
                if (lp.height >= coolingBarHeight) {
                    fightingTimerA = timeDivideAB + 1;
                    Cooling.decreaseCooling(tv_cool_g_1);
                }
            }
//        if (soundUtils != null) {
//            soundUtils.playSound(0, SoundUtil.SINGLE_PLAY);
//        }
        }
    }

    @OnClick(R.id.fab_2)
    public void setFab_2() {
        if (!isGamePause && !isGameOver) {
            firstKickDirectionA = direationA;
            ViewGroup.LayoutParams lp = tv_cool_g_2.getLayoutParams();
            if (lp.height >= coolingBarHeight) {
                fightingTimerA = 1;
                Cooling.decreaseCooling(tv_cool_g_2);
            }
        }
    }

    @OnClick(R.id.fab_3)
    public void setFab_3() {
        if (!isGamePause && !isGameOver) {
            ViewGroup.LayoutParams lp = tv_cool_g_3.getLayoutParams();
            if (lp.height >= coolingBarHeight) {
                ViewGroup.LayoutParams lp1 = tv_mp_a.getLayoutParams();
                if (fightingTimerA > 1 && fightingTimerA < timeDivideAB && direationA == 6 && lp1.width >= mpWidth) {       //触发狂躁
                    isGirlHot = true;
                    HpMp.decreaseMp(tv_mp_a);
                }
                fightingTimerA = timeDivideBC + 1;
                Cooling.decreaseCooling(tv_cool_g_3);
            }
        }
    }

    @OnClick(R.id.fab_4)
    public void setFab_4() {
        if (!isGamePause && !isGameOver) {
            firstKickDirectionB = direationB;
            if (direationB != directionStop) {
                ViewGroup.LayoutParams lp = tv_cool_b_1.getLayoutParams();
                if (lp.height >= coolingBarHeight) {
                    fightingTimerB = timeDivideAB + 1;
                    Cooling.decreaseCooling(tv_cool_b_1);
                }
            }
        }
    }

    @OnClick(R.id.fab_5)
    public void setFab_5() {
        if (!isGamePause && !isGameOver) {
            firstKickDirectionB = direationB;
            ViewGroup.LayoutParams lp = tv_cool_b_2.getLayoutParams();
            if (lp.height >= coolingBarHeight) {
                fightingTimerB = 1;
                Cooling.decreaseCooling(tv_cool_b_2);
            }
        }
    }

    @OnClick(R.id.fab_6)
    public void setFab_6() {
        if (!isGamePause && !isGameOver) {
            ViewGroup.LayoutParams lp = tv_cool_b_3.getLayoutParams();
            if (lp.height >= coolingBarHeight) {
                ViewGroup.LayoutParams lp1 = tv_mp_b.getLayoutParams();
                if (fightingTimerB > 1 && fightingTimerB < timeDivideAB && direationB == 6 && lp1.width >= mpWidth) {
                    isBoyHot = true;
                    HpMp.decreaseMp(tv_mp_b);
                }
                fightingTimerB = timeDivideBC + 1;
                Cooling.decreaseCooling(tv_cool_b_3);
            }
        }
    }


    @OnClick(R.id.iv_resume)
    public void setResume() {
        iv_resume.setVisibility(View.INVISIBLE);
        iv_menu.setVisibility(View.INVISIBLE);
        iv_pause.setVisibility(View.INVISIBLE);
        isGamePause = false;
    }

    @OnClick(R.id.iv_menu)
    public void setMenu() {
        finish();
    }


    @OnClick(R.id.iv_restart)
    public void setRestart() {
        isGameOver = false;
        isGameStart = false;
        iv_k.setVisibility(View.INVISIBLE);
        iv_o.setVisibility(View.INVISIBLE);
        iv_quit.setVisibility(View.INVISIBLE);
        iv_restart.setVisibility(View.INVISIBLE);

        HpMp.restartHp(tv_hp_a);
        HpMp.restartHp(tv_hp_b);
        HpMp.decreaseMp(tv_mp_a);
        HpMp.decreaseMp(tv_mp_b);

        RelativeLayout.LayoutParams paramsA = (RelativeLayout.LayoutParams) image_a.getLayoutParams();
        paramsA.leftMargin = aFirstLeft;
        paramsA.topMargin = aFirstTop;
        image_a.setLayoutParams(paramsA);

        RelativeLayout.LayoutParams paramsB = (RelativeLayout.LayoutParams) image_b.getLayoutParams();
        paramsB.leftMargin = bFirstLeft;
        paramsB.topMargin = bFirstTop;
        image_b.setLayoutParams(paramsB);

        lastDireationA = 1;
        lastDireationB = 4;
        isGirlHot = false;
        isBoyHot = false;
    }

    @OnClick(R.id.iv_quit)
    public void setQuit() {
        finish();
    }

    public void addRockers() {
        rockerMoveA = new RockerMove() {
            @Override
            public void move(float x, float y, double tempRad) {
                if (!isGameOver && !isGamePause && isGameStart) {
                    if (x == 0 && y == 0) {
                        if (direationA != directionStop) {
                            lastDireationA = direationA;
                        }
                        direationA = directionStop;
                    }else {
                        lastDireationA = 0;
                        if (fightingTimerA > timeDivideAB && fightingTimerA <= timeDivideBC) {          //技能1施放阶段不可移动

                        }else if (fightingTimerA > timeDivideCD && fightingTimerA <= timeDivideDE) {        //受伤阶段不可移动

                        }else {
                            if (isGameStart) {
                                direationA = controlInsideScreen(x, y, tempRad, context, image_a);
                            }
                        }
                    }
                }
            }
        };
        RockerCircle rockerCircleA = new RockerCircle(this, rockerMoveA);
        //添加到容器
        ll_a.addView(rockerCircleA);

        rockerMoveB = new RockerMove() {
            @Override
            public void move(float x, float y, double tempRad) {
                if (x == 0 && y == 0) {
                    if (direationB != directionStop) {
                        lastDireationB = direationB;
                    }
                    direationB = directionStop;
                }else {
                    lastDireationB = 0;
                    if (fightingTimerB > timeDivideAB && fightingTimerB <= timeDivideBC) {          //技能1施放阶段不可移动

                    }else if (fightingTimerB > timeDivideCD && fightingTimerB <= timeDivideDE) {        //受伤阶段不可移动

                    }else {
                        if (isGameStart) {
                            direationB = controlInsideScreen(x, y, tempRad, context, image_b);
                        }
                    }
                }
            }
        };
        RockerCircle rockerCircleB = new RockerCircle(this, rockerMoveB);
        //添加到容器
        ll_b.addView(rockerCircleB);
    }

    //单个线程内控制
    public void GgameLogicInAThread() {
        if (fightingTimerA == 0) {
            if (direationA == directionStop) {      //静止动作
                standingTimerA = Standing.setStandingMovement(image_a, standingTimerA, girlStandArray, lastDireationA);
            }else {             //正常行走
                currentStepA = Rocker.setMovements(direationA, image_a, currentStepA, girlWalkImgArray);
            }
        }else if(fightingTimerA > 0 && fightingTimerA <= timeDivideAB) {        //技能2
            fightingTimerA = Fighting.setFightingMovementsTwo(image_a, fightingTimerA, girlFightTwoArray);
            if (fightingTimerA == timeDivideAB - 1) {       //动作结束结算对方血量
                showInjuredOrDead(2, HpMp.computeDamage(image_a, image_b, 0, fightingTimerB > timeDivideBC && fightingTimerB <= timeDivideCD, tv_hp_b, isGirlHot, isBoyHot));
            }
        }else if(fightingTimerA > timeDivideAB && fightingTimerA <= timeDivideBC) {         //技能1
            fightingTimerA = Fighting.setFightingMovementsOne(image_a, fightingTimerA, direationA, girlFightOneArray);
            if (fightingTimerA == timeDivideBC - 1) {
                showInjuredOrDead(2, HpMp.computeDamage(image_a, image_b, direationA, fightingTimerB > timeDivideBC && fightingTimerB <= timeDivideCD, tv_hp_b, isGirlHot, isBoyHot));
            }
        }else if(fightingTimerA > timeDivideBC && fightingTimerA <= timeDivideCD) {         //技能3
            fightingTimerA = Fighting.setFightingMovementsThree(image_a, fightingTimerA, girlFightThreeArray);
        }else {     //受伤反馈
            if (lastDireationB == 0) {
                fightingTimerA = HpMp.setInjuredAction(image_a, fightingTimerA, firstKickDirectionB + 4 > 8 ? firstKickDirectionB - 4 : firstKickDirectionB + 4, girlInjuredArray);
            }else {
                fightingTimerA = HpMp.setInjuredAction(image_a, fightingTimerA, lastDireationB + 4 > 8 ? lastDireationB - 4 : lastDireationB + 4, girlInjuredArray);
            }
        }
    }

    public void BgameLogicInAThread() {
        if (fightingTimerB == 0) {
            if (direationB == directionStop) {
                standingTimerB = Standing.setStandingMovement(image_b, standingTimerB, boyStandArray, lastDireationB);
            }else {
                currentStepB = Rocker.setMovements(direationB, image_b, currentStepB, boyWalkImgArray);
            }
        }else if(fightingTimerB > 0 && fightingTimerB <= timeDivideAB) {
            fightingTimerB = Fighting.setFightingMovementsTwo(image_b, fightingTimerB, boyFightTwoArray);
            if (fightingTimerB == timeDivideAB - 1) {
                showInjuredOrDead(1, HpMp.computeDamage(image_b, image_a, 0, fightingTimerA > timeDivideBC && fightingTimerA <= timeDivideCD, tv_hp_a, isBoyHot, isGirlHot));
            }
        }else if(fightingTimerB > timeDivideAB && fightingTimerB <= timeDivideBC) {
            fightingTimerB = Fighting.setFightingMovementsOne(image_b, fightingTimerB, direationB, boyFightOneArray);
            if (fightingTimerB == timeDivideBC - 1) {
                showInjuredOrDead(1, HpMp.computeDamage(image_b, image_a, direationB, fightingTimerA > timeDivideBC && fightingTimerA <= timeDivideCD, tv_hp_a, isBoyHot, isGirlHot));
            }
        }else if (fightingTimerB > timeDivideBC && fightingTimerB <= timeDivideCD){
            fightingTimerB = Fighting.setFightingMovementsThree(image_b, fightingTimerB, boyFightThreeArray);
        }else {
            if (lastDireationA == 0) {
                fightingTimerB = HpMp.setInjuredAction(image_b, fightingTimerB, firstKickDirectionA + 4 > 8 ? firstKickDirectionA - 4 : firstKickDirectionA + 4, boyInjuredArray);
            }else {
                fightingTimerB = HpMp.setInjuredAction(image_b, fightingTimerB, lastDireationA + 4 > 8 ? lastDireationA - 4 : lastDireationA + 4, boyInjuredArray);
            }
        }
    }

    public void showInjuredOrDead(int girlOrBoy, int result) {
        if (result == 2) {                  //死亡
            isGameOver = true;
            if (girlOrBoy == 2) {
                whoDied = 2;
            }else {
                whoDied = 1;
            }
            VibratorUtil.Vibrate(this, 2000);
        }else if (result == 1){              //受伤
            if (girlOrBoy == 2) {
                fightingTimerB = timeDivideCD + 1;
                if (!isGirlHot) {
                    mpTimerG = HpMp.increaseMp(2, tv_mp_a, mpTimerG);
                }
                VibratorUtil.Vibrate(this, 100);   //震动100ms
            }else {
                fightingTimerA = timeDivideCD + 1;
                if (!isBoyHot) {
                    mpTimerB = HpMp.increaseMp(2, tv_mp_b, mpTimerB);
                }
                VibratorUtil.Vibrate(this, 100);
            }
        }
    }

    public void updateCooling() {
        coolingTimerB1 = Cooling.increaseCooling(1, tv_cool_b_1, coolingTimerB1);
        coolingTimerB2 = Cooling.increaseCooling(2, tv_cool_b_2, coolingTimerB2);
        coolingTimerB3 = Cooling.increaseCooling(3, tv_cool_b_3, coolingTimerB3);
        coolingTimerG1 = Cooling.increaseCooling(1, tv_cool_g_1, coolingTimerG1);
        coolingTimerG2 = Cooling.increaseCooling(2, tv_cool_g_2, coolingTimerG2);
        coolingTimerG3 = Cooling.increaseCooling(3, tv_cool_g_3, coolingTimerG3);

        if (isGameStart) {
            if (!isGirlHot) {
                mpTimerG = HpMp.increaseMp(1, tv_mp_a, mpTimerG);
            }else {
                mpTimerG++;
                if (mpTimerG == manicTime) {
                    isGirlHot = false;
                    mpTimerG = 0;
                }
            }

            if (!isBoyHot) {
                mpTimerB = HpMp.increaseMp(1, tv_mp_b, mpTimerB);
            }else {
                mpTimerB++;
                if (mpTimerB == manicTime) {
                    isBoyHot = false;
                    mpTimerB = 0;
                }
            }
        }
    }

    public void addPositionThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        while(true) {
                            wait(10);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isGameStart) {
                                        setBeginAnim();
                                    }
                                    if (!isGamePause) {
                                        if (!isGameOver) {
                                            if (!isGirlHot) {
                                                GgameLogicInAThread();
                                            }else {
                                                GHotGameLogicInAThread();
                                            }
                                            if (!isBoyHot) {
                                                BgameLogicInAThread();
                                            }else {
                                                BHotGameLogicInAThread();
                                            }
                                            updateCooling();
                                        }else {
                                            if (whoDied == 2) {
                                                image_a.setImageResource(R.drawable.g_victory);
                                                image_b.setImageResource(R.drawable.b_defeat);
                                            }else {
                                                image_a.setImageResource(R.drawable.g_defeat);
                                                image_b.setImageResource(R.drawable.b_victory);
                                            }
                                            setGameOverAnim();
                                        }
                                    }
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

//        if (isGamePause) {
//            iv_pause.setVisibility(View.INVISIBLE);
//            iv_resume.setVisibility(View.INVISIBLE);
//            iv_menu.setVisibility(View.INVISIBLE);
//            isGamePause = false;
//        }

        if (isGameStart && !isGameOver) {
            isGamePause = true;
            iv_pause.setVisibility(View.VISIBLE);
            iv_resume.setVisibility(View.VISIBLE);
            iv_menu.setVisibility(View.VISIBLE);
        }
    }

    public void setBeginAnim() {
        animTimer++;
//        iv_words.setVisibility(View.VISIBLE);
        if (animTimer == 50) {
//            iv_words.setVisibility(View.VISIBLE);
            iv_words.setImageResource(R.drawable.countdown_three);
//            iv_words.setVisibility(View.VISIBLE);
        }else if (animTimer == 200) {
            iv_words.setImageResource(R.drawable.countdown_two);
        }else if (animTimer == 350) {
            iv_words.setImageResource(R.drawable.countdown_one);
        }else if (animTimer == 500) {
            iv_words.setImageResource(R.drawable.countdown_go);
        }else if (animTimer == 550) {
//            iv_words.setVisibility(View.INVISIBLE);
            iv_words.setImageResource(R.drawable.words_empty);
//            iv_words.setBackgroundColor(getResources().getColor(R.color.transparent));
            animTimer = 0;
            isGameStart = true;
        }
    }

    public void setGameOverAnim() {

        animTimer++;
        if (whoDied == 1) {
            if (animTimer == 50) {
                iv_k.setImageResource(R.drawable.gameover_k_b);
                iv_k.setVisibility(View.VISIBLE);
            }else if (animTimer == 100) {
                iv_o.setImageResource(R.drawable.gameover_o_b);
                iv_o.setVisibility(View.VISIBLE);
                animTimer = 0;
                iv_quit.setVisibility(View.VISIBLE);
                iv_restart.setVisibility(View.VISIBLE);
            }
        }else {
            if (animTimer == 50) {
                iv_k.setImageResource(R.drawable.gameover_k_g);
                iv_k.setVisibility(View.VISIBLE);
            }else if (animTimer == 100) {
                iv_o.setImageResource(R.drawable.gameover_o_g);
                iv_o.setVisibility(View.VISIBLE);
                animTimer = 0;
                iv_quit.setVisibility(View.VISIBLE);
                iv_restart.setVisibility(View.VISIBLE);
            }
        }
    }

    public void GHotGameLogicInAThread() {
        if (fightingTimerA == 0) {
            if (direationA == directionStop) {
                standingTimerA = Standing.setStandingMovement(image_a, standingTimerA, girlHotStandArray, lastDireationA);
            }else {
                currentStepA = Rocker.setMovements(direationA, image_a, currentStepA, girlHotWalkImgArray);
            }
        }else if(fightingTimerA > 0 && fightingTimerA <= timeDivideAB) {
            fightingTimerA = Fighting.setFightingMovementsTwo(image_a, fightingTimerA, girlHotFightTwoArray);
            if (fightingTimerA == timeDivideAB - 1) {
                showInjuredOrDead(2, HpMp.computeDamage(image_a, image_b, 0, fightingTimerB > timeDivideBC && fightingTimerB <= timeDivideCD, tv_hp_b, isGirlHot, isBoyHot));
            }
        }else if(fightingTimerA > timeDivideAB && fightingTimerA <= timeDivideBC) {
            fightingTimerA = Fighting.setFightingMovementsOne(image_a, fightingTimerA, direationA, girlHotFightOneArray);
            if (fightingTimerA == timeDivideBC - 1) {
                showInjuredOrDead(2, HpMp.computeDamage(image_a, image_b, direationA, fightingTimerB > timeDivideBC && fightingTimerB <= timeDivideCD, tv_hp_b, isGirlHot, isBoyHot));
            }
        }else if(fightingTimerA > timeDivideBC && fightingTimerA <= timeDivideCD) {
            fightingTimerA = Fighting.setFightingMovementsThree(image_a, fightingTimerA, girlHotFightThreeArray);
        }else {
            if (lastDireationB == 0) {
                fightingTimerA = HpMp.setInjuredAction(image_a, fightingTimerA, firstKickDirectionB + 4 > 8 ? firstKickDirectionB - 4 : firstKickDirectionB + 4, girlHotInjuredArray);
            }else {
                fightingTimerA = HpMp.setInjuredAction(image_a, fightingTimerA, lastDireationB + 4 > 8 ? lastDireationB - 4 : lastDireationB + 4, girlHotInjuredArray);
            }
        }
    }

    public void BHotGameLogicInAThread() {
        if (fightingTimerB == 0) {
            if (direationB == directionStop) {
                standingTimerB = Standing.setStandingMovement(image_b, standingTimerB, boyHotStandArray, lastDireationB);
            }else {
                currentStepB = Rocker.setMovements(direationB, image_b, currentStepB, boyHotWalkImgArray);
            }
        }else if(fightingTimerB > 0 && fightingTimerB <= timeDivideAB) {
            fightingTimerB = Fighting.setFightingMovementsTwo(image_b, fightingTimerB, boyHotFightTwoArray);
            if (fightingTimerB == timeDivideAB - 1) {
                showInjuredOrDead(1, HpMp.computeDamage(image_b, image_a, 0, fightingTimerA > timeDivideBC && fightingTimerA <= timeDivideCD, tv_hp_a, isBoyHot, isGirlHot));
            }
        }else if(fightingTimerB > timeDivideAB && fightingTimerB <= timeDivideBC) {
            fightingTimerB = Fighting.setFightingMovementsOne(image_b, fightingTimerB, direationB, boyHotFightOneArray);
            if (fightingTimerB == timeDivideBC - 1) {
                showInjuredOrDead(1, HpMp.computeDamage(image_b, image_a, direationB, fightingTimerA > timeDivideBC && fightingTimerA <= timeDivideCD, tv_hp_a, isBoyHot, isGirlHot));
            }
        }else if (fightingTimerB > timeDivideBC && fightingTimerB <= timeDivideCD){
            fightingTimerB = Fighting.setFightingMovementsThree(image_b, fightingTimerB, boyHotFightThreeArray);
        }else {
            if (lastDireationA == 0) {
                fightingTimerB = HpMp.setInjuredAction(image_b, fightingTimerB, firstKickDirectionA + 4 > 8 ? firstKickDirectionA - 4 : firstKickDirectionA + 4, boyHotInjuredArray);
            }else {
                fightingTimerB = HpMp.setInjuredAction(image_b, fightingTimerB, lastDireationA + 4 > 8 ? lastDireationA - 4 : lastDireationA + 4, boyHotInjuredArray);
            }
        }
    }
}
