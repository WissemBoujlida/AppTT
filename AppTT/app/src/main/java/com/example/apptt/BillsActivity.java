package com.example.apptt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillsActivity extends AppCompatActivity {

    TableLayout table;
    EditText et_searchedRef;
    String searchedRef;
    Context context = BillsActivity.this;
    boolean test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        ActivityClassHelper.fullScreen((Activity) context);

        table = findViewById(R.id.tableLayout);

        Bundle bundle = getIntent().getExtras();
        String CIN = bundle.getString("CIN");

        //getResponse(response -> setVar(true));


        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + AppConfig.localhostIp + "/App_TT/bills.php";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url, response -> {
            JSONArray jsonArray;
            List<Bill> bills;
            try {
                jsonArray = new JSONArray(response);
                bills = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Bill bill = new Bill(jsonObject.getInt("REFERENCE"),
                            jsonObject.getString("TYPE"), jsonObject.getString("BILLING_DATE"),
                            (float)jsonObject.getDouble("COST"), jsonObject.getInt("STATE"));
                    bills.add(bill);
                }
                showBills(bills);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(context, "Failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cin", CIN);
                return params;
            }
        };
        queue.add(stringRequest);


        et_searchedRef = findViewById(R.id.et_ref);
        et_searchedRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_searchedRef.getText().toString().trim().length()==0) {
                    searchedRef = "___%";
                } else {
                    searchedRef = et_searchedRef.getText().toString() + "%";
                }

                while (table.getChildCount() > 2) {
                    table.removeView(table.getChildAt(table.getChildCount() - 1));
                }

                List<Bill> searchedBills = new ArrayList<>();
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://" + AppConfig.localhostIp + "/App_TT/searchBill.php";
                StringRequest stringRequest = new StringRequest( Request.Method.POST, url, response -> {
                    JSONArray jsonArray;
                    try {
                        jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Bill bill = new Bill(jsonObject.getInt("REFERENCE"),
                                    jsonObject.getString("TYPE"), jsonObject.getString("BILLING_DATE"),
                                    (float)jsonObject.getDouble("COST"), jsonObject.getInt("STATE"));
                            searchedBills.add(bill);
                        }
                        showBills(searchedBills);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(context, "Failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("cin", String.valueOf(CIN));
                        params.put("ref", String.valueOf(searchedRef));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });

    }

    private void showBills(List<Bill> bills){
        for (Bill bill : bills) {
            TableRow row = (TableRow) LayoutInflater.from(context).inflate(R.layout.table_row, null);
            TextView tv_ref = row.findViewById(R.id.tv_ref);
            tv_ref.setText(Integer.toString(bill.getReference()));
            TextView tv_type = row.findViewById(R.id.tv_type);
            tv_type.setText(bill.getType());
            TextView tv_date = row.findViewById(R.id.tv_date);
            tv_date.setText(bill.getBilling_date());
            TextView tv_cost = row.findViewById(R.id.tv_cost);
            tv_cost.setText(Float.toString(bill.getCost()));
            TextView tv_state = row.findViewById(R.id.tv_state);
            if (bill.getState() == 0) {
                tv_state.setText("unpaid");
                tv_state.setTextColor(Color.parseColor("#c84148"));
                row.setClickable(true);
                row.setOnClickListener(view -> {
                    Intent bills_pay = new Intent(context, PayActivity.class);
                    bills_pay.putExtra("ref", Integer.toString(bill.getReference()));
                    context.startActivity(bills_pay);
                });
            } else {
                tv_state.setText("paid");
                tv_state.setTextColor(Color.parseColor("#45685b"));
            }
            table.addView(row);
        }
        table.requestLayout();
    }
}
