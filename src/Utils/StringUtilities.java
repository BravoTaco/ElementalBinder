package Utils;

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

        string1 = string1.replaceAll("\\p{Z}", "");
        string2 = string2.replaceAll("\\p{Z}", "");

        char[] sChars1 = string1.toCharArray();
        char[] sChars2 = string2.toCharArray();

        for (int i = 0; i < sChars1.length; i++) {
            if (sChars1[i] != sChars2[i])
                return false;
        }

        return true;
    }

}
