package rules;

import config.Paths;
import io.ExcelReader;
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
}
