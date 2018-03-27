package component.dictionary;

import java.util.Set;

public class PostProcessor {

    public PostProcessor(){

    }

    // Apply Mandatory Rules
    public static String fixMandatory(String text, String[][] rules){
        // Find and fix
        for (String[] rule: rules){
            if (rule[0] != null && rule[1] != null){
                if (text.contains(rule[0])){
                    text = text.replaceAll(rule[0], rule[1]);
                    System.out.println("Fixed Mandatory: " + rule[0] + " => " + rule[1]);
                }
            }
        }

        return text;
    }

    // Check and fix Ambiguities
    public static String fixAmbiguity(String text, String[][] ambiguousChars, Set<String> dictionaryWordList){
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
                            System.out.println("Fixed Ambiguity: " + word + " => " + newWord);
                        }
                    }
                }
            }
        }

        return text;
    }

    // Check legitimacy
    public static String checkLegitimacy(String text, String[] vowels, String[] modifiers){
        String report = "Legitimacy Errors:\n";
        int errorCount = 0;

        text.replace("\n", "").replace("\r", "");
        String[] words = text.split(" ");
        for (String word : words){
            if (word.length() > 0) {
                // Check whether the word starting with a vowel modifier
                for (String m : modifiers){
                    if (word.substring(0, 1).equals(m)){
                        report += "  " + (++errorCount) + ": Modifier (" + m + " in " + word + ")\n";
                    }
                }

                // Check whether the word contains a vowel in the middle
                for (int i = 1; i<word.length(); i++){
                    for (String m : vowels){
                        if (m != null && word.charAt(i) == m.charAt(0)){
                            report += "  " + (++errorCount) + ": Vowel    (" + m + " in " + word + ")\n";
                        }
                    }
                }
            }
        }

        return errorCount + " " + report;
    }



}
