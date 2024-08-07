package com.example.petshop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petshop.dao.AdminDAO;
import com.example.petshop.dao.UserDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {

    Button callSignIn, callSignUp, btnForgot;
    ImageView logo;
    TextView t1, t2;
    TextInputLayout username, password;
    CheckBox chkRemember;

    private AdminDAO adminDAO;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        logo = findViewById(R.id.logoImg);
        t1 = findViewById(R.id.logoHi);
        t2 = findViewById(R.id.slogan);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        callSignIn = findViewById(R.id.signin_btn);
        callSignUp = findViewById(R.id.signup_screen);
        chkRemember = findViewById(R.id.chkRemember);
        btnForgot = findViewById(R.id.forgot_btn);

        adminDAO = new AdminDAO(this);
        userDAO = new UserDAO(this);

        SharedPreferences sharedPreferences1 = getSharedPreferences("REGISTER", MODE_PRIVATE);
        String regAcc = sharedPreferences1.getString("userRegister","");

        if(!regAcc.equals("")) {
            username.getEditText().setText(regAcc);

            SharedPreferences preferences = getSharedPreferences("REGISTER", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }else {
            SharedPreferences sharedPreferences = getSharedPreferences("LASTIME",MODE_PRIVATE);
            boolean isRemember = sharedPreferences.getBoolean("isRemember",false);

            if(isRemember) {
                String userLogin = sharedPreferences.getString("userLogin","");
                String passLogin = sharedPreferences.getString("passLogin","");

                username.getEditText().setText(userLogin);
                password.getEditText().setText(passLogin);
                chkRemember.setChecked(isRemember);
            }
        }

        callSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();

                boolean check1 = adminDAO.CheckAdLogin(user,pass);
                boolean check2 = userDAO.CheckLogin(user,pass);

                if(check1) {
                    if(chkRemember.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("LASTIME", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("userLogin", user);
                        editor.putString("passLogin", pass);
                        editor.putBoolean("isRemember",chkRemember.isChecked());
                        editor.apply();
                    }else {
                        SharedPreferences preferences = getSharedPreferences("LASTIME",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                    }

                    SharedPreferences preferences = getSharedPreferences("WELCOME",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userWelcome", user);
                    editor.apply();

                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this,MainAdminActivity.class));
                }else {
                    if(check2) {
                        if(chkRemember.isChecked()) {
                            SharedPreferences preferences = getSharedPreferences("LASTIME", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("userLogin", user);
                            editor.putString("passLogin", pass);
                            editor.putBoolean("isRemember",chkRemember.isChecked());
                            editor.apply();
                        }else {
                            SharedPreferences preferences = getSharedPreferences("LASTIME",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                        }

                        SharedPreferences preferences = getSharedPreferences("WELCOME",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userWelcome", user);
                        editor.apply();

                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this,MainUserActivity.class));
                    }else {
                        Toast.makeText(SignInActivity.this, "Tài khoản không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);

                Pair<View, String>[] pair = new Pair[7];

                pair[0] = new Pair(logo,"logo_img");
                pair[1] = new Pair(t1,"logo_text");
                pair[2] = new Pair(t2,"logo_desc");
                pair[3] = new Pair(username,"user_trans");
                pair[4] = new Pair(password,"password_trans");
                pair[5] = new Pair(callSignIn,"btn_sign_trans");
                pair[6] = new Pair(callSignUp,"signin_signup_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this,pair);

                startActivity(intent,options.toBundle());
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,ForgetActivity.class);

                Pair<View, String>[] pair = new Pair[6];

                pair[0] = new Pair(logo,"logo_img");
                pair[1] = new Pair(t1,"logo_text");
                pair[2] = new Pair(t2,"logo_desc");
                pair[3] = new Pair(username,"user_trans");
                pair[4] = new Pair(callSignIn,"btn_sign_trans");
                pair[5] = new Pair(callSignUp,"signin_signup_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this,pair);

                startActivity(intent,options.toBundle());
            }
        });
    }
}

