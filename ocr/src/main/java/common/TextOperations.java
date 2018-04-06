package common;

import java.util.ArrayList;
import java.util.List;

public class TextOperations {

    private final static char ZERO_WIDTH_JOINER = '\u200D';

    // Split text to word array
    public static String[] splitWords(String text){
        return  removeWhiteSpaces(text).split(" ");
    }

    // Remove all whitespaces
    public static String removeWhiteSpaces(String text){
        return text.replace("\n", " ").replace("\r", " ");
    }

    // Split letters
    public static List<String> splitLetters(String text){
        text = removeWhiteSpaces(text);
        List<String> letters = new ArrayList<>();

        int start = 0;
        for (int i=0; i<text.length(); i++){
            if (text.charAt(i) != ZERO_WIDTH_JOINER) { // Ignore zoro width characters
                // If next char starts a new letter
                if (i + 1 == text.length() ||  !(LanguageData.isModifier(text.charAt(i+1)) || text.charAt(i+1) == ZERO_WIDTH_JOINER)) {
                    String letter = text.substring(start, i + 1);
                    letters.add(letter);
                    start = i + 1;
                }
            }
        }

        return letters;
    }


}
