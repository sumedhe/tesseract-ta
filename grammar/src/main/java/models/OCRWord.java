package models;

import java.util.LinkedList;
import java.util.List;

public class OCRWord {
    private List<OCRLetter> letters;
    private String value;
    private boolean isInDictionary;
    private List<String> optionalWords;

    public OCRWord(String s){
        value = s;
        letters = new LinkedList<>();
        optionalWords = new LinkedList<>();
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

    public List<String> getOptionalWords(){
        return optionalWords;
    }

    public void setOptionalWords(List<String> optionalWords) {
        this.optionalWords = optionalWords;
    }

    public void copyFrom(OCRWord ocrWord){
        letters = ocrWord.letters;
        value = ocrWord.value;
        isInDictionary = ocrWord.isInDictionary;
        optionalWords = ocrWord.optionalWords;
    }
}
