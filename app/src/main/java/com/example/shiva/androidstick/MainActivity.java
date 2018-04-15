package com.example.shiva.androidstick;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button fw,bw,lf,rt;
    TextView t1;

    String address = null , name=null;

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {setw();} catch (Exception e) {}
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setw() throws IOException
    {
        t1=(TextView)findViewById(R.id.textView1);
        bluetooth_connect_device();



        fw=(Button)findViewById(R.id.fw);

        fw.setOnTouchListener(new View.OnTouchListener()
        {   @Override
        public boolean onTouch(View v, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {led_on_off("1");}
            if(event.getAction() == MotionEvent.ACTION_UP){led_on_off("5");}
            return true;}
        });


        bw=(Button)findViewById(R.id.bw);

        bw.setOnTouchListener(new View.OnTouchListener()
        {   @Override
        public boolean onTouch(View v, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {led_on_off("2");}
            if(event.getAction() == MotionEvent.ACTION_UP){led_on_off("5");}
            return true;}
        });


        lf=(Button)findViewById(R.id.lf);

        lf.setOnTouchListener(new View.OnTouchListener()
        {   @Override
        public boolean onTouch(View v, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {led_on_off("3");}
            if(event.getAction() == MotionEvent.ACTION_UP){led_on_off("6");}
            return true;}
        });

        rt=(Button)findViewById(R.id.rt);

        rt.setOnTouchListener(new View.OnTouchListener()
        {   @Override
        public boolean onTouch(View v, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {led_on_off("4");}
            if(event.getAction() == MotionEvent.ACTION_UP){led_on_off("7");}
            return true;}
        });

    }

    private void bluetooth_connect_device() throws IOException
    {
        try
        {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            address = myBluetooth.getAddress();
            pairedDevices = myBluetooth.getBondedDevices();
            if (pairedDevices.size()>0)
            {
                for(BluetoothDevice bt : pairedDevices)
                {
                    address=bt.getAddress().toString();name = bt.getName().toString();
                    Toast.makeText(getApplicationContext(),"Connected", Toast.LENGTH_SHORT).show();

                }
            }

        }
        catch(Exception we){}
        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
        btSocket.connect();
        try { t1.setText("BT Name: "+name+"\nBT Address: "+address); }
        catch(Exception e){}
    }

    @Override
    public void onClick(View v)
    {
        try
        {

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    private void led_on_off(String i)
    {
        try
        {
            if (btSocket!=null)
            {

                btSocket.getOutputStream().write(i.toString().getBytes());
            }

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


}

