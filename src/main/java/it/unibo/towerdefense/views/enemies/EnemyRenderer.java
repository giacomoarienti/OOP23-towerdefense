package it.unibo.towerdefense.views.enemies;

import java.util.List;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Renders enemies on the screen.
 */
public interface EnemyRenderer {
    /**
     * Renders the enemies given as argument.
     * @param gameRenderer the renderer on which to render
     * @param enemies the enemies to render
     */
    void render(GameRenderer gameRenderer, List<EnemyInfo> enemies);
}
