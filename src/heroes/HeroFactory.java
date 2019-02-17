package heroes;

public final class HeroFactory {

    private static HeroFactory instance;

    private HeroFactory() {
    }

    public static HeroFactory getInstance() {
        if (instance == null) {
            instance = new HeroFactory();
        }

        return instance;
    }

    /**
     * Creates hero based on input string.
     * 
     * @param str
     *            Input string.
     * @return Hero object.
     */
    public Hero getHero(final String str) {

        switch (str) {

        case "P":
            return new Pyromancer();

        case "K":
            return new Knight();

        case "W":
            return new Wizard();

        case "R":
            return new Rogue();

        default:
            return null;
        }
    }
}
