package enums;

public enum Talisman {
    AIR("Air talisman"),
    MIND("Mind talisman"),
    WATER("Water talisman"),
    EARTH("Earth talisman"),
    FIRE("Fire talisman"),
    BODY("Body talisman");

    private String name;

    Talisman(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Talisman getTalismanBasedOnRune(Runes rune) {
        switch (rune) {
            case AIR:
                return Talisman.AIR;
            case MIND:
                return Talisman.MIND;
            case WATER:
                return Talisman.WATER;
            case EARTH:
                return Talisman.EARTH;
            case FIRE:
                return Talisman.FIRE;
            case BODY:
                return Talisman.BODY;
            default:
                return null;
        }
    }
}
