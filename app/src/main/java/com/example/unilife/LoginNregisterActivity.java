package com.example.unilife;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;



public class LoginNregisterActivity extends AppCompatActivity {

    Button introBtn;
    Button createBtn;
    Button signinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_nregister);
        getSupportActionBar().hide();

        introBtn= (Button)findViewById(R.id.intro_btn);
        createBtn= (Button)findViewById(R.id.create_accbtn);
        signinBtn= (Button)findViewById(R.id.signin_btn);

        introBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),IntroActivity.class);
                startActivity(i);
            }
        });


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateAccActivity.class);
                startActivity(i);
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SigInActivity.class);
                startActivity(i);
            }
        });

    }
}
