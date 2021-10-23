package com.example.encode_isp95vb_rubanov;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EncodeActivity extends AppCompatActivity {
    ICipher cipher;
    int cipherId;
    boolean isEncode = true;

    TextView msgBox, resultBox, keyBox;
    TextView textBlock_EncodeTitle;
    Button btn_ChiperAct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        cipherId = getIntent().getIntExtra("cipherId",0);

        msgBox = findViewById(R.id.TextBox_Message);
        resultBox = findViewById(R.id.TextBox_Result);
        keyBox = findViewById(R.id.TextBox_Key);
        btn_ChiperAct = findViewById(R.id.btn_ChiperAct);
        textBlock_EncodeTitle = findViewById(R.id.textBlock_EncodeTitle);

        if(cipherId != 1){
            keyBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            keyBox.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        resultBox.setInputType(InputType.TYPE_NULL);
        resultBox.setTextIsSelectable(true);

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
            case 0:
                result = cipher.Cheaser(msg, Integer.parseInt(key));
                break;
            case 1:
                result = cipher.Vigener(msg, key);
                break;
            case 2:
                result = cipher.Gronsfeld(msg, Integer.parseInt(key));
                break;
        }

        resultBox.setText(result);
    }

    public void onClickSwap(View view) {
        isEncode = !isEncode;
        if (isEncode){
            textBlock_EncodeTitle.setText(R.string.EncodeAct);
            btn_ChiperAct.setText(R.string.EncodeAct);
        } else {
            textBlock_EncodeTitle.setText(R.string.DecodeAct);
            btn_ChiperAct.setText(R.string.DecodeAct);
        }
    }
}
