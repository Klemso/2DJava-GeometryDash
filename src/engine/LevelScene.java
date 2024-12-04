package engine;

import java.awt.*;
import java.util.logging.Level;
import javax.swing.Box;
import Component.*;
import engine.Renderer;
import dataStructure.AssetPool;
import dataStructure.Transform;
import file.Parser;
import util.Constants;
import util.Vector2;

/**
 * Scène principale du jeu où le joueur interagit avec les éléments d'un niveau.
 */
public class LevelScene extends Scene {

    /** Instance actuelle de la scène de niveau. */
    static LevelScene currentScene;

    /** Objet représentant le joueur dans la scène. */
    public GameObject player;

    /** Boîte de collision du joueur. */
    public BoxBounds playerBounds;

    /**
     * Constructeur de la scène de niveau.
     *
     * @param name Nom de la scène.
     */
    public LevelScene(String name) {
        super.Scene(name);
    }

    /**
     * Initialise les ressources, les objets et les paramètres de la scène.
     */
    @Override
    public void init() {
        // Initialisation des ressources graphiques
        initAssetPool();

        // Initialisation du joueur
        player = new GameObject("Player", new Transform(new Vector2(500, 350)), 0);
        Spritesheet layerOne = AssetPool.getSpritesheet("assets/player/layerOne.png");
        Spritesheet layerTwo = AssetPool.getSpritesheet("assets/player/layerTwo.png");
        Spritesheet layerThree = AssetPool.getSpritesheet("assets/player/layerThree.png");
        Player playerComp = new Player(
            layerOne.sprites.get(0),
            layerTwo.sprites.get(0),
            layerThree.sprites.get(0),
            Color.RED,
            Color.GREEN
        );
        player.addComponent(playerComp);
        player.addComponent(new RigidBody(new Vector2(Constants.PLAYER_SPEED, 0)));
        player.addComponent(new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        playerBounds = new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        player.addComponent(playerBounds);

        // Ajout du joueur au rendu
        renderer.submit(player);

        // Initialisation des arrière-plans
        initBackgrounds();

        // Chargement du niveau depuis un fichier
        importLevel("Test");
    }

    /**
     * Initialise les arrière-plans et le sol pour la scène.
     */
    public void initBackgrounds() {
        GameObject ground;
        ground = new GameObject("ground", new Transform(new Vector2(0, Constants.GROUND_Y)), 1);
        ground.addComponent(new Ground());
        addGameObject(ground);

        int numBackgrounds = 7;
        GameObject[] backgrounds = new GameObject[numBackgrounds];
        GameObject[] groundBgs = new GameObject[numBackgrounds]; 
        
        Ground groundComponent = ground.getComponent(Ground.class);
        if (groundComponent == null) {
            throw new NullPointerException("Ground component is not initialized.");
        }
        for (int i = 0; i < numBackgrounds; i++) {
        // Arrière-plan
        ParallaxBackground bg = new ParallaxBackground("assets/backgrounds/bg01.png", backgrounds, groundComponent, false);
        if (bg.sprite == null) {
            throw new NullPointerException("Background sprite is not initialized.");
        }
            int x = i * bg.sprite.width;
            int y = 0;

            GameObject go = new GameObject("Background", new Transform(new Vector2(x, y)), -10);
            go.setUi(true);
            go.addComponent(bg);
            backgrounds[i] = go;

            // Sol de l'arrière-plan
            ParallaxBackground groundBg = new ParallaxBackground("assets/grounds/ground01.png", groundBgs, ground.getComponent(Ground.class), true);
            x = i * bg.sprite.width;
            y = bg.sprite.height;

            GameObject groundGo = new GameObject("GroundBg", new Transform(new Vector2(x, y)), -9);
            groundGo.addComponent(groundBg);
            groundGo.setUi(true);
            groundBgs[i] = groundGo;

            // Ajout des objets au jeu
            addGameObject(go);
            addGameObject(groundGo);
        }
    }

    /**
     * Initialise les ressources graphiques (spritesheets) utilisées dans la scène.
     */
    public void initAssetPool() {
        AssetPool.addSpritesheet("assets/player/layerOne.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/player/layerTwo.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/player/layerThree.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/groundSprites.png", 42, 42, 2, 6, 12);
    }

    /**
     * Met à jour les objets et la logique de la scène.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour (en secondes).
     */
    @Override
    public void update(double deltaTime) {
        // Ajuste la position de la caméra par rapport au joueur
        if (player.transform.position.x - camera.position.x > Constants.CAMERA_OFFSET_X) {
            camera.position.x = player.transform.position.x - Constants.CAMERA_OFFSET_X;
        }
        if (player.transform.position.y - camera.position.y > Constants.CAMERA_OFFSET_Y) {
            camera.position.y = player.transform.position.y - Constants.CAMERA_OFFSET_Y;
        }
        if (camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y) {
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        // Met à jour le joueur et vérifie les collisions
        player.update(deltaTime);
        player.getComponent(Player.class).onGround = false;

        for (GameObject g : gameObjects) {
            g.update(deltaTime);

            Bounds b = g.getComponent(Bounds.class);
            if (b != null) {
                if (BoxBounds.checkCollision(playerBounds, b)) {
                    Bounds.resolveCollision(b, player);
                }
            }
        }
    }

    /**
     * Importe un niveau à partir d'un fichier.
     *
     * @param filename Nom du fichier de niveau.
     */
    private void importLevel(String filename) {
        Parser.openFile(filename);

        GameObject go = Parser.parseGameObject();
        while (go != null) {
            addGameObject(go);
            go = Parser.parseGameObject();
        }
    }

    /**
     * Dessine les éléments graphiques de la scène.
     *
     * @param g2 Contexte graphique utilisé pour le rendu.
     */
    @Override
    public void draw(Graphics2D g2) {
        // Dessin du fond de la scène
        g2.setColor(Constants.BG_COLOR);
        g2.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        // Rendu des objets
        renderer.render(g2);
    }
}
