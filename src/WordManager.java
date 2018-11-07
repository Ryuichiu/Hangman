import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WordManager {

    private static List<String> words;

    static {
        words = getWords();
    }

    public static String getRandomWord(boolean uppercase) {
        var word = words.get(new Random().nextInt(words.size()));
        if (uppercase) {
            return word.toUpperCase();
        }
        return word;
    }

    public static List<String> getWords() {
        try {
            var path = Paths.get("res/words.txt");
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public static void addToWords(String word) {
        try {
            word = "\n" + word;
            var path = Paths.get("res/words.txt");
            Files.write(path, word.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
