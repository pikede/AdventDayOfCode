package yeartwenty.dayeleven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayElevenJavaVersion {

    public static void main(String[] args) throws IOException {
        List<String> textFile = Files.readAllLines(Paths.get("app/src/main/kotlin/yeartwenty/dayeleven/file.txt"));
        System.out.println(new SeatingSystemJava(textFile).partOne());  // ANS 2316
        System.out.println(new SeatingSystemJava(textFile).partTwo());  // ANS 2128
    }
}

class SeatingSystemJava {
    ArrayList<String> seatingPositions;
    final int[] rowArray = {-1, 0, 1, -1, 1, -1, 0, 1};
    final int[] colArray = {1, 1, 1, 0, 0, -1, -1, -1};

    public SeatingSystemJava(List<String> input) {
        seatingPositions = new ArrayList<>();
        seatingPositions.addAll(input);
    }

    int partOne() {
        ArrayList<String> temp = new ArrayList<>();

        do {
            if (!temp.isEmpty()) {
                seatingPositions.clear();
                seatingPositions.addAll(temp);
            }

            temp.clear();

            /*
            SAME AS BELOW (Loop construct)
            int row = 0;
            while (row < seatingPositions.size()){
                StringBuilder line = new StringBuilder();

                int col = 0;
                while(col < seatingPositions.get(row).length()){
                    char currentChar = seatingPositions.get(row).charAt(col);
                    int numberAdjacent = getAdjacentOccupied(row, col);
                    if (currentChar == '#' && numberAdjacent > 3) {
                        line.append('L');
                    } else if (currentChar == 'L' && numberAdjacent == 0) {
                        line.append('#');
                    } else {
                        line.append(currentChar);
                    }
                    col++;
                }
                temp.add(line.toString());
                row++;
            }
            */

            for (int row = 0; row < seatingPositions.size(); row++) {
                StringBuilder line = new StringBuilder();

                for (int col = 0; col < seatingPositions.get(row).length(); col++) {
                    char currentChar = seatingPositions.get(row).charAt(col);
                    int numberAdjacent = getAdjacentOccupied(row, col);

                    if (currentChar == '#' && numberAdjacent > 3) {
                        line.append('L');
                    } else if (currentChar == 'L' && numberAdjacent == 0) {
                        line.append('#');
                    } else {
                        line.append(currentChar);
                    }
                }
                temp.add(line.toString());
            }

        } while (!seatingPositions.equals(temp));

        int ans = 0;
        for (String line : seatingPositions) {
            for (char currentChar : line.toCharArray()) {
                ans += currentChar == '#' ? 1 : 0;
            }
        }

        return ans;
    }

    private int getAdjacentOccupied(int rowX, int colY) {
        int cntAdjacent = 0;

        for (int i = 0; i < rowArray.length; i++) {
            int x = rowX + rowArray[i];
            int y = colY + colArray[i];

            if (isValid(x, y) && seatingPositions.get(x).charAt(y) == '#') {
                cntAdjacent++;
            }
        }
        return cntAdjacent;
    }

    int partTwo() {
        ArrayList<String> temp = new ArrayList<>();

        do {
            if (!temp.isEmpty()) {
                seatingPositions.clear();
                seatingPositions.addAll(temp);
            }

            temp.clear();

            for (int row = 0; row < seatingPositions.size(); row++) {
                StringBuilder line = new StringBuilder();

                for (int col = 0; col < seatingPositions.get(row).length(); col++) {
                    char currentChar = seatingPositions.get(row).charAt(col);
                    int numberAdjacent = getAdjacentOccupiedPartTwo(row, col);

                    if (currentChar == '#' && numberAdjacent > 4) {
                        line.append('L');
                    } else if (currentChar == 'L' && numberAdjacent == 0) {
                        line.append('#');
                    } else {
                        line.append(currentChar);
                    }
                }
                temp.add(line.toString());
            }

        } while (!seatingPositions.equals(temp));

        int ans = 0;
        for (String line : seatingPositions) {
            for (char currentChar : line.toCharArray()) {
                ans += currentChar == '#' ? 1 : 0;
            }
        }

        return ans;
    }

    private int getAdjacentOccupiedPartTwo(int rowX, int colY) {
        int cntAdjacent = 0;

        for (int i = 0; i < rowArray.length; i++) {
            int x = rowX + rowArray[i];
            int y = colY + colArray[i];

            while (isValid(x, y) && seatingPositions.get(x).charAt(y) == '.') {
                x += rowArray[i];
                y += colArray[i];
            }

            if (isValid(x, y) && seatingPositions.get(x).charAt(y) == '#') {
                cntAdjacent++;
            }
        }
        return cntAdjacent;
    }

    private boolean isValid(int row, int col) {
        return row >= 0
                && row < seatingPositions.size()
                && col >= 0
                && col < seatingPositions.get(row).length();
    }
}


