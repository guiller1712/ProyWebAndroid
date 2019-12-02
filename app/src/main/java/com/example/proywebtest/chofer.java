package com.example.proywebtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proywebtest.Models.Chofer;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link chofer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link chofer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chofer extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String STRING_PREFERENCES = "com.example.proywebtest";
    private static final String CELULAR_USER = "user.telefonouser";

    private TextView correoChoferText;
    private TextView celularChoferText;
    private TextView choferTitle;
    private Button btnCerrarSesion;

    private static final String TOKEN_CHOFER = "chofer.token";
    private static final String NOMBRE_CHOFER = "chofer.nombre";
    private static final String PRIMER_CHOFER = "chofer.primer";
    private static final String SEGUNDO_CHOFER = "chofer.segundo";
    private static final String TELEFONO_CHOFER = "chofer.telefono";
    private static final String CORREO_CHOFER = "chofer.correo";
    private static final String ROL_CHOFER = "chofer.rol";

    private static final String RESERVACION_EXCESO = "reservacion.exceso";
    private static final String RESERVACION_FOLIO = "reservacion.folio";

    public chofer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chofer.
     */
    // TODO: Rename and change types and number of parameters
    public static chofer newInstance(String param1, String param2) {
        chofer fragment = new chofer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chofer, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Chofer c = obtieneChofer();
        btnCerrarSesion = (Button) getView().findViewById(R.id.btnCerrarSesion);

        choferTitle = (TextView) getView().findViewById(R.id.choferTitle);
        String title = choferTitle.getText().toString();
        choferTitle.setText(title + " " + c.getNombre() + " " + c.getPrimerApellido());
        correoChoferText = (TextView) getView().findViewById(R.id.correoChoferText);
        correoChoferText.setText(c.getCorreoElectronico());
        celularChoferText = (TextView) getView().findViewById(R.id.celularChoferText);
        celularChoferText.setText(c.getTelefono());

        btnCerrarSesion.setOnClickListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        cerrarSesionChofer();
        Intent i = new Intent(getActivity(), MainActivity.class);
        Toast.makeText(this.getActivity().getBaseContext(), "Has cerrado sesión", Toast.LENGTH_LONG).show();
        startActivity(i);
        getActivity().finish();
        return;
    }

    private void cerrarSesionUser(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(CELULAR_USER, "0000000000").apply();
    }

    private Chofer obtieneChofer(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        Chofer c = new Chofer(
                sharedPreferences.getString(NOMBRE_CHOFER, "nombre"),
                sharedPreferences.getString(PRIMER_CHOFER, "primer"),
                sharedPreferences.getString(SEGUNDO_CHOFER, "segundo"),
                sharedPreferences.getString(TELEFONO_CHOFER, "telefono"),
                sharedPreferences.getString(CORREO_CHOFER, "correo"),
                sharedPreferences.getString(ROL_CHOFER, "rol"));
        return c;
    }

    private void cerrarSesionChofer(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(TOKEN_CHOFER, "0000000000").apply();
        sharedPreferences.edit().putString(NOMBRE_CHOFER, "0000000000").apply();
        sharedPreferences.edit().putString(PRIMER_CHOFER, "0000000000").apply();
        sharedPreferences.edit().putString(SEGUNDO_CHOFER, "0000000000").apply();
        sharedPreferences.edit().putString(TELEFONO_CHOFER, "0000000000").apply();
        sharedPreferences.edit().putString(CORREO_CHOFER, "0000000000").apply();
        sharedPreferences.edit().putString(ROL_CHOFER, "0000000000").apply();

        sharedPreferences.edit().putBoolean(RESERVACION_EXCESO, false).apply();
        sharedPreferences.edit().putString(RESERVACION_FOLIO, "0000000000").apply();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
