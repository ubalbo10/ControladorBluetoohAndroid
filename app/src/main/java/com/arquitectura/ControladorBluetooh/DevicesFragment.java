package com.arquitectura.ControladorBluetooh;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DevicesFragment extends ListFragment {

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> listItems = new ArrayList<>();
    private ArrayAdapter<BluetoothDevice> listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH))
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listAdapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0, listItems) {
            //vista de cada item de los dispositivos de bluetooh reconocidos por el cel
            @Override
            public View getView(int position, View view, ViewGroup parent) {
                BluetoothDevice device = listItems.get(position);
                if (view == null)
                    view = getActivity().getLayoutInflater().inflate(R.layout.device_list_item, parent, false);
                TextView text1 = view.findViewById(R.id.text1);
                TextView text2 = view.findViewById(R.id.text2);
                text1.setText(device.getName());
                text2.setText(device.getAddress());
                return view;
            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(null);
        View header = getActivity().getLayoutInflater().inflate(R.layout.device_list_header, null, false);
        getListView().addHeaderView(header, null, false);
        setEmptyText("initializing...");
        ((TextView) getListView().getEmptyView()).setTextSize(18);
        setListAdapter(listAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_principal, menu);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(bluetoothAdapter == null)
            setEmptyText("bluetooth no soportado en el dispositivo");
        else if(!bluetoothAdapter.isEnabled())
            setEmptyText("bluetooth esta desconectado");
        else
            setEmptyText("no funciona el bluetooh");
        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_menu_calculadora) {
            Toast.makeText(getActivity(),"calculadora",Toast.LENGTH_LONG).show();
           // Fragment fragment = new ControlPrincipalFragment();
           // fragment.setArguments(args);
           // getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
            return true;
        }
        if(id==R.id.item_menu_consumo){
            // Fragment fragment = new ControlPrincipalFragment();
            // fragment.setArguments(args);
            // getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
            Toast.makeText(getActivity(),"consumo presionado",Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    void refresh() {
        listItems.clear();
        if(bluetoothAdapter != null) {
            for (BluetoothDevice device : bluetoothAdapter.getBondedDevices())
                if (device.getType() != BluetoothDevice.DEVICE_TYPE_LE)
                    //lenamos la lista de los dispositivos conectados a blueetooh en el dispositivo
                    listItems.add(device);
        }
        Collections.sort(listItems, DevicesFragment::compareTo);
        listAdapter.notifyDataSetChanged();
    }
    //listener de cuando elegimos el dispositivo bluetooh a utilizar
    //cada item de bluetooh , cuando pulsamos sobre uno de ellos para usarlo en la app
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        BluetoothDevice device = listItems.get(position-1);
        Bundle args = new Bundle();
        args.putString("device", device.getAddress());
        Fragment fragment = new ControlPrincipalFragment();
        fragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
    }

    //con esto ordenamos los items que mostraremos de los dispositivos bluetooh
    static int compareTo(BluetoothDevice a, BluetoothDevice b) {
        boolean aValid = a.getName()!=null && !a.getName().isEmpty();
        boolean bValid = b.getName()!=null && !b.getName().isEmpty();
        if(aValid && bValid) {
            int ret = a.getName().compareTo(b.getName());
            if (ret != 0) return ret;
            return a.getAddress().compareTo(b.getAddress());
        }
        if(aValid) return -1;
        if(bValid) return +1;
        return a.getAddress().compareTo(b.getAddress());
    }
}
