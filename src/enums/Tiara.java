package enums;

public enum Tiara {
    AIR("Air tiara"),
    MIND("Mind tiara"),
    WATER("Water tiara"),
    EARTH("Earth tiara"),
    FIRE("Fire tiara"),
    BODY("Body tiara");

    private String name;

    Tiara(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Tiara getTiaraBasedOnRune(Runes rune) {
        switch (rune) {
            case AIR:
                return Tiara.AIR;
            case MIND:
                return Tiara.MIND;
            case WATER:
                return Tiara.WATER;
            case EARTH:
                return Tiara.EARTH;
            case FIRE:
                return Tiara.FIRE;
            case BODY:
                return Tiara.BODY;
            default:
                return null;
        }
    }
}
