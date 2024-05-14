package it.unibo.towerdefense.views;

import javax.swing.JPanel;

/**
 * Basic GameView interface.
 */
public interface GameView {

    /**
     * Returns whether the view should be re-rendered.
     * @return true if the view should be rendered, false otherwise.
     */
    boolean shouldRender();

    /**
     * Builds the View and returns its JPanel.
     * @return the JPanel with the view's content.
     */
    JPanel build();
}
