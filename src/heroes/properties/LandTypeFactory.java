package heroes.properties;

import map.LandType;

public final class LandTypeFactory {

    private static LandTypeFactory instance;

    private LandTypeFactory() {
    }

    public static LandTypeFactory getInstance() {
        if (instance == null) {
            instance = new LandTypeFactory();
        }

        return instance;
    }

    public LandType getLandType(final String str) {
        switch (str) {
        case "L":
            return LandType.LAND;

        case "V":
            return LandType.VOLCANIC;

        case "D":
            return LandType.DESERT;

        case "W":
            return LandType.WOODS;

        default:
            return null;

        }
    }
}
