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

public class Pyromancer extends Hero {

    private static final int BASEHP = 500;
    private static final int HPLEVEL = 50;
    private static final LandType PREFLAND = LandType.VOLCANIC;
    private static final float LANDBOOST = 1.25f;

    public Pyromancer() {
        super(new Health(BASEHP, HPLEVEL), new Experience(0, 0), Race.PYROMANCER,
                new LandModifier(PREFLAND, LANDBOOST));

        setAbility1(new FireBlast());
        setAbility2(new Ignite());

        setAnimations(new Animation(ANIM_TIMER, Assets.heroPyroDown),
                new Animation(ANIM_TIMER, Assets.heroPyroLeft),
                new Animation(ANIM_TIMER, Assets.heroPyroRight),
                new Animation(ANIM_TIMER, Assets.heroPyroUp));
    }

    public class FireBlast extends Ability {

        private static final int BASEDMG = 350;
        private static final int DMGPERLEV = 50;

        private static final float RACEMODIF0 = 0.8f;
        private static final float RACEMODIF1 = 1.2f;
        private static final float RACEMODIF2 = 0.9f;
        private static final float RACEMODIF3 = 1.05f;

        public FireBlast() {
            super(BASEDMG, DMGPERLEV, 0, 0,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Pyromancer.this);
        }

        @Override
        public final int calculateEffectDamage(final Hero enemy) {
            return 0;
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // No effect given
            return null;
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

    public class Ignite extends Ability {

        private static final int BASEDMG = 150;
        private static final int DMGPERLEV = 30;

        private static final float RACEMODIF0 = 0.8f;
        private static final float RACEMODIF1 = 1.2f;
        private static final float RACEMODIF2 = 0.9f;
        private static final float RACEMODIF3 = 1.05f;

        private static final int DOTBASEDMG = 50;
        private static final int DOTBASEDMGPERLEV = 30;

        public Ignite() {
            super(BASEDMG, DMGPERLEV, DOTBASEDMG, DOTBASEDMGPERLEV,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Pyromancer.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // Enemy can move and will receive damage
            return new Effect(true, 2, calculateEffectDamage(enemy));
        }

        @Override
        public final int calculateDamageAgainstEnemy(final Hero enemy) {
            // Calculates base damage
            float damage = calculateBaseDamage();

            // Check if hero is on preferred land
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
