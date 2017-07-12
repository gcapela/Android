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

    //BUtton sends data via bluetooth
    Button updateBtn;


    //defines if bluetooth is connected
    boolean connected=false;


    //flags
    private static final int REQUEST_ENABLE_BT = 1; //if user said yes to enable bt
    private static final int REQUEST_CONNECT_BT = 2; //comes from connection to phone
    private static final int MESSAGE_READ = 3;  //comes from read msg
    public static final int MESSAGE_WRITE = 4;//comes from writing a msg
    public static final int MESSAGE_TOAST = 5;

    //bt adapter of the smartphone
    BluetoothAdapter mBluetoothAdapter;

    //bluetooth device connectec
    BluetoothDevice mBluetoothDevice;

    //communication socket
    BluetoothSocket mBluetoothSocket=null;

    //mac address of connected device
    private static String MAC;

    //thread to comunicate with the bt device
    ConnectedThread thread;

    //like a service but different
    Handler mHandler;

    //enables us to perform actions to a certain string
    StringBuilder dadosBt = new StringBuilder();

    //item on the menu
    MenuItem it;

    //defauilt UUID
    UUID mUUID =UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if thte is a bt adapter on smartphone
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),R.string.no_bluetooth,Toast.LENGTH_LONG);

            //requests to enable it
        }else if (!mBluetoothAdapter.isEnabled()) {

            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //sets update button
        updateBtn=(Button)findViewById(R.id.updateBtn);

        //sets handler
        mHandler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                //if it came via the bluetooth thread
                if(msg.what==MESSAGE_READ)
                {
                    String receivedData = (String)msg.obj;

                    Toast.makeText(getBaseContext(),"RECEIVED: " + receivedData,Toast.LENGTH_SHORT).show();

                    //stringbuild faz cenas com a string recebida
                    //dadosBt.append(receivedData);

                    int fimInfo = dadosBt.indexOf("}");

                    if(fimInfo>0)
                    {
                        //String complete= dadosBt.indexOf("{");
                        String complete = dadosBt.substring(0,fimInfo);

                        int sizeInfo = complete.length();

                        if(dadosBt.charAt(0)=='{')
                        {
                            String finalData=dadosBt.substring(1,sizeInfo);

                            Log.d("Recebidos",finalData);
                            Toast.makeText(getBaseContext(),"DONE",Toast.LENGTH_SHORT).show();
                        }

                        dadosBt.delete(0,dadosBt.length());

                    }
                }
            }
        };
    }

    //creates the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //checks if option menu was selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //sets item of menu so we can change its text
        it=item;


        switch (item.getItemId()) {

            case R.id.action_connect:

                //if disconnected must connect
                if(connected==false)
                {
                    //opens list activity and awaits a result
                    Intent abreLista= new Intent(this,ListaDispositivos.class);
                    startActivityForResult(abreLista,REQUEST_CONNECT_BT);
                }else
                {
                    //tries to disconnect
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

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    //analyses result of respective flag (request code)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            //from enable bt dialog
            case REQUEST_ENABLE_BT:

                //says its all good
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getBaseContext(), R.string.bluetoothConnected, Toast.LENGTH_SHORT).show();
                } else {

                    //closes app
                    Toast.makeText(getBaseContext(), R.string.bluetoothDenied, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            //comes from list activity dialog
            case REQUEST_CONNECT_BT:

                if (resultCode == Activity.RESULT_OK) {

                    //gets the key from the other activity
                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                    Toast.makeText(getBaseContext(), "MAC OBTAINED", Toast.LENGTH_SHORT).show();

                    //sets bt device
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC);


                    try {

                        //tries to create a socket and connect
                        mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(mUUID);

                        mBluetoothSocket.connect();
                        connected = true;


                        //starts a thread
                        thread = new ConnectedThread(mBluetoothSocket);
                        thread.start();

                        //changes item text
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

        //called from Update Button
        public void sendData(View v) {
        if(connected)
        {
            //sends message to bt device
            thread.write("step");

        }else
        {
            //requests user to connect to device
            Toast.makeText(getBaseContext(),"Plz liga te ao dispositivo primeiro",Toast.LENGTH_SHORT).show();
        }
    }


//https://developer.android.com/guide/topics/connectivity/bluetooth.html#ManagingAConnection
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



//gets data from  bt device
    public void run() {
        mmBuffer = new byte[1024];
        int numBytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
           while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.

                    //Converts bytes to string
                    String dadosBlue = new String(mmBuffer,0,numBytes);


                    //gets us the msg
                    Message readMsg = mHandler.obtainMessage(MESSAGE_READ, numBytes, -1,
                            dadosBlue);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                  //  Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
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

    //writes data onto bt device
    public void write(String requestData) {
        try {
            mmOutStream.write(requestData.getBytes());

            // Share the sent message with the UI activity.
           /* Message writtenMsg = mHandler.obtainMessage(
                    MESSAGE_WRITE, -1, -1, mmBuffer);
            writtenMsg.sendToTarget();*/
        } catch (IOException e) {
            // Log.e(TAG, "Error occurred when sending data", e);

            // Send a failure message back to the activity.
            Message writeErrorMsg =
                    mHandler.obtainMessage(MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast",
                    "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            mHandler.sendMessage(writeErrorMsg);

        }
    }





}


}
