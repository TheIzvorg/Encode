package com.example.encode_isp95vb_rubanov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EncodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
