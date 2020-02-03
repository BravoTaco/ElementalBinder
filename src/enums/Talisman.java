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
}
