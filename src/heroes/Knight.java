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

public class Knight extends Hero {

    private static final int BASEHP = 900;
    private static final int HPLEVEL = 80;
    private static final LandType PREFLAND = LandType.LAND;
    private static final float LANDBOOST = 1.15f;

    public Knight() {
        super(new Health(BASEHP, HPLEVEL), new Experience(0, 0), Race.KNIGHT,
                new LandModifier(PREFLAND, LANDBOOST));
        setAbility1(new Execute());
        setAbility2(new Slam());

        setAnimations(new Animation(ANIM_TIMER, Assets.heroKnightDown),
                new Animation(ANIM_TIMER, Assets.heroKnightLeft),
                new Animation(ANIM_TIMER, Assets.heroKnightRight),
                new Animation(ANIM_TIMER, Assets.heroKnightUp));
    }

    public class Execute extends Ability {

        private static final int BASEDMG = 200;
        private static final int DMGPERLEV = 30;

        private static final float RACEMODIF0 = 1.15f;
        private static final float RACEMODIF1 = 1.0f;
        private static final float RACEMODIF2 = 1.1f;
        private static final float RACEMODIF3 = 0.8f;

        private static final float PERCENHP = 0.2f;
        private static final float PERCENHPPERLEV = 0.01f;
        private static final float PERCENMAX = 0.4f;

        public Execute() {
            super(BASEDMG, DMGPERLEV, 0, 0,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Knight.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // No effect given
            return null;
        }

        @Override
        public final int calculateDamageAgainstEnemy(final Hero enemy) {
            // Calculates the percentage of the enemy's HP
            float procent = Math.min(PERCENHP + PERCENHPPERLEV * getXp().getLevel(), PERCENMAX);

            // Checks if the enemy will be executed
            if (enemy.getHp().getCurrentHP() <= enemy.getHp().getMaxHP() * procent) {
                return enemy.getHp().getCurrentHP();
            }

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

    public class Slam extends Ability {

        private static final int BASEDMG = 100;
        private static final int DMGPERLEV = 40;

        private static final float RACEMODIF0 = 0.8f;
        private static final float RACEMODIF1 = 1.2f;
        private static final float RACEMODIF2 = 0.9f;
        private static final float RACEMODIF3 = 1.05f;

        public Slam() {
            super(BASEDMG, DMGPERLEV, 0, 0,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Knight.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // Enemy will not move and will not receive damage
            return new Effect(false, 1, 0);

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
    }

    @Override
    public final int calcDmgAbility2(final Hero enemy) {
        return getAbility2().calculateDamageAgainstEnemy(enemy);
    }

    @Override
    public final void execAbility2(final Hero enemy, final int damage) {
        enemy.getHp().applyDamage(damage);
        enemy.receiveEffect(getAbility2().createEffect(enemy));
    }
}
