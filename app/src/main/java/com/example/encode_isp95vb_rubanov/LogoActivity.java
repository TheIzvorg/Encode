package com.example.encode_isp95vb_rubanov;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class LogoActivity extends AppCompatActivity {

    ImageView imageView,imageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_layout);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        Animation animation = AnimationUtils.loadAnimation(LogoActivity.this, R.anim.rotate);
        imageView.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(LogoActivity.this, R.anim.alpha_rotated);
        imageView2.startAnimation(animation);
        Thread thread = new Thread(){
            public void run(){
                try {
                    TimeUnit.SECONDS.sleep(5);
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
