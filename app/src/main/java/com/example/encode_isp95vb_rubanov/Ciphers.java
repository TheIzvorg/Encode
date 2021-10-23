package com.example.encode_isp95vb_rubanov;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

interface ICipher {
    String Cheaser(String text, Integer key);
    String Vigener(String msg, String key);
    String Gronsfeld(String text, Integer key);
}
public class Ciphers {
    private static ArrayList<Character> alph;

    protected static void fillAlph(){
        alph = new ArrayList<Character>();
        for (char ch = 'А'; ch <= 'Я'; ch++){
            alph.add(ch);
            if (ch == 'Е') alph.add('Ё');
        }
    }

    private static int reverseInt(Integer number){
        long reversedNum = 0;
        long input_long = number;

        while (input_long != 0) {
            reversedNum = reversedNum * 10 + input_long % 10;
            input_long = input_long / 10;
        }

        if (reversedNum > Integer.MAX_VALUE || reversedNum < Integer.MIN_VALUE) {
            throw new IllegalArgumentException();
        }

        return (int) reversedNum;
    }

    private static boolean isSymbolInAlphabet(char ch){
        boolean isChecked = false;

        if (alph.contains(ch)){
            isChecked = true;
        }

        return isChecked;
    }

    public static class Encode implements ICipher {

        public Encode(){
            fillAlph();
        }

        // По-моему шифр Виженера и Гронсфильда возможно упростить до шифра Цезаря
        // Чтобы избавиться от повторения нескольких участков кода
        @Override
        public String Cheaser(String text, Integer key) {
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase(Locale.ROOT);
            for (int i = 0; i < text.length(); i++){
                char symbol = text.charAt(i);

                if (!isSymbolInAlphabet(symbol)){
                    result.append(symbol);
                    continue;
                }

                int index = (alph.indexOf(symbol) + key)%alph.size();
                symbol = alph.get(index);

                result.append(symbol);
            }
            return result.toString();
        }

        @Override
        public String Vigener(String msg, String key) {
            StringBuilder result = new StringBuilder();
            msg = msg.toUpperCase(Locale.ROOT);
            key = key.toUpperCase(Locale.ROOT);
            for (int i = 0, j = 0; i < msg.length(); i++){

                char msgSymbol = msg.charAt(i);
                char keySymbol = key.charAt(j % key.length());

                if(!isSymbolInAlphabet(keySymbol)){
                    // TODO: Сделать вывод ошибки, при неправильных символах в ключе
                    return "ERROR";
                }


                // Делать проверку на msgSymbol не нужно, т.к. эта проверка есть в методе Cheaser();

                j++;
                result.append(Cheaser(String.valueOf(msgSymbol),alph.indexOf(keySymbol)));
            }

            return result.toString();
        }

        @Override
        public String Gronsfeld(String text, Integer key) {

//            char[] charNumbers = key.toString().toCharArray();
//            int Length = charNumbers.length;
//            int[] numbers = new int[Length];
//
//            for(int i = 0; i < Length; i++){
//                numbers[i] = Integer.parseInt(String.valueOf(charNumbers[i]));
//            }

            int Length = key.toString().length();
            int[] numbers = new int[Length];

            for(int i = 0; key != 0; i++){
                numbers[i] = numbers[i] * 10 + key % 10;
                key = key / 10;
            }

            return Gronsfeld(text, numbers);
        }

        public String Gronsfeld(String text, int[] numbers) {
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase(Locale.ROOT);
            for (int i = 0, j = 0; i < text.length(); i++){

                char msgSymbol = text.charAt(i);
                int key = numbers[j % numbers.length];


                // Делать проверку на msgSymbol не нужно, т.к. эта проверка есть в методе Cheaser();

                result.append(Cheaser(String.valueOf(msgSymbol),key));
                j++;
            }
            return result.toString();
        }
    }

    public static class Decode implements ICipher {

        public Decode(){
            fillAlph();
        }

        @Override
        public String Cheaser(String text, Integer key) {
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase(Locale.ROOT);
            for (int i = 0; i < text.length(); i++){
                char symbol = text.charAt(i);

                if (!isSymbolInAlphabet(symbol)) {
                    result.append(symbol);
                    continue;
                }

                int index = alph.indexOf(symbol) - (key % alph.size());
                index = index < 0 ? index + alph.size() : index;
                result.append(alph.get(index));
            }
            return result.toString();
        }

        @Override
        public String Vigener(String msg, String key) {
            StringBuilder result = new StringBuilder();
            msg = msg.toUpperCase(Locale.ROOT);
            key = key.toUpperCase(Locale.ROOT);
            for (int i = 0, j = 0; i < msg.length(); i++){

                char msgSymbol = msg.charAt(i);
                char keySymbol = key.charAt(j % key.length());

                if (!isSymbolInAlphabet(keySymbol)) {
                    // TODO: Сделать вывод ошибки, при неправильных символах в ключе
                    return "ERROR";
                }

                result.append(Cheaser(String.valueOf(msgSymbol), alph.indexOf(keySymbol)));
                j++;
            }

            return result.toString();
        }

        @Override
        public String Gronsfeld(String text, Integer key) {
            int Length = String.valueOf(key).length();
            int[] numbers = new int[Length];

            for(int i = 0; key != 0; i++){
                numbers[i] = numbers[i] * 10 + key % 10;
                key = key / 10;
            }

            return Gronsfeld(text, numbers);
        }

        public String Gronsfeld(String text, int[] numbers){
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase(Locale.ROOT);
            for (int i = 0, j = 0; i < text.length(); i++){

                char msgSymbol = text.charAt(i);
                int key = numbers[j % numbers.length];

                result.append(Cheaser(String.valueOf(msgSymbol),key));
                j++;
            }
            return result.toString();
        }
    }
}
