package models;

import utils.Convert;

import java.util.LinkedList;
import java.util.List;

public class OCRLine {
    private List<OCRWord> words;
    private String value;

    public OCRLine(String s){
        value = s;
        words = new LinkedList<>();
    }

    public List<OCRWord> getWords(){
        return words;
    }

    public void setWords(List<OCRWord> list){
        words = list;
    }

    public void addWord(OCRWord ocrWord) {
        words.add(ocrWord);
    }

    public String getValue() {
        return value;
    }
}
