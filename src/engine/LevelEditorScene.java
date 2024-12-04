package engine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;

import Component.*;
import dataStructure.AssetPool;
import dataStructure.Transform;
import file.Parser;
import ui.MainContainer;
import util.Constants;
import util.Vector2;

/**
 * Scène de l'éditeur de niveau, permettant de concevoir et de manipuler les éléments
 * d'un niveau avant de les sauvegarder ou de les charger.
 */
public class LevelEditorScene extends Scene {

    /** Le joueur dans la scène. */
    public GameObject player;

    /** Le sol dans la scène. */
    private GameObject ground;

    /** Grille d'édition affichée dans l'éditeur. */
    private Grid grid;

    /** Contrôles de la caméra. */
    private CameraControls cameraControls;

    /** Curseur utilisé pour placer des éléments dans l'éditeur. */
    public GameObject mouseCursor;

    /** Conteneur pour les boutons d'édition dans l'interface utilisateur. */
    private MainContainer editingButtons;

    /**
     * Constructeur de la scène de l'éditeur de niveaux.
     *
     * @param name Nom de la scène.
     */
    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    /**
     * Initialise les ressources et les objets nécessaires à la scène.
     */
    @Override
    public void init() {
        initAssetPool();
        editingButtons = new MainContainer();

        grid = new Grid();
        cameraControls = new CameraControls();
        editingButtons.start();

        // Initialisation du curseur
        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector2()), 10);
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));

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

        // Initialisation du sol
        ground = new GameObject("ground", new Transform(new Vector2(0, Constants.GROUND_Y)), 1);
        ground.addComponent(new Ground());

        ground.setNonserializable();
        player.setNonserializable();
        addGameObject(player);
        addGameObject(ground);
    }

    /**
     * Initialise les ressources graphiques (spritesheets) dans le pool d'actifs.
     */
    public void initAssetPool() {
        AssetPool.addSpritesheet("assets/player/layerOne.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/player/layerTwo.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/player/layerThree.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpritesheet("assets/groundSprites.png", 42, 42, 2, 6, 12);
        AssetPool.addSpritesheet("assets/ui/buttonSprites.png", 60, 60, 2, 2, 2);
    }

    /**
     * Met à jour les objets et les interactions dans la scène.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour (en secondes).
     */
    @Override
    public void update(double deltaTime) {
        // Contrôle de la position de la caméra
        if (camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y) {
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        // Mise à jour des objets de la scène
        for (GameObject g : gameObjects) {
            g.update(deltaTime);
        }

        // Mise à jour des contrôles et de l'interface
        cameraControls.update(deltaTime);
        grid.update(deltaTime);
        editingButtons.update(deltaTime);
        mouseCursor.update(deltaTime);

        // Gestion des entrées clavier
        if (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_S)) {
            export("Test");
        } else if (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_L)) {
            importLevel("Test");
        } else if (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_E)) {
            Window.getWindow().changeScene(1);
        }
    }

    /**
     * Importe un niveau depuis un fichier JSON.
     *
     * @param filename Nom du fichier à importer.
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
     * Exporte le niveau actuel vers un fichier compressé (ZIP).
     *
     * @param filename Nom du fichier de sortie.
     */
    private void export(String filename) {
        try {
            File levelsDir = new File("levels");
            if (!levelsDir.exists()) {
                levelsDir.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream("levels/" + filename + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            zos.putNextEntry(new ZipEntry(filename + ".json"));

            int i = 0;
            for (GameObject go : gameObjects) {
                String str = go.serialize(0);
                if (!str.isEmpty()) {
                    zos.write(str.getBytes());
                    if (i != gameObjects.size() - 1) {
                        zos.write("\n".getBytes());
                    }
                }
                i++;
            }

            zos.closeEntry();
            zos.close();
            fos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Dessine la scène et ses éléments graphiques.
     *
     * @param g2 Contexte graphique utilisé pour le rendu.
     */
    @Override
    public void draw(Graphics2D g2) {
        // Fond de la scène
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        // Rendu des éléments
        renderer.render(g2);
        grid.draw(g2);
        editingButtons.draw(g2);
        mouseCursor.draw(g2);
    }
}
