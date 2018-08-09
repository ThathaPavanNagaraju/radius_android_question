package com.radius;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.radius.Entities.Facilities;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private retrofit2.Call<Facilities> call;
    private ProgressBar pbDialog;
    private TextView tvFetchData;
    private RecyclerView rvFacilities;
    private TextView tvSaveFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        tvFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbDialog.setVisibility(View.VISIBLE);
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                call = apiInterface.getFacilities();
                call.enqueue(new Callback<Facilities>() {
                    @Override
                    public void onResponse(retrofit2.Call<Facilities> call, Response<Facilities> response) {
                        pbDialog.setVisibility(View.GONE);
                        tvFetchData.setVisibility(View.GONE);
                        tvSaveFilters.setVisibility(View.VISIBLE);
                        bindData(response.body());
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Facilities> call, Throwable t) {
                        pbDialog.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,"Check your internet connection",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void initViews() {
        tvFetchData= (TextView) findViewById(R.id.tvFetchData);
        pbDialog = (ProgressBar) findViewById(R.id.pb_loading);
        rvFacilities = (RecyclerView) findViewById(R.id.rv_facilities);
        tvSaveFilters = (TextView) findViewById(R.id.tv_save_filters);
    }

    private void bindData(Facilities facilities) {
        ListAdapter listAdapter = new ListAdapter(this,facilities);
        rvFacilities.setLayoutManager(new LinearLayoutManager(this));
        rvFacilities.setAdapter(listAdapter);
    }
}