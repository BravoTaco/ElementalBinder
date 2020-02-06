package data;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;

public abstract class GlobalVariables {
    public static int rcLevel;

    public static Script script;

    public static String status = "";
    public static SavedData savedData = new SavedData();

    public static long amountOfRunesMade = 0;
    public static long amountOfEssencesTraded = 0;

    public static Area[] banks = {
            Banks.LUMBRIDGE_UPPER,
            Banks.FALADOR_WEST,
            Banks.FALADOR_EAST,
            Banks.GRAND_EXCHANGE,
            Banks.VARROCK_WEST,
            Banks.VARROCK_EAST,
            Banks.DRAYNOR,
            Banks.EDGEVILLE,
            Banks.AL_KHARID
    };
}
