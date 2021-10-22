package com.example.encode_isp95vb_rubanov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnEnter, btnRegistration;
    TextView textBox_Login, textBox_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = findViewById(R.id.btnEnter);
        btnRegistration = findViewById(R.id.btnRegistration);
        textBox_Login = findViewById(R.id.textBox_Login);
        textBox_Password = findViewById(R.id.textBox_Password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void LogInToSystem(View view) {
        if (ValidUserData()) {
            Toast.makeText(this,"Неверный логин или пароль",Toast.LENGTH_LONG).show();
            textBox_Password.setText("");
            return;
        }
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private boolean ValidUserData() {
        String login = textBox_Login.getText().toString();
        String password = textBox_Password.getText().toString();
        return login == "admin" && password == "admin";
    }

    public void goToSignInWindow(View view) {
        System.out.println("Not implemented metod");
    }
}