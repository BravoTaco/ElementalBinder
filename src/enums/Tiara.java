package enums;

public enum Tiara {
    AIR("Air tiara"),
    MIND("Mind tiara"),
    WATER("Water tiara"),
    EARTH("Earth tiara"),
    FIRE("Fire tiara"),
    BODY("Body tiara");

    private String name;

    Tiara(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
