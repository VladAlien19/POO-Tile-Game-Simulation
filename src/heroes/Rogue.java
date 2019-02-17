package heroes;

import heroes.properties.Ability;
import heroes.properties.Effect;
import heroes.properties.Experience;
import heroes.properties.Health;
import heroes.properties.LandModifier;
import heroes.properties.Race;
import map.LandType;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

public class Rogue extends Hero {

    private static final int BASEHP = 600;
    private static final int HPLEVEL = 40;
    private static final LandType PREFLAND = LandType.WOODS;
    private static final float LANDBOOST = 1.15f;

    private static final int BASENOROUNDSDOT = 3;
    private static final int EXTENDEDNOROUNDSDOT = 6;
    private int hitNo = 0;

    public Rogue() {
        super(new Health(BASEHP, HPLEVEL), new Experience(0, 0), Race.ROGUE,
                new LandModifier(PREFLAND, LANDBOOST));

        setAbility1(new Backstab());
        setAbility2(new Paralysis());

        setAnimations(new Animation(ANIM_TIMER, Assets.heroRogueDown),
                new Animation(ANIM_TIMER, Assets.heroRogueLeft),
                new Animation(ANIM_TIMER, Assets.heroRogueRight),
                new Animation(ANIM_TIMER, Assets.heroRogueUp));
    }

    /**
     * Gets the number of times rogue has used BackStab.
     *
     * @return Return the number of times rogue has used BackStab.
     */
    public final int getHitNo() {
        return hitNo;
    }

    public class Backstab extends Ability {

        private static final int BASEDMG = 200;
        private static final int DMGPERLEV = 20;

        private static final float RACEMODIF0 = 1.2f;
        private static final float RACEMODIF1 = 0.9f;
        private static final float RACEMODIF2 = 1.25f;
        private static final float RACEMODIF3 = 1.25f;

        private static final float CRITMODIF = 1.5f;
        private static final int CRITFRECV = 3;

        public Backstab() {
            super(BASEDMG, DMGPERLEV, 0, 0,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Rogue.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // No effect given
            return null;
        }

        /**
         * Multiplies the damage with the critical hit modifier.
         *
         * @param damage
         *            The damage to be multiplied.
         * @return Returns the damage multiplied with the critical hit modifier.
         */
        public float multWithCrit(final float damage) {
            return damage * CRITMODIF;
        }

        @Override
        public final int calculateDamageAgainstEnemy(final Hero enemy) {
            // Calculates base damage
            float damage = calculateBaseDamage();

            // Check if hero is on preferred land
            if (getIsOnLandType().equals(getLandModifier().getPrefferedLand())) {
                damage = multWithLandModifier(damage);
            }

            // Checks if Rogue can give a critical hit
            if (hitNo % CRITFRECV == 0 && getIsOnLandType().equals(LandType.WOODS)) {
                damage = multWithCrit(damage);
            }

            // Applies race modifier
            return Math.round(multWithRaceModifier(damage, enemy));
        }
    }

    public class Paralysis extends Ability {

        private static final int BASEDMG = 40;
        private static final int DMGPERLEV = 10;

        private static final float RACEMODIF0 = 0.9f;
        private static final float RACEMODIF1 = 0.8f;
        private static final float RACEMODIF2 = 1.2f;
        private static final float RACEMODIF3 = 1.25f;

        private static final int DOTBASEDMG = 40;
        private static final int DOTBASEDMGPERLEV = 10;

        public Paralysis() {
            super(BASEDMG, DMGPERLEV, DOTBASEDMG, DOTBASEDMGPERLEV,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Rogue.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // Enemy will not move and will receive damage
            return new Effect(false, BASENOROUNDSDOT, calculateEffectDamage(enemy));
        }

        @Override
        public final int calculateDamageAgainstEnemy(final Hero enemy) {
            // Calculates base damage
            float damage = calculateBaseDamage();

            // Checks if hero is on preferred land
            if (getIsOnLandType().equals(getLandModifier().getPrefferedLand())) {
                damage = multWithLandModifier(damage);
            }

            // Applies race modifier
            return Math.round(multWithRaceModifier(damage, enemy));
        }
    }

    @Override
    public final int calcDmgAbility1(final Hero enemy) {
        return getAbility1().calculateDamageAgainstEnemy(enemy);
    }

    @Override
    public final void execAbility1(final Hero enemy, final int damage) {
        enemy.getHp().applyDamage(damage);
        ++hitNo;
    }

    @Override
    public final int calcDmgAbility2(final Hero enemy) {
        return getAbility2().calculateDamageAgainstEnemy(enemy);
    }

    @Override
    public final void execAbility2(final Hero enemy, final int damage) {
        enemy.getHp().applyDamage(damage);
        Effect givesEffect = getAbility2().createEffect(enemy);

        // Checks if the effect duration should be 6 rounds instead of 3
        if (enemy.getIsOnLandType().equals(LandType.WOODS)) {
            givesEffect.setRoundsLeft(EXTENDEDNOROUNDSDOT);
        }

        enemy.receiveEffect(givesEffect);
    }
}
