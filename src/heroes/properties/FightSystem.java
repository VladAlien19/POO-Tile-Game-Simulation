package heroes.properties;

import heroes.Hero;
import map.MapCell;

public final class FightSystem {

    private static FightSystem instance;

    private FightSystem() {
    }

    public static FightSystem getInstance() {
        if (instance == null) {
            instance = new FightSystem();
        }

        return instance;
    }

    /**
     * Simulate the fight between the two heroes.
     *
     * @param hero1
     *            First hero.
     * @param hero2
     *            Second hero.
     */
    public void fight(final MapCell cell, final Hero hero1, final Hero hero2) {
        System.out.println("Fighting " + hero1 + " and " + hero2 + " at [" + hero1.getX() + "]["
                + hero1.getY() + "] on " + hero1.getIsOnLandType());

        // Calculates hero2's damage against hero1
        int dmgAbl1ToHero1 = hero2.calcDmgAbility1(hero1);
        int dmgAbl2ToHero1 = hero2.calcDmgAbility2(hero1);

        // Calculates hero2's damage against hero2
        int dmgAbl1ToHero2 = hero1.calcDmgAbility1(hero2);
        int dmgAbl2ToHero2 = hero1.calcDmgAbility2(hero2);

        // Applying hero1's first ability against hero2
        hero1.execAbility1(hero2, dmgAbl1ToHero2);
        System.out.println(hero1 + " gives " + hero2 + " " + dmgAbl1ToHero2 + " damage");

        // Applying hero1's second ability against hero2
        hero1.execAbility2(hero2, dmgAbl2ToHero2);
        System.out.println(hero1 + " gives " + hero2 + " " + dmgAbl2ToHero2 + " damage");
        System.out.println("\tand effect " + hero2.getReceivedEffect());

        // Calculates hero2's first ability against hero1
        hero2.execAbility1(hero1, dmgAbl1ToHero1);
        System.out.println(hero2 + " gives " + hero1 + " " + dmgAbl1ToHero1 + " damage");

        // Calculates hero2's second ability against hero2
        hero2.execAbility2(hero1, dmgAbl2ToHero1);
        System.out.println(hero2 + " gives " + hero1 + " " + dmgAbl2ToHero1 + " damage");
        System.out.println("\tand effect " + hero1.getReceivedEffect());

        System.out.println("");

        // Both heroes die
        if (hero1.isDead() && hero2.isDead()) {
            System.out.println("Both heroes are dead.");

            // Removes them from the map
            cell.removeHeroFromCell(hero1);
            cell.removeHeroFromCell(hero2);
            return;
        }

        // Hero2 wins
        if (hero1.isDead()) {
            System.out.println(hero1 + " is dead. Adding " + hero2.getXp().calculateXPWinner(hero1)
                    + " xp to " + hero2);

            // Adds XP to hero2
            hero2.getXp().addXP(hero2.getXp().calculateXPWinner(hero1));

            // Checks if hero2 has leveled up
            if (hero2.checkIfLevelUp()) {
                hero2.levelUp();
            }
            System.out.println(hero2 + " has " + hero2.getHp().getCurrentHP() + " HP and is level "
                    + hero2.getXp().getLevel() + " (" + hero2.getXp().getXp() + ")");
            return;
        }

        // Hero1 wins
        if (hero2.isDead()) {
            System.out.println(hero2 + " is dead. Adding " + hero1.getXp().calculateXPWinner(hero2)
                    + " xp to " + hero1);

            // Adds XP to hero1
            hero1.getXp().addXP(hero1.getXp().calculateXPWinner(hero2));

            // Checks if hero1 has leveled up
            if (hero1.checkIfLevelUp()) {
                hero1.levelUp();
            }
            System.out.println(hero1 + " has " + hero1.getHp().getCurrentHP() + " HP and is level "
                    + hero1.getXp().getLevel() + " (" + hero1.getXp().getXp() + ")");
            return;
        }

        System.out.println("Nobody won.");
        System.out.println(hero1 + " has " + hero1.getHp().getCurrentHP() + " HP and is level "
                + hero1.getXp().getLevel() + " (" + hero1.getXp().getXp() + ")");
        System.out.println(hero2 + " has " + hero2.getHp().getCurrentHP() + " HP and is level "
                + hero2.getXp().getLevel() + " (" + hero2.getXp().getXp() + ")");
    }
}
