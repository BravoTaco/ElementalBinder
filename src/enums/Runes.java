package enums;

import data.Location;
import org.osbot.rs07.api.map.Area;

public enum Runes {
    AIR(Location.airRuins, 1, Tiara.AIR, Talisman.AIR, "Air rune", Altar.AIR, Portal.AIR),
    MIND(Location.mindRuins, 2, Tiara.MIND, Talisman.MIND, "Mind rune", Altar.MIND, Portal.MIND),
    WATER(Location.waterRuins, 5, Tiara.WATER, Talisman.WATER, "Water rune", Altar.WATER, Portal.WATER),
    EARTH(Location.earthRuins, 9, Tiara.EARTH, Talisman.EARTH, "Earth rune", Altar.EARTH, Portal.EARTH),
    FIRE(Location.fireRuins, 14, Tiara.FIRE, Talisman.FIRE, "Fire rune", Altar.FIRE, Portal.FIRE),
    BODY(Location.bodyRuins, 20, Tiara.BODY, Talisman.BODY, "Body rune", Altar.BODY, Portal.BODY);

    private Area ruinsLocation;
    private int levelRequirement;
    private Tiara tiara;
    private Talisman talisman;
    private String name;
    private Altar altar;
    private Portal portal;

    Runes(Area ruinsLocation, int levelRequirement, Tiara tiara, Talisman talisman, String name, Altar altar, Portal portal){
        this.ruinsLocation = ruinsLocation;
        this.levelRequirement = levelRequirement;
        this.tiara = tiara;
        this.talisman = talisman;
        this.name = name;
        this.altar = altar;
        this.portal = portal;
    }

    public Area getRuinsLocation() {
        return ruinsLocation;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public Talisman getTalisman() {
        return talisman;
    }

    public Tiara getTiara() {
        return tiara;
    }

    public String getName() {
        return name;
    }

    public Altar getAltar() {
        return altar;
    }

    public Portal getPortal() {
        return portal;
    }
}
