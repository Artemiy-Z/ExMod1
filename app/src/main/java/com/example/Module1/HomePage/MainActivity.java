package com.example.Module1.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Module1.AddRoom.AddRoomActivity;
import com.example.Module1.ApiClasses.API;
import com.example.Module1.ApiClasses.DeviceArray;
import com.example.Module1.ApiClasses.RoomArrayClass;
import com.example.Module1.ApiClasses.RoomClass;
import com.example.Module1.ApiClasses.UUIDGetter;
import com.example.Module1.Devices.DevicesActivity;
import com.example.Module1.R;
import com.example.Module1.Setting.SettingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RoomClass> rooms;
    private View temp;
    private API api;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = ret.create(API.class);
        table = findViewById(R.id.table);

        temp = getLayoutInflater().inflate(R.layout.mainpage_listitem, null, false);

        api.getRooms(getSharedPreferences("prefs", MODE_PRIVATE).getString("Token", ""), new UUIDGetter().id(getApplicationContext()))
                .enqueue(new Callback<RoomArrayClass>() {
                    @Override
                    public void onResponse(Call<RoomArrayClass> call, Response<RoomArrayClass> response) {
                        if(response.isSuccessful()) {
                            rooms = response.body().items;
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RoomArrayClass> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        findViewById(R.id.kitchen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DevicesActivity.class).putExtra("title", "Кухня"));
            }
        });

        findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRoomActivity.class));
                finish();
            }
        });

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                finish();
            }
        });
    }

    void r(int i) {

        if(i == rooms.size())
            return;
        RoomClass r = rooms.get(i);
        ((ImageView)temp.findViewById(R.id.image)).setImageDrawable(getDrawable(getDr(r.type)));
        ((TextView)temp.findViewById(R.id.name)).setText(r.name);
        api.getDevices(r.id, getSharedPreferences("prefs", MODE_PRIVATE).getString("Token", ""), new UUIDGetter().id(getApplicationContext()))
                .enqueue(new Callback<DeviceArray>() {
                    @Override
                    public void onResponse(Call<DeviceArray> call, Response<DeviceArray> response) {
                        if(response.isSuccessful()) {
                            ((TextView)temp.findViewById(R.id.devices)).setText(response.body().items.size());
                            Toast.makeText(getApplicationContext(), "Succesful", Toast.LENGTH_LONG).show();
                            r(i+1);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeviceArray> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    int getDr(String type) {
        switch (type) {
            case "Кухня":
                return R.drawable.kitchen;
            case "Спальня":
                return R.drawable.bedroom;
            case "Детская":
                return R.drawable.kidroom;
            case "Кабинет":
                return R.drawable.office;
            case "Кинотеатр":
                return R.drawable.tvroom;
            case "Гараж":
                return R.drawable.garage;
            case "Комната отдыха":
                return R.drawable.livingroom;
            case "Туалет":
                return R.drawable.toilet;
            case "Ванна":
                return R.drawable.bathroom;
        }

        return 0;
    }
}