package com.warmfeet.joaoramiro.estagio1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by João on 10/07/2017.
 */

public class DialogHandler extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar =Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
TimePickerDialog dialog;
        TimeSettings timeSettings=new TimeSettings(getActivity());
        dialog=new TimePickerDialog(getActivity(),timeSettings,hour,minute, true);
return  dialog;
//        return super.onCreateDialog(savedInstanceState);
    }
}
