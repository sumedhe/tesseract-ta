package common;

import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LanguageData {

    // Sheet indexes
    private static final int MANDATORY_SHEET = 0;
    private static final int AMBIGUITY_SHEET = 1;
    private static final int DICTIONARY_SHEET = 2;
    private static final int VOWELS_SHEET = 3;
    private static final int MODIFIERS_SHEET = 4;
    private static final int LETTERS_SHEET = 5;

    private static String mandatoryRules[][], ambiguousChars[][];
    private static String vowels[], modifiers[];
    private static Set<String> dictionaryWordList;

    public LanguageData(){

    }

    public static void loadLanguageData(String fileName){
        ExcelLoader excelLoader = new ExcelLoader(fileName);
        String[][] data;

        // Load dictionary words //
        excelLoader.setSheetIndex(DICTIONARY_SHEET);
        data = excelLoader.loadData();

        int wordCount = 0;
        String[] tempList = new String[data.length * data[0].length];
        for (String[] row: data){
            for (String val: row){
                if (val != null){
                    tempList[wordCount] = val;
                    wordCount++;
                }
            }
        }
        dictionaryWordList = new HashSet<String>(Arrays.asList(Arrays.copyOfRange(tempList, 0, wordCount)));

        // Load Mandatory Rules //
        excelLoader.setSheetIndex(MANDATORY_SHEET);
        mandatoryRules = excelLoader.loadData();

        // Load Ambiguous Rules //
        excelLoader.setSheetIndex(AMBIGUITY_SHEET);
        ambiguousChars = excelLoader.loadData();

        // Load vowels //
        excelLoader.setSheetIndex(VOWELS_SHEET);
        data = excelLoader.loadData();
        vowels = new String[data.length];
        for (int i=0; i<data.length; i++){
            if (data[i][0] != null) vowels[i] = data[i][0];
        }

        // Load modifiers //
        excelLoader.setSheetIndex(MODIFIERS_SHEET);
        data = excelLoader.loadData();
        modifiers = new String[data.length];
        for (int i=0; i<data.length; i++){
            if (data[i][0] != null) modifiers[i] = data[i][0];
        }


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
