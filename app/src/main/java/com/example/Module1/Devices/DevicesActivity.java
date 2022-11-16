package com.example.Module1.Devices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.Module1.R;

public class DevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        ((TextView) findViewById(R.id.title)).setText(getIntent().getStringExtra("title"));

        findViewById(R.id.lin1).findViewById(R.id.icon2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.light).setVisibility(View.INVISIBLE);
                findViewById(R.id.thermostat).setVisibility(View.VISIBLE);
                findViewById(R.id.lin1).setVisibility(View.GONE);
                findViewById(R.id.lin2).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.lin2).findViewById(R.id.icon2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.light).setVisibility(View.VISIBLE);
                findViewById(R.id.thermostat).setVisibility(View.INVISIBLE);
                findViewById(R.id.lin1).setVisibility(View.VISIBLE);
                findViewById(R.id.lin2).setVisibility(View.GONE);
            }
        });

    }
}