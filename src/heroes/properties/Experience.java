package heroes.properties;

import heroes.Hero;

public class Experience {

    public static final int MAXXPWIN = 200;
    public static final int TIMESXPWIN = 40;
    public static final int XPLEVONE = 200;
    public static final int XPPERLEV = 50;

    private int xp;
    private int level;

    public Experience(final int xp, final int level) {
        this.xp = xp;
        this.level = level;
    }

    /**
     * @return the xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * @param xp
     *            the xp to set
     */
    public void setXp(final int xp) {
        this.xp = xp;
    }

    /**
     * Adds XP to hero.
     *
     * @param newXp
     *            XP to be added
     */
    public void addXP(final int newXp) {
        this.xp += newXp;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(final int level) {
        this.level = level;
    }

    /**
     * Calculates hero's level.
     *
     * @return Returns hero's theoretical XP level.
     */
    public int calculateLevel() {
        return Math.max(0, (xp - XPLEVONE)) / XPPERLEV;
    }

    /**
     * Checks if hero has leveled up.
     *
     * @return Returns if hero has leveled up.
     */
    public boolean checkIfLevelUp() {
        return (calculateLevel() - level != 0);
    }

    /**
     * Calculates the XP to be added to hero after winning a fight.
     *
     * @param loser
     *            Enemy hero that lost the fight
     * @return Returns hero's earned XP.
     */
    public int calculateXPWinner(final Hero loser) {
        return Math.max(0, MAXXPWIN - (level - loser.getXp().getLevel()) * TIMESXPWIN);
    }

}
