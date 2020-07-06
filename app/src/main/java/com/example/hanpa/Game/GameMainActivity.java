package com.example.hanpa.Game;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hanpa.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class GameMainActivity extends AppCompatActivity {

    private long backbtn=0;
    private GameView gameView;
    private Handler handler  = new Handler();
    private  final static long Interval  = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(gameView);

        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //게임뷰화면 갱신

                        //gameView.invalidate();

                    }
                });
            }
            //0초후 첫시작 interval마다 계속실행.
        },0,Interval);

    }

    @Override
    public void onBackPressed() {
        long curTime =System.currentTimeMillis();
        long gapTime = curTime - backbtn;
        if(0<=gapTime && 2000>=gapTime){
            GameMainActivity.this.finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            GameView.bgm.stop();
            super.onBackPressed();
        }else{
            backbtn =curTime;
            Toast.makeText(this,"뒤로 버튼을 한번 더 누르면 앱이 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }



}