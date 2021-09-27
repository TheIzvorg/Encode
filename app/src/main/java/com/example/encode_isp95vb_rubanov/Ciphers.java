package com.example.encode_isp95vb_rubanov;

import java.util.List;
import java.util.Locale;

interface ICipher {
    String Cheaser(String text, int key);
    String Vigener(String msg, String key);
    String Gronsfeld(String text, int number);
}
public class Ciphers {
    private static List<Character> alph;

    public Ciphers(){
        for (char ch = 'А'; ch < 'Я'; ch++){
            alph.add(ch);
            if (ch == 'Е') alph.add('Ё');
        }
    }

    private static boolean isSymbolInAlphabet(char ch){
        boolean isChecked = false;

        for (int j = 0; j < alph.size(); j++) {
            if (ch == alph.get(j)) {
                isChecked = true;
                break;
            }
        }

        return isChecked;
    }

    public static class Encode implements ICipher {

        /*
        public int Count;

        public Encode(){
            Count = ICipher.class.getDeclaredMethods().length;
        }
        */

        // По-моему шифр Виженера и Гронсфильда возможно упростить до шифра Цезаря
        // Чтобы избавиться от повторения нескольких участков кода
        @Override
        public String Cheaser(String text, int key) {
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase(Locale.ROOT);
            for (int i = 0; i < text.length(); i++){
                char symbol = text.charAt(i);

                for (int j = 0; j < alph.size(); j++){
                    if (symbol == alph.get(j)){
                        symbol = alph.get((j+key)%alph.size());
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

                int inter = 0;

                for (int j = 0; j < alph.size(); j++){
                    if (alph.get(j) == msgSymbol) { inter += j; }
                    if (alph.get(j) == keySymbol) { inter += j; }
                }

                char newSymbol = alph.get(inter % alph.size());

                result.append(newSymbol);
            }

            return result.toString();
        }

        @Override
        public String Gronsfeld(String text, int number) {
            int Length = String.valueOf(number).length();
            int[] numbers = new int[Length];

            number = Integer.reverse(number);

            for (int i = 0; i < Length; i++){
                numbers[i] = number % 10;
                number /= 10;
            }

            return Gronsfeld(text, numbers);
        }

        public String Gronsfeld(String text, int[] numbers) {
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
                int alpabetPos = (pos + key) % alph.size();
                char newSymbol = alph.get(alpabetPos);

                result.append(newSymbol);
            }
            return result.toString();
        }
    }

    public static class Decode implements ICipher {

        @Override
        public String Cheaser(String text, int key) {
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
        public String Gronsfeld(String text, int number) {
            int Length = String.valueOf(number).length();
            int[] numbers = new int[Length];

            number = Integer.reverse(number);

            for (int i = 0; i < Length; i++){
                numbers[i] = number % 10;
                number /= 10;
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
