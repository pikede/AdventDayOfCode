package yeartwenty.daythree;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayThree {
    public static void main(String[] args) throws IOException {
        List<String> path = Files.readAllLines(Paths.get("app/src/main/kotlin/yeartwenty/daythree/file.txt"));

        System.out.println(getNumberOfTreesEncountered(path, 3, 1));    // ANS=>  272
        System.out.println(getNumberOfTreesEncounteredPartOneVariation2(path, 3, 1));    // ANS=>  272

        System.out.println(getNumberOfTreesEncounteredPart2BigInteger(path));    // ANS=> 3898725600
        System.out.println(getNumberOfTreesEncounteredPart2Long(path));    // ANS=> 3898725600
    }

    // Answer is to big for int primitive, hence BigInteger
    private static BigInteger getNumberOfTreesEncounteredPart2BigInteger(List<String> path) {
        BigInteger a = new BigInteger(String.valueOf(getNumberOfTreesEncountered(path, 1, 1))),
                b = new BigInteger(String.valueOf(getNumberOfTreesEncountered(path, 3, 1))),
                c = new BigInteger(String.valueOf(getNumberOfTreesEncountered(path, 5, 1))),
                d = new BigInteger(String.valueOf(getNumberOfTreesEncountered(path, 7, 1))),
                e = new BigInteger(String.valueOf(getNumberOfTreesEncountered(path, 1, 2)));

        return a.multiply(b).multiply(c).multiply(d).multiply(e);
    }

    // Answer is to big for int primitive, hence long
    private static long getNumberOfTreesEncounteredPart2Long(List<String> path) {
        long a = getNumberOfTreesEncounteredPartOneVariation2(path, 1, 1),
                b = getNumberOfTreesEncounteredPartOneVariation2(path, 3, 1),
                c = getNumberOfTreesEncounteredPartOneVariation2(path, 5, 1),
                d = getNumberOfTreesEncounteredPartOneVariation2(path, 7, 1),
                e = getNumberOfTreesEncounteredPartOneVariation2(path, 1, 2);

        return a * b * c * d * e;
    }

    private static int getNumberOfTreesEncountered(List<String> inputMap, int moveRightBy, int moveDownBy) {
        int numberOfTreesEncountered = 0;

        for (int i = 0, position = 0; i < inputMap.size(); i += moveDownBy) {
            // check if possible to move down before proceeding
            if (i + moveDownBy < inputMap.size()) {
                // get string Below to moveDownBy
                String stringBelow = inputMap.get(i + moveDownBy);
                position += moveRightBy;
                // if position has exceeded the line length limit, adjust position
                if (position >= stringBelow.length()) {
                    // adjust position
                    position = position % stringBelow.length();
                }
                if ('#' == stringBelow.charAt(position)) {
                    numberOfTreesEncountered++;
                }
            } else {
                return numberOfTreesEncountered;
            }
        }

        return numberOfTreesEncountered;
    }

    private static int getNumberOfTreesEncounteredPartOneVariation2(List<String> inputMap, int moveRightBy, int moveDownBy) {
        int numberOfTreesEncountered = 0;

        for (int i = 0, position = 0; i < inputMap.size(); i += moveDownBy) {
            // check if possible to move down before proceeding
            if (i + moveDownBy < inputMap.size()) {
                // get string Below to moveDownBy
                String stringBelow = inputMap.get(i + moveDownBy);
                position = (position + moveRightBy) % stringBelow.length();
                if ('#' == stringBelow.charAt(position)) {
                    numberOfTreesEncountered++;
                }
            } else {
                return numberOfTreesEncountered;
            }
        }

        return numberOfTreesEncountered;
    }
}
