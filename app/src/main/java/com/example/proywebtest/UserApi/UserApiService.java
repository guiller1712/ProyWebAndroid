package com.example.proywebtest.UserApi;

import com.example.proywebtest.Models.Reservacion;
import com.example.proywebtest.Models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("validaWeb2.php")
    Call<ArrayList<User>> obtenerUsuario(@Query("usu") String telefonouser, @Query("pas") String contra);

    @GET("reservacion.php")
    Call<ArrayList<Reservacion>> obtenerReservacion(@Query("usu") String telefonouser);
}
