package kz.yankee.itreminder.fragment;


import android.icu.util.TimeUnit;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.yankee.itreminder.R;


/**
 * A simple {@link Fragment} subclass.
 * Отображение splash screen - брендирование приложения
 *
 */
public class SplashFragment extends android.app.Fragment {


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SplashTask splashTask = new SplashTask();
        splashTask.execute();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    class SplashTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                java.util.concurrent.TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(getActivity() != null){
                getActivity().getFragmentManager().popBackStack();
            }

            return null;
        }
    }

}
