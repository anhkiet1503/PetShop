package com.example.petshop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.dao.UserDAO;
import com.google.android.material.textfield.TextInputLayout;

public class ForgetActivity  extends AppCompatActivity {
    Button forget, callSignIn;
    ImageView logo;
    TextView t1, t2;
    TextInputLayout username;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget);

        logo = findViewById(R.id.logoImg);
        t1 = findViewById(R.id.logoHi);
        t2 = findViewById(R.id.slogan);
        username = findViewById(R.id.username);

        forget = findViewById(R.id.forget_btn);
        callSignIn = findViewById(R.id.signin_screen);

        userDAO = new UserDAO(this);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString();
                String pass = userDAO.ForgetPass(user);

                if(pass.equals("")) {
                    Toast.makeText(ForgetActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgetActivity.this, pass, Toast.LENGTH_SHORT).show();
                }
            }
        });

        callSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetActivity.this,SignInActivity.class);

                Pair<View, String>[] pair = new Pair[6];

                pair[0] = new Pair(logo,"logo_img");
                pair[1] = new Pair(t1,"logo_text");
                pair[2] = new Pair(t2,"logo_desc");
                pair[3] = new Pair(username,"user_trans");
                pair[4] = new Pair(forget,"btn_sign_trans");
                pair[5] = new Pair(callSignIn,"signin_signup_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ForgetActivity.this,pair);

                startActivity(intent,options.toBundle());
            }
        });
    }
}
