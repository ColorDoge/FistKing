package com.example.fistking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.fistking.ui.GameActivity;
import com.example.fistking.ui.InformationDialogFragment;
import com.example.fistking.ui.LearnActivity;
import com.example.fistking.ui.MainDialogFragment;
import com.example.fistking.ui.NotificationDialogFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wachsbeere(J.Y.Young) on 2018/9/22.
 *
 * 谨以此纪念我和女票的2周年纪念日
 */


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_quit_game) ImageView iv_quit_game;
    @BindView(R.id.iv_learn) ImageView iv_learn;
    @BindView(R.id.iv_play) ImageView iv_play;
//    @BindView(R.id.iv_sound_open) ImageView iv_sound_open;
    @BindView(R.id.iv_infomation) ImageView iv_infomation;
    @BindView(R.id.iv_heart) ImageView iv_heart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_play)
    public void startPlay() {
        Intent startGame = new Intent(MainActivity.this, GameActivity.class);
        startActivity(startGame);
    }

    @Override
    public void onBackPressed() {

        MainDialogFragment fragment = new MainDialogFragment();
        fragment.show(getFragmentManager(), "quitDialog");
    }

    @OnClick(R.id.iv_quit_game)
    public void quitGame() {
        MainDialogFragment fragment = new MainDialogFragment();
        fragment.show(getFragmentManager(), "quitDialog");
    }

    @OnClick(R.id.iv_learn)
    public void goToLearn() {
        Intent startGame = new Intent(MainActivity.this, LearnActivity.class);
        startActivity(startGame);
    }

//    @OnClick(R.id.iv_sound_open)
//    public void sound() {
//
//    }

    @OnClick(R.id.iv_heart)
    public void getNotification() {
        final NotificationDialogFragment fragment = new NotificationDialogFragment();
        fragment.show(getFragmentManager(), "notificationDialog");
    }

    @OnClick(R.id.iv_infomation)
    public void showInfomation() {

        InformationDialogFragment fragment = new InformationDialogFragment();
        fragment.show(getFragmentManager(), "infoDialog");
    }
}