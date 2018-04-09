package models;

public class OCRLetter {
    private String value;
    private boolean isModified;

    public OCRLetter(String s){
        value = s;
    }

    public void setValue(String s){
        value = s;
        isModified = true;
    }

    public boolean isModified() {
        return isModified;
    }

    public String getValue(){
        return value;
    }
}
