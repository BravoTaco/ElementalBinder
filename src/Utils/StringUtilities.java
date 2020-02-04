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
}
