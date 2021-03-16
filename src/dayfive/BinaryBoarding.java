package dayfive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BinaryBoarding {
    public static void main(String[] args) throws IOException {
        List<String> textFile = Files.readAllLines(Paths.get("src/dayfive/file.txt"));
        System.out.println(getMaxBoardingPassSeatId(textFile));  // ANS: 883
    }

    private static int getMaxBoardingPassSeatId(List<String> textFile) {
        int maxSeatId = Integer.MIN_VALUE;
        int lastRowIndex = 7;

        for (String boardingPass : textFile) {
            int currentMax;
            int[] rowArr = {0, 127};  // array holding  the rows in the plane (index 0)=> lower, (index 1)=> higher

            for (int row = 0; row < lastRowIndex; row++) {
                if (boardingPass.charAt(row) == 'F') {
                    // keep lower, move higher index
                    rowArr[1] = Math.max(getMedian(rowArr[0], rowArr[1], 'F'), getMedian(rowArr[0], rowArr[1], 'F') + rowArr[0]);
                } else {
                    // keep higher, move lower index
                    rowArr[0] += getMedian(rowArr[0], rowArr[1], 'B');
                }
            }

            if (boardingPass.charAt(6) == 'F') {
                currentMax = rowArr[0];
            } else {
                currentMax = rowArr[1];
            }

            // COLUMN SECTION
            int[] columnArr = {0, 7};  // array holding  the columns in the plane
            int lastColumnIndex = 9;
            int currentColumnId;
            for (int column = 7; column <= lastColumnIndex; column++) {
                if (boardingPass.charAt(column) == 'L') {
                    // keep lower, move higher index
                    columnArr[1] = Math.max(getMedian(columnArr[0], columnArr[1], 'L'), getMedian(columnArr[0], columnArr[1], 'L') + columnArr[0]);
                } else {
                    // keep higher, move lower index
                    columnArr[0] += getMedian(columnArr[0], columnArr[1], 'R');
                }
            }

            if (boardingPass.charAt(lastColumnIndex) == 'L') {
                currentColumnId = columnArr[0];
            } else {
                currentColumnId = columnArr[1];
            }

            maxSeatId = Math.max(currentMax * 8 + currentColumnId, maxSeatId);
        }
        return maxSeatId;
    }

    /*
    @param low  -> low limit
    @param high -> high limit
    @param zoneChar -> sitting zoneChar
     */
    static int getMedian(int low, int high, char zoneChar) {
        int n = high - low;
        int median;
        if (n % 2 == 0) {
            // even
            median = (int) Math.floor((double) n / 2 + (double) n / 2 - 1) / 2;
        } else {
            median = n / 2;
        }

        if (zoneChar == 'F' || zoneChar == 'L') {
            // no rounding needed, as lower half selected from division above
            return median;
        } else {
            // zoneChar is assumed to  be B or R
            // round up, as higher half selected
            return median + 1;
        }
    }

    // TODO boarding pass question 1 variation two using bitshift and binary
    // TODO boarding pass question 2
}
