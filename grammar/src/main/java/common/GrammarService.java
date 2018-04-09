package common;

import io.TextReader;
import rules.AmbiguousRules;
import rules.LegitimacyRules;
import rules.MandatoryRules;
import unicode.Sinhala;
import utils.TextUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public class GrammarService {

    private final static String LOG_FILE_NAME = "postfix_log.txt";

    private static String logBrief = "";

    public GrammarService() {

    }

    // Apply Mandatory Rules
    public static void fixMandatory(String outputFilename, String outputDirectoryPath) {
        String text = openFile(outputFilename);
        String log = "Fixed Mandatory\n";

        int fixCount = 0;

        // Find and fix
        for (String key : MandatoryRules.getRules().keySet()) {
            String val = MandatoryRules.getRules().get(key);
            if (text.contains(key)) {
                text = text.replaceAll(key, val);
                log += " " + (++fixCount) + ": Fixed Mandatory: " + key + " => " + val + "\n";
            }
        }

        logBrief += "Fix Mandatory: " + fixCount + " types found...\n";
        saveLog(outputDirectoryPath + LOG_FILE_NAME, log);
        saveFile(outputFilename, text);
    }

    // Check and fix Ambiguities
    public static void fixAmbiguity(String outputFilename, String outputDirectoryPath) {
        String      text           = openFile(outputFilename);
        Set<String> ambiguousChars = AmbiguousRules.getRules().keySet();

        String log = "Fixed Ambiguity\n";
        int fixCount = 0;

        String[] outputWords = TextUtils.splitWords(text);

        // Check for the words in the dictionary
        for (String word : outputWords) {
            // If not the word in dictionary
            if (!DictionaryService.contains(word)) {
                // Check for ambiguous options
                for (String key : ambiguousChars) {
                    if (word.contains(key)) {
                        String newWord = word.replaceAll(key, AmbiguousRules.getRules().get(key));
                        // Check for newWord in dictionary
                        if (DictionaryService.contains(newWord)) {
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
    public static void checkLegitimacy(String outputFilename, String outputDirectoryPath) {
        String text = openFile(outputFilename);

        String log = "Legitimacy Errors\n";
        int errorCount = 0;

        String[] words = TextUtils.splitWords(text);
        for (String word : words) {
            if (word.length() > 0) {
                // Check whether the word starting with _ vowel modifier
                if (Sinhala.isModifier(word.charAt(0))) {
                    log += "  " + (++errorCount) + ": Modifier (" + word.charAt(0) + " in " + word + ")\n";
                }

                // Check whether the word contains _ vowel in the middle
                for (int i = 1; i < word.length(); i++) {
                    if (Sinhala.isVowel(word.charAt(i))) {
                        log += "  " + (++errorCount) + ": Vowel    (" + word.charAt(i) + " in " + word + ")\n";
                    }
                }

                // Check HalKirima error
                if (word.length() > 1) {
                    if (word.charAt(1) == '\u0DCA' && (word.length() < 3 || word.charAt(2) != '\u200D')) {
                        log += "  " + (++errorCount) + ": HalKirima    (in " + word + ")\n";
                    }
                }
            }
        }

        logBrief += "Check Legitimacy: " + errorCount + " errors found...\n";
        saveLog(outputDirectoryPath + LOG_FILE_NAME, log);
    }

    // Verify letters with Extended Blocks
    public static void checkExBlocks(String outputFilename, String outputDirectoryPath) {
        String[] words = TextUtils.splitWords(openFile(outputFilename));

        String log = "ExBlock Errors\n";
        int errorCount = 0;

        for (String word : words) {
            List<String> letters = TextUtils.splitLetters(word);
            for (String letter : letters) {
                if (!LegitimacyRules.getExBlocks().contains(letter) && !Character.isDigit(letter.charAt(0))) {
                    log += "  " + (++errorCount) + ": " + letter + " in " + letter + "\n";
                }
            }
        }

        logBrief += "Check ExBlocks: " + errorCount + " errors found...\n";
        saveLog(outputDirectoryPath + LOG_FILE_NAME, log);
    }


    public static String getLogBrief() {
        return logBrief;
    }


    // Load recognnized text
    public static String openFile(String fileName) {
        // Load recognized text file
        TextReader fo = new TextReader();
        return fo.readAsString(fileName);
    }

    public static void saveFile(String fileName, String text) {
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

    public static void saveLog(String fileName, String text) {
        saveFile(fileName, openFile(fileName) + text);
    }

    public static void clearLog(String outputDirectoryPath) {
        logBrief = "";
        saveFile(outputDirectoryPath + LOG_FILE_NAME, "");
    }


}
