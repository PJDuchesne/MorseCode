package com.example.pauld.morsecode;

import android.util.Log;

public class MorseCodeStandards {

    // Todo: GET function based on current user settings

    // 0 -> ASCII Character
    // 1 -> Morse Code Characters
    // 2 -> DOT  (.)
    // 3 -> DASH (-)
    public final static String InternationalStandard[][] = {
            {"",  "",      "E", "T"}, // Empty is actually a space
            {"A", ".-",    "R", "W"}, // 1st entry for start of letters
            {"B", "-...",  "6", "?"},
            {"C", "-.-.",  "?", "?"},
            {"D", "-..",   "B", "X"},
            {"E", ".",     "I", "A"},
            {"F", "..-.",  "?", "?"},
            {"G", "--.",   "Z", "Q"},
            {"H", "....",  "5", "4"},
            {"I", "..",    "S", "U"},
            {"J", ".---",  "?", "1"},
            {"K", "-.-",   "C", "Y"},
            {"L", ".-..",  "?", "?"},
            {"M", "--",    "G", "O"},
            {"N", "-.",    "D", "K"},
            {"O", "---",   " ", " "},
            {"P", ".--.",  "?", "?"},
            {"Q", "--.-",  "?", "?"},
            {"R", ".-.",   "L", "?"},
            {"S", "...",   "H", "V"},
            {"T", "-",     "N", "M"},
            {"U", "..-",   "F", " "},
            {"V", "...-",  "?", "3"},
            {"W", ".--",   "P", "J"},
            {"X", "-..-",  "?", "?"},
            {"Y", "-.--",  "?", "?"},
            {"Z", "--..",  "7", "?"},
            {"0", "-----", "?", "?"}, // 27th entry for start of numbers
            {"1", ".----", "?", "?"},
            {"2", "..---", "?", "?"},
            {"3", "...--", "?", "?"},
            {"4", "....-", "?", "?"},
            {"5", ".....", "?", "?"},
            {"6", "-....", "?", "?"},
            {"7", "--...", "?", "?"},
            {"8", "---..", "?", "?"},
            {"9", "----.", "?", "?"},

            // Three special branches to lead to some numbers
            // TODO: Maybe not display ???
            {" ", "..--", "?", "2"}, // Branch leading to 2         // Coming from U
            {" ", "---.", "8", "?"}, // Branch leading to 8         // Coming from O
            {" ", "----", "9", "0"}, // Branch leading to 9 && 0    // Coming from O

            // General Confusion branch
            {"?", "",      "?", "?"} // TODO: Add links to go down and meet 0-9
    };
    private final static int InternationalLength = InternationalStandard.length;

    // MorseCodeStr -> Code that this character is coming FROM
    // DotBranch -> Whether this is looking for a dotBranch (true) or dashBranch (false)
    // Both of the above are just used to handle special cases
    public static int GetRow(char InputChar, String MorseCodeStr, boolean DotBranch) {
//        Log.e("[GetRow]: ", "InputChar >>" + InputChar + "<<");
//        Log.e("[GetRow]: ", "MorseCodeStr >>" + MorseCodeStr + "<<");
        if (InputChar == ' ') {
            // Special cases:
            if (MorseCodeStr.equals("..-")) return InternationalLength - 4;
            if (DotBranch && MorseCodeStr.equals("---")) return InternationalLength - 3;
            if (!DotBranch && MorseCodeStr.equals("---")) return InternationalLength - 2;

            // Else this is an error
            return -1;
        }

        if (InputChar == '?') {
//            return InternationalLength - 1;
            return -1;
        }

        int InputInt = (int)(Character.toUpperCase(InputChar));
//        Log.e("[GetRow]: ", "InputInt >>" + InputInt + "<<");

        // It's a number
        if (InputInt >= 48 && InputChar <= 57) {
            return InputInt - 48 + 27; // 48 for ASCII offset, 27 for table offset
        }
        // Else it's a letter
        else if (InputInt >= 65 && InputChar <= 90) {
            return InputInt - 65 + 1;
        }
        // Else it's an error input char
        else {
            // If invalid range, return error
            return -1;
        }
    }

    // TODO: Continental Standard
    public final static String ContinentalStandard[][] = {
            {"A", ""},
            {"B", ""},
            {"C", ""},
            {"D", ""},
            {"E", ""},
            {"F", ""},
            {"G", ""},
            {"H", ""},
            {"I", ""},
            {"J", ""},
            {"K", ""},
            {"L", ""},
            {"M", ""},
            {"N", ""},
            {"O", ""},
            {"P", ""},
            {"Q", ""},
            {"R", ""},
            {"S", ""},
            {"T", ""},
            {"U", ""},
            {"V", ""},
            {"W", ""},
            {"X", ""},
            {"Y", ""},
            {"Z", ""},
            {"0", ""},
            {"1", ""},
            {"2", ""},
            {"3", ""},
            {"4", ""},
            {"5", ""},
            {"6", ""},
            {"7", ""},
            {"8", ""},
            {"9", ""}
    };
}
