package com.example.apptt;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Client implements Serializable {

    private String CIN;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Client() {
    }

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Client(String CIN, String first_name, String last_name, String email, String phone, String username, String password) {
        this.CIN = CIN;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "CIN='" + CIN + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void login(Context context, final VolleyCallback callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + AppConfig.localhostIp + "/App_TT/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            callback.onSuccess(response);
        }, error -> Toast.makeText(context, "failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void welcome(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + AppConfig.localhostIp + "/App_TT/retrieveId.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String CIN = jsonObject.getString("CIN");
                Intent login_welcome = new Intent(context, WelcomeActivity.class);
                login_welcome.putExtra("CIN", CIN);
                context.startActivity(login_welcome);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(context, "failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
