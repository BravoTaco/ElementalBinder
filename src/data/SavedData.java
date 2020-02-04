package data;

import enums.Runes;
import enums.Talisman;
import enums.Tiara;
import interfaces.SaveData;

import java.util.HashSet;

public class SavedData implements SaveData {

    private HashSet<String> muleNames = new HashSet<>();
    private String runecrafterName = "";
    private boolean isMule;
    private boolean isMuling;
    private Runes selectedRune = Runes.AIR;
    private Talisman talisman = Talisman.AIR;
    private Tiara tiara = Tiara.AIR;

    public void setTalisman(Talisman talisman) {
        this.talisman = talisman;
    }

    public void setTiara(Tiara tiara) {
        this.tiara = tiara;
    }

    public void setMuleNames(HashSet<String> muleNames) {
        this.muleNames = muleNames;
    }

    public void setRunecrafterName(String runecrafterName) {
        this.runecrafterName = runecrafterName;
    }

    public void setIsMule(boolean mule) {
        isMule = mule;
    }

    public void setSelectedRune(Runes selectedRune) {
        this.selectedRune = selectedRune;
    }

    @Override
    public HashSet<String> muleNames() {
        return muleNames;
    }

    @Override
    public String runecrafterName() {
        return runecrafterName;
    }

    @Override
    public boolean isMule() {
        return isMule;
    }

    @Override
    public boolean isMuling() {
        return isMuling;
    }

    public void setMuling(boolean muling) {
        isMuling = muling;
    }

    @Override
    public Runes selectedRune() {
        return selectedRune;
    }

    @Override
    public Tiara tiara() {
        return tiara;
    }

    @Override
    public Talisman talisman() {
        return talisman;
    }
}
