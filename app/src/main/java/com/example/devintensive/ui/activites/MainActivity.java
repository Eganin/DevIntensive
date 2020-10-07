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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.devintensive.R;
import com.example.devintensive.data.managers.DataManager;
import com.example.devintensive.utils.ConstantManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";
    private int currentEditMode = 0;

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton floatingActionButton;
    private EditText editTextUserPhone, editTextUserEmail, editTextUserVK, editTextUserRepository,
            editTextUserInfo;

    private List<EditText> userInfo;

    private DataManager dataManager;

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

        dataManager = DataManager.getInstance();

        findFields();
        setupToolbar();
        setupDrawer();
        loadUserInfoValue();

        if (savedInstanceState == null) {

        } else {
            currentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(currentEditMode);
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
        saveUserInfoValue();
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
    protected void onSaveInstanceState(Bundle outState) {
        /*
        Метод вызывается при перевороте
        и в методе сохраняем данные
         */

        outState.putInt(ConstantManager.EDIT_MODE_KEY, currentEditMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.floatingActionButton:
                showSnackBar("Редактирование профиля");
                if (currentEditMode == 0) {
                    currentEditMode = 1;
                    changeEditMode(1);
                } else {
                    currentEditMode = 0;
                    changeEditMode(0);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //вызов с помощтю кнопки  NavigationView
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private void findFields() {
        coordinatorLayout = findViewById(R.id.coordinatorMainLayout);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutMain);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        editTextUserEmail = findViewById(R.id.editTextEmail);
        editTextUserInfo = findViewById(R.id.editTextPersonInfo);
        editTextUserPhone = findViewById(R.id.editTextPhone);
        editTextUserVK = findViewById(R.id.editTextVk);
        editTextUserRepository = findViewById(R.id.editTextRepository);

        userInfo = new ArrayList<EditText>();
        userInfo.add(editTextUserPhone);
        userInfo.add(editTextUserEmail);
        userInfo.add(editTextUserInfo);
        userInfo.add(editTextUserRepository);
        userInfo.add(editTextUserVK);

        floatingActionButton.setOnClickListener(MainActivity.this);
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
        /*
        создание  ToolBar всесто ActionBar
         */
        // подставляем вместо Action Bar ToolBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_toc_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        /*
        Обоаботка меню в navigation view
         */
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void changeEditMode(int mode) {
        /*
        метод переключает режим редактирования
        1 - режим режактирования
        0 - режми просмотра
         */

        if (mode == 1) {
            floatingActionButton.setImageResource(R.drawable.ic_baseline_check_24);
            for (EditText value : userInfo) {
                //  делаем EditText доступным для редактирования
                value.setEnabled(true);
                value.setFocusable(true);
                value.setFocusableInTouchMode(true);
            }
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_baseline_edit_24);
            for (EditText value : userInfo) {
                value.setEnabled(false);
                value.setFocusable(false);
                value.setFocusableInTouchMode(false);
            }
        }

    }

    private void loadUserInfoValue() {
        List<String> userData = dataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            userInfo.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();

        for (EditText value : userInfo) {
            userData.add(value.getText().toString());
        }

        dataManager.getPreferenceManager().saveUserProfileData(userData);
    }
}
