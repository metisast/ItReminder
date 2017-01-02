package kz.yankee.itreminder;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Хелпер для сохранения настроек приложения в памяти телефона
 */

public class PreferenceHelper {

    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";

    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;

    private PreferenceHelper(){

    }

    public static PreferenceHelper getInstance(){
        if (instance == null){
            instance = new PreferenceHelper();
        }

        return instance;
    }

    // Инициализируем файл настроек
    public void init(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    // Записываем значения
    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Получаем значение
    public boolean getBoolean(String key){
        return preferences.getBoolean(key, false);
    }

}
