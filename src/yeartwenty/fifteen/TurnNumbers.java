package yeartwenty.fifteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
If that was the first time the number has been spoken, the current player says 0.
Otherwise, the number had been spoken before; the current player announces how many turns apart the number is from when it was previously spoken.
 */

// URL: https://adventofcode.com/2020/day/15
public class TurnNumbers {
    public static void main(String[] args) {
        System.out.println(elvesPlayedUntilPart1(2020));  // ANS => 866
        System.out.println(elvesPlayedUntilPart2(30000000)); // ANS => 1437692
    }

    private static int elvesPlayedUntilPart1(int limit) {
        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(20);
        list.add(0);
        list.add(6);
        list.add(1);
        list.add(17);
        list.add(7);
        // 866
        // adjust starting loop (i, value) based on starting  numbers
        for (int i = 7; i < limit; i++) {
            int prev = list.get(i - 1);

            if (occursMoreThanOnce(list, prev)) {
                int index = i - 2;

                while (index >= 0) {
                    if (list.get(index) == prev) {
                        list.add(i - 1 - index);
                        break;
                    }
                    index--;
                }
            } else {
                list.add(0);
            }

        }
        return list.get(list.size()-1);
    }

    private static int elvesPlayedUntilPart2(int limit) {
        List<Integer> list = new ArrayList<>();
        HashMap<Integer, PlayedNumber> locationMap = new HashMap<>();

//        12,20,0,6,1,17,7
        list.add(12);
        list.add(20);
        list.add(0);
        list.add(6);
        list.add(1);
        list.add(17);
        list.add(7);

        locationMap.put(12, new PlayedNumber(0, -1));
        locationMap.put(20, new PlayedNumber(1, -1));
        locationMap.put(0, new PlayedNumber(2, -1));
        locationMap.put(6, new PlayedNumber(3, -1));
        locationMap.put(1, new PlayedNumber(4, -1));
        locationMap.put(17, new PlayedNumber(5, -1));
        locationMap.put(7, new PlayedNumber(6, -1));

        for (int i = 7; i < limit; i++) {
            int previousNumber = list.get(i - 1);

            if (locationMap.containsKey(previousNumber) && locationMap.get(previousNumber).previousLast != -1) {
                // contains previousNumber
                int diff = locationMap.get(previousNumber).last - locationMap.get(previousNumber).previousLast;
                list.add(diff);
                if (locationMap.containsKey(diff) ) {
                    // contains difference, last - previous last number
                    locationMap.put(diff, locationMap.get(diff).updatePositions(i));
                }  else {
                    locationMap.put(diff, new PlayedNumber(i, -1));
                }
            } else {
                // doesn't contain previousNumber, add
                list.add(0);
                locationMap.put(0, locationMap.get(0).updatePositions(i));
            }
        }
        return list.get(list.size() - 1);
    }

    // or list.indexOf(target) != list.lastIndexOf(target)
    static boolean occursMoreThanOnce(List<Integer> list, int target) {
        int cnt = 0;
        for (int k : list) {
            if (target == k) {
                cnt++;
            }
        }
        return cnt > 1;
    }
}

class PlayedNumber {
    int last;
    int previousLast;

    public PlayedNumber(int last, int beforeLast) {
        this.last = last;
        this.previousLast = beforeLast;
    }

    PlayedNumber updatePositions(int newLast) {
        previousLast = last;
        last = newLast;
        return new PlayedNumber(last, previousLast);
    }
}