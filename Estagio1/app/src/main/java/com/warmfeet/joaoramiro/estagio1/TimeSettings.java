package com.warmfeet.joaoramiro.estagio1;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by João on 10/07/2017.
 */

public class TimeSettings implements TimePickerDialog.OnTimeSetListener {

    Context context;
    public TimeSettings(Context context)
    {
        this.context=context;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(context,"Time is " + hourOfDay +":"+minute,Toast.LENGTH_LONG).show();
    }
}
