package warmfeet.com.joaoramiro.estagio2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button updateBtn;

    boolean connected=false;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final int REQUEST_CONNECT_BT = 2;
    BluetoothAdapter mBluetoothAdapter;


    private static String MAC;

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
        switch (item.getItemId()) {

            case R.id.action_connect:
                //if disconnected must connect
                if(connected==false)
                {
                    Intent abreLista= new Intent(this,ListaDispositivos.class);
                    startActivityForResult(abreLista,REQUEST_CONNECT_BT);


                }else
                {

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

        switch (requestCode)
        {
            case REQUEST_ENABLE_BT:
                if(resultCode== Activity.RESULT_OK)
                {
                    Toast.makeText(getBaseContext(),R.string.bluetoothConnected,Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getBaseContext(),R.string.bluetoothDenied,Toast.LENGTH_SHORT).show();
                    finish();
                }
            break;
            case REQUEST_CONNECT_BT:
                if(resultCode== Activity.RESULT_OK)
                {
                    MAC=data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                    Toast.makeText(getBaseContext(),"MAC OBTAINED",Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(getBaseContext(),"ERROR92",Toast.LENGTH_SHORT).show();
                }

            break;


        }
    }
}
