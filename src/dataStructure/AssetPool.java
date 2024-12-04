package dataStructure;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import file.Serialize;

import Component.Sprite;
import Component.Spritesheet;

public class AssetPool {

    /**
     * Map statique pour stocker les sprites avec leur chemin de fichier comme clé.
     */
    static Map<String, Sprite> sprites = new HashMap<>();
    static Map<String, Spritesheet> spritesheets = new HashMap<>();

    /**
     * Vérifie si un sprite existe dans le pool.
     * 
     * @param pictureFile Le chemin du fichier image.
     * @return true si le sprite existe, sinon false.
     */
    public static boolean hasSprite(String pictureFile) {
        File tmp = new File(pictureFile);
        return AssetPool.sprites.containsKey(tmp.getAbsolutePath());
    }

    /**
     * Vérifie si un spritesheet existe dans le pool.
     * 
     * @param pictureFile Le chemin du fichier image.
     * @return true si le spritesheet existe, sinon false.
     */
    public static boolean hasSpritesheet(String pictureFile) {
        File tmp = new File(pictureFile);
        return AssetPool.spritesheets.containsKey(tmp.getAbsolutePath());
    }

    /**
     * Récupère un sprite à partir du pool. Si le sprite n'existe pas, il est chargé et ajouté au pool.
     * 
     * @param pictureFile Le chemin du fichier image.
     * @return Le sprite correspondant.
     */
    public static Sprite getSprite(String pictureFile) {
        File file = new File(pictureFile);
        if (AssetPool.hasSprite(file.getAbsolutePath())) {
            return AssetPool.sprites.get(file.getAbsolutePath());
        } else {
            Sprite sprite = new Sprite(pictureFile);
            AssetPool.addSprite(pictureFile, sprite);
            return AssetPool.sprites.get(file.getAbsolutePath());
        }    
    }

    /**
     * Récupère un spritesheet à partir du pool. Si le spritesheet n'existe pas, affiche un message d'erreur et termine le programme.
     * 
     * @param pictureFile Le chemin du fichier image.
     * @return Le spritesheet correspondant.
     */
    public static Spritesheet getSpritesheet(String pictureFile){
        File file = new File(pictureFile);
        if (AssetPool.hasSpritesheet(file.getAbsolutePath())){
            return AssetPool.spritesheets.get(file.getAbsolutePath());
        } else {
            System.out.println("Spritesheet " + pictureFile + " not found");
            System.exit(-1);
        }
        return null;
    }

    /**
     * Ajoute un sprite au pool.
     * 
     * @param pictureFile Le chemin du fichier image.
     * @param sprite Le sprite à ajouter.
     */
    public static void addSprite(String pictureFile, Sprite sprite){
        File file = new File(pictureFile);
        if (!AssetPool.hasSprite(file.getAbsolutePath())) {
            AssetPool.sprites.put(file.getAbsolutePath(), sprite);
        } else {
            System.out.println(file.getAbsolutePath() + " already exists");
            System.exit(-1);
        }
    }

    /**
     * Ajoute un spritesheet au pool.
     * 
     * @param pictureFile Le chemin du fichier image.
     * @param tileWidth La largeur des tuiles.
     * @param tileHeight La hauteur des tuiles.
     * @param spacing L'espacement entre les tuiles.
     * @param columns Le nombre de colonnes.
     * @param size La taille du spritesheet.
     */
    public static void addSpritesheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size){
        File file = new File(pictureFile);
        if (!AssetPool.hasSpritesheet(file.getAbsolutePath())){
            Spritesheet spritesheet = new Spritesheet(pictureFile, tileWidth, tileHeight, spacing, columns, size);
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }
}
