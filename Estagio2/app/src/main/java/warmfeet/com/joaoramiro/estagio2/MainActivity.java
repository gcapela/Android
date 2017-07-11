package warmfeet.com.joaoramiro.estagio2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button updateBtn;

    boolean connected=false;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final int REQUEST_CONNECT_BT = 2;

    BluetoothAdapter mBluetoothAdapter;

    BluetoothDevice mBluetoothDevice;

    BluetoothSocket mBluetoothSocket=null;

    private static String MAC;

    ConnectedThread thread;

    Handler mHandler;


    MenuItem it;

    UUID mUUID =UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),R.string.no_bluetooth,Toast.LENGTH_LONG);
        }else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        updateBtn=(Button)findViewById(R.id.updateBtn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        it=item;

        switch (item.getItemId()) {

            case R.id.action_connect:
                //if disconnected must connect
                if(connected==false)
                {


                    Intent abreLista= new Intent(this,ListaDispositivos.class);
                    startActivityForResult(abreLista,REQUEST_CONNECT_BT);



                }else
                {
                    try {
                        Toast.makeText(getBaseContext(),"DISCONNECTED BOY",Toast.LENGTH_SHORT).show();
                        mBluetoothSocket.close();
                        item.setTitle(R.string.connect);
                        connected=false;


                    }catch (IOException erro)
                    {


                        Toast.makeText(getBaseContext(),"ERROR55",Toast.LENGTH_SHORT).show();
                    }
                }


                //if Connected must diconnect
                Toast.makeText(getBaseContext(),"Heya",Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getBaseContext(), R.string.bluetoothConnected, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), R.string.bluetoothDenied, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case REQUEST_CONNECT_BT:
                if (resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                    Toast.makeText(getBaseContext(), "MAC OBTAINED", Toast.LENGTH_SHORT).show();

                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC);

                    try {

                        mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(mUUID);

                        mBluetoothSocket.connect();
                        connected = true;

                        thread = new ConnectedThread(mBluetoothSocket);
                        thread.start();

                        it.setTitle(R.string.disconnect);

                        Toast.makeText(getBaseContext(), "Conectado: " + MAC, Toast.LENGTH_SHORT).show();

                    } catch (IOException error) {
                        connected = false;

                        Toast.makeText(getBaseContext(), "ERROR101", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "ERROR92", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }


        public void sendData(View v) {

        if(connected)
        {

            thread.write("step");

        }else
        {
            Toast.makeText(getBaseContext(),"Plz liga te ao dispositivo primeiro",Toast.LENGTH_SHORT).show();
        }
    }



private class ConnectedThread extends Thread {

    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private byte[] mmBuffer; // mmBuffer store for the stream

    public ConnectedThread(BluetoothSocket socket) {

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            //Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            //  Log.e(TAG, "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }




    public void run() {
        mmBuffer = new byte[1024];
        int numBytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
           /* while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.

                    Message readMsg = mHandler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                  //  Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }*/
    }

    // Call this from the main activity to send data to the remote device.
        /*public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

                // Share the sent message with the UI activity.
                Message writtenMsg = mHandler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
               // Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                mHandler.sendMessage(writeErrorMsg);
            }
        }*/

    public void write(String requestData) {
        try {
            mmOutStream.write(requestData.getBytes());

            // Share the sent message with the UI activity.
            Message writtenMsg = mHandler.obtainMessage(
                    MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
            writtenMsg.sendToTarget();
        } catch (IOException e) {
            // Log.e(TAG, "Error occurred when sending data", e);

            // Send a failure message back to the activity.
            Message writeErrorMsg =
                    mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast",
                    "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            mHandler.sendMessage(writeErrorMsg);
        }
    }





}

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }
}
