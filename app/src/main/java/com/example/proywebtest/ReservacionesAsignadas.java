package com.example.proywebtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proywebtest.Background.BackgroundService;
import com.example.proywebtest.Background.ServiceGenerator;
import com.example.proywebtest.Models.Chofer;
import com.example.proywebtest.Models.Exceso;
import com.example.proywebtest.Models.Reservacion;
import com.example.proywebtest.UserApi.UserApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String RESERVACION_BOOL = "reservacion.bool"; //Esto es para probar el fragmento de reservacion asignada
    private static final String BASE_URL = "https://pelonchas-231194.000webhostapp.com/";
    private static final String RESERVACION_EXCESO = "reservacion.exceso";
    private static final String RESERVACION_FOLIO = "reservacion.folio";

    private Retrofit retrofit;
    private Context context;
    private ProgressBar progressBarReserva;
    private Button btnReportarExceso;

    private BottomNavigationView btnNavegation;
    Fragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones_asignadas);

        btnNavegation = findViewById(R.id.bottom_navegation);
        btnNavegation.setOnNavigationItemSelectedListener(this);
        progressBarReserva = (ProgressBar) findViewById(R.id.progressBarReserva);
        btnReportarExceso = (Button) findViewById(R.id.btnReportarExceso);

        tieneReservacion("Guasave");//Aqui debe de ir el numero de telefono, usar metodo obtieneTelefonoUser
    }

    private void tieneReservacion(String telefonouser){
        context = this;

        progressBarReserva.setMax(10);
        progressBarReserva.setVisibility(View.VISIBLE);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
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
                progressBarReserva.setVisibility(View.GONE);
                ingresaReserva(true);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = reservacion_asignada.newInstance(reservacionArrayList);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                return;
            }

            @Override
            public void onFailure(Call<ArrayList<Reservacion>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "¡Ups! Algo pasó. Reintenta de nuevo.", Toast.LENGTH_LONG).show();
                progressBarReserva.setVisibility(View.GONE);
            }
        });
        return;
    }

    private void ingresaReserva(boolean r){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(RESERVACION_BOOL,r).apply();
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
        boolean change = false;
        switch (menuItem.getItemId()){
            case R.id.action_reservacion:
                tieneReservacion("Guasave");
                change = true;
                break;

            case R.id.action_chofer:
                newFragment = new chofer();
                change = loadFragment(newFragment);
                break;
        }
        return change;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBottomClick(boolean exceso) {
        //Toast.makeText(getBaseContext(), "Se selecciono: " + exceso, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setExceso(String folio){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.proyweb.com.mx/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        if(consultaExcesoReservacion() && !(consultaReservacionFolio().equals("0000000000"))){
            Toast.makeText(getBaseContext(), "Ya se reportó exceso", Toast.LENGTH_LONG).show();
            return;
        }
        UserApiService service = retrofit.create(UserApiService.class);
        Exceso e = new Exceso(folio);
        final Call<Exceso> userCall = service.registrarExceso(e);
        userCall.enqueue(new Callback<Exceso>() {
            @Override
            public void onResponse(Call<Exceso> call, Response<Exceso> response) {
                if(response.isSuccessful()){
                    Exceso e = response.body();
                    if(e == null){
                        Toast.makeText(getBaseContext(), "Algo salió mal, intente de nuevo.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(getBaseContext(), "Se registró un exceso de carga para el folio:  " + e.getFolio(), Toast.LENGTH_LONG).show();
                    ingresaExcesoReservacion(true);
                    ingresaReservacionFolio(e.getFolio());
                    //btnReportarExceso.setBackgroundColor(141);
                    //btnReportarExceso.setClickable(false);
                    return;
                }
            }

            @Override
            public void onFailure(Call<Exceso> call, Throwable t) {
                Toast.makeText(getBaseContext(), "¡Ups! Algo pasó. Reintenta de nuevo.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ingresaExcesoReservacion(boolean e){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(RESERVACION_EXCESO,e).apply();
    }

    private boolean consultaExcesoReservacion(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(RESERVACION_EXCESO,false);
    }

    private void ingresaReservacionFolio(String f){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(RESERVACION_FOLIO,f).apply();
    }

    private String consultaReservacionFolio(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(RESERVACION_FOLIO,"0");
    }
}
