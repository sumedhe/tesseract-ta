package models;

public class OCRLetter {
    private String value;
    private boolean isModified;
    private boolean legitimacyError;

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

    public boolean isLegitimacyError(){
        return legitimacyError;
    }

    public void setLegitimacyError(boolean legitimacyError) {
        this.legitimacyError = legitimacyError;
    }

    public String getValue(){
        return value;
    }

    public char getAsChar(){
        return value.charAt(0);
    }

}
