package tilegame.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import heroes.Hero;

public class HeroRenderer {

    public HeroRenderer() {
    }

    /**
     * Draw hero to screen.
     * 
     * @param graph
     *            graphics element to be drawn on.
     * @param hero
     *            hero to be drawn.
     */
    public void drawHero(final Graphics graph, final Hero hero) {
        if (hero.isDead()) {
            graph.drawImage(Assets.dead, (int) (hero.getxPos()), (int) (hero.getyPos()), null);
            return;
        }

        graph.drawImage(getCurrentAnimationFrame(hero), (int) (hero.getxPos()),
                (int) (hero.getyPos()), null);
    }

    private BufferedImage getCurrentAnimationFrame(Hero hero) {
        switch (hero.getDirectionFacing()) {
        case Hero.FACING_UP:
            return hero.getWalkingUp().getCurrentFrame();

        case Hero.FACING_DOWN:
            return hero.getWalkingDown().getCurrentFrame();

        case Hero.FACING_LEFT:
            return hero.getWalkingLeft().getCurrentFrame();

        case Hero.FACING_RIGHT:
            return hero.getWalkingRight().getCurrentFrame();
        }

        return null;
    }
}
