package com.example.hanpa.Game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import android.media.MediaPlayer;
import android.os.CountDownTimer;

import android.view.MotionEvent;
import android.view.View;

import com.example.hanpa.R;


public class GameView extends View {
    public  static MediaPlayer mp;
    public  static MediaPlayer bgm;

    int yr =0;
    int gr =0;
    int rr= 0;
    int br =0;
    int grr =0;
    int r =0;
    public  static  String intentred [] = new String[3];
    public  static  String intentkor [] = new String[3];
    public  static  long i;
    /*
        CountDownTimer countDownTimer = new   CountDownTimer(61000,1000) {
            @Override
            public void onTick(long l) {
                i =l/1000;
            }

            @Override
            public void onFinish() {

                countDownTimer.cancel();
                   bgm.stop();
                Intent intent2  = new Intent(getContext(),GameOverActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("score",score);
                getContext().startActivity(intent2);

            }
        }.start();
    */
    String [] Korean ={"버스정류장","별걸 다줄인다","열심히 공부","영원한 고통","만나서 반가워 잘 부탁해"};
    String [] redkorean = {"버정","별다줄","열공","영고","만반잘부"};
    String [] bluekorean = {"아미","미르","가람","물마","꽃잠"};
    String [] greenkorean = {"또바기","그린비","도담도담","다원","미쁘다"};
    String [] yellowkorean = {"든해","가온","나예","아람","송아리"};
    String [] greykorean = {"나봄","라라","별하","보담","새솔"};
    private   Bitmap fish[]= new Bitmap[2];
    private   int fishx =10;
    private   int fishY;
    private   int fishSpeed;
    private  int canvasWidth,canvasHeight;
    //속도 조절
    private  int yellowX,yellowY,yellowSpeed =4;
    private Paint yellowPaint = new Paint();
    //속도 조절
    private  int greenX,greenY,greenSpeed=4;
    private  Paint greenPaint = new Paint();
    //속도조절
    private  int redX,redY,redSpeed=5;
    private  Paint redPaint = new Paint();
    //속도조절
    private  int blueX,blueY,blueSpeed = 5;
    private Paint bluePaint = new Paint();
    //속도조절
    private  int greyX,greyY,greySpeed = 5;
    private Paint greyPaint = new Paint();
    public static  int score,lifeCounter;
    private  boolean touch =false;
    private Bitmap backgroundImage;
    private Bitmap backgroundImage2;
    private Bitmap backgroundImage3;
    private  Bitmap plus;
    private  Bitmap wood;
    private Paint scorePaint = new Paint();
    private  Bitmap life[]  =new Bitmap[2];
    private Paint timePaint = new Paint();

    private  Paint textPaint  = new Paint();
    private  Paint bitmapPaint  = new Paint();
    private Bitmap scorewood;

    public GameView(Context context) {
        super(context);
        mp=  MediaPlayer.create(context,R.raw.sound);
        bgm= MediaPlayer.create(context,R.raw.bgm);
        bgm.start();


        greyPaint.setColor(Color.BLACK);
        greyPaint.setTypeface(Typeface.DEFAULT_BOLD);
        bluePaint.setColor(Color.BLACK);
        bluePaint.setTypeface(Typeface.DEFAULT_BOLD);
        yellowPaint.setColor(Color.BLACK);
        yellowPaint.setTypeface(Typeface.DEFAULT_BOLD);
        greenPaint.setColor(Color.BLACK);
        greenPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //red >> 줄임말
        redPaint.setColor(Color.BLACK);
        redPaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        //안티 알리아싱을 적용하지 않으면 계단현상이 나타나지만 적용을 하면 부드러워짐.
        // scorePaint.setAntiAlias(true);
        timePaint.setColor(Color.WHITE);

        timePaint.setTypeface(Typeface.DEFAULT_BOLD);
        //안티 알리아싱을 적용하지 않으면 계단현상이 나타나지만 적용을 하면 부드러워짐.
        // timePaint.setAntiAlias(true);

        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        // textPaint.setAntiAlias(true);
        fishY=550;
        score=0;
        lifeCounter=3;
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.student);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.student2);
        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.hanpaback1);
        backgroundImage2=BitmapFactory.decodeResource(getResources(),R.drawable.hanpaback2);
        backgroundImage3=BitmapFactory.decodeResource(getResources(),R.drawable.hanpaback3);
        plus=BitmapFactory.decodeResource(getResources(), R.drawable.plus);
        wood=BitmapFactory.decodeResource(getResources(),R.drawable.wood);
        scorewood=BitmapFactory.decodeResource(getResources(),R.drawable.wood);
        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        bitmapPaint.setAntiAlias(false);
        bitmapPaint.setFilterBitmap(false);
        bitmapPaint.setDither(false);
        greyPaint.setAntiAlias(false);
        bluePaint.setAntiAlias(false);
        yellowPaint.setAntiAlias(false);
        greenPaint.setAntiAlias(false);
        redPaint.setAntiAlias(false);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        invalidate();
        canvasWidth  =canvas.getWidth();
        canvasHeight = canvas.getHeight();
        Rect dest = new Rect(0,0,getWidth(),getHeight());
        Paint back = new Paint();
        back.setFilterBitmap(true);
        fish[0]=Bitmap.createScaledBitmap(fish[0],canvasWidth/8,canvasHeight/8,true);
        fish[1]=Bitmap.createScaledBitmap(fish[1],canvasWidth/8,canvasHeight/8,true);
        life[0]=Bitmap.createScaledBitmap(life[0],canvasWidth/15,canvasHeight/15,true);
        life[1]=Bitmap.createScaledBitmap(life[1],canvasWidth/15,canvasHeight/15,true);
        plus=Bitmap.createScaledBitmap(plus,canvasWidth/15,canvasHeight/20,true);
        wood=Bitmap.createScaledBitmap(wood,canvasWidth/5,canvasHeight/22,true);
        scorewood=Bitmap.createScaledBitmap(scorewood,canvasWidth/3+50,canvasHeight/18,true);


        redPaint.setTextSize(canvasWidth/21);
        greyPaint.setTextSize(canvasWidth/21);
        yellowPaint.setTextSize(canvasWidth/21);
        greenPaint.setTextSize(canvasWidth/21);
        bluePaint.setTextSize(canvasWidth/21);
        timePaint.setTextSize(canvasWidth/16);
        scorePaint.setTextSize(canvasWidth/16);

        switch (lifeCounter){
            case 3:
                canvas.drawBitmap(backgroundImage,null,dest,back);
                break;
            case 2:
                canvas.drawBitmap(backgroundImage2,null,dest,back);
                break;
            case 1:
                canvas.drawBitmap(backgroundImage3,null,dest,back);
                break;
            case 0:
                canvas.drawBitmap(backgroundImage3,null,dest,back);
                break;
        }


        int minFishY = fish[0].getHeight();
        int maxFishY= canvasHeight -fish[0].getHeight() *3;

        fishY = fishY+fishSpeed;
        if (fishY< minFishY){
            fishY = minFishY;
        }
        if (fishY> maxFishY){
            fishY=maxFishY;
        }
        fishSpeed=fishSpeed+2;
        if (touch){
            canvas.drawBitmap(fish[1],fishx,fishY,null);
            touch =false;

        }
        else{
            canvas.drawBitmap(fish[0],fishx,fishY,null);
        }

        yellowX = yellowX - yellowSpeed;
        if (hiteBallChecker(yellowX,yellowY)){
            //mp.start();
            // canvas.drawBitmap(plus,fishx+40,fishY-70,null);
            score = score +10;
            yellowX =  -100;

        }
        if (yellowX<0){
            yellowX=canvasWidth+21;
            yellowY=(int) Math.floor(Math.random()* (maxFishY-minFishY))+minFishY;
            yr= (int)(Math.random()*5);
        }



        switch (yellowkorean[yr].length()){
            case 2:
                canvas.drawBitmap(wood,yellowX-canvasWidth/17,yellowY-canvasWidth/17,null);
                break;
            case 3:
                canvas.drawBitmap(wood,yellowX-canvasWidth/25,yellowY-canvasWidth/17,null);
                break;
            case 4:
                canvas.drawBitmap(wood,yellowX-canvasWidth/35+10,yellowY-canvasWidth/17,null);
                break;
        }


        canvas.drawText(yellowkorean[yr],yellowX,yellowY,yellowPaint);

        greyX = greyX - greySpeed;
        if (hiteBallChecker(greyX,greyY)){

            mp.start();
            // canvas.drawBitmap(plus,fishx+40,fishY-70,null);
            score = score +15;
            greyX =  -100;

        }
        if (greyX<0){
            greyX=canvasWidth+21;
            greyY=(int) Math.floor(Math.random()* (maxFishY-minFishY))+minFishY;
            grr= (int)(Math.random()*5);
        }

        switch (greykorean[grr].length()){
            case 2:
                canvas.drawBitmap(wood,greyX-canvasWidth/17,greyY-canvasWidth/17,null);
                break;
            case 3:
                canvas.drawBitmap(wood,greyX-canvasWidth/25,greyY-canvasWidth/17,null);
                break;
            case 4:
                canvas.drawBitmap(wood,greyX-canvasWidth/35+10,greyY-canvasWidth/17,null);
                break;
        }

        canvas.drawText(greykorean[grr],greyX,greyY,greyPaint);

        greenX = greenX - greenSpeed;
        if (hiteBallChecker(greenX,greenY)){
            mp.start();
            //  canvas.drawBitmap(plus,fishx+40,fishY-70,null);
            score = score + 10;
            greenX =  -100;

        }
        if (greenX<0){
            greenX=canvasWidth+21;
            greenY=(int) Math.floor(Math.random()* (maxFishY-minFishY))+minFishY;
            gr=(int)(Math.random()*5);
        }
        switch (greenkorean[gr].length()){
            case 2:
                canvas.drawBitmap(wood,greenX-canvasWidth/17,greenY-canvasWidth/17,null);
                break;
            case 3:
                canvas.drawBitmap(wood,greenX-canvasWidth/25,greenY-canvasWidth/17,null);
                break;
            case 4:
                canvas.drawBitmap(wood,greenX-canvasWidth/35+10,greenY-canvasWidth/17,null);
                break;
        }


        canvas.drawText(greenkorean[gr],greenX,greenY,greenPaint);


        blueX = blueX - blueSpeed;
        if (hiteBallChecker(blueX,blueY)){
            mp.start();
            // canvas.drawBitmap(plus,fishx+40,fishY-70,null);
            score = score + 15;
            blueX =  -100;

        }
        if (blueX<0){
            blueX=canvasWidth+21;
            blueY=(int) Math.floor(Math.random()* (maxFishY-minFishY))+minFishY;
            br=(int)(Math.random()*5);
        }
        switch (bluekorean[br].length()){
            case 2:
                canvas.drawBitmap(wood,blueX-canvasWidth/17,blueY-canvasWidth/17,null);
                break;
            case 3:
                canvas.drawBitmap(wood,blueX-canvasWidth/25,blueY-canvasWidth/17,null);
                break;
            case 4:
                canvas.drawBitmap(wood,blueX-canvasWidth/35+10,blueY-canvasWidth/17,null);
                break;
        }
        canvas.drawText(bluekorean[br],blueX,blueY,bluePaint);


        redX = redX - redSpeed;

        if (hiteBallChecker(redX,redY)){
            mp.start();
            redX =  -100;
            //닿으면 하트가 사라짐

            fishY=maxFishY;
            lifeCounter--;
            if (lifeCounter==2){
                intentred[0]=redkorean[rr];
                intentkor[0]=Korean[r];
            }
            if (lifeCounter==1){
                intentred[1]=redkorean[rr];
                intentkor[1]=Korean[r];
            }
            if (lifeCounter==0){
                intentred[2]=redkorean[rr];
                intentkor[2]=Korean[r];
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setIcon(R.drawable.bell);
                ad.setTitle(redkorean[rr]);
                ad.setCancelable(false);
                ad.setMessage(Korean[r]+" 이(가) 올바른 표현입니다");
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //countDownTimer.cancel();
                        bgm.stop();
                        Intent intent2  = new Intent(getContext(),GameOver.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent2.putExtra("score",score);
                        getContext().startActivity(intent2);
                    }
                });
                ad.show();
            }
            else{
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setIcon(R.drawable.bell);
                ad.setTitle(redkorean[rr]);
                ad.setCancelable(false);
                ad.setMessage(Korean[r]+" 이(가) 올바른 표현입니다");
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                ad.show();
            }





        }

        if (redX<0){
            redX=canvasWidth+21;
            redY=(int) Math.floor(Math.random()* (maxFishY-minFishY))+minFishY;
            rr=(int)(Math.random()*5);
            r=rr;
        }
        switch (redkorean[rr].length()){
            case 2:
                canvas.drawBitmap(wood,redX-canvasWidth/17,redY-canvasWidth/17,null);
                break;
            case 3:
                canvas.drawBitmap(wood,redX-canvasWidth/25,redY-canvasWidth/17,null);
                break;
            case 4:
                canvas.drawBitmap(wood,redX-canvasWidth/35+10,redY-canvasWidth/17,null);
                break;
        }

        canvas.drawText(redkorean[rr],redX,redY,redPaint);
        canvas.drawBitmap(scorewood,canvasWidth/50,canvasWidth/25,null);
        canvas.drawText("점수 : "+score,canvasWidth/20,canvasWidth/9,scorePaint);

        //canvas.drawText("시간 : "+i,20,150,timePaint);


        for (int i=0; i<3;i++){
            int x = (int)(canvasWidth/3+canvasWidth/3+life[0].getWidth()* 1.5 *i);
            int y =30;
            if(i<lifeCounter){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }


        if(score>100){
            yellowSpeed=6;

            greenSpeed=6;
            redSpeed=7;
            greySpeed=7;
            blueSpeed=7;
        }

    }

    public  boolean hiteBallChecker(int x,int y){
        if (fishx<x && x<(fishx+fish[0].getWidth()) && fishY< y && y<(fishY+fish[0].getHeight())){
            return  true;
        }else{
            return  false;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){

            touch =true;
            //캐릭터스피드
            fishSpeed = -25;
        }
        return  true;
    }

}

