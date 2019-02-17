package heroes.properties;

/**
 * This enum states the races of all heroes.
 */
public enum Race {
    ROGUE("R"), KNIGHT("K"), PYROMANCER("P"), WIZARD("W");

    private final String text;

    Race(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean equals(final Race race) {
        return text.equals(race.text);
    }
}
