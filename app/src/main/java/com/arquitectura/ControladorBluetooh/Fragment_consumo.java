package com.arquitectura.ControladorBluetooh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.arquitectura.ControladorBluetooh.ControlPrincipalFragment.contLum1;
import static com.arquitectura.ControladorBluetooh.ControlPrincipalFragment.contLum2;
import static com.arquitectura.ControladorBluetooh.ControlPrincipalFragment.contLum3;
import static com.arquitectura.ControladorBluetooh.ControlPrincipalFragment.contLum4;
import static com.arquitectura.ControladorBluetooh.ControlPrincipalFragment.contLum5;
import static com.arquitectura.ControladorBluetooh.ControlPrincipalFragment.contLum6;

public class Fragment_consumo extends Fragment {
    TextView texto1;
    TextView texto2;
    TextView texto3;
    TextView texto4;
    TextView texto5;
    TextView texto6;
    TextView texto7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_consumo, container, false);

        texto1=vista.findViewById(R.id.textView2);
        texto2=vista.findViewById(R.id.textView4);
        texto3=vista.findViewById(R.id.textView6);
        texto4=vista.findViewById(R.id.textView8);
        texto5=vista.findViewById(R.id.textView10);
        texto6=vista.findViewById(R.id.textView12);
        texto7=vista.findViewById(R.id.textView14);


        texto1.setText(Double.toString((contLum1*100))+" Watts - "+Double.toString((contLum1*100*2)/10000)+" Dolares - "+Double.toString((contLum1))+" Horas");
        texto2.setText(Double.toString((contLum2*100))+" Watts - "+Double.toString((contLum2*100*2)/10000)+" Dolares - "+Double.toString((contLum2))+" Horas");
        texto3.setText(Double.toString((contLum3*100))+" Watts - "+Double.toString((contLum3*100*2)/10000)+" Dolares - "+Double.toString((contLum3))+" Horas");
        texto4.setText(Double.toString((contLum4*100))+" Watts - "+Double.toString((contLum4*100*2)/10000)+" Dolares - "+Double.toString((contLum4))+" Horas");
        texto5.setText(Double.toString((contLum5*100))+" Watts - "+Double.toString((contLum5*100*2)/10000)+" Dolares - "+Double.toString((contLum5))+" Horas");
        texto6.setText(Double.toString((contLum6*100))+" Watts - "+Double.toString((contLum6*100*2)/10000)+" Dolares - "+Double.toString((contLum6))+" Horas");

        Double total=contLum1+contLum2+contLum3+contLum4+contLum5+contLum6;

        texto7.setText(Double.toString((total*100))+" Watts - "+Double.toString((total*100*2)/10000)+" Dolares - "+Double.toString((total))+" Horas");

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