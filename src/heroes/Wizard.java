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

public class Wizard extends Hero {

    private static final int BASEHP = 400;
    private static final int HPLEVEL = 30;
    private static final LandType PREFLAND = LandType.DESERT;
    private static final float LANDBOOST = 1.1f;

    public Wizard() {
        super(new Health(BASEHP, HPLEVEL), new Experience(0, 0), Race.WIZARD,
                new LandModifier(PREFLAND, LANDBOOST));

        setAbility1(new Drain());
        setAbility2(new Deflect());

        setAnimations(new Animation(ANIM_TIMER, Assets.heroWizardDown),
                new Animation(ANIM_TIMER, Assets.heroWizardLeft),
                new Animation(ANIM_TIMER, Assets.heroWizardRight),
                new Animation(ANIM_TIMER, Assets.heroWizardUp));
    }

    public class Drain extends Ability {

        private static final int BASEDMG = 20;
        private static final int DMGPERLEV = 5;

        private static final float RACEMODIF0 = 0.8f;
        private static final float RACEMODIF1 = 1.2f;
        private static final float RACEMODIF2 = 0.9f;
        private static final float RACEMODIF3 = 1.05f;

        private static final float PERCENHP = 0.3f;
        private static final float PERCENTAGE = 100.0f;

        public Drain() {
            super(BASEDMG, DMGPERLEV, 0, 0,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Wizard.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // No effect given
            return null;
        }

        @Override
        public final int calculateDamageAgainstEnemy(final Hero enemy) {
            // Calculates the percentage of the drain
            float damage = Math.min(PERCENHP * enemy.getHp().getMaxHP(),
                    enemy.getHp().getCurrentHP());

            // Multiplies the base damage with the percentage
            damage *= calculateBaseDamage() / PERCENTAGE;

            // Checks if hero is on preferred land
            if (getIsOnLandType().equals(getLandModifier().getPrefferedLand())) {
                damage = multWithLandModifier(damage);
            }

            // Applies race modifier
            return Math.round(multWithRaceModifier(damage, enemy));
        }
    }

    public class Deflect extends Ability {

        private static final int BASEDMG = 35;
        private static final int DMGPERLEV = 2;

        private static final float RACEMODIF0 = 1.2f;
        private static final float RACEMODIF1 = 1.4f;
        private static final float RACEMODIF2 = 1.3f;
        private static final float RACEMODIF3 = 0.0f;

        private static final int PERCENMAX = 70;
        private static final float PERCENTAGE = 100.0f;

        private static final int ROGUECRITFREC = 3;

        public Deflect() {
            super(BASEDMG, DMGPERLEV, 0, 0,
                    new float[] { RACEMODIF0, RACEMODIF1, RACEMODIF2, RACEMODIF3 });
            setHero(Wizard.this);
        }

        @Override
        public final Effect createEffect(final Hero enemy) {
            // No effect given
            return null;

        }

        @Override
        public final int calculateDamageAgainstEnemy(final Hero enemy) {
            // If enemy is wizard, do not apply any damage
            if (enemy.getRace().equals(Race.WIZARD)) {
                return 0;
            }

            // Gets enemy's first ability's base damage
            float damageFromAbil1 = enemy.getAbility1().getBaseDamage();

            // Checks if the enemy is a Rogue and if they applied a critical hit
            if (enemy instanceof Rogue) {
                Rogue rog = (Rogue) enemy;
                if (rog.getHitNo() % ROGUECRITFREC == 0
                        && rog.getIsOnLandType().equals(rog.getLandModifier().getPrefferedLand())) {
                    Rogue.Backstab backStab = (Rogue.Backstab) rog.getAbility1();
                    damageFromAbil1 = backStab.multWithCrit(damageFromAbil1);
                }
            }

            // Gets enemy's second ability's base damage
            float damageFromAbil2 = enemy.getAbility2().getBaseDamage();

            // Adds the total damage
            float damageTotal = damageFromAbil1 + damageFromAbil2;

            // Checks if enemy is on preferred land
            if (enemy.getIsOnLandType().equals(enemy.getLandModifier().getPrefferedLand())) {
                damageTotal *= enemy.getLandModifier().getModifier();
            }

            // Multiplies the damage with the respective percentage
            damageTotal *= Math.min(calculateBaseDamage(), PERCENMAX) / PERCENTAGE;
            damageTotal = Math.round(damageTotal);

            // Checks if hero is on preferred land
            if (getIsOnLandType().equals(getLandModifier().getPrefferedLand())) {
                damageTotal = multWithLandModifier(damageTotal);
            }

            // Applies race modifier
            return Math.round(multWithRaceModifier(damageTotal, enemy));
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
    }
}
