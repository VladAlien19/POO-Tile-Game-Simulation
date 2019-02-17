package tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    // TODO Dead texture, fix rogue texture

    // Heroes
    public static BufferedImage heroKnightDown[];
    public static BufferedImage heroKnightLeft[];
    public static BufferedImage heroKnightRight[];
    public static BufferedImage heroKnightUp[];

    public static BufferedImage heroPyroDown[];
    public static BufferedImage heroPyroLeft[];
    public static BufferedImage heroPyroRight[];
    public static BufferedImage heroPyroUp[];

    public static BufferedImage heroRogueDown[];
    public static BufferedImage heroRogueLeft[];
    public static BufferedImage heroRogueRight[];
    public static BufferedImage heroRogueUp[];

    public static BufferedImage heroWizardDown[];
    public static BufferedImage heroWizardLeft[];
    public static BufferedImage heroWizardRight[];
    public static BufferedImage heroWizardUp[];

    // Land types
    public static BufferedImage landLand;
    public static BufferedImage landVolcanic;
    public static BufferedImage landDesert;
    public static BufferedImage landWoods;
    
    // Death icon
    public static BufferedImage dead;

    private static final int FRAMES = 4;
    
    private static String TEXTURE_PATH = "res/textures/";

    private static void initHeroTextures(SpriteSheet sheet, BufferedImage[] down,
            BufferedImage[] left, BufferedImage[] right, BufferedImage[] up, int offset) {

        BufferedImage[][] matrix = { down, left, right, up };

        // First three frames
        for (int i = 0; i < FRAMES - 1; ++i) {
            for (int j = 0; j < FRAMES; ++j) {
                matrix[j][i] = sheet.crop(Constants.heroWidth * (i + offset),
                        Constants.heroHeight * j, Constants.heroWidth, Constants.heroHeight);
            }
        }

        // Last frame / standing position
        for (int j = 0; j < FRAMES; ++j) {
            matrix[j][3] = sheet.crop(Constants.heroWidth * (offset + 1), Constants.heroHeight * j,
                    Constants.heroWidth, Constants.heroHeight);
            ;
        }
    }

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(
                ImageLoader.loadImage(TEXTURE_PATH + "hero_sheet.png"));

        // Load knight textures
        heroKnightDown = new BufferedImage[FRAMES];
        heroKnightLeft = new BufferedImage[FRAMES];
        heroKnightRight = new BufferedImage[FRAMES];
        heroKnightUp = new BufferedImage[FRAMES];

        initHeroTextures(sheet, heroKnightDown, heroKnightLeft, heroKnightRight, heroKnightUp, 3);
      
        // Load pyro textures
        heroPyroDown = new BufferedImage[FRAMES];
        heroPyroLeft = new BufferedImage[FRAMES];
        heroPyroRight = new BufferedImage[FRAMES];
        heroPyroUp = new BufferedImage[FRAMES];

        initHeroTextures(sheet, heroPyroDown, heroPyroLeft, heroPyroRight, heroPyroUp, 0);

        // Load rogue textures
        heroRogueDown = new BufferedImage[FRAMES];
        heroRogueLeft = new BufferedImage[FRAMES];
        heroRogueRight = new BufferedImage[FRAMES];
        heroRogueUp = new BufferedImage[FRAMES];
        
        initHeroTextures(sheet, heroRogueDown, heroRogueLeft, heroRogueRight, heroRogueUp, 6);

        // Load wizard textures
        heroWizardDown = new BufferedImage[FRAMES];
        heroWizardLeft = new BufferedImage[FRAMES];
        heroWizardRight = new BufferedImage[FRAMES];
        heroWizardUp = new BufferedImage[FRAMES];

        initHeroTextures(sheet, heroWizardDown, heroWizardLeft, heroWizardRight, heroWizardUp, 9);

        // Load tiles textures
        sheet = new SpriteSheet(ImageLoader.loadImage(TEXTURE_PATH + "land_sprite_sheet.png"));

        landLand = sheet.crop(Constants.tileSize * 0, Constants.tileSize * 1, Constants.tileSize,
                Constants.tileSize);
        landVolcanic = sheet.crop(Constants.tileSize * 1, Constants.tileSize * 1,
                Constants.tileSize, Constants.tileSize);
        landDesert = sheet.crop(Constants.tileSize * 1, Constants.tileSize * 0, Constants.tileSize,
                Constants.tileSize);
        landWoods = sheet.crop(Constants.tileSize * 0, Constants.tileSize * 0, Constants.tileSize,
                Constants.tileSize);
        
        // Load dead hero texture
        dead = ImageLoader.loadImage(TEXTURE_PATH + "dead.png");
    }
}
