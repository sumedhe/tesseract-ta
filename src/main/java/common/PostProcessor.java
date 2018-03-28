package common;

import javafx.scene.control.Label;

import java.util.Set;

public class PostProcessor {

    private static String logBrief = "";
    private static String log = "";


    public PostProcessor(){

    }

    // Apply Mandatory Rules
    public static String fixMandatory(String text, String[][] rules){
        log += "Fixed Mandatory:\n";

        int fixCount = 0;

        // Find and fix
        for (String[] rule: rules){
            if (rule[0] != null && rule[1] != null){
                if (text.contains(rule[0])){
                    text = text.replaceAll(rule[0], rule[1]);
                    log += " " + (++fixCount) + ": Fixed Mandatory: " + rule[0] + " => " + rule[1] + "\n";
                    System.out.println(" Fixed Mandatory: " + rule[0] + " => " + rule[1]);
                }
            }
        }

        logBrief += "Fix Mandatory: " + fixCount + " types found...\n";
        return text;
    }

    // Check and fix Ambiguities
    public static String fixAmbiguity(String text, String[][] ambiguousChars, Set<String> dictionaryWordList){
        log += "\nFixed Ambiguity:\n";

        int fixCount = 0;
        String[] outputWords = text.split(" ");

        // Check for the words in the dictionary
        for (String word: outputWords){
            // If not the word in dictionary
            if (!dictionaryWordList.contains(word)){
                // Check for ambiguous options
                for (String[] s: ambiguousChars){
                    if (s[0] != null && word.contains(s[0])){
                        String newWord = word.replaceAll(s[0], s[1]);
                        // Check for newWord in dictionary
                        if (dictionaryWordList.contains(newWord)){
                            // Replace by new word
                            text = text.replaceAll(word, newWord);
                            log += " " + (++fixCount) + ": Fixed Ambiguity: " + word + " => " + newWord + "\n";
                            System.out.println(" Fixed Ambiguity: " + word + " => " + newWord);
                        }
                    }
                }
            }
        }

        logBrief += "Fix Ambiguity: " + fixCount + " types found...\n";
        return text;
    }

    // Check legitimacy
    public static String checkLegitimacy(String text, String[] vowels, String[] modifiers){
        log += "\nLegitimacy Errors:\n";
        int errorCount = 0;

//        text.replace("\n", "");

        text = text.replace("\n", " ").replace("\r", " ");
        String[] words = text.split(" ");
        for (String word : words){
            if (word.length() > 0) {
                // Check whether the word starting with a vowel modifier
                for (String m : modifiers){
                    if (word.substring(0, 1).equals(m)){
                        log += "  " + (++errorCount) + ": Modifier (" + m + " in " + word + ")\n";
                    }
                }

                // Check whether the word contains a vowel in the middle
                for (int i = 1; i<word.length(); i++){
                    for (String m : vowels){
                        if (m != null && word.charAt(i) == m.charAt(0)){
                            log += "  " + (++errorCount) + ": Vowel    (" + m + " in " + word + ")\n";
                        }
                    }
                }
            }
        }

        logBrief += "Check Legitimacy: " + errorCount + " errors found...\n";
        return errorCount + " " + log;
    }


    public static String getLogBrief(){
        return logBrief;
    }

    public static String getLog(){
        return log;
    }

    public static void clearLog(){
        logBrief = "";
    }


}
