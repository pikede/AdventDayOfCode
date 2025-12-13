package yeartwenty.dayfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayFour {

    public static void main(String[] args) throws IOException {
        List<String> inputFile = Files.readAllLines(Paths.get("src/yeartwenty/dayfour/file.txt"));
        System.out.println(cntValidPassports(inputFile));   // ANS -> 202
//        System.out.println(cntValidPassportsPartTwo(inputFile));
    }

    private static int cntValidPassports(List<String> passportFile) {
        int cntValidPassports = 0;
        int cntField = 0;
        for (String passport : passportFile) {
            if (passport.isEmpty()) {
                if (cntField == 7)
                    cntValidPassports++;
                cntField = 0;
                continue;
            }
            if (passport.contains("byr")) {
                cntField++;
            }
            if (passport.contains("iyr")) {
                cntField++;
            }
            if (passport.contains("eyr")) {
                cntField++;
            }
            if (passport.contains("hgt")) {
                cntField++;
            }
            if (passport.contains("hcl")) {
                cntField++;
            }
            if (passport.contains("ecl")) {
                cntField++;
            }
            if (passport.contains("pid")) {
                cntField++;
            }
        }

        return cntValidPassports;
    }

    // TODO convert kotlin solution for part2 to Java
    private static int cntValidPassportsPartTwo(List<String> passportFile) {
        int cntValidPassports = 0;
        int cntField = 0;
        Passport passport = new Passport();

        for (String stringLine : passportFile) {
            if (!stringLine.isEmpty()) {

            }
        }

        return cntValidPassports;
    }
}

//byr (Birth Year) - four digits; at least 1920 and at most 2002.
//iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//hgt (Height) - a number followed by either cm or in:
//If cm, the number must be at least 150 and at most 193.
//If in, the number must be at least 59 and at most 76.
//hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//pid (Passport ID) - a nine-digit number, including leading zeroes.
//cid (Country ID) - ignored, missing or not.

