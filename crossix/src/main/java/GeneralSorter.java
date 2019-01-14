import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneralSorter {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static final LinesComparator linesComparator = new LinesComparator();
    private static final int MAX_LINES = 100;
    private static final int STOP_FILES_NUMBER = 2;//directory with input csv file and result
    private static boolean isParallel = false;
    private static String csvFile;
    private static String outputDirectory;
    private static String header;

    private Consumer<List<String>> singleSort = (list) -> list.sort(linesComparator);
    private BiConsumer<String, String> singleMerge = (file1, file2) -> mergeTwoFiles(file1, file2);

    private Function<List<String>, List<String>> parallelSort = (list) ->
        {return list.parallelStream().sorted(linesComparator).collect(Collectors.toList());};

    private BiConsumer<String, String> parallelMerge = (file1, file2) -> {
        Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() {
                        mergeTwoFiles(file1, file2);
                        return null;
                    }
                };
        try {
            task.call();
        } catch (Exception e) {
            System.out.println("Merge of two files is failed, file1: " + file1 + " file2: " + file2);
            e.printStackTrace();
        }
    };

    //@Benchmark
    public void sort(boolean isParallel, String csvFile, String outputDirectory) {
        this.isParallel = isParallel;
        this.csvFile = csvFile;
        this.outputDirectory = outputDirectory;

        partitionByFile(csvFile);
        while(mergeFilesInDirectoryGetRemains(outputDirectory) != STOP_FILES_NUMBER);
    }

    private void partitionByFile(String csvFile) {
        String line;
        List<String> inputList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            header = br.readLine();// save the header row
            while ((line = br.readLine()) != null) {
                inputList.add(line);
                if (inputList.size() == MAX_LINES) {
                    sortAndSaveNewFile(inputList);
                    inputList.clear();
                }
            }
            if(inputList.size()>0) {
                sortAndSaveNewFile(inputList);
                inputList.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortAndSaveNewFile(List<String> inputList) throws IOException {
        FileWriter writer = generateOutputWriter();
        if(isParallel)
            inputList = parallelSort.apply(inputList);
        else
            singleSort.accept(inputList);

        flushIntoFile(inputList, writer);
        writer.flush();
        writer.close();
        System.out.println("Sorted file created");
    }

    private int mergeFilesInDirectoryGetRemains(String folder) {
        ArrayList<String> fileList = new ArrayList<>();
        File[] files = new File(folder).listFiles();
        if (files.length == STOP_FILES_NUMBER) { // single sorted output file should stay
            System.out.println("Merge is done succesfully");
            return files.length;
        }

        for (File file : files) {
            if(file.isDirectory()) {
                continue;
            }
            fileList.add(file.getName());
            if(fileList.size() == 2) {
                System.out.println("Merging two files: " + fileList.get(0) + " with " + fileList.get(1));
                if(isParallel)
                    parallelMerge.accept(fileList.get(0), fileList.get(1));
                else
                    singleMerge.accept(fileList.get(0), fileList.get(1));
                fileList.clear();
            }
        }
        System.out.println("One merge loop is done");
        return files.length;
    }

    private void mergeTwoFiles(String fileName1, String fileName2) {
        String line1 = null;
        String line2 = null;
        List<String> inputList = new ArrayList<>();
        FileWriter writer = generateOutputWriter();

        try {
            File file1 = new File(outputDirectory + fileName1);
            File file2 = new File(outputDirectory + fileName2);

            FileReader fileReader1 = new FileReader(file1);
            FileReader fileReader2 = new FileReader(file2);

            BufferedReader br1 = new BufferedReader(fileReader1);
            BufferedReader br2 = new BufferedReader(fileReader2);

            line1 = br1.readLine();
            line2 = br2.readLine();
            while(line1 != null && line2 != null){
                if (linesComparator.compare(line1, line2) <= 0) {
                    appendToFile(inputList, line1, writer);
                    line1 = br1.readLine();
                } else {
                    appendToFile(inputList, line2, writer);
                    line2 = br2.readLine();
                }
            }
            if (line1 != null) {
                appendToFile(inputList, line1, writer);
                while (((line1 = br1.readLine()) != null)) {
                    appendToFile(inputList, line1, writer);
                }
            }
            if (line2 != null) {
                appendToFile(inputList, line2, writer);
                while ((line2 = br2.readLine()) != null) {
                    appendToFile(inputList, line2, writer);
                }
            }

            writer.flush();
            writer.close();

            file1.delete();
            System.out.println("File " + fileName1 + " merged and deleted");
            file2.delete();
            System.out.println("File " + fileName2 + " merged and deleted");

            fileReader1.close();
            fileReader2.close();
            br1.close();
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileWriter generateOutputWriter() {
        String outputFile = outputDirectory + "output$.txt".replace("$", String.valueOf(counter.getAndAdd(1)));
        System.out.println("New file output created: " + outputFile);
        try {
            Files.deleteIfExists(Paths.get(outputFile));
            return new FileWriter(outputFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void appendToFile(List<String> inputList, String line, FileWriter writer) {
        inputList.add(line);
        flushIntoFile(inputList, writer);
    }

    private void flushIntoFile(List<String> inputList, FileWriter writer) {
        if(inputList.size()>0) {
            saveToFile(inputList, writer);
            inputList.clear();
        }
    }

    private void saveToFile(List<String> sortedList, FileWriter writer) {
        try {
            for(String str: sortedList) {
                writer.append(str);
                writer.append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Can't save to file: " + e);
            e.printStackTrace();
        }
    }
}