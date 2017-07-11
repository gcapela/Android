package com.warmfeet.joaoramiro.estagio1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static com.warmfeet.joaoramiro.estagio1.R.id.bn;
import static com.warmfeet.joaoramiro.estagio1.R.id.checkbox;

public class sixthevents extends AppCompatActivity {


    AutoCompleteTextView autotext;
    String[] Country_names;

    ArrayList<String> selection = new ArrayList<String>();
    TextView final_text;


    TextView texttoggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Button bn;


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sixthevents);

        autotext=(AutoCompleteTextView)findViewById(R.id.country);

        final_text=(TextView)findViewById(R.id.final_result);

        Country_names=getResources().getStringArray(R.array.county_names);

        //converts string to arrayadapater
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Country_names);

        autotext.setAdapter(adapter);

        bn=(Button) findViewById(R.id.bn);

        final_text.setEnabled(false);

        texttoggle=(TextView)findViewById(R.id.resultToggle);


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


    public  void  selectItem(View view)
    {
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId())
        {
            case R.id.fruit_apple:
            if(checked)
                selection.add("Apple");
            else
                selection.remove("Apple");
            break;
            case R.id.fruit_orange:
            if(checked)
                selection.add("Orange");
            else
                selection.remove("Orange");
            break;
            case R.id.fruit_grapes:
            if(checked)
                selection.add("Grapes");
            else
                selection.remove("Grapes");
            break;
        }
    }

    public  void  finalSelection(View view)
    {
        String final_fruits="";

        for (String Selections: selection)
        {
            final_fruits=final_fruits+Selections+"\n";
        }

        final_text.setEnabled(true);
        final_text.setText(final_fruits);
    }

    public void toggleFunc(View view)
    {
boolean checker = ((ToggleButton)view).isChecked();

    }
    public  void showDialog(View v)
    {
        DialogHandler dialogHandler = new DialogHandler();
        dialogHandler.show(getFragmentManager(),"time_picker");

    }

}
