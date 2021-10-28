package com.example.encode_isp95vb_rubanov;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button SelectRadioBtn;

    void init(){
        radioGroup = findViewById(R.id.radioGroup);
        SelectRadioBtn = findViewById(R.id.menuBtn_SelectRadioBtn);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SelectRadioBtn.setEnabled(true);
            }
        });
        addNewRadioBtnInGroup(R.string.CheaserChiper);
        addNewRadioBtnInGroup(R.string.VishenereChiper);
        addNewRadioBtnInGroup(R.string.GronfieldChiper);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_menu);
        init();
    }

    @SuppressLint("ResourceAsColor")
    void addNewRadioBtnInGroup(String name){
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(name);
        radioButton.setTextSize(26);
        getResources().newTheme();
        radioButton.setTextColor(R.color.TextColor);
        radioButton.setHintTextColor(R.color.TextColor);
        radioButton.setLinkTextColor(R.color.TextColor);
        radioButton.setId(radioGroup.getChildCount());
        radioGroup.addView(radioButton);
    }

    void addNewRadioBtnInGroup(int rId){
        addNewRadioBtnInGroup(getResources().getString(rId));
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
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
