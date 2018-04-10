package models;

public class Block {
    private String value;
    private String oldValue;
    private boolean modified;

    public Block(String s){
        value = s;
    }

    public boolean isModified(){
        return modified;
    }

    public void set(String s){
        oldValue = value;
        value = s;
        modified = true;
    }

    public String get(){
        return value;
    }

    public String getOld(){
        return oldValue;
    }

    public void reset(){
        modified = false;
    }
}
