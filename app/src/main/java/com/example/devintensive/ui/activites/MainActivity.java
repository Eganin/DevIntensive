package com.example.devintensive.ui.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.devintensive.R;
import com.example.devintensive.utils.ConstantManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = ConstantManager.TAG_PREFIX+ "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        в методе инициализируется UI- статика
        инициализация статических данных activity
        связь данных со списками
        нельзя запускать длительные операции
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG , "onCreate");

        if(savedInstanceState == null){
            // первое создание activity

        }else{

        }
    }

    @Override
    protected void onStart(){
        /*
        метод вызывается перед показом UI
        в методе происходит регистрация подписки на события
        который бвли остановлены в onStop
         */
        super.onStart();
        Log.d(TAG , "onStart");
    }

    @Override
    protected void onResume(){
        /*
        метод вызывается когда UI виден и доступен
        в методе происходит запуск анимаций  видео звука
        запуск потоков необходимых для UI
         */
        super.onResume();
        Log.d(TAG , "onResume");
    }

    @Override
    protected void onPause(){
        /*
        когда смещется фокус на другое на activity видна
        в методе реализуется легковесное сохранение данных
         */
        super.onPause();
        Log.d(TAG , "onPause");
    }

    @Override
    protected void onStop(){
        /*
        метод вызывается когда activity становится невидимым для пользователя
        в методе происходит остановка сложных анимаций , отписка от событий
        сложные операции по сохранениию данных
        прерывание потоков
         */
        super.onStop();
        Log.d(TAG , "onStop");
    }

    @Override
    protected void onDestroy(){
        /*
        уничтожение activity
         */
        super.onDestroy();
        Log.d(TAG , "onDestroy");
    }

    @Override
    protected void onRestart(){
        /*
        restart activity после метода onStop
         */
        super.onRestart();
        Log.d(TAG , "onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle){
        /*
        Метод вызывается при перевороте
        и в методе сохраняем данные
         */
        super.onSaveInstanceState(outBundle);
    }
}