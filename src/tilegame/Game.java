package tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import tilegame.gfx.Assets;
import tilegame.states.GameState;
import tilegame.states.MainMenuState;
import tilegame.states.SettingsState;
import tilegame.states.State;
import tilegame.states.StateManager;

public class Game implements Runnable {

    private Display display;

    private String title;
    private int width;
    private int height;
    private String inputFile;
    private String outputFile;

    private boolean isRunning;
    private Thread thread;

    private BufferStrategy buffStrat;
    private Graphics graph;

    private State gameState;
    private State mainMenuState;
    private State settingsState;

    public Game(final String title, final int width, final int height, final String inputFile,
            final String outputFile) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        isRunning = false;
    }

    private void init() {
        display = new Display(title, width, height);
        Assets.init();

        gameState = new GameState(inputFile, outputFile);
        mainMenuState = new MainMenuState();
        settingsState = new SettingsState();
        StateManager.setState(gameState);
    }

    private void tick() {
        if (StateManager.getState() != null) {
            StateManager.getState().tick();
        }
    }

    private void render() {
        buffStrat = display.getCanvas().getBufferStrategy();
        if (buffStrat == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        graph = buffStrat.getDrawGraphics();

        // Clear screen
        graph.clearRect(0, 0, width, height);
        // Draw Here

        if (StateManager.getState() != null) {
            StateManager.getState().render(graph);
        }
        
        // End drawing
        buffStrat.show();
        graph.dispose();
    }

    /**
     *
     */
    @Override
    public void run() {
        init();

        // Making sure game runs at 60 fps
        int fps = 60;
        double timePerTick = 1e9 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;

        // Game loop
        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;

            lastTime = now;

            // Call tick and render method every fps
            if (delta >= 1) {
                tick();
                render();
                --delta;
            }

            if (timer >= 1e9) {
                timer = 0;
            }
        }

        stop();
    }

    /**
     * Start thread.
     */
    public synchronized void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stop thread.
     */
    public synchronized void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
