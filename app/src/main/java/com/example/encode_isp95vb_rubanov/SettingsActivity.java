package com.example.encode_isp95vb_rubanov;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    MediaPlayer audio;
    CheckBox checkBox;
    TextView textBox_AudioVolume;
    SeekBar seekBar;

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        Settings.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    void init(){
        seekBar = findViewById(R.id.seekBar);
        checkBox = findViewById(R.id.checkBox);
        textBox_AudioVolume = findViewById(R.id.textBox_AudioVolume);
        audio = new MediaPlayer();
        TextView choosedLang;
        choosedLang = findViewById(R.id.textBox_ChoosedLanguage);

        choosedLang.setText(Settings.getCurrLocaleResId());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress * 0.01f;
                audio.setVolume(volume,volume);
                checkBox.setChecked(seekBar.getProgress() == 0);
                textBox_AudioVolume.setText(String.format("%s%%", progress));
                if (fromUser) {
                    audio.start();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                audio = MediaPlayer.create(getApplicationContext(), R.raw.poi);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.setAudioVolume(seekBar.getProgress());
                audio.stop();
            }
        });
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    seekBar.setProgress(0);
                }
            }
        });

        seekBar.setProgress(Settings.getAudioVolume());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audio.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_settings);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickChangeLang(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.TextBox_ChooseLang);
        String[] langCode = {"ru", "en"};
        String[] lang = {getResources().getString(R.string.RussianLang),
                getResources().getString(R.string.EnglishLang)};
        Locale currentLocale = Settings.getLocale();
        int currentLocaleId = 0;
        for(int i = 0; i < langCode.length; i++){
            if (currentLocale.getLanguage() == langCode[i]){
                currentLocaleId = i;
                break;
            }
        }

        dialog.setSingleChoiceItems(lang, currentLocaleId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int checkedItem) {
                int newLang = 0;
                switch (checkedItem){
                    case 0:
                        setLocale(SettingsActivity.this,"ru");
                        newLang = R.string.RussianLang;
                        Toast.makeText(SettingsActivity.this, "Выбран Русский язык",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        setLocale(SettingsActivity.this,"en");
                        newLang = R.string.EnglishLang;
                        Toast.makeText(SettingsActivity.this, "Choosed English language",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                Settings.setCurrLocaleResId(newLang);
                setContentView(R.layout.activity_settings);

                TextView choosedLang = findViewById(R.id.textBox_ChoosedLanguage);
                choosedLang.setText(newLang);
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
