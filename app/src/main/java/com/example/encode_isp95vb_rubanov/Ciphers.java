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

    private static boolean isSymbolInAlphabet(char ch){
        boolean isChecked = false;

        if (alph.contains(ch)){
            isChecked = true;
        }

        /*
        for (int j = 0; j < alph.size(); j++) {
            if (ch == alph.get(j)) {
                isChecked = true;
                break;
            }
        }
        */

        return isChecked;
    }

    public static class Encode implements ICipher {

        public Encode(){
            fillAlph();
        }

        /*
        public int Count;

        public Encode(){
            Count = ICipher.class.getDeclaredMethods().length;
        }
        */

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

            int Length = key.toString().length(); // key = 6152
            int[] numbers = new int[Length];

            key = Integer.reverse(key); // key = 2516

            for (int i = 0; i < Length; i++){
                numbers[i] = key % 10; // {6, 1, 5, 2}
                key /= 10; // key = 251, key = 25, key = 2, key = 0;
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

                for (int j = 0; j < alph.size(); j++){
                    if (symbol == alph.get(j)){
                        if(j - key < 0) {
                            key -= j + 1;
                            j = alph.size()-1;
                        }
                        symbol = alph.get(j-key);
                        break;
                    }
                }

                result.append(symbol);
            }
            return result.toString();
        }

        @Override
        public String Vigener(String msg, String key) {
            StringBuilder result = new StringBuilder();
            msg = msg.toUpperCase(Locale.ROOT);
            key = key.toUpperCase(Locale.ROOT);
            for (int i = 0; i < msg.length(); i++){

                char msgSymbol = msg.charAt(i);
                char keySymbol = key.charAt(i % key.length());

                if (!isSymbolInAlphabet(msgSymbol)) {
                    result.append(msgSymbol);
                    continue;
                }

                int inter = 0; // а

                for (int j = 0; j < alph.size(); j++){
                    if (alph.get(j) == msgSymbol) { inter += j; break;} // Б
                }

                for (int j = 0; j < alph.size(); j++){
                    if (alph.get(j) == keySymbol) {
                        if (inter - j < 0) { // Б-В = -1
                            j -= inter + 1; // 0
                            inter = alph.size() - 1; // Я
                        }
                        inter -= j;
                        break;
                    }
                }

                char newSymbol = alph.get(inter % alph.size());

                result.append(newSymbol);
            }

            return result.toString();
        }

        @Override
        public String Gronsfeld(String text, Integer key) {
            int Length = String.valueOf(key).length();
            int[] numbers = new int[Length];

            key = Integer.reverse(key);

            for (int i = 0; i < Length; i++){
                numbers[i] = key % 10;
                key /= 10;
            }

            return Gronsfeld(text, numbers);
        }

        public String Gronsfeld(String text, int[] numbers){
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase(Locale.ROOT);
            for (int i = 0; i < text.length(); i++){

                char msgSymbol = text.charAt(i);
                int key = numbers[i % numbers.length];

                if (!isSymbolInAlphabet(msgSymbol)) {
                    result.append(msgSymbol);
                    continue;
                }

                int pos = 0;

                for (int j = 0; j < alph.size(); j++){
                    if (msgSymbol == alph.get(j)) {
                        pos = j;
                        break;
                    }
                }
                if (pos < key){
                    key -= pos + 1;
                    pos = alph.size() - 1;
                }
                int alphabetPos = pos - key;
                char newSymbol = alph.get(alphabetPos);

                result.append(newSymbol);
            }
            return result.toString();
        }
    }
}
