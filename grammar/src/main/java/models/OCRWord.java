package models;

import java.util.LinkedList;
import java.util.List;

public class OCRWord {
    private List<OCRLetter> letters;
    private String value;
    private boolean isInDictionary;

    public OCRWord(String s){
        value = s;
        letters = new LinkedList<>();
    }

    public List<OCRLetter> getLetters(){
        return letters;
    }

    public boolean isInDictionary() {
        return isInDictionary;
    }

    public void setLetters(List<OCRLetter> list){
        letters = list;
    }

    public void addLetter(OCRLetter ocrLetter){
        letters.add(ocrLetter);
    }

    public String getValue() {
        return value;
    }

    public void setInDictionary(boolean inDictionary) {
        isInDictionary = inDictionary;
    }

    public void refresh(){
        value = "";
        for (OCRLetter l : letters){
            value += l.getValue();
        }
    }
}
