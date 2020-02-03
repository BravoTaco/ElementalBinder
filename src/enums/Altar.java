package enums;

public enum Altar {
    AIR(330),
    MIND(266),
    WATER(458),
    EARTH(394),
    FIRE(266),
    BODY(266);

    private int config;

    Altar(int config) {
        this.config = config;
    }

    public int getConfig() {
        return config;
    }
}
