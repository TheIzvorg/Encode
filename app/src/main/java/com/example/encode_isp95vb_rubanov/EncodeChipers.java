package com.example.encode_isp95vb_rubanov;

import java.lang.reflect.Array;

interface IEncode{
    String EnCheaserChiper(int count, String text);
    Integer[] EnVigenerChiper(String msg, String key);
}

public class EncodeChipers implements IEncode {
    char[] alph = {'а','б','в','г','д','е','ё'};
    @Override
    public String EnCheaserChiper(int count, String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            char symbol = text.charAt(i);
            for (int j = 0; j < alph.length; j++){
                if (symbol == alph[j]){
                    symbol = alph[(j+count)%alph.length];
                    break;
                }
            }

            result.append(symbol);
        }
        return result.toString();
    }

    @Override
    public Integer[] EnVigenerChiper(String msg, String key) {
        Integer[] result = new Integer[msg.length()];
        StringBuilder resulter = new StringBuilder();
        for (int i = 0; i < msg.length(); i++){

            int inter = 0;

            char msgSymbol = msg.charAt(i);
            char keySymbol = key.charAt(i % key.length());
            for (int j = 0; j < alph.length; j++){
                if (alph[j] == msgSymbol) { inter += j; }
                if (alph[j] == keySymbol) { inter += j; }
            }
            char newSymbol = alph[inter % alph.length];
            resulter.append(newSymbol);

        }

        return result;
    }
}
