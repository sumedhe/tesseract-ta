package common;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileOperations {
    public FileOperations(){

    }

    public String openFile(String fileName) {
        String line = null;
        String text = "";

        try {
            // Open and read file.
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                text += line + '\n';
            }

            // Close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return text;
    }
}
