package com.example.hanpa.VoiceRecognition;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanpa.R;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.impl.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Locale;

import at.markushi.ui.CircleButton;

import static android.speech.tts.TextToSpeech.ERROR;

public class VoiceRecognitionActivity extends AppCompatActivity implements View.OnClickListener, SpeechRecognizeListener
{
    private SpeechRecognizerClient client;
    private TextView textView, search_text, meaning_text, meaning_text2;
    private int lang;
    private CircleButton voice;
    private ImageButton speaker;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        androidx.appcompat.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("음성인식"); // ActionBar 이름 설정

        setContentView(R.layout.activity_voice_recognition_main);

        if (ab != null) {
            ab.setBackgroundDrawable(getResources().getDrawable(R.color.signitureColor));
        }

        search_text = (TextView) findViewById(R.id.search_text);
        meaning_text = (TextView) findViewById(R.id.meaning_text);
        meaning_text2 = (TextView) findViewById(R.id.meaning_text2);

        voice = (CircleButton) findViewById(R.id.btn);
        speaker = (ImageButton) findViewById(R.id.speaker);
        textView = (TextView)findViewById(R.id.tv);

        lang = getIntent().getIntExtra("language",0);

        switch (lang) {
            case 0:
                ab.setTitle(Html.fromHtml("<font color='#FFFFFF'>음성인식</font>"));
                break;

            case 1:
                ab.setTitle(Html.fromHtml("<font color='#FFFFFF'>Voice Recognition</font>"));
                meaning_text.setText("Please press the button.");
                break;

            case 2:
                ab.setTitle(Html.fromHtml("<font color='#FFFFFF'>语音识别</font>"));
                meaning_text.setText("请按按钮。");
                break;

            case 3:
                ab.setTitle(Html.fromHtml("<font color='#FFFFFF'>おんせいにんしき</font>"));
                meaning_text.setText("ボタンを押してください。");
                break;
        }

        voice.setOnClickListener(this);

        speaker.setOnClickListener(this);

        setButtonsStatus(true);

/*        // 해시키
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.hanpa", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

        // SDK 초기화
        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        // 클라이언트 생성
        SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
                setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);

        client = builder.build();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if(status != ERROR)
                {
                    // 언어를 선택한다.
                    switch (lang) {
                        case 0:
                            tts.setLanguage(Locale.KOREAN);
                            break;

                        case 1:
                            tts.setLanguage(Locale.ENGLISH);
                            break;

                        case 2:
                            tts.setLanguage(Locale.CHINESE);
                            break;

                        case 3:
                            tts.setLanguage(Locale.JAPANESE);
                            break;
                    }
                }
            }
        });

    }

    public void onDestroy()
    {
        super.onDestroy();

        SpeechRecognizerManager.getInstance().finalizeLibrary();

        if(tts != null)
        {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

    private void setButtonsStatus(boolean enabled)
    {
        findViewById(R.id.btn).setEnabled(enabled);
    }

    // 클릭 리스너
    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        switch (id) {

            case R.id.speaker:
                AudioManager audio = (AudioManager) view.getContext().getSystemService(Context.AUDIO_SERVICE);
                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // 최대볼륨

                tts.setPitch(1.0f);         // 음성 톤은 기본 설정
                tts.setSpeechRate(0.7f);    // 읽는 속도를 0.5빠르기로 설정
                // editText에 있는 문장을 읽는다.
                tts.speak(meaning_text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;

            case R.id.btn:
                switch (lang) {
                    case 0:
                        textView.setText("단어를 말씀해주세요.");
                        search_text.setText("");
                        meaning_text.setText("새로운 단어를 듣는중입니다...");
                        meaning_text2.setText("");
                        speaker.setVisibility(View.INVISIBLE);
                        break;

                    case 1:
                        textView.setText("Speak the word.");
                        search_text.setText("");
                        meaning_text.setText("Listening to new words...");
                        meaning_text2.setText("");
                        speaker.setVisibility(View.INVISIBLE);
                        break;

                    case 2:
                        textView.setText("请告诉我单词。");
                        search_text.setText("");
                        meaning_text.setText("正在听新词...");
                        meaning_text2.setText("");
                        speaker.setVisibility(View.INVISIBLE);
                        break;

                    case 3:
                        textView.setText("単語をおっしゃってください。");
                        search_text.setText("");
                        meaning_text.setText("新しい単語を聞いています。");
                        meaning_text2.setText("");
                        speaker.setVisibility(View.INVISIBLE);
                        break;
                }
                if (PermissionUtils.checkAudioRecordPermission(this)) {

                    String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;

                    SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
                            setServiceType(serviceType);

                    client = builder.build();

                    client.setSpeechRecognizeListener(this);
                    client.startRecording(true);

                    setButtonsStatus(false);
                }

                break;

        }
    }

    @Override
    public void onReady()
    {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onBeginningOfSpeech()
    {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onEndOfSpeech()
    {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onError(int errorCode, String errorMsg)
    {
        //TODO implement interface DaumSpeechRecognizeListener method
        Log.e("MainActivity", "onError");
        final String msg = errorMsg;
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(VoiceRecognitionActivity.this, msg, Toast.LENGTH_SHORT).show();
                setButtonsStatus(true);
            }
        });

        client = null;
    }

    @Override
    public void onPartialResult(String text)
    {
        //TODO implement interface DaumSpeechRecognizeListener method
    }

    @Override
    public void onResults(Bundle results)
    {
        /*final StringBuilder builder = new StringBuilder();
        Log.i("SpeechSampleActivity", "onResults");

        ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        ArrayList<Integer> confs = results.getIntegerArrayList(SpeechRecognizerClient.KEY_CONFIDENCE_VALUES);

        for (int i = 0; i < texts.size(); i++) {
            builder.append(texts.get(i));
            builder.append(" (");
            builder.append(confs.get(i).intValue());
            builder.append(")\n");
        }*/
        final ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);

        final Activity activity = this;
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // finishing일때는 처리하지 않는다.
                String text = texts.get(0).replaceAll(" ","");

                textView.setText(text);
                speaker.setVisibility(View.VISIBLE);

                switch(lang) {

                    case 0:
                        if(text.equals("버정"))
                        {
                            search_text.setText("'"+text+"'"+" 에 대한 검색결과 입니다.");
                            meaning_text.setText("버스정류장");
                            meaning_text2.setText("'버스정류장'"+" (을)를 이 올바른 표현입니다.\n신조어 사용을 줄입시다!");
                        }
                        else if(text.equals("버카충"))
                        {
                            search_text.setText("'"+text+"'"+" 에 관한 음성인식 결과입니다.");
                            meaning_text.setText("버스카드충전");
                            meaning_text2.setText("'버스카드충전'"+" (을)를 이 올바른 표현입니다.\n신조어 사용을 줄입시다!");
                        }
                        else if(text.equals("가온누리"))
                        {
                            search_text.setText("'"+text+"'"+" 에 관한 음성인식 결과입니다.");
                            meaning_text.setText("세상의 중심");
                            meaning_text2.setText("");
                        }
                        else
                        {
                            search_text.setText("'   ' 에 관한 음성인식 결과입니다.");
                            meaning_text.setText("'"+text+"'"+" 에 관한 결과가 존재하지 않습니다.");
                            meaning_text2.setText("다시 음성인식을 시작해주세요.");
                            speaker.setVisibility(View.INVISIBLE);
                        }
                        break;

                    case 1:

                        if(text.equals("버정"))
                        {
                            search_text.setText("Result of '버정(Bujung)'");
                            meaning_text.setText("Bus station");
                            meaning_text2.setText("'Bus station'"+" is the correct expression.\nLet's not use any abbreviation!");
                        }
                        else if(text.equals("버카충"))
                        {
                            search_text.setText("Result of '버카충(BuKaChung)'");
                            meaning_text.setText("Charging bus station card");
                            meaning_text2.setText("'Charging bus station card'"+" is the correct expression.\nLet's not use any abbreviation!");
                        }
                        else if(text.equals("가온누리"))
                        {
                            search_text.setText("Result of '가온누리(Gaonnuri)'");
                            meaning_text.setText("Center of the world");
                            meaning_text2.setText("");
                        }
                        else
                        {
                            search_text.setText("Result of '   '");
                            meaning_text.setText("The word does not exists.");
                            meaning_text2.setText("Please restart voice recognition.");
                            speaker.setVisibility(View.INVISIBLE);
                        }
                        break;
                }

                setButtonsStatus(true);

            }
        });

        client = null;
    }

    @Override
    public void onAudioLevel(float audioLevel)
    {

    }

    @Override
    public void onFinished()
    {

    }

}
