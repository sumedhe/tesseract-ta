package utils;

import models.OCRLetter;
import models.OCRLine;
import models.OCRWord;

import java.util.*;

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

    public static HashMap<String, List<String>> toHashMapMultiple(String[][] matrix){
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (String[] row : matrix){
            if (row[0] != null && !row[0].equals("") && row[1] != null){
                if (hashMap.containsKey(row[0])){
                    List<String> val = hashMap.get(row[0]);
                    val.add(row[1]);
                    hashMap.put(row[0], val);
                } else {
                    hashMap.put(row[0], new ArrayList<>(Arrays.asList(row[1])));
                }
            }
        }
        return hashMap;
    }

    public static OCRWord toOCRWord(String word){
        OCRWord ocrWord = new OCRWord(word);
        for (String s: TextUtils.splitLetters(word)){
            ocrWord.addLetter(new OCRLetter(s));
        }
        return ocrWord;
    }

    public static OCRLine toOCRLine(String line){
        OCRLine ocrLine = new OCRLine(line);
        for (String word : TextUtils.splitWords(line)){
            if (!word.equals("")){
                ocrLine.addWord(toOCRWord(word));
            }
        }
        return ocrLine;
    }

    public static List<OCRLine> toOCRLines(List<String> lines){
        List<OCRLine> ocrLines = new LinkedList<>();
        for (String line : lines){
            ocrLines.add(toOCRLine(line));
        }
        return ocrLines;
    }

    public static String toString(List<String> list){
        return String.join("\n", list);
    }

}
