package common;

import io.HTMLWriter;
import io.TextReader;
import models.OCRLine;
import models.OCRWord;
import rules.AmbiguousRules;
import rules.LegitimacyRules;
import rules.MandatoryRules;
import utils.Convert;

import java.util.ArrayList;
import java.util.List;

public class GrammarService {

    public static void execute(String inTextPath, String outDocPath){
        List<OCRLine> document = new ArrayList<>();

        for (String line : TextReader.readLines(inTextPath)){
            // Apply Mandatory Rules
            line = MandatoryRules.apply(line);
            OCRLine ocrLine = Convert.toOCRLine(line);

            for (OCRWord ocrWord : ocrLine.getWords()){
                // Check dictionary words
                DictionaryService.check(ocrWord);

                // Check for ambiguity
                AmbiguousRules.apply(ocrWord);

                // Check legitimacy errors
                LegitimacyRules.checkForPositions(ocrWord);

            }

            document.add(ocrLine);
        }

        HTMLWriter.writeReport(outDocPath, document);
    }


}
