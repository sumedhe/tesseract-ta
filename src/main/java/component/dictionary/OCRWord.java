package component.dictionary;

public class OCRWord {
    private String text;
    private String formattedWord;

    public OCRWord(String word){
        text = word;
        formattedWord = word;
    }


    public String toString(){
        return text;
    }

    public void setWord(String s){
        text = s;
    }

    public void replace(String s, String s1){
        text.replaceAll(s, s1);
    }


    public String toFormattedString(){
        return formattedWord;
    }

    public void setFormattedWord(String s){
        formattedWord = s;
    }

    public void replaceFormatted(String s, String s1){
        formattedWord.replaceAll(s, s1);
    }

    public void appendFormattedWord(String s){
        formattedWord += s;
    }


}
