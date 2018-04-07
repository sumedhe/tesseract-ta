package common;

import google.DiffMatchPatch;
import google.DiffMatchPatch.Operation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DiffService {

    /**
     * @param outputDirectoryPath
     * @return
     * @throws IOException
     */
    public static List<CustomDiff> getDefaultDiff(String outputDirectoryPath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(outputDirectoryPath + "/input.txt"));
        String s1 = new String(encoded, Charset.defaultCharset());

        byte[] encoded2 = Files.readAllBytes(Paths.get(outputDirectoryPath + "/output.txt"));
        String s2 = new String(encoded2, Charset.defaultCharset());

        DiffMatchPatch difference = new DiffMatchPatch();
        List<CustomDiff> deltas = new ArrayList<>();

        for(DiffMatchPatch.Diff d: difference.diff_main(s2, s1)){
            CustomDiff diff = new CustomDiff();
            diff.text = d.text;

            switch (d.operation) {
                case EQUAL:
                    diff.operation = Operation.EQUAL;
                    diff.description = "Equal";
                    break;
                case INSERT:
                    diff.operation = Operation.INSERT;
                    diff.description = "Insert";
                    break;
                case DELETE:
                    diff.operation = Operation.DELETE;
                    diff.description = "Delete";
                    break;
            }

            deltas.add(diff);
        }

        return deltas;
    }

    /**
     * @param deltas
     * @throws IOException
     */
    public static void formatDiff(List<CustomDiff> deltas) throws IOException {
        ListIterator<CustomDiff> iterator = deltas.listIterator();
        while(iterator.hasNext()){
            CustomDiff diff = iterator.next();

            if(diff.text.equals("<n><n>")){
                CustomDiff diff2 = new CustomDiff();

                diff.text = diff2.text = "<n>";
                diff2.operation = diff.operation;
                if(diff.operation == Operation.INSERT){
                    diff.customOperation = diff2.customOperation = CustomOperation.INSERT_LINE;
                    diff.description = diff2.description = "Insert new line";
                }else if(diff.operation == Operation.DELETE){
                    diff.customOperation = diff2.customOperation = CustomOperation.DELETE_LINE;
                    diff.description = diff2.description = "Delete line";
                }

                iterator.add(diff2);
            }
            else if(diff.text.equals("<s>")){
                if(diff.operation == Operation.INSERT){
                    diff.customOperation = CustomOperation.INSERT_SPACE;
                    diff.description = "Insert space";
                }else if(diff.operation == Operation.DELETE){
                    diff.customOperation = CustomOperation.DELETE_SPACE;
                    diff.description = "Delete space";
                }
            }else if(diff.text.equals("<n>")){
                if(diff.operation == Operation.INSERT){
                    diff.customOperation = CustomOperation.INSERT_LINE;
                    diff.description = "Insert new line";
                }else if(diff.operation == Operation.DELETE){
                    diff.customOperation = CustomOperation.DELETE_LINE;
                    diff.description = "Delete line";
                }
            }
        }
    }

    public static class CustomDiff{
        public String text;
        public Operation operation;
        public CustomOperation customOperation;
        public String description;
    }

    public enum CustomOperation{
        EQUAL,

        INSERT,
        INSERT_LINE,
        INSERT_SPACE,

        DELETE,
        DELETE_LINE,
        DELETE_SPACE
    }
}
