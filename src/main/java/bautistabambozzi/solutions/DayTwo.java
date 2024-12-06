package bautistabambozzi.solutions;

import bautistabambozzi.helpers.FileLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DayTwo {


    private static Pattern splitByWhitespaceRegex = Pattern.compile("\\s+");


    public static boolean hasNoErrors(List<Integer> line) {
        for (int j = 0; j < line.size(); j++) {
            if (j == 0) {
                continue;
            }
            int difference = Math.abs(line.get(j) - line.get(j - 1));


            // pattern of increase/decrease changes
            if (j < line.size() - 1) {
                boolean left = line.get(j - 1) < line.get(j);
                boolean right = line.get(j + 1) < line.get(j);
                if (left == right) {
                    return false;
                }
            }
            // wrong difference
            if (difference == 0 || difference > 3) {
                return false;
            }
        }
        return true;
    }

    public static int p1() {
        Stream<String> file = FileLoader.loadDay(2);
        List<List<Integer>> lines = new ArrayList<>();
        file.forEach(line -> {
            String[] splitReadings = splitByWhitespaceRegex.split(line);
            List<Integer> integerList = Stream.of(splitReadings).map(Integer::valueOf).toList();
            lines.add(integerList);
        });

        int answer = 0;
        for (List<Integer> line : lines) {
            if (hasNoErrors(line)) {
                answer++;
            }
        }
        return answer;

    }

    public static int p2() {
        Stream<String> file = FileLoader.loadDay(2);
        List<List<Integer>> lines = new ArrayList<>();
        file.forEach(line -> {
            String[] splitReadings = splitByWhitespaceRegex.split(line);
            List<Integer> integerList = Stream.of(splitReadings).map(Integer::valueOf).toList();
            lines.add(integerList);
        });

        int answer = 0;
        for (List<Integer> line : lines) {
            HashMap<Integer, Boolean> maybeBad = new HashMap<>();
            // mark possibly bad
            for (int j = 0; j < line.size(); j++) {
                if (j == 0) {
                    continue;
                }
                int difference = Math.abs(line.get(j) - line.get(j - 1));


                // pattern of increase/decrease changes
                if (j < line.size() - 1) {
                    boolean left = line.get(j - 1) < line.get(j);
                    boolean right = line.get(j + 1) < line.get(j);
                    if (left == right) {
                        maybeBad.put(j, true);
                        maybeBad.put(j - 1, true);
                        maybeBad.put(j + 1, true);
                    }
                }
                // wrong difference
                if (difference == 0 || difference > 3) {
                    maybeBad.put(j, true);
                }
                if (j == line.size() - 1) {
                    int count = maybeBad.size();
                    if (count == 0) {
                        answer++;
                        break;
                    }
                    for (int idx = 0 ; idx < line.size() ; idx++) {
                        int key = idx;
                        List<Integer> copy = new ArrayList<>(line);
                        copy.remove(key);
                        if (hasNoErrors(copy)) {
                            System.out.println("found fixable error at " + line);
                            answer++;
                            break;
                        }
                    }


                    // now try but removing possible bad readings
               }
            }
        }
        return answer;

    }


}
