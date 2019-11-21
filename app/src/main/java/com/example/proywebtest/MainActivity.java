package com.example.proywebtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proywebtest.Models.User;
import com.example.proywebtest.UserApi.UserApiService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String STRING_PREFERENCES = "com.example.proywebtest";
    private static final String CELULAR_USER = "user.telefonouser";

    private Retrofit retrofit;
    private Context context;
    private ProgressBar progressBar;

    private Button btnConsultar;
    private EditText editTelefono;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnConsultar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        btnConsultar.setClickable(false);
        progressBar.setMax(10);
        progressBar.setVisibility(View.VISIBLE);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editPassword = (EditText) findViewById(R.id.editPassword);
        consultarUsuario(editTelefono.getText().toString(), editPassword.getText().toString());
    }

    private void consultarUsuario(String telefono, String password){
        context = this;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pelonchas-231194.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        UserApiService service = retrofit.create(UserApiService.class);
        final Call<ArrayList<User>> userCall = service.obtenerUsuario(telefono, password);

        userCall.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if(response.isSuccessful()){
                    ArrayList<User> userArrayList = response.body();
                    if (userArrayList.size() == 0){
                        Toast.makeText(getBaseContext(), "No existe el usuario", Toast.LENGTH_LONG).show();
                        btnConsultar.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    Toast.makeText(getBaseContext(), userArrayList.get(0).getModemid() + " " + userArrayList.get(0).getNombreuser(), Toast.LENGTH_LONG).show();
                    Intent reservaIntent = new Intent(MainActivity.this, ReservacionesAsignadas.class);
                    reservaIntent.putExtra("telefonouser", userArrayList.get(0).getTelefonouser());
                    btnConsultar.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                    guardarSesionUsuario(userArrayList.get(0).getTelefonouser());
                    startActivity(reservaIntent);
                    finish();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "¡Ups! Algo pasó. Reintenta de nuevo.", Toast.LENGTH_LONG).show();
                btnConsultar.setClickable(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void guardarSesionUsuario(String telefonouser){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(CELULAR_USER, telefonouser).apply();
    }
}
