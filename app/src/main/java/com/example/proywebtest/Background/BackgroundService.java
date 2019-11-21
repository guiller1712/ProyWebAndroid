package com.example.proywebtest.Background;

import android.app.IntentService;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.proywebtest.Models.Reservacion;
import com.example.proywebtest.UserApi.UserApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundService extends IntentService {

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Reservacion reservacion = new Reservacion();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pelonchas-231194.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        UserApiService service = retrofit.create(UserApiService.class);
        final Call<ArrayList<Reservacion>> userCall = service.obtenerReservacion("Guasave");
        try {
            Response<ArrayList<Reservacion>> arrayListResponse = userCall.execute();
            ArrayList<Reservacion> reservacionArrayList = arrayListResponse.body();
            if (reservacionArrayList.size() == 0){
                Toast.makeText(getBaseContext(), "No existen reservaciones", Toast.LENGTH_LONG).show();
                //progressBarReserva.setVisibility(View.GONE);
                return;
            }
            Toast.makeText(getBaseContext(), "ID camion: " + reservacionArrayList.get(0).getIdCamion() + " ID reservacion: " + reservacionArrayList.get(0).getFolioReservacion(), Toast.LENGTH_LONG).show();
            //progressBarReserva.setVisibility(View.GONE);
            //tieneR = true;
            //finish();
            return;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
