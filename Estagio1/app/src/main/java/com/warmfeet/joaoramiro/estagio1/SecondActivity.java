package com.warmfeet.joaoramiro.estagio1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by João on 10/07/2017.
 */

public class SecondActivity extends Activity {

    EditText msg_text;
    public final static String MESSAGE_KEY ="com.warmfeet.joaoramiro.estagio1.message_key";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
    }

    public void sendMsg(View view)
    {
msg_text=(EditText) findViewById(R.id.message);
       String message = msg_text.getText().toString();
        Intent intent = new Intent(this,MessageActivity.class);
        intent.putExtra(MESSAGE_KEY,message);
        startActivity(intent);
    }
}
