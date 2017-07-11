package warmfeet.com.joaoramiro.estagio2;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

public class ListaDispositivos extends ListActivity {

    //gets the bt adapter of code
    private BluetoothAdapter mbluetoothadapter2;

    //gets the mac address of the device
    static String ENDERECO_MAC=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creates array adapter
        ArrayAdapter<String> ArrayBluetooth=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        //gets bt adapter
        mbluetoothadapter2=BluetoothAdapter.getDefaultAdapter();

        //finds all the paired devices
        Set<BluetoothDevice> pairedDevices = mbluetoothadapter2.getBondedDevices();


        //adds paired devices onto the ArrayAdapter
        if(pairedDevices.size()>0)
        {
            for(BluetoothDevice device: pairedDevices)
            {
                String nomeBt= device.getName();
                String address = device.getAddress();
                ArrayBluetooth.add(nomeBt+"\n"+address);
            }
        }

        //displays array adapter on this activity
        setListAdapter(ArrayBluetooth);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //gets the text from the item clicked
        String info=((TextView)v).getText().toString();

        //gets mac address from selected item
        String macAddr = info.substring(info.length()-17);

        //creates a new intent
        Intent returnMac = new Intent();

        //key value
        returnMac.putExtra(ENDERECO_MAC,macAddr);
        setResult(RESULT_OK,returnMac);


        //executes intent
        finish();
    }
}
