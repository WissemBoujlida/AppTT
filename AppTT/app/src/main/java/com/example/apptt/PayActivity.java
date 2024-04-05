package com.example.apptt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PayActivity extends AppCompatActivity {

    Context context = PayActivity.this;

    TextInputEditText et_phone;
    TextInputEditText et_ref;
    TextInputEditText et_code;
    TextView tv_code;
    Button btn_pay;
    TextView tv_warning;
    TextView tv_warning_phone;
    TextView tv_warning_ref;
    TextView tv_warning_code;

    String phone;
    String ref;
    String randomCode;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ActivityClassHelper.fullScreen((Activity) context);

        et_phone = findViewById(R.id.et_phone);
        et_ref = findViewById(R.id.et_ref);
        et_code = findViewById(R.id.et_code);
        tv_code = findViewById(R.id.tv_code);
        btn_pay = findViewById(R.id.btn_pay);
        tv_warning = findViewById(R.id.tv_warning);
        tv_warning_phone = findViewById(R.id.tv_warning_phone);
        tv_warning_ref = findViewById(R.id.tv_warning_ref);
        tv_warning_code = findViewById(R.id.tv_warning_code);

        randomCode = randomCode();
        tv_code.setText(randomCode);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            ref = extras.getString("ref");
            et_ref.setText(ref);
        }

        btn_pay.setOnClickListener(view -> {

            phone = et_phone.getText().toString().trim();
            ref = et_ref.getText().toString().trim();
            code = et_code.getText().toString().trim();

            if (phone.length()>0 && ref.length()>0 && code.length()>0){
                boolean [] pay = {true};

                try{
                    Integer.parseInt(phone);
                }catch (NumberFormatException e){
                    pay[0] = false;
                    tv_warning_phone.setText("Phone number could only contain digits!");
                }

                if (!code.equals(randomCode)){
                    pay[0] = false;
                    tv_warning_code.setText("wrong code!");
                }

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://" + AppConfig.localhostIp + "/App_TT/exist.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    if (response.equalsIgnoreCase("NO")){
                        pay[0] = false;
                        tv_warning_phone.setText("Bill 's reference does not exist!");
                    }

                    if (pay[0]){
                        Toast.makeText(context, "I need to implement the payment API !!", Toast.LENGTH_SHORT).show();
                    }

                }, error -> Toast.makeText(context, "failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("ref", ref);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }else {
                tv_warning.setText("All fields are required !");
            }

        });
    }

    private String randomCode(){
        Random rand = new Random();
        String code="";

        for (int i = 0; i < 6; i++)
        {
            int index = rand.nextInt(94) + 33;
            code += Character.toString((char)index);
        }

        return code;
    }
}