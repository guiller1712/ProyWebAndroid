package com.example.proywebtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.proywebtest.Models.Chofer;

public class Splash extends AppCompatActivity {

    private static final String STRING_PREFERENCES = "com.example.proywebtest";
    private static final String CELULAR_USER = "user.telefonouser";

    private static final String TOKEN_CHOFER = "chofer.token";
    private static final String NOMBRE_CHOFER = "chofer.nombre";
    private static final String PRIMER_CHOFER = "chofer.primer";
    private static final String SEGUNDO_CHOFER = "chofer.segundo";
    private static final String TELEFONO_CHOFER = "chofer.telefono";
    private static final String CORREO_CHOFER = "chofer.correo";
    private static final String ROL_CHOFER = "chofer.rol";
    private static int SPLASH_SCREEN_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        setContentView(R.layout.activity_splash);
        //this will bind your MainActivity.class file with activity_main.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String telefonouser = obtieneChofer().getCorreoElectronico();
                if (telefonouser.equals("0000000000")){
                    Intent i = new Intent(Splash.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
                Intent i = new Intent(Splash.this,
                        ReservacionesAsignadas.class);
                i.putExtra("telefonouser", telefonouser);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private String obtenerSesionUsuario(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(CELULAR_USER, "0000000000");
    }

    private Chofer obtieneChofer(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        Chofer c = new Chofer(
                sharedPreferences.getString(NOMBRE_CHOFER, "nombre"),
                sharedPreferences.getString(PRIMER_CHOFER, "primer"),
                sharedPreferences.getString(SEGUNDO_CHOFER, "segundo"),
                sharedPreferences.getString(TELEFONO_CHOFER, "telefono"),
                sharedPreferences.getString(CORREO_CHOFER, "correo"),
                sharedPreferences.getString(ROL_CHOFER, "rol"));
        return c;
    }
}
