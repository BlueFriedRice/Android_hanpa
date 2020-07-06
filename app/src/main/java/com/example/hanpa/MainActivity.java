package com.example.hanpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hanpa.Dictionary.DicMainActivity;
import com.example.hanpa.Game.GameMainActivity;
import com.example.hanpa.VoiceRecognition.VoiceRecognitionActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner_country;
    String[] spinnerNames;
    int[] spinnerImages;
    int selected_country_idx = 0;

    private long backbtn = 0;   //뒤로 두번눌러서 종료이벤트에대한 변수.
    private Spinner spinner;
    private ImageButton Dic, Voice, Game;
    private TextView voicetext, dictext, gametext;
    private static int lang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.MainActivity_Spinner);

        Dic = (ImageButton)findViewById(R.id.MainActivity_Button_Dic);
        Voice = (ImageButton)findViewById(R.id.MainActivity_Button_Voice);
        Game = (ImageButton)findViewById(R.id.MainActivity_Button_Game);

        dictext = (TextView)findViewById(R.id.dictext);
        voicetext = (TextView)findViewById(R.id.voicetext);
        gametext = (TextView)findViewById(R.id.gametext);

//        LinearLayout Dicli = (LinearLayout)findViewById(R.id.Dicli);
//        LinearLayout Voiceli = (LinearLayout)findViewById(R.id.Voiceli);
//        LinearLayout Gameli = (LinearLayout)findViewById(R.id.Gameli);

//        Dicli.setOnClickListener(this);
//        Voiceli.setOnClickListener(this);

        Dic.setOnClickListener(this);
        Voice.setOnClickListener(this);
        Game.setOnClickListener(this);

        spinner_country = (Spinner)findViewById(R.id.MainActivity_Spinner);

        spinnerNames = new String[]{"한국어", "English", "汉语", "日本語"};
        spinnerImages = new int[]
                { R.drawable.korea
                , R.drawable.america
                , R.drawable.china
                , R.drawable.japan };

        // 어댑터와 스피너를 연결합니다.
        SpinnerAdapter SpinnerAdapter = new SpinnerAdapter(MainActivity.this, spinnerNames, spinnerImages);
        spinner_country.setAdapter(SpinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                lang = i;
//                selected_country_idx = spinner_country.getSelectedItemPosition();
//                Toast.makeText(MainActivity.this, spinnerNames[selected_country_idx], Toast.LENGTH_LONG).show();

               switch (i)
               {
                   case 0:
                       dictext.setText("사전");
                       voicetext.setText("음성인식");
                       gametext.setText("게임시작");
                       break;
                   case 1:
                       dictext.setText("Dictionary");
                       voicetext.setText("\t\t\t\t\t\tVoice\nRecognition");
                       gametext.setText("Game Start");
                       break;
                   case 2:
                       dictext.setText("词典");
                       voicetext.setText("语音识别");
                       gametext.setText("游戏开始");
                       break;
                   case 3:
                       dictext.setText("じぜん");
                       voicetext.setText("おんせいにんしき");
                       gametext.setText("ゲーム開始");
                       break;
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

    }
    //뒤로 가기버튼 두번눌러 앱종료
    @Override
    public void onBackPressed()
    {
        long curTime =System.currentTimeMillis();
        long gapTime = curTime - backbtn;
        if(0<=gapTime && 2000>=gapTime){
            super.onBackPressed();
        }
        else
            {
            backbtn =curTime;
            Toast.makeText(this,"뒤로 버튼을 한번 더 누르면 앱이 종료됩니다.",Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.MainActivity_Button_Dic:
                Intent intent1 = new Intent(MainActivity.this, DicMainActivity.class);
                intent1.putExtra("language",lang);
                startActivity(intent1);
                break;

            case R.id.MainActivity_Button_Voice:
                Intent intent2 = new Intent(MainActivity.this, VoiceRecognitionActivity.class);
                intent2.putExtra("language",lang);
                startActivity(intent2);
                break;
            case R.id.MainActivity_Button_Game:
                Intent intent3 = new Intent(MainActivity.this, GameMainActivity.class);
                intent3.putExtra("language",lang);
                startActivity(intent3);
                break;
        }
    }

    public static int getLang(){
        return lang;
    }
}
