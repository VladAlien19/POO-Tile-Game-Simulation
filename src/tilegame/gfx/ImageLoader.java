package tilegame.gfx;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
    public static BufferedImage loadImage(final String path) {
        try {
            //return ImageIO.read(ImageLoader.class.getResource(path));
            return ImageIO.read(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
