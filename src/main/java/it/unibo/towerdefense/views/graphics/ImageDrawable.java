package it.unibo.towerdefense.views.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.Size;

/**
 * Class that represents a drawable image.
 */
public class ImageDrawable extends Drawable {

    private final Image image;

    /**
     * Constructor from image, position and size.
     * @param image the image to draw
     * @param pos starting position
     * @param size size of the drawable
     */
    public ImageDrawable(final Image image, final Position pos, final Size size) {
        super(pos, size);
        this.image = image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        final Position pos = this.getPosition();
        final Size size = this.getSize();
        // draw image
        g2d.drawImage(
            this.image,
            pos.getX(), pos.getY(),
            size.getWidth(), size.getHeight(),
            null
        );
    }
}
