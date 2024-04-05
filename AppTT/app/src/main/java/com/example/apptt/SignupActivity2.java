package com.example.apptt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupActivity2 extends AppCompatActivity {

    Context context = SignupActivity2.this;

    TextInputEditText et_cin;
    TextInputEditText et_fName;
    TextInputEditText et_lName;
    TextInputEditText et_tel;
    Button btn_validate;
    TextView tv_warning;

    TextView tv_warning_CIN;
    TextView tv_warning_fName;
    TextView tv_warning_lName;
    TextView tv_warning_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        ActivityClassHelper.fullScreen((Activity) context);

        btn_validate = findViewById(R.id.btn_validate);
        et_cin = findViewById(R.id.et_cin);
        et_fName = findViewById(R.id.et_fname);
        et_lName = findViewById(R.id.et_lname);
        et_tel = findViewById(R.id.et_tel);
        tv_warning = findViewById(R.id.tv_warning);

        tv_warning_CIN = findViewById(R.id.tv_warning_CIN);
        tv_warning_fName = findViewById(R.id.tv_warning_fName);
        tv_warning_lName = findViewById(R.id.tv_warning_lName);
        tv_warning_phone = findViewById(R.id.tv_warning_phone);

        Intent intent = getIntent();

        btn_validate.setOnClickListener(v -> {
            Client client = (Client)intent.getSerializableExtra("client");

            client.setCIN(et_cin.getText().toString().trim());
            client.setFirst_name(et_fName.getText().toString().trim());
            client.setLast_name(et_lName.getText().toString().trim());
            client.setPhone(et_tel.getText().toString().trim());

            if ((client.getCIN().length()>0)&&(client.getFirst_name().length()>0)&&(client.getLast_name().length()>0)&&(client.getPhone().length()>0)) {
                Boolean [] signUp = {true};
                Pattern patternNames = Pattern.compile("\\p{Alpha}+");

                if (!patternNames.matcher(client.getFirst_name()).matches()) {
                    signUp[0] = false;
                    tv_warning_fName.setText("First name should only contain alphabetic characters !");
                }

                if (!patternNames.matcher(client.getLast_name()).matches()) {
                    signUp[0] = false;
                    tv_warning_lName.setText("Last name should only contain alphabetic characters !");
                }

                try {
                    Integer.parseInt(client.getPhone());
                }
                catch (NumberFormatException e) {
                    tv_warning_phone.setText("Phone number could only contain digits !");
                    signUp[0] = false;
                }
                try {
                    Integer.parseInt(client.getCIN());
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "http://" + AppConfig.localhostIp + "/App_TT/isUnique.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        if (response.equalsIgnoreCase("USED")) {
                            tv_warning_CIN.setText("CIN already used !");
                            signUp[0] = false;
                        }

                        if (signUp[0]) {
                            RequestQueue queue2 = Volley.newRequestQueue(context);
                            String url2 = "http://" + AppConfig.localhostIp + "/App_TT/signup.php";
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2, response2 -> {
                                Toast.makeText(context, response2, Toast.LENGTH_SHORT).show();
                            }, error -> Toast.makeText(context, "failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("CIN", client.getCIN());
                                    params.put("first_name", client.getFirst_name());
                                    params.put("last_name", client.getLast_name());
                                    params.put("email", client.getEmail());
                                    params.put("phone", client.getPhone());
                                    params.put("username", client.getUsername());
                                    params.put("password", client.getPassword());
                                    return params;
                                }
                            };
                            queue2.add(stringRequest2);

                            Intent signup_info = new Intent(context, LoginActivity.class);
                            startActivity(signup_info);
                            finish();
                        }

                    }, error -> Toast.makeText(context, "failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("column", "CIN");
                            params.put("value", client.getCIN());
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }catch (NumberFormatException e) {
                    tv_warning_CIN.setText("CIN could only contain digits !");
                }

            }else {
                tv_warning.setText("All fields are required !");
            }
        });
    }
}