package kz.yankee.itreminder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.yankee.itreminder.R;


/**
 * A simple {@link Fragment} subclass.
 * Отображение выполненных задач
 *
 */
public class DoneTaskFragment extends android.app.Fragment {


    public DoneTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done_task, container, false);
    }

}
