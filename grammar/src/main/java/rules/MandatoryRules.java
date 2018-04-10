package rules;

import config.Paths;
import io.ExcelReader;
import utils.Convert;

import java.util.HashMap;

public class MandatoryRules {
    private static HashMap<String, String> rules;

    static {
        load(); // Load rules
    }

    // Load rules
    public static void load(){
        rules = Convert.toHashMap(ExcelReader.read(Paths.MANDATORY_PATH));
    }

    // Check whether a rule exists
    public static boolean contains(String s){
        return rules.containsKey(s);
    }

    // Apply mandatory rules
    public static String apply(String text){
        for (String key : rules.keySet()){
            if (text.contains(key)){
                text = text.replaceAll(key, rules.get(key));
            }
        }
        return text;
    }


    // Get alternative value of a given key
    public static String getAlternative(String s){
        return rules.get(s);
    }

    // Get rules
    public static HashMap<String, String> getRules(){
        return rules;
    }
}
