package com.aelinstudios.covid_tracker_2021;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {

    EditText edtsearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;

    public static List<ModelCountry> modelCountryList = new ArrayList<>();
    ModelCountry modelCountry;
    MyCustomAdapter myCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
    //connecting the layout elements to the Activity

        listView=findViewById(R.id.listView);
        edtsearch=findViewById(R.id.edtsearch);
        simpleArcLoader=findViewById(R.id.loader);

        //this will give back icon on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        // now setting onCLick listener on Lsit views items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
            }
        });

        //now to make the editSearch work
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // we have to initiate the adapter filter so that it checks each chararacter being entered in search bar
                myCustomAdapter.getFilter().filter(charSequence);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // this onclick is to go back to the home activity when back is pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url ="https://disease.sh/v3/covid-19/countries/";

        simpleArcLoader.start();

        //Create an Object for the StringRequest Class from the Volley Library
        StringRequest request=new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject= jsonArray.getJSONObject(i);

                            String countryName = jsonObject.getString("country");
                            String cases = jsonObject.getString("cases");
                            String todayCases = jsonObject.getString("todayCases");
                            String deaths = jsonObject.getString("deaths");
                            String todayDeaths = jsonObject.getString("todayDeaths");
                            String recovered = jsonObject.getString("recovered");
                            String critical = jsonObject.getString("critical");
                            String active = jsonObject.getString("active");
                            //  to fetch the flag we have the flag image inside an object so

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flag = object.getString("flag");

                        // create an object of Modelcountry class and pass these above fetched values to the constructor
                        modelCountry = new ModelCountry(flag,countryName,cases,todayCases,deaths,todayDeaths,recovered,critical,active);
                        // Pass this onject to the List
                        modelCountryList.add(modelCountry);

                    }

                    // initialize the List adapter
                    myCustomAdapter = new MyCustomAdapter(AffectedCountries.this,modelCountryList);
                    listView.setAdapter(myCustomAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        //Create an Object of class RequestQue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }
}
