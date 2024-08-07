package com.example.petshop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class WelcomeActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    Animation topAnim, bottomAnim;
    ImageView logo;
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);

        logo.setAnimation(topAnim);
        t1.setAnimation(bottomAnim);
        t2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);

                Pair<View, String>[] pair = new Pair[2];
                pair[0] = new Pair(logo,"logo_img");
                pair[1] = new Pair(t1,"logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pair);

                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);
    }
}
