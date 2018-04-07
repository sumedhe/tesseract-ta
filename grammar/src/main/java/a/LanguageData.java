package a;

import java.util.HashSet;
import java.util.Set;

public class LanguageData {

    // Sheet indexes
    private static final int MANDATORY_SHEET  = 0;
    private static final int AMBIGUITY_SHEET  = 1;
    private static final int DICTIONARY_SHEET = 2;
    private static final int VOWELS_SHEET     = 3;
    private static final int CONSONANTS_SHEET = 4;
    private static final int MODIFIERS_SHEET  = 5;

    private static final int EXTENDED_BLOCKS_SHEET    = 6;
    private static final int EXTENDED_BLOCKS_COLS     = 118;

    private static String mandatoryRules[][], ambiguousChars[][];
    private static HashSet<String> vowels, consonants, modifiers;
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
        vowels = ExcelLoader.loadAsHashSet(fileName, VOWELS_SHEET, 1);

        // Load consonants //
        consonants = ExcelLoader.loadAsHashSet(fileName, CONSONANTS_SHEET, 1);

        // Load modifiers //
        modifiers = ExcelLoader.loadAsHashSet(fileName, MODIFIERS_SHEET, 1);

        // Load extended blocks
        extendedBlocks = ExcelLoader.loadAsHashSet(fileName, EXTENDED_BLOCKS_SHEET, EXTENDED_BLOCKS_COLS);

    }

    // Get mandatory rules
    public static String[][] getMandatoryRules(){
        return mandatoryRules;
    }

    // Get Ambituous characters
    public static String[][] getAmbiguousChars(){
        return ambiguousChars;
    }

    // Get dictionary word list
    public static Set<String> getDictionaryWordList(){
        return dictionaryWordList;
    }

    // Check whether a char is a vowel
    public static boolean isVowel(char character){
        return vowels.contains(String.valueOf(character));
    }

    // Check whether a char is a consonant
    public static boolean isConsonant(char character){
        return consonants.contains(String.valueOf(character));
    }


    // Check whether a char is a modifier
    public static boolean isModifier(char character){
        return modifiers.contains(String.valueOf(character));
    }

    // Check whether a word in a dictionary
    public static boolean isInDictionary(String word){
        return dictionaryWordList.contains(word);
    }

    // Check whether a letter in extended block (check validity of a letter)
    public static boolean isInExtendedBlock(String letter){
        return extendedBlocks.contains(letter);
    }

}
