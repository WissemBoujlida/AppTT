package com.example.apptt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OffersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context context = OffersActivity.this;

    RecyclerView recyclerView;

    String type;
    Spinner spinner;
    String selection;
    ArrayAdapter<CharSequence> adapterSpinner;
    AdapterForRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offres);
        ActivityClassHelper.fullScreen((Activity) context);

        spinner = findViewById(R.id.spinner);
        adapterSpinner = ArrayAdapter.createFromResource(this, R.array.OfferType, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);
        selection = spinner.getSelectedItem().toString();

        TextInputEditText search_bar = findViewById(R.id.et_search_bar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (i==0){
            getOffers();
            type = "%";
        } else {
            type = adapterView.getItemAtPosition(i).toString().trim();
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "http://" + AppConfig.localhostIp + "/App_TT/getFilteredOffers.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                AdapterForRecyclerView adapter;
                JSONArray jsonArray;
                ArrayList<Offer> filteredOffersList;
                try {
                    jsonArray = new JSONArray(response);
                    filteredOffersList = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        Offer offer;
                        try {
                            offer = new Offer(jsonObject.getString("NAME"),
                                    jsonObject.getString("TYPE"), jsonObject.getString("BRIEF_DESCRIPTION"),
                                    jsonObject.getString("DESCRIPTION"), R.drawable.class.getField(jsonObject.getString("IMAGE")).getInt(null));
                            filteredOffersList.add(offer);
                        } catch (Exception e) {
                            Toast.makeText(context, "Failed to get drawable id!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    adapter = new AdapterForRecyclerView(this, filteredOffersList);
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(context, "Failed to reach our servers!", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("type", type);
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void filter(String text){

        String search;
        if (text.trim().length()>0){
            search = text+"%";
        }else {
            search = "%";
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + AppConfig.localhostIp + "/App_TT/searchOffer.php";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url, response -> {

            AdapterForRecyclerView adapter;
            JSONArray jsonArray;
            ArrayList<Offer> filteredOffersList;
            try {
                jsonArray = new JSONArray(response);
                filteredOffersList = new ArrayList<>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    Offer offer;
                    try {
                        offer = new Offer(jsonObject.getString("NAME"),
                                jsonObject.getString("TYPE"), jsonObject.getString("BRIEF_DESCRIPTION"),
                                jsonObject.getString("DESCRIPTION"), R.drawable.class.getField(jsonObject.getString("IMAGE")).getInt(null));
                        filteredOffersList.add(offer);
                    }catch (Exception e){
                        Toast.makeText(context, "Failed to get drawable id!", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter = new AdapterForRecyclerView(this, filteredOffersList);
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(context, "Failed to reach our servers!", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", search);
                params.put("type", type);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getOffers(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + AppConfig.localhostIp + "/App_TT/getOffers.php";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url, response -> {
            JSONArray jsonArray;
            ArrayList<Offer> offersList;
            try {
                jsonArray = new JSONArray(response);
                offersList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Offer offer;
                    try {
                        offer = new Offer(jsonObject.getString("NAME"),
                                jsonObject.getString("TYPE"), jsonObject.getString("BRIEF_DESCRIPTION"),
                                jsonObject.getString("DESCRIPTION"), R.drawable.class.getField(jsonObject.getString("IMAGE")).getInt(null));
                        offersList.add(offer);
                    }catch (Exception e){
                        Toast.makeText(context, "Failed to get drawable id!", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter = new AdapterForRecyclerView(this, offersList);
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(context, "Failed to reach our servers!", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

}
