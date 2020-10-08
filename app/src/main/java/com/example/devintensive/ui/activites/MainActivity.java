package com.example.devintensive.ui.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.devintensive.R;
import com.example.devintensive.data.managers.DataManager;
import com.example.devintensive.utils.ConstantManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private RelativeLayout profilePlaceholder;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private AppBarLayout.LayoutParams appBarParams = null;
    private ImageView userPhoto;

    private List<EditText> userInfo;

    private DataManager dataManager;

    private File photoFile = null;
    private Uri selectedImage = null;

    public MainActivity() throws IOException {
    }

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
        Picasso.with(MainActivity.this)
                .load(dataManager.getPreferenceManager().loadUserPhoto())
                .into(userPhoto);


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
        saveUserInfoValue();
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

            case R.id.profile_placeholder:
                //  идем в метод onCreateDialog
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
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

    /**
     * Получение результата из другой Activity (фото из камеры или галереи)
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && photoFile != null) {
                    selectedImage = Uri.fromFile(photoFile);
                    insertProfileImage(selectedImage);
                }
                break;

            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    selectedImage = data.getData();
                    insertProfileImage(selectedImage);
                }
                break;

        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = new String[]{
                        getString(R.string.user_profile_dialog_gallery),
                        getString(R.string.user_profile_dialog_camera),
                        getString(R.string.user_profile_dialog_cancel)
                };

                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.dialog_title);
                // добавляем вырианты в dialog и обрабатываем их нажатия
                dialog.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int choiceItem) {
                        switch (choiceItem) {
                            case 0:
                                showSnackBar(getString(R.string.user_profile_dialog_gallery));
                                loadPhotoFromGallery();
                                break;

                            case 1:
                                showSnackBar(getString(R.string.user_profile_dialog_camera));
                                loadPhotoFromCamera();
                                break;

                            case 2:
                                showSnackBar(getString(R.string.user_profile_dialog_cancel));
                                dialogInterface.cancel();
                                break;
                        }
                    }
                });
                return dialog.create();

            default:
                return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == ConstantManager.CAMERA_PERMISSION_REQUEST_CODE &&
                grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {

        }

    }

    private void findFields() {
        coordinatorLayout = findViewById(R.id.coordinatorMainLayout);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutMain);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        profilePlaceholder = findViewById(R.id.profile_placeholder);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        appBarLayout = findViewById(R.id.appBar);
        userPhoto = findViewById(R.id.userPhoto);

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

        profilePlaceholder.setOnClickListener(MainActivity.this);
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

        appBarParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
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
        1 - режим редактирования
        0 - режми просмотра
         */

        if (mode == 1) {
            floatingActionButton.setImageResource(R.drawable.ic_baseline_check_24);
            showProfilePlaceHolder();
            lockToolBar();
            // делаем запись из collapsingToolBar прозрачным
            collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
            for (EditText value : userInfo) {
                //  делаем EditText доступным для редактирования
                value.setEnabled(true);
                value.setFocusable(true);
                value.setFocusableInTouchMode(true);
            }
        } else {
            saveUserInfoValue();
            hideProfilePlaceHolder();
            unlockToolBar();
            // делаем запись из collapsingToolBar видимой
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white_text));
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

    private void hideProfilePlaceHolder() {
        profilePlaceholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceHolder() {
        profilePlaceholder.setVisibility(View.VISIBLE);
    }

    private void lockToolBar() {
        // ToolBar не будет схлопываться

        // использование встроенной анимации
        // плавное разворачивание
        appBarLayout.setExpanded(true, true);

        // устанавливаем нескручиваемость ToolBar
        appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL);
        // применяем новые параметры на CollapsingTollBar
        collapsingToolbarLayout.setLayoutParams(appBarParams);
    }

    private void unlockToolBar() {
        // делаем ToolBar схлопываемым
        appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        collapsingToolbarLayout.setLayoutParams(appBarParams);
    }

    private void loadPhotoFromGallery() {
        // intent для получения изображения из галерем
        Intent takeGallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // какой тип данных мы собираемся достать
        takeGallery.setType("image/*");
        startActivityForResult(
                Intent.createChooser(takeGallery, getString(R.string.choice_photo_from_gallery)),
                ConstantManager.REQUEST_GALLERY_PICTURE
        );
    }

    private void loadPhotoFromCamera() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File photoFile = null;
            // intent для открытия камеры
            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                // добавляем данные в intent где будет хранится потом  сфотканная фотка
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, ConstantManager.CAMERA_PERMISSION_REQUEST_CODE);

            Snackbar.make(coordinatorLayout, "Для необходимой работы приложения необходимо" +
                    " дать требуемые разрешения ", Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // открываем натройки приложения
                            openApplicationSettings();
                        }
                    })
                    .show();
        }
    }

    private File createImageFile() throws IOException {
        // получаем точную дату получения снимка
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // получаем доступ к  хранилищю
        // и указываем на изображения
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // create file
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN , System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE , "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.DATA , image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , contentValues);

        return image;
    }

    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(MainActivity.this)
                .load(selectedImage)
                .into(userPhoto);

        dataManager.getPreferenceManager().saveUserPhoto(selectedImage);
    }

    public void openApplicationSettings() {
        // обработка разрешений
        // intent для перехода в настройки приложения
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));

        startActivityForResult(appSettingsIntent, ConstantManager.REQUEST_SETTINGS_CODE);
    }
}
