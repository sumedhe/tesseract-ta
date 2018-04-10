package rules;

import config.Paths;
import io.ExcelReader;
import models.OCRLetter;
import models.OCRWord;
import unicode.Sinhala;
import utils.Convert;

import java.util.HashSet;

public class LegitimacyRules {
    private static HashSet<String> exBlocks;

    static {
        load(); // Load ExBlocks
    }

    // Load exBlocks
    public static void load(){
        exBlocks = Convert.toHashSet(ExcelReader.read(Paths.EXBLOCKS_PATH));
    }

    // Check whether a letter/block contains in the set
    public static HashSet<String> getExBlocks(){
        return exBlocks;
    }

    // Check legitimacy for position
    public static void checkForPositions(OCRWord ocrWord){
        OCRLetter ocrLetter;

        // Check for modifiers in the beginning of the word
        ocrLetter = ocrWord.getLetters().get(0);
        if (Sinhala.isModifier(ocrLetter.getAsChar())){
            ocrLetter.setLegitimacyError(true);
        }

        // Check for HalKirima as the first word
        if (ocrWord.getValue().length() > 1){
            if (ocrWord.getValue().charAt(1) == '\u0DCA' && (ocrWord.getValue().length() < 3 || ocrWord.getValue().charAt(2) != '\u200D')) {
                ocrWord.getLetters().get(0).setLegitimacyError(true);
            }
        }

        // Check for vowels in the middle of the
        for (OCRLetter ocrLetter1 : ocrWord.getLetters()){
            if (ocrWord.getLetters().indexOf(ocrLetter1) > 0 && Sinhala.isVowel(ocrLetter1.getAsChar())){
                ocrLetter1.setLegitimacyError(true);
            }
        }

    }

    /**
     * @param ocrWord
     */
    // Check legitimacy for characters
    public static void checkForCharacters(OCRWord ocrWord){

    }
}
