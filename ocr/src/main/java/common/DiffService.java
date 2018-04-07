package common;

import google.DiffMatchPatch;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class DiffService {

    public static LinkedList<DiffMatchPatch.Diff> getDefaultDiff(String outputDirectoryPath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(outputDirectoryPath + "/input.txt"));
        String s1 = new String(encoded, Charset.defaultCharset());

        byte[] encoded2 = Files.readAllBytes(Paths.get(outputDirectoryPath + "/output.txt"));
        String s2 = new String(encoded2, Charset.defaultCharset());

        DiffMatchPatch difference = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> deltas = difference.diff_main(s2, s1);

        return deltas;
    }

}
