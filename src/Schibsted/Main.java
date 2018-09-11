package Schibsted;

import java.io.File;
import java.util.*;

public class Main {

    private static final String quitWord = ":quit";
    private static final String searchWord = "search>";
    private static final String noMatchesFoundWord = "No matches found";

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        final File indexableDirectory = new File(args[0]);
        Map<String, Set<String>> filesWithSplitedWords = Utils.getFilesNameWithUniqueContentWords(indexableDirectory);

        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.print(searchWord);
            final String line = keyboard.nextLine();
            if (quitWord.equals(line)) {
                break;
            }

            Set<String> inputWordsToCompare = new HashSet<>(Arrays.asList(line.trim().split(Utils.splitWordsRegex)));
            if (!line.isEmpty() && !inputWordsToCompare.isEmpty()) {
                Map<String, Integer> fileNameRank = computeCompareInputWithFiles(filesWithSplitedWords, inputWordsToCompare);

                if (fileNameRank.isEmpty()) {
                    System.out.println(noMatchesFoundWord);
                } else {
                    printFinalResult(fileNameRank);
                }
            }
        }
    }

    //TODO rozbicie na klasy

    static Map<String, Integer> computeCompareInputWithFiles(Map<String, Set<String>> filesWithWords, Set<String> inputWordsToCompare) {
        Map<String, Integer> fileNameRank = new HashMap<>();

        //TODO ogarnac co jest bardziej optymalne
        filesWithWords.forEach((fileName, uniqueWords) -> {
            System.out.println((fileName + ":\t" + uniqueWords));

            int foundedWordsCount = 0;
            for (String word : inputWordsToCompare) {
                if (uniqueWords.contains(word)) {
                    System.out.println("-- Found word: " + word);

                    foundedWordsCount++;
                }
            }

            if (foundedWordsCount > 0) {
                int percentageOfContains = rankAlgorythm(foundedWordsCount, inputWordsToCompare.size());
                fileNameRank.put(fileName, percentageOfContains);
            }

        });

        return fileNameRank;
    }

    private static void printFinalResult(Map<String, Integer> fileNameRank) {
        fileNameRank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue() + "%"));
    }

    private static int rankAlgorythm(int foundedWord, int allWordsToFind) {
        return (int) ((foundedWord * 100.0f) / allWordsToFind);
    }

    /*@FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }*/

}
