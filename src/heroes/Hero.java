package heroes;

import heroes.properties.Ability;
import heroes.properties.Effect;
import heroes.properties.Experience;
import heroes.properties.Health;
import heroes.properties.LandModifier;
import heroes.properties.Race;
import map.LandType;
import tilegame.gfx.Animation;
import tilegame.gfx.Constants;

/**
 * This class implements the heroes that will move and attack each other on the
 * map.
 */
public abstract class Hero {

    private final Health hp;
    private final Experience xp;
    private final Race race;

    private LandType isOnLandType;
    private final LandModifier landModifier;

    private Effect receivedEffect;

    private Ability ability1;
    private Ability ability2;

    private int x;
    private int y;
    private float xPos;
    private float yPos;
    private float xMove;
    private float yMove;

    private int directionFacing;

    private Animation walkingDown;
    private Animation walkingLeft;
    private Animation walkingRight;
    private Animation walkingUp;

    public static final int RACENO = 4;
    private static final float DEFAULT_XMOVE = 2.5f;
    private static final float DEFAULT_YMOVE = 2.5f;
    private static final float EPS = 5f;
    public static final int ANIM_TIMER = 100;
    public static final int FACING_DOWN = 0;
    public static final int FACING_LEFT = 1;
    public static final int FACING_RIGHT = 2;
    public static final int FACING_UP = 3;

    public Hero(final Health hp, final Experience xp, final Race race,
            final LandModifier landModifier) {
        this.hp = hp;
        this.xp = xp;
        this.race = race;
        this.landModifier = landModifier;

        xPos = 0;
        yPos = 0;
        xMove = DEFAULT_XMOVE;
        yMove = DEFAULT_YMOVE;
        directionFacing = 0;
    }

    /**
     * Calculates hero's maximum HP.
     *
     * @return Returns the theoretical value of maximum HP.
     */
    private int calculateMaxHP() {
        return hp.getBaseHP() + xp.getLevel() * hp.gethPPerLevel();
    }

    /**
     * Checks if hero has leveled up.
     *
     * @return Returns if hero has leveled up.
     */
    public boolean checkIfLevelUp() {
        return (xp.calculateLevel() - xp.getLevel() != 0);
    }

    /**
     * Levels up the hero.
     */
    public void levelUp() {
        if (!checkIfLevelUp()) {
            return;
        }

        xp.setLevel(xp.calculateLevel());
        hp.setMaxHP(calculateMaxHP());
        hp.refillHP();
    }

    /**
     * Check if hero is dead.
     *
     * @return Returns if hero is dead.
     */
    public boolean isDead() {
        return (hp.getCurrentHP() == 0);
    }

    /**
     * Check if the effect given to hero makes them unable to move.
     *
     * @return Returns if hero can move.
     */
    public boolean canMove() {
        if (receivedEffect == null) {
            return true;
        }

        return receivedEffect.canMove();
    }

    /**
     * Applies the damage from the received effect.
     */
    public void applyReceivedEffect() {
        if (receivedEffect == null) {
            return;
        }
        if (receivedEffect.getRoundsLeft() == 0) {
            receivedEffect = null;
            return;
        }

        hp.applyDamage(receivedEffect.getDamageOverTime());
        receivedEffect.decreaseRoundsLeft();
    }

    /**
     * Calculates the damage dealt by hero's first ability.
     *
     * @param enemy
     *            The hero the ability will be applied to.
     * @return Returns the amount of damage to be dealt to enemy.
     */
    public abstract int calcDmgAbility1(Hero enemy);

    /**
     * Executes the first ability.
     *
     * @param enemy
     *            The hero the ability will be applied to.
     * @param damage
     *            The amount of damage dealt to enemy.
     */
    public abstract void execAbility1(Hero enemy, int damage);

    /**
     * Calculates the damage dealt by hero's second ability.
     *
     * @param enemy
     *            The hero the ability will be applied to.
     * @return Returns the amount of damage to be dealt to enemy.
     */
    public abstract int calcDmgAbility2(Hero enemy);

    /**
     * Executes the second ability.
     *
     * @param enemy
     *            The hero the ability will be applied to.
     * @param damage
     *            The amount of damage dealt to enemy.
     */
    public abstract void execAbility2(Hero enemy, int damage);

    @Override
    public final String toString() {
        return this.getClass().getSimpleName();
    }

    public void move() {
        // moveX();
        // moveY();
        moveUp();
        moveDown();
        moveLeft();
        moveRight();
    }

    private void moveLeft() {
        if (xPos - x * Constants.tileSize > EPS) {
            xPos -= xMove;
            walkingLeft.tick();
            return;
        }

        walkingLeft.stop();
    }

    private void moveRight() {
        if (xPos - x * Constants.tileSize < -EPS) {
            xPos += xMove;
            walkingRight.tick();
            return;
        }

        walkingRight.stop();
    }

    private void moveUp() {
        if (yPos - y * Constants.tileSize > EPS) {
            yPos -= yMove;
            walkingUp.tick();
            return;
        }

        walkingUp.stop();
    }

    private void moveDown() {
        if (yPos - y * Constants.tileSize < -EPS) {
            yPos += yMove;
            walkingDown.tick();
            return;
        }

        walkingDown.stop();
    }

    public void tick() {
        // Movement
        move();
    }

    // ----------------- Getters and setters ---------------------------

    /**
     *
     * @return
     */
    public Health getHp() {
        return hp;
    }

    /**
     *
     * @return
     */
    public Experience getXp() {
        return xp;
    }

    /**
     * Gets hero's race.
     *
     * @return Returns hero's race.
     */
    public Race getRace() {
        return race;
    }

    /**
     * Gets the land type the hero is in a round.
     *
     * @return Returns the land the hero is on.
     */
    public LandType getIsOnLandType() {
        return isOnLandType;
    }

    /**
     * Sets the land type the hero is in a round.
     *
     * @param newIsOnLandType
     *            The land type the hero is on.
     */
    public void setIsOnLandType(final LandType newIsOnLandType) {
        this.isOnLandType = newIsOnLandType;
    }

    /**
     * Gets hero's land to have a boost on.
     *
     * @return Returns hero's land to have a boost on.
     */
    public LandModifier getLandModifier() {
        return landModifier;
    }

    /**
     * Adds effect to hero.
     *
     * @param effect
     *            Effect to be added.
     */
    public void receiveEffect(final Effect effect) {
        this.receivedEffect = effect;
    }

    /**
     * Gets the effect applied to hero.
     *
     * @return Returns the effect applied to hero.
     */
    public Effect getReceivedEffect() {
        return receivedEffect;
    }

    /**
     * Gets hero's first ability.
     *
     * @return Returns hero's first ability.
     */
    public Ability getAbility1() {
        return ability1;
    }

    /**
     * Sets hero's first ability.
     *
     * @param newAbility1
     *            Hero's first ability.
     */
    public void setAbility1(final Ability newAbility1) {
        this.ability1 = newAbility1;
    }

    /**
     * Gets hero's second ability.
     *
     * @return Returns hero's second ability.
     */
    public Ability getAbility2() {
        return ability2;
    }

    /**
     * Set's hero's second ability.
     *
     * @param newAbility2
     *            Hero's second ability
     */
    public void setAbility2(final Ability newAbility2) {
        this.ability2 = newAbility2;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(final int y) {
        this.y = y;
    }

    public void setAnimations(Animation walkingDown, Animation walkingLeft, Animation walkingRight,
            Animation walkingUp) {

        this.walkingDown = walkingDown;
        this.walkingLeft = walkingLeft;
        this.walkingRight = walkingRight;
        this.walkingUp = walkingUp;
    }

    /**
     * @return the walkingDown
     */
    public Animation getWalkingDown() {
        return walkingDown;
    }

    /**
     * @return the walkingLeft
     */
    public Animation getWalkingLeft() {
        return walkingLeft;
    }

    /**
     * @return the walkingRight
     */
    public Animation getWalkingRight() {
        return walkingRight;
    }

    /**
     * @return the walkingUp
     */
    public Animation getWalkingUp() {
        return walkingUp;
    }

    /**
     * @return the xPos
     */
    public float getxPos() {
        return xPos;
    }

    /**
     * @param xPos
     *            the xPos to set
     */
    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    /**
     * @return the yPos
     */
    public float getyPos() {
        return yPos;
    }

    /**
     * @param yPos
     *            the yPos to set
     */
    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    /**
     * @return the directionFacing
     */
    public int getDirectionFacing() {
        return directionFacing;
    }

    /**
     * @param directionFacing
     *            the directionFacing to set
     */
    public void setDirectionFacing(int directionFacing) {
        this.directionFacing = directionFacing;
    }

    // ----------------------------------------------------------
}
