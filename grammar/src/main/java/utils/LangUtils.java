package utils;

import io.ExcelReader;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Set;

public class LangUtils {

    // Sheet indexes
    private static final int MANDATORY_SHEET = 0;
    private static final int AMBIGUITY_SHEET = 1;
    private static final int DICTIONARY_SHEET = 2;
    private static final int VOWELS_SHEET = 3;
    private static final int CONSONANTS_SHEET = 4;
    private static final int MODIFIERS_SHEET = 5;

    private static final int EXTENDED_BLOCKS_SHEET = 6;
    private static final int EXTENDED_BLOCKS_COLS = 118;

    private static String mandatoryRules[][], ambiguousChars[][];
    private static HashSet<String> vowels, consonants, modifiers;
    private static HashSet<String> dictionaryWordList;
    private static HashSet<String> extendedBlocks;

    public LangUtils() {

    }

    /**
     * @param fileName
     */
    public static void loadLanguageData(String fileName) {
        String[][] data;

        // Load Mandatory Rules //
        mandatoryRules = ExcelReader.readAsArray(fileName, MANDATORY_SHEET);

        // Load Ambiguous Rules //
        ambiguousChars = ExcelReader.readAsArray(fileName, AMBIGUITY_SHEET);

        // Load dictionary words //
        dictionaryWordList = ExcelReader.readAsHashSet(fileName, DICTIONARY_SHEET, 2);

        // Load vowels //
        vowels = ExcelReader.readAsHashSet(fileName, VOWELS_SHEET, 1);

        // Load consonants //
        consonants = ExcelReader.readAsHashSet(fileName, CONSONANTS_SHEET, 1);

        // Load modifiers //
        modifiers = ExcelReader.readAsHashSet(fileName, MODIFIERS_SHEET, 1);

        // Load extended blocks
        extendedBlocks = ExcelReader.readAsHashSet(fileName, EXTENDED_BLOCKS_SHEET, EXTENDED_BLOCKS_COLS);

        //showLanguageDataInfo();
    }

    /**
     * Get mandatory rules
     *
     * @return
     */
    @Contract(pure = true)
    public static String[][] getMandatoryRules() {
        return mandatoryRules;
    }

    /**
     * Get Ambituous characters
     *
     * @return
     */
    @Contract(pure = true)
    public static String[][] getAmbiguousChars() {
        return ambiguousChars;
    }

    /**
     * Get dictionary word list
     *
     * @return
     */
    @Contract(pure = true)
    public static Set<String> getDictionaryWordList() {
        return dictionaryWordList;
    }

    /**
     * Check whether _ char is _ vowel
     *
     * @param character
     * @return
     */
    public static boolean isVowel(char character) {
        return vowels.contains(String.valueOf(character));
    }

    /**
     * Check whether _ char is _ consonant
     *
     * @param character
     * @return
     */
    public static boolean isConsonant(char character) {
        return consonants.contains(String.valueOf(character));
    }


    /**
     * Check whether _ char is _ modifier
     *
     * @param character
     * @return
     */
    public static boolean isModifier(char character) {
        return modifiers.contains(String.valueOf(character));
    }

    /**
     * Check whether _ word in _ dictionary
     *
     * @param word
     * @return
     */
    @Contract(pure = true)
    public static boolean isInDictionary(String word) {
        return dictionaryWordList.contains(word);
    }

    /**
     * Check whether _ letter in extended block (check validity of _ letter)
     *
     * @param letter
     * @return
     */
    @Contract(pure = true)
    public static boolean isInExtendedBlock(String letter) {
        return extendedBlocks.contains(letter);
    }

    /**
     * Show Language Data info
     */
    public static void showLanguageDataInfo() {
        System.out.println("Mandatory rules: " + mandatoryRules.length);
        System.out.println("Ambiguity rules: " + ambiguousChars.length);
        System.out.println("Dictionary words: " + dictionaryWordList.size());
        System.out.println("Vowels: " + vowels.size());
        System.out.println("Consonants: " + consonants.size());
        System.out.println("Modifiers: " + modifiers.size());
        System.out.println("ExBlocks: " + extendedBlocks.size());
    }

}
