package faculty.pmcar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";
    private final static String TAG = "PMCAR: ";

    private static String FORWARD_START = "a";
    private static String FORWARD_STOP = "b";

    private static String REVERSE_START = "c";
    private static String REVERSE_STOP = "d";

    private static String LEFT_START = "e";
    private static String LEFT_STOP = "f";

    private static String RIGHT_START = "g";
    private static String RIGHT_STOP = "h";


    private TextView infoPanel;
    private Button forward, reverse, left, right;
    private BluetoothDevice car;
    private BluetoothSocket bluetoothSocket = null;
    private boolean connected;
    OutputStream out = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getElementsFromView();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            infoPanel.append("Phone doesn't support bluetooth, Sorry! \n");
        }


        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                if (device.getName().contains("HC-06")) {
                    car = device;
                }
            }
        }

        if (car != null) {
            connected = false;
            try {
                bluetoothSocket = car.createRfcommSocketToServiceRecord(UUID.fromString(UUID_STRING_WELL_KNOWN_SPP));
                bluetoothSocket.connect();
                out = bluetoothSocket.getOutputStream();
                connected = true;
                infoPanel.append("Connected! \n");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if(connected) {
            forward.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.onTouchEvent(motionEvent);
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        sendCommand("a");
                        return true;
                    }
                    else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        sendCommand("b");
                        return true;
                    }
                    return false;
                }
            });
            reverse.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.onTouchEvent(motionEvent);
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        sendCommand("c");
                        return true;
                    }
                    else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        sendCommand("d");
                        return true;
                    }
                    return false;
                }
            });
            left.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.onTouchEvent(motionEvent);
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        sendCommand("e");
                        return true;
                    }
                    else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        sendCommand("f");
                        return true;
                    }
                    return false;
                }
            });

            right.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.onTouchEvent(motionEvent);
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        sendCommand("g");
                        return true;
                    }
                    else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        sendCommand("h");
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public void getElementsFromView() {
        infoPanel = (TextView) findViewById(R.id.info_panel);
        forward = (Button) findViewById(R.id.button_forward);
        reverse = (Button) findViewById(R.id.button_reverse);
        left = (Button) findViewById(R.id.button_left);
        right = (Button) findViewById(R.id.button_right);
    }

    @Override
    protected void onStop() {
        try {
            if ( bluetoothSocket != null && bluetoothSocket.isConnected()) {
                bluetoothSocket.close();
            }
            if( out != null )
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }


    public void sendCommand(String command) {
        try {
            out.write(command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }
    protected void onResume() {
        try {
            if(!bluetoothSocket.isConnected()) {
                bluetoothSocket.connect();
                out = bluetoothSocket.getOutputStream();
                Log.w(TAG, "RECONECTAT");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

}
