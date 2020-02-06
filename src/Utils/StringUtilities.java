package Utils;

public final class StringUtilities {
    private StringUtilities() {
    }

    public static boolean stringMatchesWithRemovedWhiteSpace(String string1, String string2) {
        if (string1.length() != string2.length())
            return false;

        string1 = string1.replaceAll("\\p{Z}", "");
        string2 = string2.replaceAll("\\p{Z}", "");

        return string1.equalsIgnoreCase(string2);
    }

}
