package com.aelinstudios.covid_tracker_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int countryPosition;
    TextView tvCountry,tvCases,tvRecovered,tvActive,tvDeaths,tvTodayCases,tvTodaydeaths,tvCritical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        allFindViewCalls();

        Intent intent = getIntent();
        countryPosition = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+AffectedCountries.modelCountryList.get(countryPosition).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



         //setting up the textviews from the list
        tvCountry.setText(AffectedCountries.modelCountryList.get(countryPosition).getCountry());
        tvCases.setText(AffectedCountries.modelCountryList.get(countryPosition).getCases());
        tvActive.setText(AffectedCountries.modelCountryList.get(countryPosition).getActive());
        tvCritical.setText(AffectedCountries.modelCountryList.get(countryPosition).getCritical());
        tvRecovered.setText(AffectedCountries.modelCountryList.get(countryPosition).getRecovered());
        tvDeaths.setText(AffectedCountries.modelCountryList.get(countryPosition).getDeaths());
        tvTodayCases.setText(AffectedCountries.modelCountryList.get(countryPosition).getTodayCases());
        tvTodaydeaths.setText(AffectedCountries.modelCountryList.get(countryPosition).getTodayDeaths());
    }
    // this onclick is to go back to the home activity when back is pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void allFindViewCalls(){
        tvActive=findViewById(R.id.tv_Active);
        tvCases=findViewById(R.id.tv_Cases);
        tvRecovered=findViewById(R.id.tv_Recovered);
        tvCritical=findViewById(R.id.tv_Critical);
        tvTodayCases=findViewById(R.id.tv_TodayCases);
        tvTodaydeaths=findViewById(R.id.tv_TodayDeaths);
        tvDeaths=findViewById(R.id.tv_Deaths);
        tvCountry=findViewById(R.id.tvCountry);
    }
}