package com.arquitectura.ControladorBluetooh;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ControlPrincipalFragment extends Fragment implements ServiceConnection, /*esta es la interface que creamos*/ SerialListener {

    private enum Connected { False, Pending, True }

    private String deviceAddress;
    private SerialService service;

 //agregado
    private Connected connected = Connected.False;
    private boolean initialStart = true;
    private boolean hexEnabled = false;
    private String newline = TextUtil.newline_crlf;

    /*
     * Lifecycle
     */
    //primero
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        deviceAddress = getArguments().getString("device");

    }

    @Override
    public void onDestroy() {
        if (connected != Connected.False)
            disconnect();
        getActivity().stopService(new Intent(getActivity(), SerialService.class));
        super.onDestroy();
    }
    //segundo
    //aqui realizamos la conexion con la clase serialservice y emparejamos con bluetooh
    @Override
    public void onStart() {
        super.onStart();
        if(service != null)
            service.attach(this);
        else
            getActivity().startService(new Intent(getActivity(), SerialService.class));
    }

    @Override
    public void onStop() {
        if(service != null && !getActivity().isChangingConfigurations())
            service.detach();
        super.onStop();
    }

    @SuppressWarnings("deprecation") // onAttach(context) was added with API 23. onAttach(activity) works for all API versions
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        getActivity().bindService(new Intent(getActivity(), SerialService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDetach() {
        try { getActivity().unbindService(this); } catch(Exception ignored) {}
        super.onDetach();
    }
    //tercero
    @Override
    public void onResume() {
        super.onResume();
        if(initialStart && service != null) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        service.attach(this);
        if(initialStart && isResumed()) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    /*
     * UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        View btn1 = view.findViewById(R.id.button);
        View btn1apagado = view.findViewById(R.id.button2);
        View btn2 = view.findViewById(R.id.button3);
        View btn2apagado = view.findViewById(R.id.button4);
        View btn3 = view.findViewById(R.id.button5);
        View btn3apagado = view.findViewById(R.id.button6);
        View btn4 = view.findViewById(R.id.button7);
        View btn4apagado = view.findViewById(R.id.button8);
        View btn5 = view.findViewById(R.id.button9);
        View btn5apagado = view.findViewById(R.id.button10);
        View btn6 = view.findViewById(R.id.button11);
        View btn6apagado = view.findViewById(R.id.button12);

        //al pulsar los botones
        btn1.setOnClickListener(v -> send("1"));
        btn1apagado.setOnClickListener(v -> send("2"));
        btn2.setOnClickListener(v -> send("3"));
        btn2apagado.setOnClickListener(v -> send("4"));
        btn3.setOnClickListener(v -> send("5"));
        btn3apagado.setOnClickListener(v -> send("6"));
        btn4.setOnClickListener(v -> send("7"));
        btn4apagado.setOnClickListener(v -> send("8"));
        btn5.setOnClickListener(v -> send("A"));
        btn5apagado.setOnClickListener(v -> send("B"));
        btn6.setOnClickListener(v -> send("C"));
        btn6apagado.setOnClickListener(v -> send("D"));




        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_principal, menu);
        menu.findItem(R.id.item_menu_calculadora).setChecked(hexEnabled);
        menu.findItem(R.id.item_menu_consumo).setChecked(hexEnabled);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_menu_consumo) {
            Toast.makeText(getActivity(),"consumo",Toast.LENGTH_LONG).show();
             //Fragment fragment = new ControlPrincipalFragment();
            // fragment.setArguments(args);
             //getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();

            return true;
        } else if (id == R.id.item_menu_calculadora) {
            Toast.makeText(getActivity(),"calculadora",Toast.LENGTH_LONG).show();
             Fragment fragment = new CalculadoraFragment();
            // fragment.setArguments(args);
             getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();

            return true;
        }  else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Serial + UI
     */
    private void connect() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
            //status("connecting...");

            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(getActivity().getApplicationContext(), device);
            service.connect(socket);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    private void disconnect() {
        connected = Connected.False;
        service.disconnect();
    }

    private void send(String str) {
       // if(connected != Connected.True) {
       //     Toast.makeText(getActivity(), "no estas conectado correctamente", Toast.LENGTH_SHORT).show();
       //     return;
       // }
        try {
            String msg;
            byte[] data;
            if(hexEnabled) {
                StringBuilder sb = new StringBuilder();
                TextUtil.toHexString(sb, TextUtil.fromHexString(str));
                TextUtil.toHexString(sb, newline.getBytes());
                msg = sb.toString();
                data = TextUtil.fromHexString(msg);
            } else {
                msg = str;
                data = (str + newline).getBytes();
            }
            //SpannableStringBuilder spn = new SpannableStringBuilder(msg+'\n');
            //spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSendText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //receiveText.append(spn);
            //llamamos al servicio para hacer el envio

            service.write(data);
        } catch (Exception e) {
            onSerialIoError(e);
        }
    }



    /*
     * SerialListener
     */

    @Override
    public void onSerialConnect() {
        //status("connected");
        Toast.makeText(getActivity(),"serial conectado ",Toast.LENGTH_LONG).show();

        connected = Connected.True;
    }

    @Override
    public void onSerialConnectError(Exception e) {
        //status("connection failed: " + e.getMessage());
        Toast.makeText(getActivity(),"serial desconectado "+e.getMessage(),Toast.LENGTH_LONG).show();
        disconnect();
    }



    @Override
    public void onSerialRead(byte[] data) {
        //receive(data);
    }

    @Override
    public void onSerialIoError(Exception e) {
        //status("connection lost: " + e.getMessage());
        Toast.makeText(getActivity(),"desconectado en la salida",Toast.LENGTH_LONG).show();

        disconnect();
    }

}
