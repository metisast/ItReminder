package kz.yankee.itreminder.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Диалог для выбора даты
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Инициализируем календарь с установкой даты
        Calendar c = java.util.Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month =  c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
