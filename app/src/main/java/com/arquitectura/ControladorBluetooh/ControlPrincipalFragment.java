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

import java.util.Date;

public class ControlPrincipalFragment extends Fragment implements ServiceConnection, /*esta es la interface que creamos*/ SerialListener {

    private enum Connected { False, Pending, True }

    private String deviceAddress;
    private SerialService service;


    private Connected connected = Connected.False;
    private boolean initialStart = true;
    private boolean hexEnabled = false;
    private String newline = TextUtil.newline_crlf;

    //variables para calcular consumo real
    static int contLum1;
    static int horaInicioLum1;
    static int horaFinLum1;

    static int contLum2;
    static int horaInicioLum2;
    static int horaFinLum2;

    static int contLum3;
    static int horaInicioLum3;
    static int horaFinLum3;

    static int contLum4;
    static int horaInicioLum4;
    static int horaFinLum4;

    static int contLum5;
    static int horaInicioLum5;
    static int horaFinLum5;

    static int contLum6;
    static int horaInicioLum6;
    static int horaFinLum6;

    static int conTotal;

    Date horainicio;
    Date horaFin;

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
        //btn1.setOnClickListener(v -> send("1"));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("1");
                horainicio=new Date();
                horaInicioLum1= horainicio.getMinutes();
            }
        });

       // btn1apagado.setOnClickListener(v -> send("2"));
        btn1apagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("2");

                horaFin=new Date();
                horaFinLum1=horaFin.getMinutes();
                contLum1=horaFinLum1-horaInicioLum1;
            }
        });
        //btn2.setOnClickListener(v -> send("3"));
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("3");
                horainicio=new Date();
                horaInicioLum2= horainicio.getMinutes();
            }
        });

        //btn2apagado.setOnClickListener(v -> send("4"));
        btn2apagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("4");
                horaFin=new Date();
                horaFinLum2=horaFin.getMinutes();
                contLum2=horaFinLum1-horaInicioLum1;
            }
        });

        //btn3.setOnClickListener(v -> send("5"));
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("5");
                horainicio=new Date();
                horaInicioLum3= horainicio.getMinutes();
            }
        });

        //btn3apagado.setOnClickListener(v -> send("6"));
        btn3apagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("6");
                horaFin=new Date();
                horaFinLum3=horaFin.getMinutes();
                contLum3=horaFinLum1-horaInicioLum1;
            }
        });

        //btn4.setOnClickListener(v -> send("7"));
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("7");
                horainicio=new Date();
                horaInicioLum4= horainicio.getMinutes();
            }
        });

        //btn4apagado.setOnClickListener(v -> send("8"));
        btn4apagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("8");
                horaFin=new Date();
                horaFinLum4=horaFin.getMinutes();
                contLum4=horaFinLum1-horaInicioLum1;
            }
        });

        //btn5.setOnClickListener(v -> send("A"));
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("A");
                horainicio=new Date();
                horaInicioLum5= horainicio.getMinutes();
            }
        });

        //btn5apagado.setOnClickListener(v -> send("B"));
        btn5apagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("B");
                horaFin=new Date();
                horaFinLum5=horaFin.getMinutes();
                contLum5=horaFinLum1-horaInicioLum1;
            }
        });

        //btn6.setOnClickListener(v -> send("C"));
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("C");
                horainicio=new Date();
                horaInicioLum6= horainicio.getMinutes();
            }
        });

        //btn6apagado.setOnClickListener(v -> send("D"));
        btn6apagado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("D");
                horaFin=new Date();
                horaFinLum6=horaFin.getMinutes();
                contLum6=horaFinLum1-horaInicioLum1;
            }
        });



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
           // Toast.makeText(getActivity(),"consumo",Toast.LENGTH_LONG).show();
            Fragment fragment = new Fragment_consumo();
            // fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();

            return true;
        } else if (id == R.id.item_menu_calculadora) {
           // Toast.makeText(getActivity(),"calculadora",Toast.LENGTH_LONG).show();
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
                //Toast.makeText(getActivity(),"sime ejecuto",Toast.LENGTH_LONG).show();
                StringBuilder sb = new StringBuilder();
                TextUtil.toHexString(sb, TextUtil.fromHexString(str));
                TextUtil.toHexString(sb, newline.getBytes());
                msg = sb.toString();
                data = TextUtil.fromHexString(msg);
            } else {
                //Toast.makeText(getActivity(),"sime ejecuto",Toast.LENGTH_LONG).show();
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
