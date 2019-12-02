package com.example.proywebtest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.proywebtest.Models.Reservacion;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link reservacion_asignada.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link reservacion_asignada#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reservacion_asignada extends Fragment implements View.OnClickListener, BottomSheetExceso.BottomSheetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESERVACION_KEY = "folioReservacion";
    private ArrayList<Reservacion> mReservacion;

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;
    private Button btnReportarExceso;
    private BottomSheetExceso bottomSheetExceso;

    private TextView folioReservacionText;
    private TextView choferText;
    private TextView camionText;
    private TextView sedeOrigenText;
    private TextView sedeDestinoTitleText;
    private TextView fechaReservacionText;
    private TextView nombreCompletoText;
    private TextView celularText;




    public reservacion_asignada() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static reservacion_asignada newInstance(ArrayList<Reservacion> reservacionArrayList) {
        reservacion_asignada fragment = new reservacion_asignada();
        Bundle args = new Bundle();
        args.putSerializable(RESERVACION_KEY, (Serializable) reservacionArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            mReservacion = (ArrayList<Reservacion>) getArguments().getSerializable(RESERVACION_KEY);
            Toast.makeText(getActivity().getBaseContext(), "ID camion: " + mReservacion.get(0).getIdCamion() + " ID reservacion: " + mReservacion.get(0).getFolioReservacion(), Toast.LENGTH_LONG).show();
        }
        return inflater.inflate(R.layout.fragment_reservacion_asignada, container, false);
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
        setEtiquetasReservacion();
        btnReportarExceso = (Button) getView().findViewById(R.id.btnReportarExceso);
        btnReportarExceso.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnReportarExceso:
                bottomSheetExceso = new BottomSheetExceso();
                bottomSheetExceso.onAttach(getContext());
                bottomSheetExceso.show(getActivity().getSupportFragmentManager(), "bottomSheetExceso");
                break;
        }
    }

    @Override
    public void onBottomClick(boolean exceso) {

    }

    @Override
    public void setExceso(String folio) {

    }

    private void setEtiquetasReservacion(){
        folioReservacionText = (TextView) getActivity().findViewById(R.id.folioReservacionText);
        folioReservacionText.setText(mReservacion.get(0).getFolioReservacion());
        //choferText = (TextView) getActivity().findViewById(R.id.choferText);
        //choferText.setText(mReservacion.get(0).get);
        camionText = (TextView) getActivity().findViewById(R.id.camionText);
        camionText.setText(mReservacion.get(0).getIdCamion());
        sedeOrigenText = (TextView) getActivity().findViewById(R.id.sedeOrigenText);
        sedeOrigenText.setText(mReservacion.get(0).getSedeOrigen());
        sedeDestinoTitleText = (TextView) getActivity().findViewById(R.id.sedeDestinoTitleText);
        sedeDestinoTitleText.setText(mReservacion.get(0).getSedeDestino());
        fechaReservacionText = (TextView) getActivity().findViewById(R.id.fechaReservacionText);
        fechaReservacionText.setText(mReservacion.get(0).getFechaReservacion());
        nombreCompletoText = (TextView) getActivity().findViewById(R.id.nombreCompletoText);
        nombreCompletoText.setText(mReservacion.get(0).getNombreCliente() + " " + mReservacion.get(0).getPrimerApellidoCliente() + " " + mReservacion.get(0).getSegundoApellidoCliente());
        celularText = (TextView) getActivity().findViewById(R.id.celularText);
        celularText.setText(mReservacion.get(0).getCelularCliente());
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
