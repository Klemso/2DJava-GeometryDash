 package util;

import java.awt.Color;


/**
 * La classe Constants contient toutes les constantes utilisées dans le jeu Geometry Dash.
 * Ces constantes incluent les dimensions de la fenêtre, les propriétés du joueur, 
 * les paramètres de la caméra, les propriétés de la gravité et les couleurs.
 */
public class Constants {

    /**
     * Largeur de la fenêtre du jeu en pixels.
     */
    public static final int WIDTH = 1280;

    /**
     * Hauteur de la fenêtre du jeu en pixels.
     */
    public static final int HEIGHT = 720;

    /**
     * Titre de la fenêtre du jeu.
     */
    public static final String TITLE = "Geometry Dash";

    /**
     * Fréquence d'images par seconde (FPS) du jeu.
     */
    public static final int FPS = 60;

    /**
     * Largeur du joueur en pixels.
     */
    public static final int PLAYER_WIDTH = 42;

    /**
     * Hauteur du joueur en pixels.
     */
    public static final int PLAYER_HEIGHT = 42;

    /**
     * Force de saut du joueur.
     */
    public static final float JUMP_FORCE = -650;

    /**
     * Vitesse du joueur en pixels par seconde.
     */
    public static final float PLAYER_SPEED = 395;

    /**
     * Position Y du sol en pixels.
     */
    public static final int GROUND_Y = 714;

    /**
     * Décalage X de la caméra par rapport au joueur.
     */
    public static final int CAMERA_OFFSET_X = 300;

    /**
     * Décalage Y de la caméra par rapport au joueur.
     */
    public static final int CAMERA_OFFSET_Y = 325;

    /**
     * Décalage Y de la caméra par rapport au sol.
     */
    public static final int CAMERA_OFFSET_GROUND_Y = 150;

    /**
     * Gravité appliquée au joueur en pixels par seconde carré.
     */
    public static final float GRAVITY = 2850;

    /**
     * Vitesse maximale du joueur en pixels par seconde.
     */
    public static final float MAX_VELOCITY = 1900;

    /**
     * Largeur d'une tuile en pixels.
     */
    public static final int TILE_WIDTH = 42;

    /**
     * Hauteur d'une tuile en pixels.
     */
    public static final int TILE_HEIGHT = 42;

    /**
     * Décalage X des boutons par rapport à l'origine.
     */
    public static final int BUTTON_OFFSET_X = 400;

    /**
     * Décalage Y des boutons par rapport à l'origine.
     */
    public static final int BUTTON_OFFSET_Y = 560;

    /**
     * Espacement horizontal entre les boutons en pixels.
     */
    public static final int BUTTON_SPACING_HZ = 10;

    /**
     * Espacement vertical entre les boutons en pixels.
     */
    public static final int BUTTON_SPACING_VT = 5;

    /**
     * Largeur d'un bouton en pixels.
     */
    public static final int BUTTON_WIDTH = 60;

    /**
     * Hauteur d'un bouton en pixels.
     */
    public static final int BUTTON_HEIGHT = 60;

    /**
     * Couleur de fond du jeu.
     */
    public static final Color BG_COLOR = new Color(15.0f / 255.0f, 98.0f / 255.0f, 212.0f / 255.0f, 1.0f);

    /**
     * Couleur du sol du jeu.
     */
    public static final Color GROUND_COLOR = new Color(28.0f / 255.0f, 70.0f / 255.0f, 148.0f / 255.0f, 1.0f);

    public static final int BG_WIDTH = 512;
    public static final int BG_HEIGHT = 512;
    public static final int GROUND_BG_WIDTH = 256;
    public static final int GROUND_BG_HEIGHT = 256;
}