package io;

import models.OCRLetter;
import models.OCRLine;
import models.OCRWord;

import java.io.PrintWriter;
import java.util.List;

public class HTMLWriter {
    private PrintWriter writer = null;
    private String content;
    private boolean bold;
    private boolean underline;
    private boolean fontColor;

    public HTMLWriter(String fileName){
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        content = "<html><body>\n";
    }

    public void type(String text){
        content += text;
    }

    public void typeBold(String text){
        content += String.format("<b>%s</b>", text);
    }

    public void typeColor(String text, String color){
        content += String.format("<font color=\"%s\">%s</font>", color, text);
    }

    public void typeUnderline(String text){
        content += String.format("<u>%s</u>", text);
    }

    public void setBold(boolean b){
        if (b != bold){
            bold = b;
            content += (b) ? "<b>" : "</b>";
        }
    }

    public void setUnderline(boolean u){
        if (u != underline){
            underline = u;
            content += (u) ? "<u>" : "</u>";
        }
    }

    public void setColor(String color){
        if (fontColor){
            content += "</font>";
        }
        if (!color.equals("none")){
            content += String.format("<font color=\"%s\">", color);
            fontColor = true;
        } else {
            fontColor = false;
        }
    }

    public void newLine(){
        content += "<br>\n";
    }

    public void write(){
        content = content + "\n</body></html>";
        writer.println(content);
        writer.close();
    }

    // Write HTML Output
    public static void writeReport(String fileName, List<OCRLine> document){
        HTMLWriter html = new HTMLWriter(fileName);

        for (OCRLine ocrLine : document){
            for (OCRWord ocrWord : ocrLine.getWords()){
                // Mark if it is not in dictionary
                html.setColor(ocrWord.isInDictionary() ? "none" : "blue");

                // For each letter
                for (OCRLetter ocrLetter : ocrWord.getLetters()){
                    if (ocrLetter.isModified()) {
                        html.typeColor(ocrLetter.getValue(), "green");
                    } else if (ocrLetter.isLegitimacyError()) {
                        html.typeColor(ocrLetter.getValue(), "red");
                    } else {
                        html.type(ocrLetter.getValue());
                    }
                }

                // Type optional words
                if (ocrWord.getOptionalWords().size() > 0){
                    html.type(String.format("[%s]", String.join("|", ocrWord.getOptionalWords())));
                }

                html.type(" ");
                html.setColor("none");
            }

            html.newLine();
        }

        html.write();
    }
}
