package rules;

import common.DictionaryService;
import config.Paths;
import io.ExcelReader;
import models.OCRWord;
import models.Tuple;
import utils.Convert;

import java.util.*;

public class AmbiguousRules {
    private static HashMap<String, List<String>> rules;

    static {
        load(); // Load rules
    }

    // Load rules
    public static void load(){
        rules = Convert.toHashMapMultiple(ExcelReader.read(Paths.AMBIGUOUS_PATH));
    }

    // Check whether a rule exists
    public static boolean contains(String s){
        return rules.containsKey(s);
    }

    // Get alternative value of a given key
    public static List<String> getAlternatives(String s){
        return rules.get(s);
    }

    // Get rules
    public static HashMap<String, List<String>> getRules(){
        return rules;
    }

    // Apply Ambiguity rules
    public static void apply(OCRWord ocrWord){
        String word = ocrWord.getValue();

        // Check available keys
        List<Tuple<String, String>> availableRules = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : rules.entrySet()){
            if (word.contains(e.getKey())){
                for (String val : e.getValue()){
                    availableRules.add(new Tuple<>(e.getKey(), val));
                    System.out.println("RULE:" + e.getKey() + " " + val);
                }
            }
        }


        // Find suggesting words
        // Generating combinations
        int n = availableRules.size();
        for (int i = 1; i < Math.pow(2, n); i++) {
            String bin = Integer.toBinaryString(i);
            while(bin.length() < n){
                bin = "0" + bin;
            }

            // Generate a optional word
            String optWord = word;
            for (int j = 0; j < bin.length(); j++){
                if (bin.charAt(j) == '1'){
                    Tuple<String, String> rule = availableRules.get(j);
                    optWord = optWord.replaceAll(rule.getFirst(), rule.getSecond());
                    // TODO: 4/10/18 Set colors for opt words 
                }
            }

            // Add to optional list if the optional word in dictionary
            if (DictionaryService.contains(optWord) && !ocrWord.getOptionalWords().contains(optWord)){
                ocrWord.getOptionalWords().add(optWord);
            }
        }

        // Decide default word from optionals
        if (!ocrWord.isInDictionary() && ocrWord.getOptionalWords().size() > 0){
            // Select randomly first option
            List<String> optList = ocrWord.getOptionalWords();
            ocrWord.copyFrom(Convert.toOCRWord(optList.get(0)));
            ocrWord.setOptionalWords(optList);
            ocrWord.getOptionalWords().remove(0);
            ocrWord.setInDictionary(true);
        }

    }
}
