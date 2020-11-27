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


        texto1.setText(Integer.toString((contLum1*20))+" Watts "+Integer.toString((contLum1*20*2)/10000)+"Dolares");
        texto2.setText(Integer.toString((contLum2*20))+" Watts "+Integer.toString((contLum2*20*2)/10000)+"Dolares");
        texto3.setText(Integer.toString((contLum3*20))+" Watts "+Integer.toString((contLum3*20*2)/10000)+"Dolares");
        texto4.setText(Integer.toString((contLum4*20))+" Watts "+Integer.toString((contLum4*20*2)/10000)+"Dolares");
        texto5.setText(Integer.toString((contLum5*20))+" Watts "+Integer.toString((contLum5*20*2)/10000)+"Dolares");
        texto6.setText(Integer.toString((contLum6*20))+" Watts "+Integer.toString((contLum6*20*2)/10000)+"Dolares");

        int total=contLum1+contLum2+contLum3+contLum4+contLum5+contLum6;

        texto7.setText(Integer.toString((total*20))+" Watts "+Integer.toString((total*20*2)/10000)+"Dolares");

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