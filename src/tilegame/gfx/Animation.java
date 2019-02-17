package tilegame.gfx;

import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int index;
    private long lastTime;
    private long timer;

    private boolean isActive;

    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        isActive = false;

        lastTime = System.currentTimeMillis();
    }

    /**
     * Do calculus every tick.
     */
    public void tick() {
        if (!isActive) {
            return;
        }

        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            ++index;
            timer = 0;

            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    /**
     * Start animation.
     */
    public void start() {
        timer = 0;
        index = 0;
        isActive = true;

        lastTime = System.currentTimeMillis();
    }

    /**
     * Stop animation.
     */
    public void stop() {
        timer = 0;
        index = frames.length - 1;
        isActive = false;

        lastTime = System.currentTimeMillis();
    }

    /**
     * Get animation current frame to be drawn.
     * 
     * @return frame to be drawn.
     */
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}
