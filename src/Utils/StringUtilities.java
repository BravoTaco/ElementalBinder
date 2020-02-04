package Utils;

import data.GlobalVariables;

public final class StringUtilities {
    private StringUtilities() {
    }

    public static boolean isStringEmpty(String string) {
        char[] chars = string.toCharArray();

        if (string.length() == 0)
            return true;

        for (char c : chars) {
            if (c != ' ')
                return false;
        }

        return true;
    }

    public static boolean stringMatchesBasedOnChars(String string1, String string2) {
        if (string1.length() != string2.length())
            return false;

        string1 = string2.trim();
        string2 = string2.trim();

        GlobalVariables.script.log("Comparing: " + string1 + " To: " + string2);

        char[] sChars1 = string1.toCharArray();
        char[] sChars2 = string2.toCharArray();

        for (int i = 0; i < sChars1.length; i++) {
            GlobalVariables.script.log(sChars1[i] == sChars2[i]);
            if (sChars1[i] != sChars2[i])
                return false;
        }

        GlobalVariables.script.log("Same Strings!");
        return true;
    }

}
