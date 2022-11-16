package com.example.Module1.EnterYourHouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Module1.ApiClasses.API;
import com.example.Module1.ApiClasses.TokenClass;
import com.example.Module1.ApiClasses.UUIDGetter;
import com.example.Module1.HomePage.MainActivity;
import com.example.Module1.NewResident.RegisterActivity;
import com.example.Module1.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnterActivity extends AppCompatActivity {

    private EditText email;
    private EditText pasw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = ret.create(API.class);

        email = findViewById(R.id.email);
        pasw = findViewById(R.id.pasw);

        findViewById(R.id.newres).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnterActivity.this, RegisterActivity.class));
                finish();
            }
        });

        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Введите e-mail", Toast.LENGTH_LONG).show();
                    return;
                }

                if(pasw.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Введите Пароль", Toast.LENGTH_LONG).show();
                    return;
                }

                Pattern pat = Pattern.compile("^(.+)@(.+)$");
                Matcher m = pat.matcher(email.getText().toString());

                if(!m.matches()) {
                    Toast.makeText(getApplicationContext(), "Неверный формат e-mail", Toast.LENGTH_LONG).show();
                    return;
                }

                UUIDGetter getter = new UUIDGetter();

                api.auth(email.getText().toString(), pasw.getText().toString(), getter.id(getApplicationContext()))
                        .enqueue(new Callback<TokenClass>() {
                            @Override
                            public void onResponse(Call<TokenClass> call, Response<TokenClass> response) {
                                if(response.isSuccessful()) {
                                    getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("Token", response.body().getToken()).apply();
                                    startActivity(new Intent(EnterActivity.this, MainActivity.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenClass> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}