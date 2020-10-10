package com.example.devintensive.ui.activites;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devintensive.R;
import com.example.devintensive.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity {

    static final String TAG = ConstantManager.TAG_PREFIX + "Base Activity";
    protected ProgressDialog progressDialog;

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this, R.style.custom_dialog);
            // мы не позволяем закрыть progressDialog кнопокой назад
            progressDialog.setCancelable(false);
            // устанавливаем прозрачный фон
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
            // вставляем view
            progressDialog.setContentView(R.layout.dialog_splash);
        } else {
            progressDialog.show();
            progressDialog.setContentView(R.layout.dialog_splash);
        }
    }

    public void hideProgress() {
        if (progressDialog != null) {
            // если он сейчас показывается
            if(progressDialog.isShowing()){
                progressDialog.hide();
            }
        }
    }

    public void showError(String message, Exception error) {
        showToast(message);
        Log.e(TAG, String.valueOf(error));
    }

    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }


}
