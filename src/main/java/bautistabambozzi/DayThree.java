package bautistabambozzi;

import bautistabambozzi.helpers.FileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;



public class DayThree {


    enum TokenType {
        DO,
        DONT,
        MUL;
        static TokenType parse(String raw) {
            return switch (raw) {
                case "do()" -> DO;
                case "don't()" -> DONT;
                default -> MUL;
            };
        }

        public boolean canCompute() {
            return this.equals(MUL);
        }

        public boolean canToggle() {
            return switch (this) {
                case MUL -> false;
                default -> true;
            };
        }

        public boolean shouldStop() {
            return switch (this) {
                case DO -> false;
                default -> true;
            };
        }
    }
    record InstToken(String raw, TokenType tokenType) {
        static InstToken parse(String raw) {
            return new InstToken(raw, TokenType.parse(raw));
        }

         Optional<Integer> getMultValue() {
            return parseSimpleMul(this.raw());
        }

    }

    // match every statement mul(*) BUT avoid nesting muls, so always stop at a ')'
    public static Pattern mulRegex = Pattern.compile("mul\\([^()mul]*\\)");
    public static Pattern mulAndDoRegex = Pattern.compile("mul\\([^()mul]*\\)|do\\(\\)|don't\\(\\)");

    public static Optional<Integer> parseSimpleMul(String mulString) {
        try {
            String substring = mulString.substring(4, mulString.length() - 1);
            String[] splitNums = substring.split(",");
            int first = Integer.parseInt(splitNums[0]);
            int second = Integer.parseInt(splitNums[1]);
            return Optional.of(first * second);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static long p1() {
        Stream<String> file = FileLoader.loadDay(3);
        AtomicLong answer = new AtomicLong();
        file.forEach(rawLine -> {
            Matcher matcher = mulRegex.matcher(rawLine);
            List<String> matchedMuls = new ArrayList<>();
            while (matcher.find()) {
                matchedMuls.add(matcher.group());
            }
            List<Integer> nums = matchedMuls
                    .stream()
                    .map(DayThree::parseSimpleMul)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            for (var num : nums) {
                answer.addAndGet(num);
            }
        });
        return answer.get();
    }


    public static long p2() {
        Stream<String> file = FileLoader.loadDay(3);
        AtomicLong answer = new AtomicLong();
        file.forEach(rawLine -> {
            Matcher matcher = mulAndDoRegex.matcher(rawLine);
            List<String> matchedMulsAndInsturctions = new ArrayList<>();
            while (matcher.find()) {
                matchedMulsAndInsturctions.add(matcher.group());
            }
            List<InstToken> mulsAndInstructions = matchedMulsAndInsturctions
                    .stream()
                    .map(InstToken::parse)
                    .toList();
            boolean shouldAdd = true;
            for (var token : mulsAndInstructions) {
                System.out.println(token);
                if (shouldAdd && token.tokenType().canCompute()) {
                    answer.addAndGet(token.getMultValue().orElse(0));
                    continue;
                }
                if (token.tokenType().canToggle()) {
                    shouldAdd = !token.tokenType.shouldStop();
                }
            }
        });
        return answer.get();
    }
}
