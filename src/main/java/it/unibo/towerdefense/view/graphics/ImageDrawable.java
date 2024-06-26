package it.unibo.towerdefense.view.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;

/**
 * Class that represents a drawable image.
 */
public class ImageDrawable extends Drawable {

    @SuppressFBWarnings(
        value = "EI2",
        justification = "Image is intentionally mutable and safe to store."
    )
    private final Image image;

    /**
     * Constructor from Image and LogicalPosition.
     * @param image the image to draw
     * @param pos position where to draw
     */
    public ImageDrawable(final Image image, final LogicalPosition pos) {
        super(pos, Size.of(image.getWidth(null), image.getHeight(null)));
        this.image = image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        final Position pos = this.getPosition();
        // draw image
        g2d.drawImage(
            this.image,
            pos.getX(), pos.getY(),
            null
        );
    }
}
