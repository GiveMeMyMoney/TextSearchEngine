package Schibsted.algorythm;

import Schibsted.util.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StandardFileSearchAlgorythm extends FileSearchAlgorythmTemplate<File> {
    private static final String noMatchesFoundWord = "No matches found";

    private Map<String, Set<String>> filesWithSplitedWords = new HashMap<>();

    private Set<String> inputWordsToCompare = new HashSet<>();
    private Map<String, Integer> fileNameRank = new HashMap<>();

    @Override
    public void prepareDataToSearch(File indexableDirectory) throws IllegalArgumentException {
        filesWithSplitedWords = Utils.getFilesNameWithUniqueContentWords(indexableDirectory);
        if (filesWithSplitedWords.isEmpty()) {
            throw new IllegalArgumentException("No files to compare!");
        }
    }

    @Override
    protected void preparePattern(String textToFind) {
        if (!textToFind.isEmpty()) {
            inputWordsToCompare.addAll(Utils.splitTextToUniqueWords(textToFind));
        }
    }

    @Override
    protected boolean checkPatternIsCorrect() {
        return !inputWordsToCompare.isEmpty();
    }

    @Override
    protected void computeCompareResult() {
        filesWithSplitedWords.forEach((fileName, uniqueWords) -> {
            //TODO multithread mechanism to compute
            int foundedWordsCount = 0;

            for (String word : inputWordsToCompare) {
                if (uniqueWords.contains(word)) {
                    foundedWordsCount++;
                }
            }

            if (foundedWordsCount > 0) {
                int percentageOfContains = computeRank(foundedWordsCount, inputWordsToCompare.size());
                fileNameRank.put(fileName, percentageOfContains);
            }

        });
    }

    private static int computeRank(int foundedWord, int allWordsToFind) {
        return (int) ((foundedWord * 100.0f) / allWordsToFind);
    }

    @Override
    protected void printResult() {
        if (fileNameRank.isEmpty()) {
            System.out.println(noMatchesFoundWord);
        } else {
            printFinalResult(fileNameRank);
        }
    }

    private void printFinalResult(Map<String, Integer> fileNameRank) {
        fileNameRank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue() + "%"));
    }

    @Override
    protected void reset() {
        inputWordsToCompare.clear();
        fileNameRank.clear();
    }

    //region getters & setters

    public void setFilesWithSplitedWords(Map<String, Set<String>> filesWithSplitedWords) {
        this.filesWithSplitedWords = filesWithSplitedWords;
    }

    public Set<String> getInputWordsToCompare() {
        return inputWordsToCompare;
    }

    public void setInputWordsToCompare(Set<String> inputWordsToCompare) {
        this.inputWordsToCompare = inputWordsToCompare;
    }

    public Map<String, Integer> getFileNameRank() {
        return fileNameRank;
    }

    public void setFileNameRank(Map<String, Integer> fileNameRank) {
        this.fileNameRank = fileNameRank;
    }

    //endregion

}
