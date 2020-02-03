package enums;

public enum Portal {
    AIR(266),
    MIND(266),
    WATER(266),
    EARTH(394),
    FIRE(458),
    BODY(266);

    private int config;

    Portal(int config) {
        this.config = config;
    }

    public int getConfig() {
        return config;
    }
}
