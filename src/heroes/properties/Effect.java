package heroes.properties;

/**
 * This class implements the effects the heroes will apply to each other.
 */
public class Effect {
    private boolean canMove;
    private int roundsLeft;
    private int damageOverTime;

    public Effect(final boolean canMove, final int roundsLeft, final int damageOverTime) {
        this.canMove = canMove;
        this.roundsLeft = roundsLeft;
        this.damageOverTime = damageOverTime;
    }

    /**
     * Checks if the effect allows the hero to move.
     *
     * @return Returns if the effect allows the hero to move.
     */
    public boolean canMove() {
        return canMove;
    }

    /**
     * Sets effect's ability to allow the hero to move.
     *
     * @param newCanMove
     *            The ability to allow the hero to move.
     */
    public void setCanMove(final boolean newCanMove) {
        this.canMove = newCanMove;
    }

    /**
     * Gets the number of rounds the effect will be applied.
     *
     * @return Return the number of rounds the effect will be applied.
     */
    public int getRoundsLeft() {
        return roundsLeft;
    }

    /**
     * Sets the number of rounds the effect will be applied.
     *
     * @param newRoundsLeft
     *            The number of rounds the effect will be applied.
     */
    public void setRoundsLeft(final int newRoundsLeft) {
        this.roundsLeft = newRoundsLeft;
    }

    /**
     * Decrease the number of rounds the effect will be applied.
     */
    public void decreaseRoundsLeft() {
        --roundsLeft;
    }

    /**
     * Gets the amount of damage over time.
     *
     * @return Returns the amount of damage over time.
     */
    public int getDamageOverTime() {
        return damageOverTime;
    }

    /**
     * Sets the amount of damage over time.
     *
     * @param newDamageOverTime
     *            The amount of damage over time.
     */
    public void setDamageOverTime(final int newDamageOverTime) {
        this.damageOverTime = newDamageOverTime;
    }

    @Override
    public final String toString() {
        String str = "";

        if (!canMove) {
            str += "Stun, ";
        }

        if (damageOverTime > 0) {
            str += "DoT: " + damageOverTime + " for " + roundsLeft + " rounds";
        }

        return str;
    }
}
