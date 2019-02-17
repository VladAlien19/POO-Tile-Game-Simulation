package tilegame.states;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

import fileio.FileSystem;
import heroes.Hero;
import heroes.HeroFactory;
import heroes.properties.LandTypeFactory;
import map.FightVisitor;
import map.Map;
import map.MapCell;
import tilegame.gfx.Constants;
import tilegame.gfx.HeroRenderer;
import tilegame.gfx.MapRenderer;

public class GameState implements State {

    private String inputFileName;
    private String outputFileName;

    private FileSystem fileSystem;

    private int mapHeight;
    private int mapWidth;
    private Map map;

    private int heroNo;
    private Hero[] heroArray;

    private int currentRound;
    private int roundNo;

    private HeroRenderer hRend;
    private MapRenderer mapRend;

    private static final int MOVE_TIME = 1500;
    private static final int IN_BETW_TIME = 250;
    private long waitTime;

    public GameState(final String inputFIle, final String outputFile) {
        this.inputFileName = inputFIle;
        this.outputFileName = outputFile;

        hRend = new HeroRenderer();
        mapRend = new MapRenderer();
        currentRound = 0;

        init();
    }

    private void init() {

        // Reads from file
        try {
            fileSystem = new FileSystem(inputFileName, outputFileName);

            // Creates map
            mapHeight = fileSystem.nextInt();
            mapWidth = fileSystem.nextInt();
            map = new Map(mapHeight, mapWidth);

            // Adds land types to map
            for (int y = 0; y < mapHeight; ++y) {
                String line = fileSystem.nextWord();
                String[] landArray = line.split("");
                LandTypeFactory ldFact = LandTypeFactory.getInstance();

                for (int x = 0; x < mapWidth; ++x) {
                    MapCell mapCell = new MapCell(ldFact.getLandType(landArray[x]));
                    map.addCell(mapCell, x, y);
                }
            }

            // Reads heroes
            heroNo = fileSystem.nextInt();
            heroArray = new Hero[heroNo];
            for (int i = 0; i < heroNo; ++i) {

                // Creates hero
                String heroRace = fileSystem.nextWord();
                HeroFactory hFact = HeroFactory.getInstance();
                heroArray[i] = hFact.getHero(heroRace);

                // Add hero to map
                int y = fileSystem.nextInt();
                int x = fileSystem.nextInt();
                heroArray[i].setX(x);
                heroArray[i].setxPos(x * Constants.tileSize);
                heroArray[i].setY(y);
                heroArray[i].setyPos(y * Constants.tileSize);
                heroArray[i].setIsOnLandType(map.getMapCell(x, y).getLandType());
                map.addHeroToCell(heroArray[i], x, y);

                System.out.println("Spawning " + heroArray[i] + " at [" + x + "][" + y + "]");
            }
            System.out.println("");

            // Reads number of rounds
            roundNo = fileSystem.nextInt();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file '" + inputFileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + inputFileName + "'");
        }
    }

    /**
     * Moves a hero based on the move presented in the string.
     *
     * @param hero
     *            Hero to be moved.
     * @param move
     *            String that contains the move.
     */
    public void moveHero(final Hero hero, final String move) {        
        int x = hero.getX();
        int y = hero.getY();

        switch (move) {

        case "_":
            break;

        case "U":
            map.moveHeroToCell(hero, x, y, x, --y);
            hero.setDirectionFacing(Hero.FACING_UP);
            hero.getWalkingUp().start();
            System.out.println("Moving " + hero + " to [" + x + "][" + y + "]");
            break;

        case "D":
            map.moveHeroToCell(hero, x, y, x, ++y);
            hero.setDirectionFacing(Hero.FACING_DOWN);
            hero.getWalkingDown().start();
            System.out.println("Moving " + hero + " to [" + x + "][" + y + "]");
            break;

        case "L":
            map.moveHeroToCell(hero, x, y, --x, y);
            hero.setDirectionFacing(Hero.FACING_LEFT);
            hero.getWalkingLeft().start();
            System.out.println("Moving " + hero + " to [" + x + "][" + y + "]");
            break;

        case "R":
            map.moveHeroToCell(hero, x, y, ++x, y);
            hero.setDirectionFacing(Hero.FACING_RIGHT);
            hero.getWalkingRight().start();
            System.out.println("Moving " + hero + " to [" + x + "][" + y + "]");
            break;

        default:
            break;
        }

        hero.setX(x);
        hero.setY(y);
        hero.setIsOnLandType(map.getMapCell(x, y).getLandType());
    }

    @Override
    public void tick() {
        if (waitTime - System.currentTimeMillis() > 0) {
            for (int i = 0; i < heroArray.length; ++i) {
                heroArray[i].tick();
            }
            return;
        }

        // Check if all rounds are done
        if (currentRound == roundNo) {
            try {
                // Writes to file
                fileSystem = new FileSystem(inputFileName, outputFileName);
                for (int i = 0; i < heroNo; ++i) {
                    Hero hero = heroArray[i];
                    String line = hero.getRace().toString() + " ";

                    if (hero.isDead()) {
                        line += "dead";
                    } else {
                        line += hero.getXp().getLevel() + " " + hero.getXp().getXp() + " "
                                + hero.getHp().getCurrentHP() + " " + hero.getX() + " "
                                + hero.getY();
                    }

                    fileSystem.writeWord(line);
                    fileSystem.writeNewLine();
                }

                fileSystem.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to open file '" + outputFileName + "'");
            } catch (IOException e) {
                System.out.println("Error writing file '" + outputFileName + "'");
                e.printStackTrace();
            }

            return;
        }

        // Simulate round
        try {
            
            // Reads moves
            String line = fileSystem.nextWord();
            String[] moves = line.split("");
            System.out.println("Round: " + currentRound);

            waitTime = MOVE_TIME + IN_BETW_TIME + System.currentTimeMillis();

            for (int j = 0; j < heroNo; ++j) {

                // Applies DoT
                heroArray[j].applyReceivedEffect();

                if (heroArray[j].getReceivedEffect() != null) {
                    System.out.println("Applying Effect to " + heroArray[j] + " "
                            + heroArray[j].getReceivedEffect());
                    System.out.println("\tRemaining HP: " + heroArray[j].getHp().getCurrentHP());
                }

                // If hero is dead, removes hero from map
                if (heroArray[j].isDead()) {
                    map.removeHeroFromCell(heroArray[j], heroArray[j].getX(), heroArray[j].getY());
                }
            }

            // Moves heros
            for (int j = 0; j < heroNo; ++j) {
                if (heroArray[j].canMove() && !heroArray[j].isDead()) {
                    moveHero(heroArray[j], moves[j]);
                } else {
                    System.out.println("Cannot move " + heroArray[j] + " due to Stun");
                }
            }

            // Checks fights
            FightVisitor fVisitor = new FightVisitor();
            for (int x = 0; x < map.getHeight(); ++x) {
                for (int y = 0; y < map.getWidth(); ++y) {
                    MapCell cell = map.getMapCell(x, y);
                    cell.accept(fVisitor);
                }
            }
            System.out.println("------------------------\n\n");
            ++currentRound;

        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file '" + inputFileName + "'");
        } catch (IOException ex) {
            System.out.println("Error writing file '" + inputFileName + "'");
        }
    }

    @Override
    public void render(final Graphics graph) {
        mapRend.drawMap(graph, map);
        for (int i = 0; i < heroNo; ++i) {
            hRend.drawHero(graph, heroArray[i]);
        }
        graph.drawString("Round " + currentRound, 500, 500);
        
        // TODO moving tombstones
    }

}
