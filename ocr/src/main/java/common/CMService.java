package common;

import common.DiffService.CustomDiff;
import google.DiffMatchPatch;
import utils.TextUtils;

import java.util.HashMap;
import java.util.List;

public class CMService {

    /**
     * @param deltas
     * @return
     */
    public static HashMap<String, HashMap<String, Integer>> getConfusionMap(List<CustomDiff> deltas){
        HashMap<String, HashMap<String, Integer>> confusionMap = new HashMap<>();

        for(int i = 0; i < deltas.size(); i++){
            CustomDiff d = deltas.get((i));

            if(d.operation == DiffMatchPatch.Operation.EQUAL){                  // Add equally matched
                for (String letter: TextUtils.splitLetters(d.text)){
                    if(!confusionMap.containsKey(letter)){
                        confusionMap.put(letter, new HashMap<>());
                    }

                    confusionMap.get(letter).put(letter, confusionMap.get(letter).getOrDefault(letter, 0) + 1);
                    break;
                }
            }else{

            }
        }

        return confusionMap;
    }

}
