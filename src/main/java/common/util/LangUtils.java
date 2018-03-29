package common.util;

import common.FileOperations;
import common.LanguageData;
import configuration.ConfigurationHandler;
import javafx.scene.control.Label;
import org.apache.commons.codec.language.bm.Lang;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.OpenOption;
import java.util.Set;

public class LangUtils {

    private final static String LOG_FILE_NAME = "postfix_log.txt";

    private static String logBrief = "";

    public LangUtils(){

    }

    // Apply Mandatory Rules
    public static void fixMandatory(String outputFilename, String outputDirectoryPath){
        String text = openFile(outputFilename);
        String log = "Fixed Mandatory:\n";

        int fixCount = 0;

        // Find and fix
        for (String[] rule: LanguageData.getMandatoryRules()){
            if (rule[0] != null && rule[1] != null){
                if (text.contains(rule[0])){
                    text = text.replaceAll(rule[0], rule[1]);
                    log += " " + (++fixCount) + ": Fixed Mandatory: " + rule[0] + " => " + rule[1] + "\n";
                }
            }
        }

        logBrief += "Fix Mandatory: " + fixCount + " types found...\n";
        saveLog(outputDirectoryPath + LOG_FILE_NAME, log);
        saveFile(outputFilename, text);
    }

    // Check and fix Ambiguities
    public static void fixAmbiguity(String outputFilename, String outputDirectoryPath){
        String text = openFile(outputFilename);
        String[][] ambiguousChars = LanguageData.getAmbiguousChars();
        Set<String> dictionaryWordList = LanguageData.getDictionaryWordList();

        String log = "\nFixed Ambiguity:\n";
        int fixCount = 0;

        String[] outputWords = splitWords(text);

        // Check for the words in the dictionary
        for (String word: outputWords){
            // If not the word in dictionary
            if (!LanguageData.isInDictionary(word)){
                // Check for ambiguous options
                for (String[] s: ambiguousChars){
                    if (s[0] != null && word.contains(s[0])){
                        String newWord = word.replaceAll(s[0], s[1]);
                        // Check for newWord in dictionary
                        if (LanguageData.isInDictionary(newWord)){
                            // Replace by new word
                            text = text.replaceAll(word, newWord);
                            log += " " + (++fixCount) + ": Fixed Ambiguity: " + word + " => " + newWord + "\n";
                        }
                    }
                }
            }
        }

        logBrief += "Fix Ambiguity: " + fixCount + " types found...\n";
        saveLog(outputDirectoryPath + LOG_FILE_NAME, log);
        saveFile(outputFilename, text);
    }

    // Check legitimacy
    public static void checkLegitimacy(String outputFilename, String outputDirectoryPath){
        String text = openFile(outputFilename);

        String log = "\nLegitimacy Errors:\n";
        int errorCount = 0;

        String[] words = splitWords(text);
        for (String word : words){
            if (word.length() > 0) {
                // Check whether the word starting with a vowel modifier
                    if (LanguageData.isModifier(word.charAt(0))){
                    log += "  " + (++errorCount) + ": Modifier (" + word.charAt(0) + " in " + word + ")\n";
                }

                // Check whether the word contains a vowel in the middle
                for (int i = 1; i<word.length(); i++){
                    if (LanguageData.isVowel(word.charAt(i))){
                        log += "  " + (++errorCount) + ": Vowel    (" + word.charAt(i) + " in " + word + ")\n";
                    }
                }
            }
        }

        logBrief += "Check Legitimacy: " + errorCount + " errors found...\n";
        saveLog(outputDirectoryPath + LOG_FILE_NAME, log);
    }

    // Verify letters with Extended Blocks
    public static void checkExBlocks(String outputFilename, String outputDirectoryPath){
        String[] words = splitWords(openFile(outputFilename));

        for (String word : words){
//            System.out.println(word);
            int start = 0;
            boolean skip = false;
            for (int i=0; i<word.length(); i++){

//              System.out.println(Integer.toHexString(word.charAt(i) | 0x10000).substring(1));
                if (word.charAt(i) != '\u200D') {
                    if (i + 1 == word.length() ||  !(LanguageData.isModifier(word.charAt(i+1)) || word.charAt(i+1) == '\u200D')) {
                        String letter = word.substring(start, i + 1);
                        System.out.println(letter);
                        start = i + 1;
                    }
                }

            }
        }
    }


    public static String getLogBrief(){
        return logBrief;
    }


    // Load recognnized text
    public static String openFile(String fileName){
        // Load recognized text file
        FileOperations fo = new FileOperations();
        return fo.openFile(fileName);
    }

    public static void saveFile(String fileName, String text){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void saveLog(String fileName, String text){
        saveFile(fileName, openFile(fileName) + text);
    }

    public static void clearLog(String outputDirectoryPath){
        logBrief = "";
        saveFile(outputDirectoryPath + LOG_FILE_NAME, "");
    }

    public static String[] splitWords(String text){
        return text.replace("\n", " ").replace("\r", " ").split(" ");
    }


}
