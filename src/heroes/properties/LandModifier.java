package heroes.properties;

import map.LandType;

/**
 * This class implements the boost given to a hero when they are on a specific
 * land type on the map.
 */
public class LandModifier {

    private final LandType prefferedLand;
    private final float landModifier;

    public LandModifier(final LandType prefferedLand, final float landModifier) {
        this.prefferedLand = prefferedLand;
        this.landModifier = landModifier;
    }

    /**
     * Gets the land type the hero will gain a boost.
     *
     * @return Returns the land type the hero will gain a boost.
     */
    public LandType getPrefferedLand() {
        return prefferedLand;
    }

    /**
     * Gets the boost of the land type the hero is on.
     *
     * @return Returns the boost of the land type the hero is on.
     */
    public float getModifier() {
        return landModifier;
    }
}
