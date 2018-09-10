package Schibsted;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        final File indexableDirectory = new File(args[0]);
        //TODO: Index all files in indexableDirectory
        //TODO getFilesWithContents();

        Scanner keyboard = new Scanner(System.in);


        while (true) {
            System.out.print("search>");
            final String line = keyboard.nextLine();
            //TODO: Search indexed files for words in line
        }


    }

    @FunctionalInterface
    private interface CheckedFunction<T> {
        void apply(T t) throws IOException;
    }

    private static <T> Consumer<T> throwingConsumerWrapper(CheckedFunction<T> throwingConsumer) {
        return element -> {
            try {
                throwingConsumer.apply(element);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    static String writeToCache(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private static Map<String, String> getFilesWithContents(File folder) {
        Map<String, String> filesWithContent = new HashMap<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folder.getPath()))) {
            filesWithContent = paths.filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            //TODO ogarnac obsluge bledu
                            Path::toFile,
                            throwingConsumerWrapper(path -> writeToCache((Path) path))
                    ));

        } catch (IOException e) {
            //TODO obsluga bledu
            e.printStackTrace();
        }


    }


}
