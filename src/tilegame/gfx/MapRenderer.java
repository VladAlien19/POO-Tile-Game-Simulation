package tilegame.gfx;

import java.awt.Graphics;

import map.Map;

public class MapRenderer {

    public MapRenderer() {
    }

    public void drawMap(Graphics graph, Map map) {
        for (int i = 0; i < map.getHeight(); ++i) {
            for (int j = 0; j < map.getWidth(); ++j) {
                // Draw each tile
                switch (map.getMapCell(i, j).getLandType()) {

                case LAND:
                    drawLand(graph, i * Constants.tileSize, j * Constants.tileSize);
                    break;

                case VOLCANIC:
                    drawVolcanic(graph, i * Constants.tileSize, j * Constants.tileSize);
                    break;

                case DESERT:
                    drawDesert(graph, i * Constants.tileSize, j * Constants.tileSize);
                    break;

                case WOODS:
                    drawWoods(graph, i * Constants.tileSize, j * Constants.tileSize);
                    break;

                default:
                    break;
                }
            }
        }
    }

    private void drawLand(Graphics graph, int i, int j) {
        graph.drawImage(Assets.landLand, i, j, null);
    }

    private void drawVolcanic(Graphics graph, int i, int j) {
        graph.drawImage(Assets.landVolcanic, i, j, null);
    }

    private void drawDesert(Graphics graph, int i, int j) {
        graph.drawImage(Assets.landDesert, i, j, null);
    }

    private void drawWoods(Graphics graph, int i, int j) {
        graph.drawImage(Assets.landWoods, i, j, null);
    }
}
