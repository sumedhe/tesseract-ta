package rules;

import common.DictionaryService;
import config.Paths;
import io.ExcelReader;
import models.OCRLetter;
import models.OCRWord;
import utils.Convert;

import java.util.HashMap;

public class AmbiguousRules {
    private static HashMap<String, String> rules;

    static {
        load(); // Load rules
    }

    // Load rules
    public static void load(){
        rules = Convert.toHashMap(ExcelReader.read(Paths.AMBIGUOUS_PATH));
    }

    // Check whether a rule exists
    public static boolean contains(String s){
        return rules.containsKey(s);
    }

    // Get alternative value of a given key
    public static String getAlternative(String s){
        return rules.get(s);
    }

    // Get rules
    public static HashMap<String, String> getRules(){
        return rules;
    }

    // Apply Ambiguity rules
    public static void apply(OCRWord ocrWord){
        // Check for the words in the dictionary
        String word = ocrWord.getValue();
        for (String key : rules.keySet()) {
            String val = rules.get(key);
            if (ocrWord.getValue().contains(key)) {
                String newWord = word.replaceAll(key, val);
                // Check for newWord in dictionary
                if (DictionaryService.contains(newWord)) {
                    // Mark changed letter
                    for (OCRLetter ocrLetter : ocrWord.getLetters()){
                        if (ocrLetter.getValue().equals(key)){
                            ocrLetter.setValue(val);
                        }
                    }
                    ocrWord.setInDictionary(true);
                    ocrWord.refresh();
                }
            }
        }
    }
}
