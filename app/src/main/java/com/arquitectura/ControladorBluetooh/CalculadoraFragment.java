package com.arquitectura.ControladorBluetooh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculadoraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculadoraFragment extends Fragment {

    EditText nLuminarias;
    EditText nTiempo;
    Button consultar;
    ImageView imagen;
    TextView textoMostrar;
    String num_luminarias;
    String num_minutos;
    double consumoEnWatsPorminuto=(100.0/60);
    double consumoEnDineroPorHora=(0.20/1000);
    String mensaje;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalculadoraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculadoraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculadoraFragment newInstance(String param1, String param2) {
        CalculadoraFragment fragment = new CalculadoraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_calculadora, container, false);
        nLuminarias=vista.findViewById(R.id.editText_num_leds);
        nTiempo=vista.findViewById(R.id.editText_numero_horas);
        imagen=vista.findViewById(R.id.imageView);
        consultar=vista.findViewById(R.id.button_calculadora);
        textoMostrar=vista.findViewById(R.id.texto_amostrar_encalculadora);
        imagen.setVisibility(View.INVISIBLE);
        textoMostrar.setVisibility(View.INVISIBLE);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nLuminarias.getText().toString().isEmpty() || nTiempo.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"complete los campos",Toast.LENGTH_LONG).show();
                }else{
                    DecimalFormat df = new DecimalFormat("#.00");
                    num_luminarias=nLuminarias.getText().toString();
                    num_minutos=nTiempo.getText().toString();
                    int luminariasInt=Integer.parseInt(num_luminarias);
                    int minutosInt=Integer.parseInt(num_minutos);
                    double consumoenwats;
                    double consumoendolares;
                    //formula
                    consumoenwats=((luminariasInt*minutosInt)*consumoEnWatsPorminuto);
                    consumoendolares=consumoenwats*consumoEnDineroPorHora;
                    if(consumoenwats>=1000.00){
                        consumoenwats=consumoenwats/1000;
                        mensaje="Si mantiene encendidas: "+num_luminarias +" luminarias, En un periodo de "+num_minutos+
                                " minutos, Su consumo en wats seria de :"+ df.format(consumoenwats)+
                                "KW, Lo cual equivale a $"+ df.format(consumoendolares);
                    }else{
                        mensaje="Si mantiene encendidas: "+num_luminarias +" luminarias, En un periodo de "+num_minutos+
                                " minutos, Su consumo en wats seria de :"+ df.format(consumoenwats)+
                                "W, Lo cual equivale a $"+ df.format(consumoendolares);

                    }
                    //texto a mostrar
                    textoMostrar.setText(mensaje);
                    imagen.setVisibility(View.VISIBLE);
                    textoMostrar.setVisibility(View.VISIBLE);
                }
            }
        });
        return vista;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_secundario, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_menu_devices) {
            Toast.makeText(getActivity(),"volviendo al inicio",Toast.LENGTH_LONG).show();
            Fragment fragment = new DevicesFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
            return true;
        }
         else {
            return super.onOptionsItemSelected(item);
        }
    }
}