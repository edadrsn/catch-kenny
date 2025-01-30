package com.example.catchkenny;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText userName,userEmail;
    private Button startGame,btnSave,btnDelete;
    private ImageView kennyPink,kennyBlue,kennyOriginal;
    private  String usrName,usrEmail;
    private String selectedKenny=null;
    int storedScore;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        userName=(EditText)findViewById(R.id.userName);
        userEmail=(EditText)findViewById(R.id.userEmail);
        startGame=(Button)findViewById(R.id.startGame);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        kennyBlue=(ImageView)findViewById(R.id.kennyBlue);
        kennyPink=(ImageView)findViewById(R.id.kennyPink);
        kennyOriginal=(ImageView)findViewById(R.id.kennyOriginal);

        sharedPreferences=this.getSharedPreferences("com.example.catchkenny",MODE_PRIVATE);
        storedScore=sharedPreferences.getInt("score",0);



        kennyOriginal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectedKenny="original";
                startGame.setEnabled(true);
            }
        });

        kennyPink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectedKenny="pink";
                startGame.setEnabled(true);
            }
        });

        kennyBlue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectedKenny="blue";
                startGame.setEnabled(true);
            }
        });

        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("selectedKenny",selectedKenny);
                startActivity(intent);
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                usrName=userName.getText().toString();
                usrEmail=userEmail.getText().toString();
                if(usrName.isEmpty() || usrEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name and email", Toast.LENGTH_SHORT).show();
                }
                else{
                    sharedPreferences.edit().putString("userName", usrName).apply();
                    sharedPreferences.edit().putString("userEmail", usrEmail).apply();
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                usrName=userName.getText().toString();
                usrEmail=userEmail.getText().toString();
                if(!(usrName.isEmpty() && usrEmail.isEmpty())) {
                    sharedPreferences.edit().remove("userName").apply();
                    sharedPreferences.edit().remove("userEmail").apply();
                    sharedPreferences.edit().remove("score").apply();
                    userName.setText("");
                    userEmail.setText("");
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"There is no data to delete",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}