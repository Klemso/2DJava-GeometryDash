package Component;

import dataStructure.AssetPool;
import engine.Component;
import file.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Représente un sprite, une image 2D utilisée dans le jeu.
 * Cette classe permet de charger des images, de gérer leurs dimensions 
 * et de les dessiner à l'écran.
 */
public class Sprite extends Component {

    /** L'image associée au sprite. */
    public BufferedImage image;

    /** Le chemin du fichier de l'image. */
    public String pictureFile;

    /** Largeur et hauteur du sprite. */
    public int width, height;

    /** Indique si le sprite est une sous-image d'une spritesheet. */
    public boolean isSubsprite = false;

    /** Ligne et colonne de la sous-image dans une spritesheet, ainsi que son index. */
    public int row, column, index;

    /**
     * Constructeur principal pour créer un sprite à partir d'un fichier image.
     * 
     * @param pictureFile Chemin du fichier image à charger.
     * @throws RuntimeException Si un sprite avec ce fichier existe déjà.
     * @throws IOException Si le fichier image n'existe pas ou ne peut pas être lu.
     */
    public Sprite(String pictureFile) {
        this.pictureFile = pictureFile;

        try {
            File file = new File(pictureFile);

            if (AssetPool.hasSprite(pictureFile)) {
                throw new RuntimeException("Asset already exists: " + pictureFile);
            }

            if (!file.exists()) {
                throw new IOException("File not found: " + file.getAbsolutePath());
            }

            this.image = ImageIO.read(file);
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Constructeur pour créer un sprite à partir d'une image déjà chargée.
     * 
     * @param image L'image à utiliser pour ce sprite.
     * @param pictureFile Chemin associé au fichier image (facultatif).
     */
    public Sprite(BufferedImage image, String pictureFile) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pictureFile = pictureFile;
    }

    /**
     * Constructeur pour créer un sprite en tant que sous-image d'une spritesheet.
     * 
     * @param image L'image de la spritesheet.
     * @param row Ligne de la sous-image.
     * @param column Colonne de la sous-image.
     * @param index Index de la sous-image dans la spritesheet.
     * @param pictureFile Chemin associé au fichier de la spritesheet.
     */
    public Sprite(BufferedImage image, int row, int column, int index, String pictureFile) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.row = row;
        this.column = column;
        this.index = index;
        this.isSubsprite = true;
        this.pictureFile = pictureFile;
    }

    /**
     * Dessine le sprite à l'écran.
     * 
     * @param g2 Le contexte graphique utilisé pour dessiner.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, (int) gameObject.transform.position.x, 
                    (int) gameObject.transform.position.y,
                    width, height, null);
    }

    /**
     * Crée une copie du sprite.
     * 
     * @return Une nouvelle instance identique de ce sprite.
     */
    @Override
    public Component copy() {
        if (!isSubsprite)
            return new Sprite(this.image, pictureFile);
        else
            return new Sprite(this.image, this.row, this.column, this.index, pictureFile);
    }

    /**
     * Sérialise le sprite en une chaîne de caractères.
     * 
     * @param tabSize Taille de l'indentation pour la sérialisation.
     * @return Une chaîne représentant l'état du sprite.
     */
    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Sprite", tabSize));
        builder.append(addBooleanProperty("isSubsprite", isSubsprite, tabSize + 1, true, true));

        if (isSubsprite) {
            builder.append(addStringProperty("FilePath", pictureFile, tabSize + 1, true, true));
            builder.append(addIntProperty("row", row, tabSize + 1, true, true));
            builder.append(addIntProperty("column", column, tabSize + 1, true, true));
            builder.append(addIntProperty("index", index, tabSize + 1, true, false));
            builder.append(closeObjectProperty(tabSize));

            return builder.toString();
        }

        builder.append(addStringProperty("FilePath", pictureFile, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));
        return builder.toString();
    }

    /**
     * Désérialise un sprite à partir de données préalablement sérialisées.
     * 
     * @return Une instance de sprite recréée à partir des données.
     */
    public static Sprite deserialize() {
        boolean isSubsprite = Parser.consumeBooleanProperty("isSubsprite");
        Parser.consume(',');
        String filePath = Parser.consumeStringProperty("FilePath");

        if (isSubsprite) {
            Parser.consume(',');
            Parser.consumeIntProperty("row");
            Parser.consume(',');
            Parser.consumeIntProperty("column");
            Parser.consume(',');
            int index = Parser.consumeIntProperty("index");
            if (!AssetPool.hasSpritesheet(filePath)) {
                System.out.println("Spritesheet '" + filePath + "' has not been loaded yet!");
                System.exit(-1);
            }
            Parser.consumeEndObjectProperty();
            return (Sprite) AssetPool.getSpritesheet(filePath).sprites.get(index).copy();
        }

        if (!AssetPool.hasSprite(filePath)) {
            System.out.println("Sprite '" + filePath + "' has not been loaded yet!");
            System.exit(-1);
        }

        Parser.consumeEndObjectProperty();
        return (Sprite) AssetPool.getSprite(filePath).copy();
    }
}
