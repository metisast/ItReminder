package kz.yankee.itreminder.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import kz.yankee.itreminder.R;
import kz.yankee.itreminder.Utils;
import kz.yankee.itreminder.model.ModelTask;

/**
 * Создаем диалоговое окно для постановки задачи
 */

public class AddingTaskDialogFragment extends DialogFragment {

    private AddingTaskListener addingTaskListener;

    // Реализуем интерфейсы для отлова состояния из activity
    public interface AddingTaskListener {
        void onTaskAdded(ModelTask newTask);
        void onTaskAddingCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            addingTaskListener = (AddingTaskListener) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString() + "must implement");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Инициализируем
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);// Текст заголовка окна

        // Устанавливаем layout для отобраения диалогового окна
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_task, null);

        // Задаем текст элементам интерфейса
        final TextInputLayout tilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        final EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        final EditText etDate = tilDate.getEditText();

        TextInputLayout tilTime = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        final EditText etTime = tilTime.getEditText();

        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilDate.setHint(getResources().getString(R.string.task_date));
        tilTime.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        final ModelTask task = new ModelTask();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);

        // Запускаем диалоговое окно для выбора даты
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etDate.length() == 0){
                    etDate.setText(" ");
                }
                // Запускаем слушателя выбора даты
                DialogFragment datePickerFragment = new DatePickerFragment(){
                    // Устанавливаем выбранные дату пользователем в текстовое поле
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    // Отменяем диалог
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                // Запускаем фрагмент с выбором даты
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        // Запускаем диалоговое окно для выбора времени
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTime.length() == 0){
                    etTime.setText(" ");
                }
                // Запускаем слушателя выбора времени
                DialogFragment timePickerFragment = new TimePickerFragment(){
                    // Устанавливаем выбранное время пользователем в текстовое поле
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        etTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }
                    // Отменяем диалог
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etTime.setText(null);
                    }
                };
                // Запускаем фрагмент с выбором времени
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        // Описание события кнопки OK
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                task.setTitle(etTitle.getText().toString());
                if(etDate.length() != 0 || etTime.length() != 0){
                    task.setDate(calendar.getTimeInMillis());
                }
                // Говорим MainActivity что произошло событие
                addingTaskListener.onTaskAdded(task);
                dialogInterface.dismiss();
            }
        });

        // Описание события кнопки Cancel
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Говорим MainActivity что произошло событие
                addingTaskListener.onTaskAddingCancel();
                dialogInterface.cancel();
            }
        });

        // Инициализируем диалог с слушателем
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                // Устанавливаем валидацию для поля Title task
                if(etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Блокируем кнопку если поле пустое
                        if (charSequence.length() == 0){
                            positiveButton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                        }else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
