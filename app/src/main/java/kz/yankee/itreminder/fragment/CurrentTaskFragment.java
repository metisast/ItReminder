package kz.yankee.itreminder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.yankee.itreminder.R;


/**
 * A simple {@link Fragment} subclass.
 * Отображение текущих задач
 *
 */
public class CurrentTaskFragment extends android.app.Fragment {


    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_task, container, false);
    }

}
