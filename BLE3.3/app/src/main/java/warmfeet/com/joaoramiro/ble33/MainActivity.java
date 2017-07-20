package warmfeet.com.joaoramiro.ble33;

import android.app.ProgressDialog;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    Thread thread;
    Thread thread2;
    ProgressBar progressBar;
    android.os.Handler handler;

    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[]drugs = {"Heroin","Siriracha","Terryaki Sauce","Potate Grello"};

        //convert an array to list
        //this, format, object array to make into a list
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,drugs);

        Button btnScan = (Button)findViewById(R.id.ScanBtn);

        Button btnSend = (Button)findViewById(R.id.SendBtn);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonFunction();
            }
        });

        final TextView textView;

        //get list
        ListView listView=(ListView)findViewById(R.id.btlist);

        //attribute the adapter to the list view
        listView.setAdapter(listAdapter);


        //sets listener on the list
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    //when they click an item make something happen
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String drug= String.valueOf(parent.getItemAtPosition(position));

                        Toast.makeText(MainActivity.this,drug,Toast.LENGTH_SHORT).show();
                    }
                }

        );

        progressBar= (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setProgress(50);
        //assigns our thread to this and runs it
        thread=new Thread(new MyThread());
        thread.start();

        //thread2=new Thread(new MySecondThread());
       // thread2.start();


        textView=(TextView) findViewById(R.id.text);

        //handles incoming message from the other thread
        //was created on main thread  so its bound to it
        handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
              //  super.handleMessage(msg);

                //progressBar.setProgress(msg.arg1);
              //  Toast.makeText(getApplicationContext (),"WORKING",Toast.LENGTH_SHORT).show();
                //progressBar.setProgress(msg.arg1);
                progressBar.setProgress(msg.arg1);

                textView.setText(Integer.toString(msg.arg1));
            }
        };

    }

    public void ButtonFunction()
    {

        Toast.makeText(MainActivity.this,"Stop Clickin Me boy",Toast.LENGTH_SHORT).show();
    }

    class MyThread implements Runnable{
        @Override
        public void run() {
            //empty msg


            //this loop crashes app
            for (int i=0;i<=100;i++)
            {
                Message message= Message.obtain();
                message.arg1=i;
                handler.sendMessage(message);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //do stuff on main thread
                }
            });*/
        }
    }

    public void sendMsg(View view)
    {
      /* MySecondThread.handler2.post(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(getBaseContext(),"Cool Thread Stuff",Toast.LENGTH_SHORT).show();
           }
       });*/
    }





}
