package Schibsted.algorythm;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StandardFileSearchAlgorythmTest {
    private File file;
    private StandardFileSearchAlgorythm algorythm;
    private Map<String, Set<String>> filesWithSplitedWords;
    private Set<String> inputWordsToCompare;
    private Map<String, Integer> fileNameRank;

    @BeforeEach
    void setUp() {
        this.file = EasyMock.createNiceMock(File.class);
        this.algorythm = new StandardFileSearchAlgorythm();

        this.filesWithSplitedWords = setUpFilesWithSplitedWords();
        this.inputWordsToCompare = setUpInputWordsToCompare();
        this.fileNameRank = setUpFileNameRank();
    }

    private Map<String, Set<String>> setUpFilesWithSplitedWords() {
        Map<String, Set<String>> filesWithSplitedWords = new HashMap<>();
        filesWithSplitedWords.put("filename1", Stream.of("Ala", "ma", "kocislawa").collect(Collectors.toSet()));
        filesWithSplitedWords.put("filename2", Stream.of("To", "be", "Ala", "not").collect(Collectors.toSet()));
        filesWithSplitedWords.put("filename3", Stream.of("W", "imie", "zasad").collect(Collectors.toSet()));

        return filesWithSplitedWords;
    }

    private Set<String> setUpInputWordsToCompare() {
        return Stream.of("Ala", "be", "oR", "zasady", "BRAK").collect(Collectors.toSet());
    }

    private Map<String, Integer> setUpFileNameRank() {
        Map<String, Integer> fileNameRank = new HashMap<>();
        fileNameRank.put("filename1", 20);
        fileNameRank.put("filename2", 40);

        return fileNameRank;
    }

    @Test
    void prepareDataToSearchFail() {
        assertThrows(NullPointerException.class, () -> algorythm.prepareDataToSearch(file));
    }

    @Test
    void emptyStringForPreparePatternCheck() {
        algorythm.preparePattern("");
        assertTrue(algorythm.getInputWordsToCompare().isEmpty());
    }

    @Test
    void patternIsCorrect() {
        String textToFind = "  Ala ma &&& kota";

        algorythm.preparePattern(textToFind);
        boolean isCorrect = algorythm.checkPatternIsCorrect();
        assertTrue(isCorrect);
    }

    @Test
    void patternIsInCorrectForEmptyText() {
        String textToFind = "";

        algorythm.preparePattern(textToFind);
        boolean isCorrect = algorythm.checkPatternIsCorrect();
        assertFalse(isCorrect);
    }

    @Test
    void patternIsInCorrectForSpecialCharactersText() {
        String textToFind = "&&& **** ...";

        algorythm.preparePattern(textToFind);
        boolean isCorrect = algorythm.checkPatternIsCorrect();
        assertFalse(isCorrect);
    }

    @Test
    void patternIsCorrectAfterChange() {
        String textToFind = "&&& **** ...";

        algorythm.preparePattern(textToFind);
        boolean isCorrect = algorythm.checkPatternIsCorrect();
        assertFalse(isCorrect);

        textToFind = "to find something!";

        algorythm.preparePattern(textToFind);
        isCorrect = algorythm.checkPatternIsCorrect();
        assertTrue(isCorrect);

    }

    @Test
    void checkComputedAlgorythm() {
        //algorythm.prepareDataToSearch(file);
        algorythm.setFilesWithSplitedWords(this.filesWithSplitedWords);

        algorythm.preparePattern("Ala be oR &&& zasady,,, BRAK");
        assertTrue(algorythm.checkPatternIsCorrect());

        assertEquals(this.inputWordsToCompare, algorythm.getInputWordsToCompare());

        algorythm.computeCompareResult();
        Map<String, Integer> fileNameRankFromAlgorythm = algorythm.getFileNameRank();

        assertEquals(this.fileNameRank, fileNameRankFromAlgorythm);
    }

    @Test
    void isInputWordsToCompareEmptyAfterReset() {
        algorythm.setInputWordsToCompare(this.inputWordsToCompare);

        algorythm.reset();
        assertTrue(algorythm.getInputWordsToCompare().isEmpty());

    }

    @Test
    void isFileNameRankEmptyAfterReset() {
        algorythm.setFileNameRank(this.fileNameRank);

        algorythm.reset();
        assertTrue(algorythm.getFileNameRank().isEmpty());
    }

    @Test
    void isFileNameRankAndInputWordsToCompareEmptyWhenReset() {
        algorythm.setInputWordsToCompare(this.inputWordsToCompare);
        algorythm.setFileNameRank(this.fileNameRank);

        algorythm.reset();
        assertTrue(algorythm.getInputWordsToCompare().isEmpty());
        assertTrue(algorythm.getFileNameRank().isEmpty());
    }

}