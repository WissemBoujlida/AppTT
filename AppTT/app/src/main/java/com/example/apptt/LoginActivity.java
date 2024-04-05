package com.example.apptt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Context context = LoginActivity.this;

    Button btn_login;
    EditText et_usr, et_pwd;
    ImageView iv_fb, iv_ins, iv_tw, iv_yt, iv_www;
    TextView tv_signMeUp, tv_warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityClassHelper.fullScreen((Activity) context);

        btn_login = findViewById(R.id.btn_login);
        et_usr = findViewById(R.id.et_usr);
        et_pwd = findViewById(R.id.et_pwd);
        iv_fb = findViewById(R.id.iv_FB);
        iv_ins = findViewById(R.id.iv_INS);
        iv_tw = findViewById(R.id.iv_TW);
        iv_yt = findViewById(R.id.iv_YT);
        iv_www = findViewById(R.id.iv_WWW);
        tv_signMeUp = findViewById(R.id.tv_signMeUp);
        tv_warning = findViewById(R.id.tv_warning);

        btn_login.setOnClickListener(v -> {
            Client client = new Client(et_usr.getText().toString().trim(), et_pwd.getText().toString().trim());
            client.login(LoginActivity.this, response -> {
                if (response.equals("Success")) {
                    client.welcome(context);
                    finish();
                }else {
                    tv_warning.setText(response);
                }
            });
        });

        iv_fb.setOnClickListener(view -> {
            String url = "https://www.facebook.com/TunisieTelecom";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });

        iv_ins.setOnClickListener(view -> {
            String url = "https://www.instagram.com/tunisietelecom";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });

        iv_tw.setOnClickListener(view -> {
            String url = "https://twitter.com/_tunisietelecom";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });

        iv_yt.setOnClickListener(view -> {
            String url = "https://www.youtube.com/user/groupett";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });

        iv_www.setOnClickListener(view -> {
            String url = "https://www.tunisietelecom.tn";
            Uri uriUrl = Uri.parse(url);EditText et_usr;
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });

        tv_signMeUp.setOnClickListener(view -> {
            Intent login_signup = new Intent(LoginActivity.this, SignupActivity1.class);
            startActivity(login_signup);
        }) ;
    }

    }
