package map;

import heroes.Hero;

public class Map {
    private final int height;
    private final int width;
    private MapCell[][] map;

    public Map(final int height, final int width) {
        this.height = height;
        this.width = width;

        map = new MapCell[height][width];
    }

    /**
     * Gets the height of the map.
     *
     * @return Returns the height of the map.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the map.
     *
     * @return Returns the width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the map of the game.
     *
     * @return Returns the map of the game.
     */
    public MapCell[][] getMap() {
        return map;
    }

    /**
     * Gets the cell located at the respective height and width.
     *
     * @param x
     *            The height to look for.
     * @param y
     *            The width to look for.
     * @return The cell located at height x and width y.
     */
    public MapCell getMapCell(final int x, final int y) {
        return map[x][y];
    }

    /**
     * Adds a cell at the specified height and width.
     *
     * @param mapCell
     *            The cell to be added.
     * @param x
     *            The height to add at.
     * @param y
     *            The width to add at.
     */
    public void addCell(final MapCell mapCell, final int x, final int y) {
        map[x][y] = mapCell;
    }

    /**
     * Checks if the hero is in the cell located at the respective height and width.
     *
     * @param hero
     *            The hero to be looked for.
     * @param x
     *            The height of the cell.
     * @param y
     *            The width of the cell.
     * @return Returns if the hero is in the cell at height x and width y.
     */
    public boolean isHeroInCell(final Hero hero, final int x, final int y) {
        return map[x][y].isHeroInCell(hero);
    }

    /**
     * Adds the hero to the cell located at the respective height and width.
     *
     * @param hero
     *            The hero to be looked for.
     * @param x
     *            The height of the cell.
     * @param y
     *            The width of the cell.
     */
    public void addHeroToCell(final Hero hero, final int x, final int y) {
        if (isHeroInCell(hero, x, y)) {
            return;
        }

        map[x][y].addHeroToCell(hero);
    }

    /**
     * Moves the hero from the current position to the new position.
     *
     * @param hero
     *            The hero to be moved.
     * @param currX
     *            The height of the current cell.
     * @param currY
     *            The width of the current cell.
     * @param newX
     *            The height of the new cell.
     * @param newY
     *            The width of the new cell.
     */
    public void moveHeroToCell(final Hero hero, final int currX, final int currY, final int newX,
            final int newY) {
        addHeroToCell(hero, newX, newY);
        removeHeroFromCell(hero, currX, currY);
    }

    /**
     * Removes hero from the cell located at the respective height and width.
     *
     * @param hero
     *            The hero to be removed.
     * @param x
     *            The height of the cell.
     * @param y
     *            The width of the cell.
     */
    public void removeHeroFromCell(final Hero hero, final int x, final int y) {
        if (!isHeroInCell(hero, x, y)) {
            return;
        }

        map[x][y].removeHeroFromCell(hero);
    }
}
