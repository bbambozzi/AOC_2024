package bautistabambozzi.solutions;

import bautistabambozzi.helpers.FileLoader;

import java.util.stream.Stream;

public class DayOne {

    public static int partOne() {
        Stream<String> lines = FileLoader.loadDay(1);
        lines.forEach(System.out::println);
        return 0;
    }

    int partTwo() {
        return 0;
    }

}
