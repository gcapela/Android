package warmfeet.com.joaoramiro.byteconversiontests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView1;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=(EditText) findViewById(R.id.bytes);

        textView1=(TextView) findViewById(R.id.result1);
        textView2=(TextView) findViewById(R.id.result2);
        textView3=(TextView) findViewById(R.id.result3);
    }

    public void ConvertIt(View v)
    {
      //  Toast.makeText(getBaseContext(),"Converted!",Toast.LENGTH_SHORT).show();

        String stuff;

        stuff=  editText.getText().toString();
        Toast.makeText(getBaseContext(),stuff,Toast.LENGTH_SHORT).show();

        if(stuff.length()==6)
        {
            //  Toast.makeText(getBaseContext(),"Converted!",Toast.LENGTH_SHORT).show();



            stuff=  editText.getText().toString();
            Toast.makeText(getBaseContext(),stuff,Toast.LENGTH_SHORT).show();

            if(stuff.length()==6)
            {
                String val = stuff.substring(0,2);
                int value=Integer.decode("0x"+val);
                //Toast.makeText(getBaseContext(),val,Toast.LENGTH_SHORT).show();
                textView1.setText(Integer.toString(value));

                val = stuff.substring(2,4);
                value=Integer.decode("0x"+val);
                //Toast.makeText(getBaseContext(),val,Toast.LENGTH_SHORT).show();
                textView2.setText(Integer.toString(value));

                val = stuff.substring(4,6);
                value=Integer.decode("0x"+val);
               // Toast.makeText(getBaseContext(),val,Toast.LENGTH_SHORT).show();
                textView3.setText(Integer.toString(value));


            }


        }

    }
}
