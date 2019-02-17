package map;

import heroes.Hero;
import heroes.properties.FightSystem;

public class FightVisitor implements Visitor {

    private static int nbFights = 0;
    private FightSystem fSys;

    public FightVisitor() {
        fSys = FightSystem.getInstance();
    }

    /**
     *
     */
    @Override
    public void visit(final MapCell cell) {
        if (cell.nbHeroesInCell() == 2) {
            Hero hero1 = cell.getHero(0);
            Hero hero2 = cell.getHero(1);

            ++nbFights;
            fSys.fight(cell, hero1, hero2);
        }
    }

    /**
     *
     * @return
     */
    public int getTotalFights() {
        return nbFights;
    }

}
