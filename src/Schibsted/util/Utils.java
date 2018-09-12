package Schibsted.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static final String splitWordsRegex = "\\W+";
    public static final int folderDepthSearch = 1;

    //region SINGLETON
    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }
    //endregion

    public static Map<String, Set<String>> getFilesNameWithUniqueContentWords(File folder) {
        //TODO multithread mechanism to compute
        Map<String, Set<String>> filesWithSplitedWords = new HashMap<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folder.getPath()), folderDepthSearch)) {
            filesWithSplitedWords = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            path -> path.getFileName().toString(),
                            path -> splitFileContentToWords(path) //TODO
                    ));
        } catch (NoSuchFileException ex) {
            throw new IllegalArgumentException("No files to compare!");
        } catch (IOException e) {
            System.out.println("ERROR walking through files: " + e.getMessage());
        }

        return filesWithSplitedWords;
    }

    private static Set<String> splitFileContentToWords(Path path) {
        Set<String> wordsInFile = new HashSet<>();

        try {
            Scanner sc = new Scanner(path);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                wordsInFile.addAll(splitTextToUniqueWords(line));
            }
        } catch (IOException e) {
            System.out.println("ERROR get words from file: " + path.getFileName().toString() + ", message: " + e.getMessage());
        }

        return wordsInFile;
    }

    public static Set<String> splitTextToUniqueWords(String textToFind) {
        return Stream.of((textToFind.trim().split(Utils.splitWordsRegex)))
                .collect(Collectors.toSet());
    }

}
