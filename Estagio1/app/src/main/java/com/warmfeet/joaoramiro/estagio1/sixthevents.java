package com.warmfeet.joaoramiro.estagio1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import static com.warmfeet.joaoramiro.estagio1.R.id.bn;

public class sixthevents extends AppCompatActivity {


    AutoCompleteTextView autotext;
    String[] Country_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Button bn;


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sixthevents);

        autotext=(AutoCompleteTextView)findViewById(R.id.country);

        Country_names=getResources().getStringArray(R.array.county_names);

        //converts string to arrayadapater
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Country_names);

        autotext.setAdapter(adapter);

        bn=(Button) findViewById(R.id.bn);

        bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"You click on second button",Toast.LENGTH_LONG).show();

            }
        });


    }

    public void getMe(View v)
    {
        Toast.makeText(getBaseContext(),"You click on first button",Toast.LENGTH_LONG).show();

    }
}
