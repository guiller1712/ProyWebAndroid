package com.example.proywebtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proywebtest.Models.Chofer;
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
    private static final String TOKEN_CHOFER = "chofer.token";
    private static final String NOMBRE_CHOFER = "chofer.nombre";
    private static final String PRIMER_CHOFER = "chofer.primer";
    private static final String SEGUNDO_CHOFER = "chofer.segundo";
    private static final String TELEFONO_CHOFER = "chofer.telefono";
    private static final String CORREO_CHOFER = "chofer.correo";
    private static final String ROL_CHOFER = "chofer.rol";

    private static final String BASE_URL = "http://www.proyweb.com.mx/";

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
        loginChofer(editTelefono.getText().toString(), editPassword.getText().toString());
    }

    private void loginChofer(String correoElectronico, String password){
        context = this;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        Chofer chofer = new Chofer(correoElectronico, password);
        UserApiService service = retrofit.create(UserApiService.class);
        final Call<Chofer> choferCall = service.loginChofer(chofer);
        choferCall.enqueue(new Callback<Chofer>() {
            @Override
            public void onResponse(Call<Chofer> call, Response<Chofer> response) {
                if(response.isSuccessful()){
                    Chofer ch = response.body();
                    if (ch == null){
                        Toast.makeText(getBaseContext(), "No existe el usuario", Toast.LENGTH_LONG).show();
                        btnConsultar.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                    Toast.makeText(getBaseContext(), "Bienvenido " + ch.getNombre(), Toast.LENGTH_LONG).show();
                    Intent reservaIntent = new Intent(MainActivity.this, ReservacionesAsignadas.class);
                    btnConsultar.setClickable(true);
                    progressBar.setVisibility(View.GONE);

                    guardarSesionUsuario(ch.getToken(), ch.getNombre(), ch.getPrimerApellido(), ch.getSegundoApellido(), ch.getTelefono(), ch.getCorreoElectronico(), ch.getRol());

                    startActivity(reservaIntent);
                    finish();
                    return;
                }
                Toast.makeText(getBaseContext(), "No existe el usuario", Toast.LENGTH_LONG).show();
                btnConsultar.setClickable(true);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Chofer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "¡Ups! Algo pasó. Reintenta de nuevo.", Toast.LENGTH_LONG).show();
                btnConsultar.setClickable(true);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void guardarSesionUsuario(String token, String nombre, String primer, String segundo, String telefono, String correo, String rol){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(TOKEN_CHOFER, token).apply();
        sharedPreferences.edit().putString(NOMBRE_CHOFER, nombre).apply();
        sharedPreferences.edit().putString(PRIMER_CHOFER, primer).apply();
        sharedPreferences.edit().putString(SEGUNDO_CHOFER, segundo).apply();
        sharedPreferences.edit().putString(TELEFONO_CHOFER, telefono).apply();
        sharedPreferences.edit().putString(CORREO_CHOFER, correo).apply();
        sharedPreferences.edit().putString(ROL_CHOFER, rol).apply();
    }
}
