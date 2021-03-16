package daytwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayTwo {
    public static void main(String[] args) throws IOException {
        List<String> str = Files.readAllLines(Paths.get("src/daytwo/file.txt"));

        System.out.println(getNumberOfValidPasswordsPartOne(str)); // ANS => 622
        System.out.println(getNumberOfValidPasswordsPartTwo(str)); // ANS => 263
    }

    static int getNumberOfValidPasswordsPartOne(List<String> listOfPasswords) {
        int cntValidPasswords = 0;
        char dash = '-';
        char colon = ':';
        char space = ' ';

        for (String passwordPolicy : listOfPasswords) {
            // sample passwordPolicy -> 4-8 n: dnjjrtclnzdnghnbnn
            int dashIndex = passwordPolicy.indexOf(dash);        // index of dash position in password
            int spaceIndex = passwordPolicy.indexOf(space);      // index of first space position in password
            int colonIndex = passwordPolicy.indexOf(colon);      // index of colon position in password
            int low = Integer.parseInt(passwordPolicy.substring(0, dashIndex));   // lower frequency
            int high = Integer.parseInt(passwordPolicy.substring(dashIndex + 1, spaceIndex));   // higher frequency
            String policyChar = passwordPolicy.substring(spaceIndex + 1, colonIndex);   // password character to examine
            String password = passwordPolicy.substring(colonIndex + 1);     // entered password

            int frequency = 0;
            // count number of character occurrences
            for (int characterCnt = 0; characterCnt < password.length(); characterCnt++) {
                if (policyChar.charAt(0) == password.charAt(characterCnt)) {
                    frequency++;
                }
            }

            // character count must fall within the required limit
            if (frequency >= low && frequency <= high) {
                cntValidPasswords++;
            }
        }

        return cntValidPasswords;
    }

    static int getNumberOfValidPasswordsPartTwo(List<String> listOfPasswords) {
        int cntValidPasswords = 0;
        char dash = '-';
        char colon = ':';
        char space = ' ';

        for (String passwordPolicy : listOfPasswords) {

            int dashIndex = passwordPolicy.indexOf(dash);        // index of dash position in password
            int spaceIndex = passwordPolicy.indexOf(space);      // index of first space position in password
            int colonIndex = passwordPolicy.indexOf(colon);      // index of colon position in password
            int first = Integer.parseInt(passwordPolicy.substring(0, dashIndex));   // first occurrence
            int second = Integer.parseInt(passwordPolicy.substring(dashIndex + 1, spaceIndex)); //second occurrence
            String policyChar = passwordPolicy.substring(spaceIndex + 1, colonIndex);    // password character to examine
            String password = passwordPolicy.substring(colonIndex + 1);     // entered password

            // Exactly one of these positions must contain the given letter
            if ((password.charAt(first) == policyChar.charAt(0) && password.charAt(second) != policyChar.charAt(0))
                    || password.charAt(first) != policyChar.charAt(0) && password.charAt(second) == policyChar.charAt(0)) {
                cntValidPasswords++;
            }
        }

        return cntValidPasswords;
    }
}
