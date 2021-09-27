package com.example.encode_isp95vb_rubanov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        radioGroup = findViewById(R.id.radioGroup);
        addNewRadioBtnInGroup("Шифр Цезаря");
        addNewRadioBtnInGroup("Шифр Виженера");
        addNewRadioBtnInGroup("Шифр Гронсфельда");
    }

    void addNewRadioBtnInGroup(String name){
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(name);
        radioButton.setTextSize(26);
        radioGroup.addView(radioButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClickSelectRadioBtn(View view) {
        Intent intent = new Intent(this, EncodeActivity.class);
        int checkedBtnId = radioGroup.getCheckedRadioButtonId();
        intent.putExtra("cipherId",checkedBtnId);
        startActivity(intent);
    }

    public void onClickOpenSettings(View view) {
        // Intent intent = new Intent(this, SettingsActivity.class);
        // startActivity(intent);
    }
}
