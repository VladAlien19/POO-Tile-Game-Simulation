package tilegame.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(final BufferedImage sheet) {
        this.sheet = sheet;
    }

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public BufferedImage crop(final int x, final int y, final int width, final int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
