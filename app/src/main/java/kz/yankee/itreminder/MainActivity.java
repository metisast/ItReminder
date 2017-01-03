package kz.yankee.itreminder;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import kz.yankee.itreminder.adapter.CurrentTaskAdapter;
import kz.yankee.itreminder.adapter.TabAdapter;
import kz.yankee.itreminder.dialog.AddingTaskDialogFragment;
import kz.yankee.itreminder.fragment.CurrentTaskFragment;
import kz.yankee.itreminder.fragment.DoneTaskFragment;
import kz.yankee.itreminder.fragment.SplashFragment;
import kz.yankee.itreminder.model.ModelTask;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener{

    android.app.FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;
    TabAdapter tabAdapter;

    CurrentTaskFragment currentTaskFragment;
    DoneTaskFragment doneTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем хелпер для отлова и сохранения состояния splash screen
        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();

        fragmentManager = getFragmentManager();

        // Запускаем splash screen
        runSplash();
        // Запускаем toolbar, tabs и fragments
        setUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Определяем пункт меню splash screen
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        // Определяем текущее состояние splash screen
        splashItem.setCheckable(preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_splash){
            item.setChecked(!item.isChecked()); //Меняем состояние splash screen
            preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked()); // Сохраняем состояние splash screen
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Метод запуска splash screen
    public void runSplash(){
        // Проверяем текущее состояние и при необходимости запускаем
        if(!preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)){
            SplashFragment splashFragment = new SplashFragment(); // Инициализируем фрагмент splash screen

            // Добавляем фрагент в транзакцию
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // Интерфейс отображения toolbar, tabs и fragments
    private void setUI(){

        // Создаем toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        // Создаем tabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // Элементы табов
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        // Контейнер для отображения фрагментов
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Слушатель для отлова авктивности табов
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Определяем фрагменты
        currentTaskFragment = (CurrentTaskFragment) tabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);
        doneTaskFragment = (DoneTaskFragment) tabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        // Слушатель для создания новой задачи
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Инициализируем диалог для создания новой задачи
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(fragmentManager, "AddingTaskDialogFragment");
            }
        });

    }

    // Слушатели для отлова состояний задач
    @Override
    public void onTaskAdded(ModelTask newTask) {
        currentTaskFragment.addTask(newTask);
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Task adding cancel", Toast.LENGTH_SHORT).show();
    }
}
