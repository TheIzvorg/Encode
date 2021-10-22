package com.example.encode_isp95vb_rubanov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EncodeActivity extends AppCompatActivity {
    ICipher cipher;
    int cipherId;
    boolean isEncode = true;

    TextView msgBox;
    TextView resultBox;
    TextView keyBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        msgBox = findViewById(R.id.TextBox_Message);
        resultBox = findViewById(R.id.TextBox_Result);
        keyBox = findViewById(R.id.TextBox_Key);

        cipherId = getIntent().getIntExtra("cipherId",0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);

    }

    public void onClickCipherActBtn(View view) {
        ICipher cipher;
        if (isEncode){
            cipher = new Ciphers.Encode();
        } else {
            cipher = new Ciphers.Decode();
        }

        String msg = msgBox.getText().toString();
        String key = keyBox.getText().toString();
        String result = "";

        switch (cipherId){
            case 1:
                result = cipher.Cheaser(msg, Integer.parseInt(key));
                break;
            case 2:
                result = cipher.Vigener(msg, key);
                break;
            case 3:
                result = cipher.Gronsfeld(msg, Integer.parseInt(key));
                break;
        }

        resultBox.setText(result);
    }
}
