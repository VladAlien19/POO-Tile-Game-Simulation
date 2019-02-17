package tilegame;

public final class Launcher {

    private Launcher() {

    }

    public static void main(final String[] args) {
        Game game = new Game("Title!", 700, 700, args[0], args[1]);
        game.start();
    }
}
