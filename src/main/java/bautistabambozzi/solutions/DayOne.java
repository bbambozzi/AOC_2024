package bautistabambozzi.solutions;

import bautistabambozzi.helpers.FileLoader;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class DayOne {

    private static final Pattern separateByWhitespace = Pattern.compile("\\s+");

    public static int p1() {
        Stream<String> lines = FileLoader.loadDay(1);
        List<Integer> leftCol = new ArrayList<>(1000);
        List<Integer> rightCol = new ArrayList<>(1000);
        int answer = 0;
        lines.forEach(line -> {
            var rawPair = separateByWhitespace.split(line);
            int left = Integer.parseInt(rawPair[0]);
            int right = Integer.parseInt(rawPair[1]);
            leftCol.add(left);
            rightCol.add(right);
        });
        List<Integer> left = leftCol.stream().sorted().toList();
        List<Integer> right = rightCol.stream().sorted().toList();
        for (int i = 0 ; i < left.size() ; i++) {
            answer +=  Math.abs(left.get(i) - right.get(i));
        }
        return answer;
    }

    public static int p2() {
        int answer = 0;
        Stream<String> lines = FileLoader.loadDay(1);

        List<Integer> leftCol = new ArrayList<>(1000);
        Map<Integer, Integer> map = new HashMap<>();
        lines.forEach(line -> {
            var rawPair = separateByWhitespace.split(line);
            int left = Integer.parseInt(rawPair[0]);
            int right = Integer.parseInt(rawPair[1]);
            leftCol.add(left);
            map.put(right, map.getOrDefault(right, 0) + 1);
        });
        for (var left : leftCol) {
            answer += left * map.getOrDefault(left, 0);
        }
        return answer;
    }
}
