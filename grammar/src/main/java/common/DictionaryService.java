package common;

import io.TextReader;
import io.TextWriter;
import utils.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

public class DictionaryService {
    private static HashSet<String> words;
    private static String dictionaryFileName;

    // Load dictionary words
    public static void load(){
        String text = TextReader.readAsString(dictionaryFileName);
        words = new HashSet<String>(Arrays.asList(TextUtils.splitWords(text)));
    }

    // Check whether a word contains in the dictionary
    public static boolean contains(String word){
        return words.contains(word);
    }

    // Add new word to the dictionary
    public static void addWord(String word){
        words.add(word);
        TextWriter.append(dictionaryFileName, word);
    }
}
