package Component;

import java.util.List;
import dataStructure.AssetPool;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Représente une spritesheet, une image contenant plusieurs sprites organisés
 * en grille, utilisée pour les jeux vidéo.
 */
public class Spritesheet {

    /** Liste des sprites extraits de la spritesheet. */
    public List<Sprite> sprites;

    /** Largeur des tuiles (tiles) dans la spritesheet. */
    public int tileWidth;

    /** Hauteur des tuiles (tiles) dans la spritesheet. */
    public int tileHeight;

    /** Espacement entre les tuiles dans la spritesheet. */
    public int spacing;

    /**
     * Crée une spritesheet à partir d'une image et des dimensions de chaque tuile.
     * 
     * @param pictureFile Chemin du fichier de l'image de la spritesheet.
     * @param tileWidth Largeur d'une tuile.
     * @param tileHeight Hauteur d'une tuile.
     * @param spacing Espacement entre les tuiles.
     * @param columns Nombre de colonnes dans la spritesheet.
     * @param size Nombre total de sprites à extraire.
     */
    public Spritesheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size) {
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.spacing = spacing;

        Sprite parent = AssetPool.getSprite(pictureFile);
        sprites = new ArrayList<>();
        int row = 0;
        int count = 0;

        // Parcours et extraction des sprites depuis la spritesheet
        while (count < size) {
            for (int i = 0; i < columns; i++) {
                if (count >= size) {
                    break;
                }
                int imgX = i * (tileWidth + spacing);
                int imgY = row * (tileHeight + spacing);

                sprites.add(new Sprite(parent.image.getSubimage(imgX, imgY, tileWidth, tileHeight), row, i, count, pictureFile));
                count++;

                if (count > size - 1) {
                    break;
                }
            }
            row++;
        }
    }

    /**
     * Récupère un sprite spécifique dans la spritesheet.
     * 
     * @param index L'index du sprite dans la liste.
     * @return Le sprite correspondant à l'index.
     */
    public Sprite getSprite(int index) {
        return sprites.get(index);
    }
}
