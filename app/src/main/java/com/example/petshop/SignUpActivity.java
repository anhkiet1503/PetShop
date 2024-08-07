package com.example.petshop;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SignUpActivity extends AppCompatActivity {
    Button regBtn, regToSignInBtn;

    ImageView logo;
    TextView t1, t2;
    TextInputLayout name, username, password, rePassword;
    private UserDAO userDAO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        logo = findViewById(R.id.logoImg);
        t1 = findViewById(R.id.logoHi);
        t2 = findViewById(R.id.slogan);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.re_password);

        regBtn = findViewById(R.id.signup_btn);
        regToSignInBtn = findViewById(R.id.signin_screen);

        userDAO = new UserDAO(this);

        name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    name.setError("Vui lòng nhập họ và tên");
                }else {
                    name.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    username.setError("Vui lòng nhập username");
                }else {
                    username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    password.setError("Vui lòng nhập password");
                }else {
                    password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rePassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    rePassword.setError("Vui lòng nhập lại password");
                }else {
                    rePassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = name.getEditText().getText().toString();
                String user = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                String rePass = rePassword.getEditText().getText().toString();

                if(fullname.equals("") || user.equals("") || pass.equals("") || rePass.equals("")) {
                    if(fullname.equals("")) {
                        name.setError("Vui lòng nhập họ và tên");
                    }else {
                        name.setError(null);
                    }

                    if(user.equals("")) {
                        username.setError("Vui lòng nhập username");
                    }else {
                        name.setError(null);
                    }

                    if(pass.equals("")) {
                        password.setError("Vui lòng nhập password");
                    }else {
                        name.setError(null);
                    }

                    if(rePass.equals("")) {
                        rePassword.setError("Vui lòng nhập lại password");
                    }else {
                        name.setError(null);
                    }
                }else if(!pass.equals(rePass)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }else {
                    boolean checkAcc = userDAO.CheckRegister(user);
                    if(checkAcc) {
                        Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }else {
                        boolean check = userDAO.Register(fullname,user,pass);
                        if(check){
                            SharedPreferences preferences = getSharedPreferences("REGISTER", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("userRegister", user);
                            editor.apply();

                            Toast.makeText(SignUpActivity.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                        }else {
                            Toast.makeText(SignUpActivity.this,"Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        regToSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);

                Pair<View, String>[] pair = new Pair[7];

                pair[0] = new Pair(logo,"logo_img");
                pair[1] = new Pair(t1,"logo_text");
                pair[2] = new Pair(t2,"logo_desc");
                pair[3] = new Pair(username,"user_trans");
                pair[4] = new Pair(password,"password_trans");
                pair[5] = new Pair(regBtn,"btn_sign_trans");
                pair[6] = new Pair(regToSignInBtn,"signin_signup_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this,pair);

                startActivity(intent,options.toBundle());
            }
        });
    }

    public static class MainAdminActivity {
    }
}
