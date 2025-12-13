package yeartwenty.daynine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayNineJava {
    public static void main(String[] args) throws IOException {
        List<String> inputFile = Files.readAllLines(Paths.get("app/src/main/kotlin/yeartwenty/daynine/file.txt"));

        ExceptionNumber exceptionNumberObj = new ExceptionNumber(inputFile);
        int exceptionNumber = exceptionNumberObj.getExceptionNumber();
        System.out.println(exceptionNumber);   // ANS -> 88311122
        System.out.println(exceptionNumberObj.getEncryptionWeakness(exceptionNumber));    // ANS  ->  13549369
    }
}

class ExceptionNumber {
    ArrayList<String> input;

    public ExceptionNumber(List<String> input) {
        this.input = (ArrayList<String>) input;
    }

    int getExceptionNumber() {
        for (int index = 0; index < input.size(); index++) {
            boolean seen = false;
            int currentNumber = index;
            int end = index + 24;
            int target = getIntValueForIndex(end + 1);
            while (currentNumber <= end) {
                int temp = currentNumber + 1;
                while (temp <= end) {
                    if (getIntValueForIndex(temp) + getIntValueForIndex(currentNumber) == target) {
                        seen = true;
                    }
                    temp++;
                }
                currentNumber++;
            }
            if (!seen) {
                return target;
            }
        }

        return -1;
    }

    int getEncryptionWeakness(int target) {

        int start = 0, end = 1;
        int sum;

        for (int index = 0; index < input.size(); index++) {
            int tempEnd = end;
            sum =  getIntValueForIndex(start) + getIntValueForIndex(end);
            while (tempEnd < input.size() && start < tempEnd) {
                if (sum == target) {
                    return getMinMaxSum(start, tempEnd);
                } else if (sum > target) {
                    // reduce window by moving window's starting position
                    sum -= getIntValueForIndex(start++);
                } else {
                    // increase window by moving window's end position
                    tempEnd++;
                    sum += getIntValueForIndex(tempEnd);
                }
            }
            end = tempEnd;
        }
        return -1;
    }

    // returns sum of max number and minimum number in contiguous set
    int getMinMaxSum(int start, int end) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        for (int i = start; i <= end; i++) {
            int currentIndex = getIntValueForIndex(i);
            if (min > currentIndex) {
                min = currentIndex;
            }
            if (max < currentIndex) {
                max = currentIndex;
            }
        }

        return min + max;
    }

    private int getIntValueForIndex(int index) {
        return Integer.parseInt(input.get(index));
    }
}
