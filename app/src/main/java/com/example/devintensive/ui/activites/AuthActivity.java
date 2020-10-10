package com.example.devintensive.ui.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.devintensive.R;
import com.google.android.material.snackbar.Snackbar;

public class AuthActivity extends BaseActivity implements View.OnClickListener {

    private Button signIn;
    private TextView rememberPassword;
    private EditText editTextLogin, editTextPassword;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authefication);

        findFields();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.authButton:
                showSnackBar("Вход");
                loginSuccess();
                break;

            case R.id.remeberPassword:
                rememberPasswordUser();
                break;
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void findFields() {
        signIn = findViewById(R.id.authButton);
        rememberPassword = findViewById(R.id.remeberPassword);
        editTextLogin = findViewById(R.id.signInEmail);
        editTextPassword = findViewById(R.id.signInPassword);
        coordinatorLayout = findViewById(R.id.coordinator_main_layout);

        rememberPassword.setOnClickListener(AuthActivity.this);
        signIn.setOnClickListener(AuthActivity.this);
    }

    private void rememberPasswordUser(){
        Intent rememberIntent = new Intent(Intent.ACTION_VIEW ,
                Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccess(){

    }


}