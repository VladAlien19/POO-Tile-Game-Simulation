package heroes.properties;

public class Health {

    private final int baseHP;
    private int currentHP;
    private int maxHP;
    private final int hPPerLevel;

    public Health(final int baseHP, final int hPPerLevel) {
        this.baseHP = baseHP;
        this.currentHP = baseHP;
        this.maxHP = baseHP;
        this.hPPerLevel = hPPerLevel;
    }

    /**
     * @return the currentHP
     */
    public int getCurrentHP() {
        return currentHP;
    }

    /**
     * @param currentHP
     *            the currentHP to set
     */
    public void setCurrentHP(final int currentHP) {
        this.currentHP = currentHP;
    }

    /**
     * @return the baseHP
     */
    public int getBaseHP() {
        return baseHP;
    }

    /**
     * @return the maxHP
     */
    public int getMaxHP() {
        return maxHP;
    }

    /**
     * @param maxHP
     *            the maxHP to set
     */
    public void setMaxHP(final int maxHP) {
        this.maxHP = maxHP;
    }

    /**
     * @return the hPPerLevel
     */
    public int gethPPerLevel() {
        return hPPerLevel;
    }

    /**
     * Refills hero's HP.
     */
    public void refillHP() {
        currentHP = maxHP;
    }

    /**
     * Decreases hero's current HP with damage received.
     *
     * @param damage
     *            Amount of HP lost in a fight.
     */
    public void applyDamage(final int damage) {
        currentHP = Math.max(0, currentHP - damage);
    }
}
