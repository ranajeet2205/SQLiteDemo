package com.ranajeet2205.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEditText,passwordEditText;
    private Button loginBtn;
    private TextView signUpTxtView;
    static DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        userNameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginBtn = findViewById(R.id.login_btn);
        signUpTxtView = findViewById(R.id.sign_up_txt_view);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

               if (db.isUserExist(userName,password))
                   startActivity(new Intent(LoginActivity.this,MainActivity.class));
               else
                   Toast.makeText(LoginActivity.this, "Wrong Username OR Password", Toast.LENGTH_SHORT).show();
            }
        });

        signUpTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }
}
