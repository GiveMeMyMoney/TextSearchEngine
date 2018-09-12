package Schibsted.algorythm;

import java.io.File;

public abstract class FileSearchAlgorythmTemplate<T extends File> {

    public abstract void prepareDataToSearch(File typeOfFile) throws IllegalArgumentException;

    public final void search(String textToFind) {
        preparePattern(textToFind);
        if (checkPatternIsCorrect()) {
            computeCompareResult();
            printResult();
        }
        reset();
    }

    protected abstract void preparePattern(String textToFind);
    protected abstract boolean checkPatternIsCorrect();
    protected abstract void computeCompareResult();
    protected abstract void printResult();
    protected abstract void reset();
}
