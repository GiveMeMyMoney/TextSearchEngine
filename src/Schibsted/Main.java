package Schibsted;

import Schibsted.algorythm.FileSearchAlgorythmTemplate;
import Schibsted.algorythm.StandardFileSearchAlgorythm;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static final String quitWord = ":quit";
    private static final String searchWord = "search>";

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        final File indexableDirectory = new File(args[0]);

        FileSearchAlgorythmTemplate<File> algorythm = new StandardFileSearchAlgorythm();
        algorythm.prepareDataToSearch(indexableDirectory);

        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.print(searchWord);

            final String line = keyboard.nextLine();
            if (quitWord.equals(line)) {
                break;
            }

            algorythm.search(line);
        }
    }

}
