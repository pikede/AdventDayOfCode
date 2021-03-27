package dayfive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinaryBoarding {
    public static void main(String[] args) throws IOException {
        List<String> textFile = Files.readAllLines(Paths.get("src/dayfive/file.txt"));
        System.out.println(getMaxBoardingPassSeatId(textFile));  // ANS: 883
        System.out.println(getMaxBoardingPassSeatIdVariationTwo(textFile));    // ANS: 883

        System.out.println(getMissingBoardingSeatPartTwo(textFile));  // ANS:  532
    }

    private static int getMaxBoardingPassSeatId(List<String> boardingPasses) {
        int maxSeatId = Integer.MIN_VALUE;
        final int lastRowIndex = 7;
        final int lastColumnIndex = 9;

        for (String boardingPass : boardingPasses) {
            int currentMax;

            // ROW SECTION
            int[] rowArr = {0, 127};  // array holding  the rows in the plane (index 0)=> lower, (index 1)=> higher
            for (int row = 0; row < lastRowIndex; row++) {
                if (boardingPass.charAt(row) == 'F') {
                    // keep lower, move higher index
                    int median = getMedian(rowArr[0], rowArr[1], 'F');
                    // median + rowArr[0]=> if median is less than lower bound, adjust (by adding lower to median)
                    rowArr[1] = Math.max(median, median + rowArr[0]);
                } else {
                    // keep higher, move lower index
                    rowArr[0] += getMedian(rowArr[0], rowArr[1], 'B');
                }
            }

            //  select lower/upper (F/B) value from the boarding pass
            currentMax = boardingPass.charAt(6) == 'F' ? rowArr[0] : rowArr[1];

            // COLUMN SECTION
            int[] columnArr = {0, 7};  // array holding  the columns in the plane
            for (int column = 7; column <= lastColumnIndex; column++) {
                if (boardingPass.charAt(column) == 'L') {
                    // keep lower, move higher index
                    int median = getMedian(columnArr[0], columnArr[1], 'L');
                    columnArr[1] = Math.max(median, median + columnArr[0]);
                } else {
                    // keep higher, move lower index
                    columnArr[0] += getMedian(columnArr[0], columnArr[1], 'R');
                }
            }
            //  select lower/upper (L/R) value from the boarding pass
//            currentColumnId = columnArr[boardingPass.charAt(lastColumnIndex) == 'L' ? 0 : 1];same as below
            int currentColumnId = boardingPass.charAt(lastColumnIndex) == 'L' ? columnArr[0] : columnArr[1];

            maxSeatId = Math.max(currentMax * 8 + currentColumnId, maxSeatId);
        }
        return maxSeatId;
    }

    /*
     * @param low  -> low limit
     * @param high -> high limit
     * @param zoneChar -> sitting zoneChar,
     * F -> Front,
     * B -> Back (must be rounded up),
     * L -> Left,
     * R -> Right (must be rounded up)
     */
    private static int getMedian(int low, int high, char zoneChar) {
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

    private static int getMaxBoardingPassSeatIdVariationTwo(List<String> boardingPasses) {
        int maxId = Integer.MIN_VALUE;
        for (String file : boardingPasses) {
            String binary = "";
            for (char k : file.toCharArray()) {
                if (k == 'F' || k == 'L') {
                    binary += "0";
                } else if (k == 'B' || k == 'R') {
                    binary += "1";
                }
            }
            int currentSeatId = getDecimalValue(binary);
            maxId = Math.max(currentSeatId, maxId);
        }
        return maxId;
    }

    private static int getMissingBoardingSeatPartTwo(List<String> boardingPasses) {
        List<Integer> integerSeatId = new ArrayList<>();
        //  store integer representation for all seatIds
        for (String id : boardingPasses) {
            String binary = "";
            for (char k : id.toCharArray()) {
                if (k == 'F' || k == 'L') {
                    binary += "0";
                } else if (k == 'B' || k == 'R') {
                    binary += "1";
                }
            }
            integerSeatId.add(getDecimalValue(binary));
        }

        Collections.sort(integerSeatId);
        int currentSeatId = integerSeatId.get(0);  // start search  with the smallest seat id
        // search for missing seat id
        for (int id : integerSeatId) {
            if (currentSeatId != id) {   // check if id is missing
                return currentSeatId;    // return missing id
            }
            currentSeatId++;
        }
        return currentSeatId;
    }

    private static int getDecimalValue(String binary) {
        int value = 0;
        for (int i = binary.length() - 1, j = 0; i >= 0; i--, j++) {
            value += Character.getNumericValue(binary.charAt(i)) * (int) Math.pow(2, j);
//            value += (binary.charAt(i) - 48) *  Math.pow(2, j);
        }
        return value;
    }


}
