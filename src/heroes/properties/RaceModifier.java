package heroes.properties;

/**
 * This class implements the damage modifier based on race applied to a hero
 * when they fight another hero.
 */
public class RaceModifier {
    private Race race;
    private float modifier;

    public RaceModifier() {
        this.race = null;
        this.modifier = 1f;
    }

    /**
     * Gets the race of the modifier.
     *
     * @return Returns the race of the modifier.
     */
    public Race getRace() {
        return race;
    }

    /**
     * Gets the value of the modifier.
     *
     * @return Returns the value of the modifier.
     */
    public float getModifier() {
        return modifier;
    }

    /**
     * Sets the race of the modifier.
     *
     * @param race
     *            The race of the modifier.
     */
    public void setRace(final Race race) {
        this.race = race;
    }

    /**
     * Sets the value of the modifier.
     *
     * @param modifier
     *            The value of the modifier.
     */
    public void setModifier(final float modifier) {
        this.modifier = modifier;
    }
}
