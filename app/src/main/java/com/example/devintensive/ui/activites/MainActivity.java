package com.example.devintensive.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.devintensive.R;
import com.example.devintensive.utils.ConstantManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

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
        Log.d(TAG, "onCreate");

        findFields();
        setupToolbar();
        setupDrawer();

        if (savedInstanceState == null) {
            // первое создание activity
            showSnackBar("Активити запускается впервые");
            showToast("Активити запускается впервые");
        } else {
            showSnackBar("Активити уже запускалось");
            showToast("Активити уже запускалось");
        }
    }

    @Override
    protected void onStart() {
        /*
        метод вызывается перед показом UI
        в методе происходит регистрация подписки на события
        который бвли остановлены в onStop
         */
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        /*
        метод вызывается когда UI виден и доступен
        в методе происходит запуск анимаций  видео звука
        запуск потоков необходимых для UI
         */
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        /*
        когда смещется фокус на другое на activity видна
        в методе реализуется легковесное сохранение данных
         */
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        /*
        метод вызывается когда activity становится невидимым для пользователя
        в методе происходит остановка сложных анимаций , отписка от событий
        сложные операции по сохранениию данных
        прерывание потоков
         */
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        /*
        уничтожение activity
         */
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        /*
        restart activity после метода onStop
         */
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle) {
        /*
        Метод вызывается при перевороте
        и в методе сохраняем данные
         */
        super.onSaveInstanceState(outBundle);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private void findFields() {
        coordinatorLayout = findViewById(R.id.coordinatorMainLayout);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutMain);
    }

    private void showSnackBar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void rinWithDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // выполнение метода с задержкой

                hideProgress();
            }
        }, 5000);
    }

    private void setupToolbar() {
        // подставляем вместо Action Bar ToolBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_toc_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer(){
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
}
