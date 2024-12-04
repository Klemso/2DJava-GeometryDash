package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

import util.Constants;
import util.Time;

/**
 * La classe principale de la fenêtre du jeu, responsable de l'affichage, de la gestion des scènes
 * et de la boucle principale du jeu.
 */
public class Window extends JFrame implements Runnable {
    /** Instance unique de la classe Window (Singleton). */
    private static Window window = null;

    /** Indique si la boucle du jeu est en cours d'exécution. */
    private boolean isRunning = true;

    /** La scène actuellement active (éditeur ou niveau de jeu). */
    private Scene currentScene = null;

    /** Image utilisée pour le double buffering (pour éviter le scintillement). */
    private Image doubleBufferImage = null;

    /** Graphics pour le double buffering. */
    private Graphics doubleBufferGraphics = null;

    /** Gestionnaire des événements de souris. */
    public ML mouseListener;

    /** Gestionnaire des événements clavier. */
    public KL keyListener;

    /** Indique si l'utilisateur est dans l'éditeur de niveau. */
    public boolean isInEditor = true;

    /** Constructeur de la classe Window. Initialise la fenêtre et les gestionnaires d'événements. */
    public Window() {
        this.mouseListener = new ML();
        this.keyListener = new KL();

        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.setTitle(Constants.TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setLocationRelativeTo(null);
    }

    /**
     * Initialise la fenêtre en lançant la première scène.
     */
    public void init() {
        changeScene(0);
    }

    /**
     * Retourne la scène actuellement active.
     *
     * @return La scène active.
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Change la scène active en fonction de l'index fourni.
     *
     * @param scene L'index de la scène à charger (0 pour l'éditeur, 1 pour le niveau de jeu).
     */
    public void changeScene(int scene) {
        switch (scene) {
            case 0:
                isInEditor = true;
                currentScene = new LevelEditorScene("Level Editor");
                currentScene.init();
                break;
            case 1:
                isInEditor = false;
                currentScene = new LevelScene("Level");
                currentScene.init();
                break;
            default:
                System.out.println("Invalid scene");
                currentScene = null;
                break;
        }
    }

    /**
     * Retourne l'instance unique de la fenêtre (Singleton).
     *
     * @return L'instance unique de la fenêtre.
     */
    public static Window getWindow() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    /**
     * Met à jour l'état de la fenêtre et de la scène active.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    public void update(double deltaTime) {
        draw(getGraphics());
        currentScene.update(deltaTime);
    }

    /**
     * Dessine le contenu de la scène active à l'écran en utilisant le double buffering.
     *
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void draw(Graphics g) {
        if (doubleBufferImage == null) {
            doubleBufferImage = createImage(getWidth(), getHeight());
            doubleBufferGraphics = doubleBufferImage.getGraphics();
        }

        renderOffScreen(doubleBufferGraphics);
        g.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * Effectue le rendu de la scène active dans un buffer hors écran.
     *
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void renderOffScreen(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        currentScene.draw(g2);
    }

    /**
     * La méthode principale de la boucle du jeu. Exécute des mises à jour en continu
     * jusqu'à ce que le jeu soit arrêté.
     */
    @Override
    public void run() {
        double lastFrameTime = 0.0;
        try {
            while (isRunning) {
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;

                update(deltaTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
