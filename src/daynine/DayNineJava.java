package daynine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayNineJava {
    public static void main(String[] args) throws IOException {
        List<String> inputFile = Files.readAllLines(Paths.get("src/daynine/file.txt"));

        ExceptionNumber part1 = new ExceptionNumber(inputFile);
        System.out.println(part1.getExceptionNumber());   //ANS -> 88311122
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

    private int getIntValueForIndex(int index) {
        return Integer.parseInt(input.get(index));
    }
}
