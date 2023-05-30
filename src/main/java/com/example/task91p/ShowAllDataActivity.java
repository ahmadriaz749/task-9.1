package com.example.task91p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowAllDataActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    private static final int REMOVE_ADVERT_REQUEST_CODE = 1;
    private RecyclerView lostAndFoundRecyclerView;
    private Adapter adapter;
    private TextView headingTextView;
    private TextView noDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_data_activity);

        // Initialize views
        headingTextView = findViewById(R.id.headingTextView);
        noDataTextView = findViewById(R.id.noDataTextView);
        lostAndFoundRecyclerView = findViewById(R.id.lostAndFoundRecyclerView);

        // Configure RecyclerView
        lostAndFoundRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(new ArrayList<>(), this); // Pass the listener
        lostAndFoundRecyclerView.setAdapter(adapter);
        updateAdvertList();
    }

    // Check advert data is empty and display message
    private void checkDataEmpty(List<InformationOfAdvertData> informationOfAdvertDataList) {
        if (informationOfAdvertDataList.isEmpty()) {
            showNoDataMessage();
        } else {
            showAdverts();
        }
    }

    // Update the advert list with data from the database
    public void updateAdvertList() {
        DatabaseAdvert databaseAdvert = new DatabaseAdvert(this);
        List<InformationOfAdvertData> informationOfAdvertDataList = databaseAdvert.getAllAdverts();
        adapter.setAdvertList(informationOfAdvertDataList);
        adapter.notifyDataSetChanged();
        checkDataEmpty(informationOfAdvertDataList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Update the advert list after removing an advert
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REMOVE_ADVERT_REQUEST_CODE && resultCode == RESULT_OK) {
            updateAdvertList();
        }
    }

    // Shows No Data message
    private void showNoDataMessage() {
        noDataTextView.setVisibility(View.VISIBLE);
        lostAndFoundRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClickListener(long advertId) {
        // Open the RemoveData activity to remove the selected advert
        Intent intent = new Intent(ShowAllDataActivity.this, RemoveData.class);
        intent.putExtra("advert_id", advertId); // Pass the advert ID to the next activity
        startActivityForResult(intent, REMOVE_ADVERT_REQUEST_CODE);
    }

    // Show the list of adverts
    private void showAdverts() {
        noDataTextView.setVisibility(View.GONE);
        lostAndFoundRecyclerView.setVisibility(View.VISIBLE);
    }
}
