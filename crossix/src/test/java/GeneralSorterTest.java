import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.stream.Stream;

public class GeneralSorterTest {

    private static void cleanDirectory(String outputDirectory) {
        File[] files = new File(outputDirectory).listFiles();
        Stream.of(files).filter(file -> !file.isDirectory()).forEach(file -> file.delete());
    }

    private final static String csvFile = "/home/berda/Documents/Works/MyJava/interviews/crossix/src/main/resources/input/scvSortInput.txt";
    private final static String outputDirectory = "/home/berda/Documents/Works/MyJava/interviews/crossix/src/main/resources/";
    private final GeneralSorter baseSorter = new GeneralSorter();
    private static long parallelTime = 0;
    private static long singleTime = 0;

    @Before
    public void start() {
        cleanDirectory(outputDirectory);
    }

    @AfterClass
    public static void finish() {
        System.out.println("Total time taken in parallel process :" + parallelTime + " millisec.");
        System.out.println("Total time taken in single process :" + singleTime + " millisec.");
    }

    @Test
    public void givenFile_whenResultExist_thenSortedInParallel() {
        long start = System.currentTimeMillis();
        baseSorter.sort(true, csvFile, outputDirectory);
        long end = System.currentTimeMillis();
        parallelTime = end - start;
    }

    @Test
    public void givenFile_whenResultExist_thenSorted() {
        long start = System.currentTimeMillis();
        baseSorter.sort(false, csvFile, outputDirectory);
        long end = System.currentTimeMillis();
        singleTime = end - start;
    }
}