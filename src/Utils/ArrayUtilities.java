package Utils;

import java.util.HashSet;

public final class ArrayUtilities {
    private ArrayUtilities() {
    }

    public static String[] getArrayFromHashSet(HashSet<String> strings) {
        String[] arrayToReturn = new String[strings.size()];
        Object[] objects = strings.toArray();

        for (int i = 0; i < strings.size(); i++) {
            arrayToReturn[i] = (String) objects[i];
        }

        return arrayToReturn;
    }


}
