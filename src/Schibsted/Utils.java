package Schibsted;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static final String splitWordsRegex = "\\W+";

    //region SINGLETON
    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }
    //endregion

    public static Map<String, Set<String>> getFilesNameWithUniqueContentWords(File folder) {
        Map<String, Set<String>> filesWithSplitedWords = new HashMap<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folder.getPath()))) {
            filesWithSplitedWords = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            //TODO wyciagnac tylko childName
                            Path::toString,
                            path -> splitFileContentToWords(path)
                    ));
        } catch (IOException e) {
            System.out.println("ERROR!: " + e.getMessage());
            e.printStackTrace();
        }

        return filesWithSplitedWords;
    }

    private static Set<String> splitFileContentToWords(Path path) {
        //TODO sprawdzic optymalnosc
        Set<String> wordsInFile = new HashSet<>();

        try {
            Scanner sc = new Scanner(path);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                wordsInFile.addAll(Arrays.asList(line.trim().split(splitWordsRegex)));
            }
        } catch (IOException e) {
            System.out.println("splitFileContentToWords ERROR!: " + e.getMessage());
        }

        return wordsInFile;
    }

}
