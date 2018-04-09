package common;

import io.HTMLWriter;
import io.TextReader;
import models.OCRLetter;
import models.OCRLine;
import models.OCRWord;
import rules.AmbiguousRules;
import rules.MandatoryRules;
import utils.Convert;

import java.util.ArrayList;
import java.util.List;

public class GrammarService {

    public static void execute(String inTextPath, String outDocPath){
        List<OCRLine> document = new ArrayList<>();

        for (String line : TextReader.readLines(inTextPath)){
            // Apply Mandatory Rules
            System.out.println(line);
            line = MandatoryRules.apply(line);
            OCRLine ocrLine = Convert.toOCRLine(line);
            System.out.println(line);

            for (OCRWord ocrWord : ocrLine.getWords()){
                // Check dictionary words
                DictionaryService.check(ocrWord);

                // If invalid word check for ambiguity
                if (!ocrWord.isInDictionary()){
                    AmbiguousRules.apply(ocrWord);
                }

            }

            document.add(ocrLine);
        }

        HTMLWriter.writeReport(outDocPath, document);
    }


}
