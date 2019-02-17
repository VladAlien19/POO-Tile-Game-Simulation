package map;

import java.util.LinkedList;
import java.util.List;

import heroes.Hero;

/**
 * This class implements cells of the map.
 */
public class MapCell implements Visitable {
    private final LandType landType;

    // List of heroes that are in the respective cell
    private List<Hero> heroes;

    public MapCell(final LandType landType) {
        this.landType = landType;
        heroes = new LinkedList<Hero>();
    }

    /**
     * Gets the land type of the cell.
     *
     * @return Returns the land type of the cell.
     */
    public LandType getLandType() {
        return landType;
    }

    /**
     * Gets the hero at the specified index.
     *
     * @param index
     *            The index of the hero.
     * @return Returns the hero at the specified index.
     */
    public Hero getHero(final int index) {
        return heroes.get(index);
    }

    /**
     * Checks if the hero is in the cell.
     *
     * @param hero
     *            The hero to be found.
     * @return Returns if the hero is in the cell.
     */
    public boolean isHeroInCell(final Hero hero) {
        return heroes.contains(hero);
    }

    /**
     * Adds a hero to the cell.
     *
     * @param hero
     *            The hero to be added.
     */
    public void addHeroToCell(final Hero hero) {
        if (isHeroInCell(hero)) {
            return;
        }

        heroes.add(hero);
    }

    /**
     * Removes the hero from the cell.
     *
     * @param hero
     *            The hero to be removed.
     */
    public void removeHeroFromCell(final Hero hero) {
        if (!isHeroInCell(hero)) {
            return;
        }

        heroes.remove(hero);
    }

    /**
     * Calculates the number of heroes in the cell.
     *
     * @return Returns the number of heroes in the cell.
     */
    public int nbHeroesInCell() {
        return heroes.size();
    }

    /**
     *
     */
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
