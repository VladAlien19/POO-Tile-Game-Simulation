package tilegame.states;

import java.awt.Graphics;

public interface State {

    void tick();

    void render(Graphics graph);
}
