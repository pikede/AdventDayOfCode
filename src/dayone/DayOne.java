package dayone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// URl https://adventofcode.com/2020/day/1
public class DayOne {
    public static void main(String[] args) throws IOException {
        List<Integer> listOfReports = new ArrayList<>();

        String content;
        content = new String(Files.readAllBytes(Paths.get("src/dayone/file.txt")));

        String number = "";
        for (int i = 0; i < content.length(); i++) {
            if (Character.getNumericValue(content.charAt(i)) != -1) {
                number += content.charAt(i);
            } else {
                listOfReports.add(Integer.valueOf(number));
                number = "";
            }
        }

//        System.out.println(get2020ReportRepair(listOfReports, 2020)); //  ANS => 471019
//        System.out.println(get2020ReportRepairVariationTwo(listOfReports, 2020)); //  ANS => 471019
//        System.out.println(get2020ReportRepairPart2(listOfReports, 2020)); // ANS => 103927824
    }


    /*
    Finds two numbers that sums up to the target from the given list, and then multiplies those two numbers together
    returns: value of multiplication if found, else -1
     */
    static int get2020ReportRepair(List<Integer> integerArrayList, int target) {
        int n = integerArrayList.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (integerArrayList.get(i) + integerArrayList.get(j) == target) {
                    return integerArrayList.get(i) * integerArrayList.get(j);
                }
            }
        }

        return -1;
    }

    static int get2020ReportRepairVariationTwo(List<Integer> integerArrayList, int target) {
        int n = integerArrayList.size();
        for (int i = 0; i < n; i++) {
            if (integerArrayList.contains(target - integerArrayList.get(i))) {
                return integerArrayList.get(i) * integerArrayList.get(find(integerArrayList, target - integerArrayList.get(i)));
            }
        }

        return -1;
    }

    /*
    Finds three numbers that sums up to the target from the given list, and then multiplies those three numbers together
    returns: value of multiplication if found, else -1
     */
    static int get2020ReportRepairPart2(List<Integer> integerArrayList, int target) {
        int n = integerArrayList.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (integerArrayList.contains(target - (integerArrayList.get(i) + integerArrayList.get(j)))) {
                    return integerArrayList.get(i) * integerArrayList.get(j) * integerArrayList.get(find(integerArrayList, target - (integerArrayList.get(i) + integerArrayList.get(j))));
                }
            }
        }

        return -1;
    }

    static int find(List<Integer> list, int target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == target) {
                return i;
            }
        }
        return -1;
    }
}
