package com.example.task91p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createNewAdvertButton;
    private Button showAllItemsButton;
    private Button ShowOnMapsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewAdvertButton = findViewById(R.id.nnew);
        showAllItemsButton = findViewById(R.id.show);
        ShowOnMapsButton = findViewById(R.id.maps);

        createNewAdvertButton.setOnClickListener(this);
        showAllItemsButton.setOnClickListener(this);
        ShowOnMapsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HashMap<Integer, Class<?>> intentMap = new HashMap<>();
        intentMap.put(R.id.nnew, NewAdvertCreate.class);
        intentMap.put(R.id.show, ShowAllDataActivity.class);
        intentMap.put(R.id.maps, ShowAllMapsMarkLoaction.class);

        Class<?> destinationClass = intentMap.get(v.getId());
        if (destinationClass != null) {
            Intent intent = new Intent(MainActivity.this, destinationClass);
            startActivity(intent);
        }
    }
}