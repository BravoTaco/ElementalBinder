package interfaces;

import enums.Runes;
import enums.Talisman;
import enums.Tiara;

import java.io.Serializable;
import java.util.HashSet;

public interface SaveData extends Serializable {
    long serialVersionUID = 1L;

    HashSet<String> muleNames();

    String runecrafterName();

    boolean isMule();

    boolean isMuling();

    Runes selectedRune();

    Tiara tiara();

    Talisman talisman();
}
