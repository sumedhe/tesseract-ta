package utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Convert {
    public static HashSet<String> toHashSet(String[][] matrix){
        HashSet<String> hashSet = new HashSet<>();
        for (String[] row : matrix){
            for (String cell : row){
                hashSet.add(cell);
            }
        }
        return hashSet;
    }

    public static HashMap<String, String> toHashMap(String[][] matrix){
        HashMap<String, String> hashMap = new HashMap<>();
        for (String[] row : matrix){
            if (row[0] != null && !row[0].equals("") && row[1] != null){
                hashMap.put(row[0], row[1]);
            }
        }
        return hashMap;
    }

    public static String toString(List<String> list){
        return String.join("\n", list);
    }

}
