package bautistabambozzi.helpers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileLoader {

    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private static final Logger logger =  Logger.getLogger(FileLoader.class.getName());

    public static Stream<String> loadDay(int day) {
        try {
        if (day  < 0 || (day > 25)) {
            throw new IllegalArgumentException("day must be between 0 and 25");
        }
        String pth = Objects.requireNonNull(classLoader.getResource("day" + day + ".txt")).getPath();
        Path path = Path.of(pth);
            return Files.lines(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.severe("failed to parse the input\n" + e.getMessage());
        }
        return Stream.empty();
    }
}
