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

    private BluetoothAdapter mbluetoothadapter2;

    static String ENDERECO_MAC=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        mbluetoothadapter2=BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mbluetoothadapter2.getBondedDevices();

        if(pairedDevices.size()>0)
        {
            for(BluetoothDevice device: pairedDevices)
            {
                String nomeBt= device.getName();
                String address = device.getAddress();
                ArrayBluetooth.add(nomeBt+"\n"+address);
            }
        }
        setListAdapter(ArrayBluetooth);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String info=((TextView)v).getText().toString();

     //   Toast.makeText(getBaseContext(),"Info: " +info, Toast.LENGTH_SHORT).makeText();

        String macAddr = info.substring(info.length()-17);

        Intent returnMac = new Intent();

        //key value
        returnMac.putExtra(ENDERECO_MAC,macAddr);
        setResult(RESULT_OK,returnMac);


        //executes intent
        finish();
    }
}
