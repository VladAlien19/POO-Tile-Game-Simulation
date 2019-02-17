package heroes.properties;

import heroes.Hero;

/**
 * The class implements heroes' ability to deal damage and apply effects to
 * other heroes.
 */
public abstract class Ability implements Strategy {

    private final int baseDamage;
    private final int damagePerLevel;
    private final int dotBaseDmg;
    private final int dotBaseDmgLev;
    private final RaceModifierTable raceModifierTable;
    private Hero hero;

    public Ability(final int baseDamage, final int damagePerLevel, final int dotBaseDmg,
            final int dotBaseDmgLev, final float[] modifiers) {
        this.baseDamage = baseDamage;
        this.damagePerLevel = damagePerLevel;
        this.dotBaseDmg = dotBaseDmg;
        this.dotBaseDmgLev = dotBaseDmgLev;
        this.raceModifierTable = new RaceModifierTable(modifiers);
    }

    /**
     * Gets ability's base damage.
     *
     * @return Returns ability's base damage.
     */
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * Gets ability's damage which increases base damage every level.
     *
     * @return Returns ability's damage which increases base damage every level.
     */
    public int getDamagePerLevel() {
        return damagePerLevel;
    }

    /**
     * Gets ability's race modifier table.
     *
     * @return Returns ability's race modifier table.
     */
    public RaceModifierTable getRaceModifierTable() {
        return raceModifierTable;
    }

    /**
     * @return the hero
     */
    public Hero getHero() {
        return hero;
    }

    /**
     *
     * @param hero
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     * Calculates ability's base damage for hero's current level.
     *
     * @return Ability's base damage.
     */
    public float calculateBaseDamage() {
        return this.getBaseDamage() + this.getDamagePerLevel() * this.getHero().getXp().getLevel();
    }

    /**
     * Multiplies the damage with the land modifier.
     *
     * @param damage
     *            The damage to be multiplied.
     * @return Returns the damage after multiplying.
     */
    public float multWithLandModifier(final float damage) {
        return damage * this.getHero().getLandModifier().getModifier();
    }

    /**
     * Multiplies the damage with enemy's race modifier.
     *
     * @param damage
     *            The damage to be multiplied.
     * @param enemy
     *            The hero whom the damage will be applied to.
     * @return Return the damage after multiplying.
     */
    public float multWithRaceModifier(final float damage, final Hero enemy) {
        return damage * this.getRaceModifierTable().getRaceModifier(enemy.getRace()).getModifier();
    }

    /**
     * Creates ability's effect to be applied to the enemy.
     *
     * @param enemy
     *            The hero whom the effect will be applied to.
     * @return Returns the effect created for the enemy.
     */
    public abstract Effect createEffect(Hero enemy);

    /**
     *
     * @param enemy
     * @return
     */
    public int calculateEffectDamage(final Hero enemy) {
        // Calculates base damage
        float damage = (dotBaseDmg + dotBaseDmgLev * this.getHero().getXp().getLevel());

        // Checks if hero is on preferred land
        if (this.getHero().getIsOnLandType()
                .equals(this.getHero().getLandModifier().getPrefferedLand())) {
            damage = multWithLandModifier(damage);
        }

        // Applies race modifier
        return Math.round(multWithRaceModifier(damage, enemy));
    }

    /**
     * Applies an effect to the enemy.
     *
     * @param enemy
     *            The hero whom the effect will be applied to.
     * @param effect
     *            The effect which will be applied to the enemy.
     */
    public void applyEffectToEnemy(final Hero enemy, final Effect effect) {
        enemy.receiveEffect(effect);
    }
}
