import java.io.File;
import java.util.stream.Stream;

public class Starter {
/*
There is test to run and verify
 */
    public static void main(String[] args) {
        //String csvFile = "C:\\Users\\goodluck\\Downloads\\scvSortInput.txt";
        String csvFile = "/home/berda/Documents/Works/MyJava/interviews/crossix/src/main/resources/input/scvSortInput.txt";
        String outputDirectory = "/home/berda/Documents/Works/MyJava/interviews/crossix/src/main/resources/";

        GeneralSorter baseSorter = new GeneralSorter();

        cleanDirectory(outputDirectory);

        baseSorter.sort(false, csvFile, outputDirectory);

        cleanDirectory(outputDirectory);

        baseSorter.sort(true, csvFile, outputDirectory);
    }

    private static void cleanDirectory(String outputDirectory) {
        File[] files = new File(outputDirectory).listFiles();
        Stream.of(files).filter(file -> !file.isDirectory()).forEach(file -> file.delete());        
    }
}
