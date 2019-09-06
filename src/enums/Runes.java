package enums;

import data.Location;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public enum Runes {
    AIR(Location.airRuins, Location.airRuinPaths.paths, 1, Tiara.AIR, Talisman.AIR, "Air rune", Altar.AIR, Portal.AIR),
    MIND(Location.mindRuins, Location.mindRuinPaths.paths, 2, Tiara.MIND, Talisman.MIND, "Mind rune", Altar.MIND, Portal.MIND),
    WATER(Location.waterRuins, Location.waterRuinPaths.paths, 5, Tiara.WATER, Talisman.WATER, "Water rune", Altar.WATER, Portal.WATER),
    EARTH(Location.earthRuins, Location.earthRuinsPaths.paths, 9, Tiara.EARTH, Talisman.EARTH, "Earth rune", Altar.EARTH, Portal.EARTH),
    FIRE(Location.fireRuins, Location.fireRuinPaths.paths, 14, Tiara.FIRE, Talisman.FIRE, "Fire rune", Altar.FIRE, Portal.FIRE),
    BODY(Location.bodyRuins, Location.bodyRuinPaths.paths, 20, Tiara.BODY, Talisman.BODY, "Body rune", Altar.BODY, Portal.BODY);

    private Area ruinsLocation;
    private Position[][] walkPaths;
    private int levelRequirement;
    private Tiara tiara;
    private Talisman talisman;
    private String name;
    private Altar altar;
    private Portal portal;

    Runes(Area ruinsLocation, Position[][] walkPaths, int levelRequirement, Tiara tiara, Talisman talisman, String name, Altar altar, Portal portal) {
        this.ruinsLocation = ruinsLocation;
        this.walkPaths = walkPaths;
        this.levelRequirement = levelRequirement;
        this.tiara = tiara;
        this.talisman = talisman;
        this.name = name;
        this.altar = altar;
        this.portal = portal;
    }

    public Position[][] getWalkPaths() {
        return walkPaths;
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
