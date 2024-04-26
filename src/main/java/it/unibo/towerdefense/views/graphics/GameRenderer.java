package it.unibo.towerdefense.views.graphics;

import java.util.List;

import it.unibo.towerdefense.views.View;

/**
 * Interface that models the game renderer.
 * It acts as a Proxy for the Window class.
 */
public interface GameRenderer {

    /**
     * Adds view contents to the info panel.
     * @param view the view to render
     */
    void renderInfo(View view);

    /**
     * Adds view contents to the buy menu panel.
     * @param view the view to render
     */
    void renderBuyMenu(View view);

    /**
     * Adds view contents to the upgrades panel.
     * @param view the view to render
     */
    void renderUpgrades(View view);

    /**
     * Adds a drawable object to the canvas.
     * @param drawable the drawable object to paint
     */
    void submitToCanvas(Drawable drawable);

    /**
     * Adds a list of drawable objects to the canvas.
     * @param drawables the drawable objects list to paint
     */
    void submitAllToCanvas(List<Drawable> drawables);

    /**
     * Forces the canvas to be rendered.
     */
    void renderCanvas();
}