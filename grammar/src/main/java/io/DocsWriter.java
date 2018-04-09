package io;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocsWriter {
    FileOutputStream outStream;
    XWPFDocument document;
    XWPFParagraph paragraph;
    XWPFRun run;

    boolean bold;
    boolean underline;

    public DocsWriter(String fileName){
        try {
            outStream = new FileOutputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document = new XWPFDocument();
        paragraph = document.createParagraph();
    }

    private void newRun(){
        run = paragraph.createRun();
        run.setBold(bold);
        if (underline){
            run.setUnderline(UnderlinePatterns.DOTTED);
        } else {
            run.setUnderline(UnderlinePatterns.NONE);
        }
    }

    public void type(String text){
        newRun();
        run.setText(text);
    }

    public void typeBold(String text){
        bold = true;
        newRun();
        run.setText(text);
        bold = false;
    }

    public void typeUnderline(String text){
        underline = true;
        newRun();
        run.setText(text);
        underline = false;
    }

    public void newLine(){
        run.addBreak();
    }


    public void setUnderline(boolean b){
        underline = b;
    }

    public void setBold(boolean b){
        bold = b;
    }


    public void write(){
        try {
            document.write(outStream);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
