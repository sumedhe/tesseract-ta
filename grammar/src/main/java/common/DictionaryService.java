package common;

import io.TextReader;
import io.TextWriter;
import models.OCRWord;

import java.util.HashSet;

import static config.Paths.*;

public class DictionaryService {
    private static HashSet<String> words;

    static {
        load(); // Load words
    }

    // Load dictionary words
    public static void load(){
        words = new HashSet<String>(TextReader.readLines(DICTIONARY_PATH));
    }

    // Check whether a word contains in the dictionary
    public static boolean contains(String word) {
        return words.contains(word.replace(".", "").replace(",", ""));
    }

    // Add new word to the dictionary
    public static void addWord(String word) {
        words.add(word);
        TextWriter.append(DICTIONARY_PATH, word);
    }

    public static void check(OCRWord ocrWord){
        ocrWord.setInDictionary(contains(ocrWord.getValue()));
    }
}
