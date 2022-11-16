package com.example.Module1.NewResident;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Module1.ApiClasses.API;
import com.example.Module1.ApiClasses.App;
import com.example.Module1.ApiClasses.SequreClass;
import com.example.Module1.ApiClasses.TokenClass;
import com.example.Module1.ApiClasses.UUIDGetter;
import com.example.Module1.EnterYourHouse.EnterActivity;
import com.example.Module1.HomePage.MainActivity;
import com.example.Module1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText pasw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        pasw = findViewById(R.id.pasw);
        name = findViewById(R.id.username);

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = ret.create(API.class);

        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, EnterActivity.class));
                finish();
            }
        });

        findViewById(R.id.newres).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.register(email.getText().toString(), name.getText().toString(), pasw.getText().toString(), new UUIDGetter().id(getApplicationContext()))
                        .enqueue(new Callback<SequreClass>() {
                            public void onResponse(Call<SequreClass> call, Response<SequreClass> response) {
                                if(response.isSuccessful()) {
                                    getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("Sequre", response.body().getSequre()).apply();
                                    api.auth(email.getText().toString(), pasw.getText().toString(), new UUIDGetter().id(getApplicationContext()))
                                            .enqueue(new Callback<TokenClass>() {
                                                @Override
                                                public void onResponse(Call<TokenClass> call, Response<TokenClass> response) {
                                                    if(response.isSuccessful()) {
                                                        getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("Token", response.body().getToken()).apply();
                                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
                                else {
                                    Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SequreClass> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}