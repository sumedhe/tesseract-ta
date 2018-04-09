package io;

import java.io.PrintWriter;

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
        content += String.format("<font color=\"r%s\">%s</font>", color, text);
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
}
