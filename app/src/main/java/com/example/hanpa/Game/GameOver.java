package com.example.hanpa.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.example.hanpa.MainActivity;
import com.example.hanpa.R;

public class GameOver extends AppCompatActivity {

    private Button playagain;
    private  Button gomain;
    private TextView score;
    TextView redtextview1;
    TextView redtextview2;
    TextView redtextview3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        playagain = (Button)findViewById(R.id.playagain);
        gomain =(Button)findViewById(R.id.gomain);
        redtextview1=(TextView)findViewById(R.id.texview1);
        redtextview2=(TextView)findViewById(R.id.texview2);
        redtextview3=(TextView)findViewById(R.id.texview3);
        redtextview1.setText(GameView.intentred[0]+" : "+GameView.intentkor[0]);
        redtextview2.setText(GameView.intentred[1]+" : "+GameView.intentkor[1]);
        redtextview3.setText(GameView.intentred[2]+" : "+GameView.intentkor[2]);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(GameOver.this,GameMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(GameOver.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        score = (TextView)findViewById(R.id.score);
        score.setText("점수 : "+getIntent().getExtras().get("score").toString());


    }
}