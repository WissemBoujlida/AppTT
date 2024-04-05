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

public class SignupActivity1 extends AppCompatActivity {

    Context context = SignupActivity1.this;

    TextInputEditText et_email;
    TextInputEditText et_username;
    TextInputEditText et_password;
    TextInputEditText et_confirm_pwd;
    Button btn_next;
    TextView tv_warning;
    TextView signMeUp;
    String confirm_pwd;

    TextView tv_warning_email;
    TextView tv_warning_usr;
    TextView tv_warning_pwd;
    TextView tv_warning_confirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        ActivityClassHelper.fullScreen((Activity) context);

        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confirm_pwd = findViewById(R.id.et_pwdconfirm);
        btn_next = findViewById(R.id.btn_next);
        tv_warning = findViewById(R.id.tv_warning);
        signMeUp = findViewById(R.id.tv_signmein);

        tv_warning_email = findViewById(R.id.tv_warning_email);
        tv_warning_usr = findViewById(R.id.tv_warning_usr);
        tv_warning_pwd = findViewById(R.id.tv_warning_pwd);
        tv_warning_confirmPwd = findViewById(R.id.tv_warning_confirmPwd);


        btn_next.setOnClickListener(view -> {

            Client client = new Client();
            client.setEmail(et_email.getText().toString().trim());
            client.setUsername(et_username.getText().toString().trim());
            client.setPassword(et_password.getText().toString().trim());
            confirm_pwd = et_confirm_pwd.getText().toString().trim();

            if ((client.getEmail().length()>0)&&(client.getUsername().length()>0)&&(client.getPassword().length()>0)&&(confirm_pwd.length()>0)) {

                Boolean [] signUp = {true};
                Pattern patternEmail = Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
                Pattern patternPwd = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$");

                if (!patternEmail.matcher(client.getEmail()).matches()) {
                    tv_warning_email.setText("Invalid email : your email should look like: example@example.example");
                    signUp[0] = false;
                }

                if (!(patternPwd.matcher(client.getPassword()).matches() && client.getPassword().length()>=8)) {
                    tv_warning_pwd.setText("Password should be minimum 8 characters, have at least 1 upper case, 1 lower case letter and 1 digit!");
                    signUp[0] = false;
                }

                if (!client.getPassword().equals(confirm_pwd)) {
                    tv_warning_confirmPwd.setText("Confirm password does not match!!");
                    signUp[0] = false;
                }

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://" + AppConfig.localhostIp + "/App_TT/isUnique.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    if (response.equalsIgnoreCase("USED")){
                        tv_warning_usr.setText("Username already taken !");
                        signUp[0] = false;
                    }

                    if (signUp[0]){
                        Intent signup_info = new Intent(context, SignupActivity2.class);
                        signup_info.putExtra("client", client);
                        startActivity(signup_info);
                    }

                }, error -> Toast.makeText(context, "failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("column", "USERNAME");
                        params.put("value", client.getUsername());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }else {
                tv_warning.setText("Please fill in all fields !");
            }

        });
        signMeUp.setOnClickListener(view -> {
            Intent signup_login = new Intent(SignupActivity1.this, LoginActivity.class);
            startActivity(signup_login);
        });
    }

}