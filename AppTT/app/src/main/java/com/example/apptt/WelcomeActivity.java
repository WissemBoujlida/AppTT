package com.example.apptt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Context context = WelcomeActivity.this;

    Button btn_bills,btn_offers,btn_pay,btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActivityClassHelper.fullScreen((Activity) context);

        btn_bills = findViewById(R.id.btn_bills);
        btn_offers = findViewById(R.id.btn_offers);
        btn_pay = findViewById(R.id.btn_pay);
        btn_logout = findViewById(R.id.btn_logout);

        Bundle bundle = getIntent().getExtras();
        String CIN = bundle.getString("CIN");

        btn_bills.setOnClickListener(v -> {
            Intent welcome_bills = new Intent(WelcomeActivity.this, BillsActivity.class);
            welcome_bills.putExtra("CIN", CIN);
            WelcomeActivity.this.startActivity(welcome_bills);
        });

        btn_offers.setOnClickListener(v -> {
            Intent welcome_offers = new Intent(WelcomeActivity.this, OffersActivity.class);
            WelcomeActivity.this.startActivity(welcome_offers);
        });

        btn_logout.setOnClickListener(v -> {
            Intent welcome_login = new Intent(WelcomeActivity.this, LoginActivity.class);
            WelcomeActivity.this.startActivity(welcome_login);
            finish();
        });

        btn_pay.setOnClickListener(v -> {
            Intent welcome_pay = new Intent(WelcomeActivity.this, PayActivity.class);
            WelcomeActivity.this.startActivity(welcome_pay);
        });

    }
}