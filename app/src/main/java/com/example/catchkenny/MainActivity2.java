package com.example.catchkenny;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private TextView textTime,textScore;
    private ImageView imgOrg1,imgOrg2,imgOrg3,imgOrg4,imgOrg5,imgOrg6,imgOrg7,imgOrg8,imgOrg9;
    private ImageView imgPink1,imgPink2,imgPink3,imgPink4,imgPink5,imgPink6,imgPink7,imgPink8,imgPink9;
    private ImageView imgBlue1,imgBlue2,imgBlue3,imgBlue4,imgBlue5,imgBlue6,imgBlue7,imgBlue8,imgBlue9;
    private ImageView imgCat1,imgCat2,imgCat3,imgCat4,imgCat5,imgCat6,imgCat7,imgCat8,imgCat9;
    private GridLayout gridLayoutPink,gridLayoutBlue,gridLayoutOriginal,gridLayoutCat;
    ImageView[] imageArray, imageArrayOriginal,imageArrayPink,imageArrayBlue,imgArrayCat;
    int score,remainingTime=20000;
    Handler handler;
    Runnable runnable;
    String userName,userEmail;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        sharedPreferences=this.getSharedPreferences("com.example.catchkenny",MODE_PRIVATE);
        userName=sharedPreferences.getString("userName","");
        userEmail=sharedPreferences.getString("userEmail","");

        score=0;
        textTime=(TextView)findViewById(R.id.textTime);
        textScore=(TextView)findViewById(R.id.textScore);
        imgOrg1=(ImageView)findViewById(R.id.imgOriginal1);
        imgOrg2=(ImageView)findViewById(R.id.imgOriginal2);
        imgOrg3=(ImageView)findViewById(R.id.imgOriginal3);
        imgOrg4=(ImageView)findViewById(R.id.imgOriginal4);
        imgOrg5=(ImageView)findViewById(R.id.imgOriginal5);
        imgOrg6=(ImageView)findViewById(R.id.imgOriginal6);
        imgOrg7=(ImageView)findViewById(R.id.imgOriginal7);
        imgOrg8=(ImageView)findViewById(R.id.imgOriginal8);
        imgOrg9=(ImageView)findViewById(R.id.imgOriginal9);

        imgBlue1=(ImageView)findViewById(R.id.imgBlue1);
        imgBlue2=(ImageView)findViewById(R.id.imgBlue2);
        imgBlue3=(ImageView)findViewById(R.id.imgBlue3);
        imgBlue4=(ImageView)findViewById(R.id.imgBlue4);
        imgBlue5=(ImageView)findViewById(R.id.imgBlue5);
        imgBlue6=(ImageView)findViewById(R.id.imgBlue6);
        imgBlue7=(ImageView)findViewById(R.id.imgBlue7);
        imgBlue8=(ImageView)findViewById(R.id.imgBlue8);
        imgBlue9=(ImageView)findViewById(R.id.imgBlue9);

        imgPink1=(ImageView)findViewById(R.id.imgPink1);
        imgPink2=(ImageView)findViewById(R.id.imgPink2);
        imgPink3=(ImageView)findViewById(R.id.imgPink3);
        imgPink4=(ImageView)findViewById(R.id.imgPink4);
        imgPink5=(ImageView)findViewById(R.id.imgPink5);
        imgPink6=(ImageView)findViewById(R.id.imgPink6);
        imgPink7=(ImageView)findViewById(R.id.imgPink7);
        imgPink8=(ImageView)findViewById(R.id.imgPink8);
        imgPink9=(ImageView)findViewById(R.id.imgPink9);

        imgCat1=(ImageView)findViewById(R.id.cat1);
        imgCat2=(ImageView)findViewById(R.id.cat2);
        imgCat3=(ImageView)findViewById(R.id.cat3);
        imgCat4=(ImageView)findViewById(R.id.cat4);
        imgCat5=(ImageView)findViewById(R.id.cat5);
        imgCat6=(ImageView)findViewById(R.id.cat6);
        imgCat7=(ImageView)findViewById(R.id.cat7);
        imgCat8=(ImageView)findViewById(R.id.cat8);
        imgCat9=(ImageView)findViewById(R.id.cat9);

        gridLayoutOriginal=(GridLayout)findViewById(R.id.gridLayoutOriginal);
        gridLayoutPink=(GridLayout)findViewById(R.id.gridLayoutPink);
        gridLayoutBlue=(GridLayout)findViewById(R.id.gridLayoutBlue);
        gridLayoutCat=(GridLayout)findViewById(R.id.gridLayoutCat);


        imageArrayOriginal=new ImageView[] {imgOrg1,imgOrg2,imgOrg3,imgOrg4,imgOrg5,imgOrg6,imgOrg7,imgOrg8,imgOrg9};
        imageArrayPink=new ImageView[] {imgPink1,imgPink2,imgPink3,imgPink4,imgPink5,imgPink6,imgPink7,imgPink8,imgPink9};
        imageArrayBlue=new ImageView[] {imgBlue1,imgBlue2,imgBlue3,imgBlue4,imgBlue5,imgBlue6,imgBlue7,imgBlue8,imgBlue9};
        imgArrayCat=new ImageView[] {imgCat1,imgCat2,imgCat3,imgCat4,imgCat5,imgCat6,imgCat7,imgCat8,imgCat9};
        hideImages();

        Intent intent=getIntent();
        String selectedKenny=intent.getStringExtra("selectedKenny");
        if(selectedKenny.matches("pink")){
            gridLayoutPink.setVisibility(View.VISIBLE);
            gridLayoutCat.setVisibility(View.VISIBLE);
            imageArray=imageArrayPink;

        } else if (selectedKenny.matches("blue")) {
            gridLayoutBlue.setVisibility(View.VISIBLE);
            gridLayoutCat.setVisibility(View.VISIBLE);
            imageArray=imageArrayBlue;
        }
        else{
            gridLayoutOriginal.setVisibility(View.VISIBLE);
            gridLayoutCat.setVisibility(View.VISIBLE);
            imageArray=imageArrayOriginal;
        }

        new CountDownTimer(20000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                textTime.setText("TIME:"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                textTime.setText("TIME OFF !");
                handler.removeCallbacks(runnable);
                textScore.setText("HIGH SCORE: " + getHighScore());
                for(ImageView img:imageArray){
                    img.setVisibility(View.INVISIBLE);
                }

                AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity2.this);
                ad.setTitle("Restart?");
                ad.setMessage("Do  you want to restart the game?");
                ad.setCancelable(false);
                ad.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                        startActivity(intent);
                        sharedPreferences.edit().putInt("score",score).apply();
                        finish();
                    }
                });
                ad.setNegativeButton("No",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        finish();

                    }
                });
                ad.show();

            }
        }.start();
    }

    public void scoreIncrease(View view){
        score++;
        textScore.setText(userName+ " SCORE : " + score);
        int highScore=sharedPreferences.getInt("score",0);
        if(score>highScore){
            sharedPreferences.edit().putInt("score",score).apply();
        }
    }

    public void scoreDecrease(View view){
        score=score-2;
        textScore.setText(userName+" SCORE : " + score);
        textTime.setText("TIME:"+remainingTime/1000);
    }

    public void hideImages(){
        handler=new Handler();
        runnable= new Runnable() {
            @Override
            public void run() {
                for(ImageView img:imageArray){
                    img.setVisibility(View.INVISIBLE);
                }
                for(ImageView img2:imgArrayCat){
                    img2.setVisibility(View.INVISIBLE);
                }
                Random random=new Random();
                int i=random.nextInt(9);
                int j=random.nextInt(9);
                if(j==1){
                    imgArrayCat[i].setVisibility(View.VISIBLE);
                    imgArrayCat[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            scoreDecrease(view);
                        }
                    });
                }
                else{
                    imageArray[i].setVisibility(View.VISIBLE);
                }
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);

    }

    public int getHighScore(){
        return sharedPreferences.getInt("score",0);
    }



}