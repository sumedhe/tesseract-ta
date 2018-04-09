package diff;

import java.util.HashMap;
import java.util.Map;

public class CMReportService {

    /**
     * @param confusionMap
     */
    public static void generateDefault(HashMap<String, HashMap<String, Integer>> confusionMap){
        for(Map.Entry<String, HashMap<String , Integer>> entry: confusionMap.entrySet()){
            System.out.print(entry.getKey() + "|");
            for(Map.Entry<String, Integer> child: entry.getValue().entrySet()){
                System.out.print(child.getValue() + "|");
            }
            System.out.println();
        }
    }

}
