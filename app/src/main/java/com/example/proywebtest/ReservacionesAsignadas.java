package com.example.proywebtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proywebtest.Background.BackgroundService;
import com.example.proywebtest.Background.ServiceGenerator;
import com.example.proywebtest.Models.Reservacion;
import com.example.proywebtest.UserApi.UserApiService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//@EActivity(R.layout.activity_reservaciones_asignadas)
public class ReservacionesAsignadas extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, chofer.OnFragmentInteractionListener, reservacion.OnFragmentInteractionListener, reservacion_asignada.OnFragmentInteractionListener,View.OnClickListener, BottomSheetExceso.BottomSheetListener {

    private static final String STRING_PREFERENCES = "com.example.proywebtest";
    private static final String CELULAR_USER = "user.telefonouser";
    private static final String TEST_FRAGMENT = "user.test"; //Esto es para probar el fragmento de reservacion asignada
    private static final String BASE_URL = "https://pelonchas-231194.000webhostapp.com/";

    private Retrofit retrofit;
    private Context context;
    private ProgressBar progressBarReserva;

    private BottomNavigationView btnNavegation;
    Fragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones_asignadas);
        Bundle extras = getIntent().getExtras();

        btnNavegation = findViewById(R.id.bottom_navegation);
        btnNavegation.setOnNavigationItemSelectedListener(this);
        progressBarReserva = (ProgressBar) findViewById(R.id.progressBarReserva);

        String telefonouser = extras.getString("telefonouser");
        Toast.makeText(getBaseContext(), "Celular del usuario es: " + telefonouser, Toast.LENGTH_LONG).show();

        tieneReservacion("Guasave");//Aqui debe de ir el numero de telefono, usar metodo obtieneTelefonoUser
    }

    private void tieneReservacion(String telefonouser){
        context = this;

        progressBarReserva.setMax(10);
        progressBarReserva.setVisibility(View.VISIBLE);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        UserApiService service = retrofit.create(UserApiService.class);
        final Call<ArrayList<Reservacion>> userCall = service.obtenerReservacion(telefonouser);
        userCall.enqueue(new Callback<ArrayList<Reservacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservacion>> call, Response<ArrayList<Reservacion>> response) {
                ArrayList<Reservacion> reservacionArrayList = response.body();
                if (reservacionArrayList.size() == 0){
                    Toast.makeText(getBaseContext(), "No existen reservaciones", Toast.LENGTH_LONG).show();
                    progressBarReserva.setVisibility(View.GONE);
                    ingresaReserva(false);
                    loadFragment(new reservacion());
                    return;
                }
                Toast.makeText(getBaseContext(), "ID camion: " + reservacionArrayList.get(0).getIdCamion() + " ID reservacion: " + reservacionArrayList.get(0).getFolioReservacion(), Toast.LENGTH_LONG).show();
                progressBarReserva.setVisibility(View.GONE);
                ingresaReserva(true);
                loadFragment(new reservacion_asignada());
                return;
            }

            @Override
            public void onFailure(Call<ArrayList<Reservacion>> call, Throwable t) {

            }
        });
        return;
    }

    private boolean obtieneReserva(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(TEST_FRAGMENT, false);
    }

    private void ingresaReserva(boolean r){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(TEST_FRAGMENT,r);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_reservacion:
                if(obtieneReserva()){
                    newFragment = new reservacion_asignada();
                    break;
                }
                newFragment = new reservacion();
                break;

            case R.id.action_chofer:
                newFragment = new chofer();
        }
        return loadFragment(newFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBottomClick(boolean exceso) {
        Toast.makeText(getBaseContext(), "Se selecciono: " + exceso, Toast.LENGTH_LONG).show();
    }
}
