package com.example.Module1.AddRoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Module1.ApiClasses.API;
import com.example.Module1.ApiClasses.RoomResponse;
import com.example.Module1.ApiClasses.UUIDGetter;
import com.example.Module1.HomePage.MainActivity;
import com.example.Module1.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRoomActivity extends AppCompatActivity {

    private EditText name;
    private String type;

    private ImageButton kitchen;
    private ImageButton bedroom;
    private ImageButton livingroom;
    private ImageButton bathroom;
    private ImageButton toilet;
    private ImageButton tvroom;
    private ImageButton kidroom;
    private ImageButton garage;
    private ImageButton office;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = ret.create(API.class);

        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        String token = sp.getString("Token", null);
        if(token == null) {
            Toast.makeText(getApplicationContext(), "USER NOT AUTORIZED", Toast.LENGTH_LONG).show();
            return;
        }

        name = findViewById(R.id.name);
        kitchen = findViewById(R.id.kitchen);
        bedroom = findViewById(R.id.bedroom);
        livingroom = findViewById(R.id.livingroom);
        bathroom = findViewById(R.id.bathroom);
        toilet = findViewById(R.id.toilet);
        tvroom = findViewById(R.id.tvroom);
        kidroom = findViewById(R.id.kidroom);
        garage = findViewById(R.id.garage);
        office = findViewById(R.id.office);

        findViewById(R.id.kitchen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, true);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kitchen, false);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Кухня";
            }
        });

        findViewById(R.id.bedroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, true);
                setSelected(livingroom, false);
                setSelected(kidroom, false);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Спальня";
            }
        });

        findViewById(R.id.livingroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, true);
                setSelected(kidroom, false);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Комната отдыха";
            }
        });

        findViewById(R.id.tvroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kidroom, false);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, true);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Кинотеатр";
            }
        });

        findViewById(R.id.bathroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kidroom, false);
                setSelected(bathroom, true);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Ванна";
            }
        });

        findViewById(R.id.toilet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kidroom, false);
                setSelected(bathroom, false);
                setSelected(toilet, true);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Туалет";
            }
        });

        findViewById(R.id.garage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kidroom, false);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, true);
                setSelected(office, false);

                type = "Гараж";
            }
        });

        findViewById(R.id.kidroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kidroom, true);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, false);

                type = "Детская";
            }
        });

        findViewById(R.id.office).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(kitchen, false);
                setSelected(bedroom, false);
                setSelected(livingroom, false);
                setSelected(kidroom, false);
                setSelected(bathroom, false);
                setSelected(toilet, false);
                setSelected(tvroom, false);
                setSelected(garage, false);
                setSelected(office, true);

                type = "Кабинет";
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Введите название комнаты", Toast.LENGTH_LONG).show();
                    return;
                }
                if(type.equals("")){
                    Toast.makeText(getApplicationContext(), "Выберите тип комнаты", Toast.LENGTH_LONG).show();
                    return;
                }

                name.setText(String.valueOf(name.getText().toString().charAt(0)).toUpperCase(Locale.ROOT) + name.getText().toString().subSequence(1, name.getText().toString().length()-1));

                api.addRoom(name.getText().toString(), type, token, new UUIDGetter().id(getApplicationContext()))
                        .enqueue(new Callback<RoomResponse>() {
                            @Override
                            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                                if(response.isSuccessful()) {
                                    startActivity(new Intent(AddRoomActivity.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RoomResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    void setSelected(ImageButton im, boolean b) {
        if(b)
            im.setBackgroundTintList(ContextCompat.getColorStateList(AddRoomActivity.this, R.color.purple_state));
        else
            im.setBackgroundTintList(ContextCompat.getColorStateList(AddRoomActivity.this, R.color.white_state));
    }
}