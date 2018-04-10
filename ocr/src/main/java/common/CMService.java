package common;

import common.DiffService.CustomDiff;
import google.DiffMatchPatch;
import utils.TextUtils;

import java.util.List;
import java.util.TreeMap;

public class CMService {

    /**
     * @param deltas
     * @return
     */
    public static TreeMap<String, TreeMap<String, Integer>> getConfusionMap(List<CustomDiff> deltas) {
        TreeMap<String, TreeMap<String, Integer>> confusionMap = new TreeMap<>();

        for (int i = 0; i < deltas.size(); i++) {
            CustomDiff d = deltas.get((i));

            if (d.operation == DiffMatchPatch.Operation.EQUAL) {        // Add equally matched
                for (String letter : TextUtils.splitLetters(d.text)) {
                    if (!confusionMap.containsKey(letter)) {
                        confusionMap.put(letter, new TreeMap<>());
                    }

                    confusionMap.get(letter).put(letter, confusionMap.get(letter).getOrDefault(letter, 0) + 1);
                    break;
                }
            } else {                                                    // Add unmatched

            }
        }

        return confusionMap;
    }

}
