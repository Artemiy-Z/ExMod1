package com.example.Module1.Launch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Module1.ApiClasses.API;
import com.example.Module1.ApiClasses.App;
import com.example.Module1.ApiClasses.AppArray;
import com.example.Module1.ApiClasses.KeyClass;
import com.example.Module1.ApiClasses.MobileClass;
import com.example.Module1.ApiClasses.TokenClass;
import com.example.Module1.ApiClasses.UUIDGetter;
import com.example.Module1.BuildConfig;
import com.example.Module1.EnterYourHouse.EnterActivity;
import com.example.Module1.HomePage.MainActivity;
import com.example.Module1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchActivity extends AppCompatActivity {

    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        ImageView logo = findViewById(R.id.logo);

        RotateAnimation rotA = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotA.setDuration(500);
        rotA.setFillAfter(true);
        logo.startAnimation(rotA);

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = ret.create(API.class);

        api.getApps("Competitor-1").enqueue(new Callback<AppArray>() {
            @Override
            public void onResponse(Call<AppArray> call, Response<AppArray> response) {
                if(response.isSuccessful()) {
                    if(response.body().array.size() == 0) {
                        regMobile();
                    }
                    else {
                        App app = new App();
                        app.appId = BuildConfig.APPLICATION_ID;
                        app.competitor = getString(R.string.competitor);
                        api.regApp(app).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if(response.isSuccessful()){
                                    regMobile();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AppArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void regMobile(){
        MobileClass mob = new MobileClass();
        mob.uuid = new UUIDGetter().id(getApplicationContext());
        mob.appId = BuildConfig.APPLICATION_ID;
        mob.device = Build.MODEL;

        api.regMobile(mob).enqueue(new Callback<KeyClass>() {
            @Override
            public void onResponse(Call<KeyClass> call, Response<KeyClass> response) {
                if(response.isSuccessful()) {
                    getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("keyDevice", response.body().keyDevice).apply();
                    startActivity(new Intent(LaunchActivity.this, EnterActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<KeyClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}