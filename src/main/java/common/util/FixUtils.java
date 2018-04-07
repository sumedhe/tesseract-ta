package common.util;

import common.LanguageData;
import common.TextFormatter;
import common.TextOperations;
import component.dictionary.OCRWord;

import java.util.List;

public class FixUtils {

    // Apply Mandatory Rules
    public void applyMandatoryRules(OCRWord ocrWord){
        for (String[] rule: LanguageData.getMandatoryRules()){
            if (rule[0] != null && rule[1] != null){
                if (ocrWord.toString().contains(rule[0])){
                    ocrWord.replace(rule[0], rule[1]);
                }
            }
        }
    }

    // Check Legitimacy of the word
    public void legitimacyCheck(OCRWord ocrWord){
        List<String> letters = TextOperations.splitLetters(ocrWord.toString());
        for (String letter : letters){
            if (!LanguageData.isInExtendedBlock(letter) && !Character.isDigit(letter.charAt(0))){
                ocrWord.replaceFormatted(letter, TextFormatter.setBold(letter));
            }
        }
    }

    // Check the word in the dictionary
    public void dictionaryCheck(){

    }
}
