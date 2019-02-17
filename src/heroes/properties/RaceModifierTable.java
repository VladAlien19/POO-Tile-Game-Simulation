package heroes.properties;

import heroes.Hero;

/**
 * This class implements the modifiers a hero's attack will suffer when they
 * attack another hero.
 */
public class RaceModifierTable {
    private RaceModifier[] raceModifierTable;

    public RaceModifierTable(final float[] modifiers) {
        raceModifierTable = new RaceModifier[Hero.RACENO];
        int i = 0;

        for (Race race : Race.values()) {
            raceModifierTable[i] = new RaceModifier();
            raceModifierTable[i].setRace(race);
            raceModifierTable[i].setModifier(modifiers[i]);
            ++i;
        }
    }

    /**
     * Gets the race modifier table with all the heroes.
     *
     * @return Returns the race modifier table with all the heroes.
     */
    public RaceModifier[] getRaceModifierTable() {
        return raceModifierTable;
    }

    /**
     * Gets the race modifier at a certain index in the table.
     *
     * @param index
     *            The index in the race modifier table.
     * @return Return the race modifier at a certain index in the table.
     */
    public RaceModifier getRaceModifier(final int index) {
        return raceModifierTable[index];
    }

    /**
     * Gets the race modifier of a certain race in the table.
     *
     * @param enemy
     *            The enemy to be found in the table.
     * @return Returns the race modifier of a certain race in the table.
     */
    public RaceModifier getRaceModifier(final Race enemy) {
        for (int i = 0; i < Hero.RACENO; ++i) {
            if (raceModifierTable[i].getRace().equals(enemy)) {
                return raceModifierTable[i];
            }
        }

        return null;
    }

    /**
     * Prints the race modifier table.
     */
    public void printRaceModifierTable() {
        for (int i = 0; i < Hero.RACENO; ++i) {
            Race race = raceModifierTable[i].getRace();
            float modif = raceModifierTable[i].getModifier();
            System.out.println(race + "\t | \t" + modif);
        }
        System.out.println("");
    }

}
