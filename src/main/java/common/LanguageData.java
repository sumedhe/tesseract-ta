package common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LanguageData {

    // Sheet indexes
    private static final int MANDATORY_SHEET  = 0;
    private static final int AMBIGUITY_SHEET  = 1;
    private static final int DICTIONARY_SHEET = 2;
    private static final int VOWELS_SHEET     = 3;
    private static final int MODIFIERS_SHEET  = 4;

    private static final int EXTENDED_BLOCKS_SHEET    = 5;
    private static final int EXTENDED_BLOCKS_COLS     = 118;

    private static String mandatoryRules[][], ambiguousChars[][];
    private static String vowels[], modifiers[];
    private static HashSet<String> dictionaryWordList;
    private static HashSet<String> extendedBlocks;

    public LanguageData(){

    }

    public static void loadLanguageData(String fileName){
        String[][] data;

        // Load Mandatory Rules //
        mandatoryRules = ExcelLoader.loadAsArray(fileName, MANDATORY_SHEET);

        // Load Ambiguous Rules //
        ambiguousChars = ExcelLoader.loadAsArray(fileName, AMBIGUITY_SHEET);

        // Load dictionary words //
        dictionaryWordList = ExcelLoader.loadAsHashSet(fileName, DICTIONARY_SHEET, 2);

        // Load vowels //
        data = ExcelLoader.loadAsArray(fileName, VOWELS_SHEET);
        vowels = new String[data.length];
        for (int i=0; i<data.length; i++){
            if (data[i][0] != null) vowels[i] = data[i][0];
        }

        // Load modifiers //
        data = ExcelLoader.loadAsArray(fileName, MODIFIERS_SHEET);
        modifiers = new String[data.length];
        for (int i=0; i<data.length; i++){
            if (data[i][0] != null) modifiers[i] = data[i][0];
        }

        // Load extended blocks
        extendedBlocks = ExcelLoader.loadAsHashSet(fileName, EXTENDED_BLOCKS_SHEET, EXTENDED_BLOCKS_COLS);

    }

    public static String[][] getMandatoryRules(){
        return mandatoryRules;
    }

    public static String[][] getAmbiguousChars(){
        return ambiguousChars;
    }

    public static Set<String> getDictionaryWordList(){
        return dictionaryWordList;
    }

    public static String[] getVowels(){
        return vowels;
    }

    public static String[] getModifiers(){
        return modifiers;
    }



}
