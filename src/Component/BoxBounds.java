package Component;

import java.awt.Graphics2D;
import engine.Component;
import engine.GameObject;
import util.Vector2;
import file.Parser;
import util.Constants;

/**
 * Classe représentant les limites d'une boîte.
 */
public class BoxBounds extends Bounds {
    public float width, height;
    public float halfWidth, halfHeight; 
    public Vector2 center = new Vector2();

    /**
     * Constructeur de la classe BoxBounds.
     * @param width Largeur de la boîte.
     * @param height Hauteur de la boîte.
     */
    public BoxBounds(float width, float height){
        this.width = width;
        this.height = height;
        this.halfWidth = this.width / 2;
        this.halfHeight = this.height / 2;
        this.type = BoundsType.Box;
    }

    /**
     * Calcule le centre de la boîte.
     */
    public void calculateCenter(){
        this.center.x = this.gameObject.transform.position.x + this.halfWidth;
        this.center.y = this.gameObject.transform.position.y + this.halfHeight;
    }

    @Override
    public void start(){
        this.calculateCenter();
    }

    @Override
    public void update(double deltaTime){
        // Méthode vide pour l'instant
    }

    /**
     * Vérifie la collision entre deux boîtes.
     * @param b1 Première boîte.
     * @param b2 Deuxième boîte.
     * @return true si les boîtes sont en collision, false sinon.
     */
    public static boolean checkCollision(BoxBounds b1, BoxBounds b2){
        b1.calculateCenter();
        b2.calculateCenter();

        float dx = b1.center.x - b2.center.x;
        float dy = b1.center.y - b2.center.y;

        float combinedHalfWidth = b1.halfWidth + b2.halfWidth;
        float combinedHalfHeight = b1.halfHeight + b2.halfHeight;

        if (Math.abs(dx) <= combinedHalfWidth){
            return Math.abs(dy) <= combinedHalfHeight;
        }
        return false;
    }

    /**
     * Résout la collision avec un objet de jeu.
     * @param player Objet de jeu représentant le joueur.
     */
    public void resolveCollision(GameObject player){
        BoxBounds playerBounds = player.getComponent(BoxBounds.class);
        playerBounds.calculateCenter();
        this.calculateCenter();

        float dx = this.center.x - playerBounds.center.x;
        float dy = this.center.y - playerBounds.center.y;

        float combinedHalfWidth = this.halfWidth + playerBounds.halfWidth;
        float combinedHalfHeight = this.halfHeight + playerBounds.halfHeight;

        float overlapX = combinedHalfWidth - Math.abs(dx);
        float overlapY = combinedHalfHeight - Math.abs(dy);

        if (overlapX >= overlapY){
            if (dy > 0){
                // Collision par le haut
                player.transform.position.y = gameObject.transform.position.y - playerBounds.getHeight();
                player.getComponent(RigidBody.class).velocity.y = 0;
                player.getComponent(Player.class).onGround = true;
            } else {
                player.getComponent(Player.class).die();
            }
        } else {
            // Collision par les côtés
            if (dx < 0 && dy <= 0.3){
                player.transform.position.y = gameObject.transform.position.y - playerBounds.getHeight();
                player.getComponent(RigidBody.class).velocity.y = 0;
                player.getComponent(Player.class).onGround = true;
            } else {
                player.getComponent(Player.class).die();
            }
        }
    }

    @Override
    public Component copy(){
        return new BoxBounds(width, height);
    }

    @Override
    public float getWidth(){
        return this.width;
    }

    @Override
    public float getHeight(){
        return this.height;
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("BoxBounds", tabSize));
        builder.append(addFloatProperty("Width", width, tabSize + 1, true, true));
        builder.append(addFloatProperty("Height", height, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    /**
     * Désérialise un objet BoxBounds.
     * @return Un nouvel objet BoxBounds.
     */
    public static BoxBounds deserialize() {
        float width = Parser.consumeFloatProperty("Width");
        Parser.consume(',');
        float height = Parser.consumeFloatProperty("Height");
        Parser.consumeEndObjectProperty();

        return new BoxBounds(width, height);
    }
}
