package it.unibo.towerdefense.model.enemies;

import java.util.Set;

import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Class responsible for maintaining all the Enemy currently alive.
 */
interface EnemyCollection extends Observer<RichEnemy> {

    /**
     * Moves the enemies.
     */
    void move();

    /**
     * Adds an Enemy to the collection.
     *
     * @param e the new Enemy.
     */
    void add(RichEnemy e);

    /**
     * Method called by enemies when they die.
     *
     * @param e the dead enemy
     */
    @Override
    void notify(RichEnemy e);

    /**
     * Gets the enemies.
     *
     * @return the enemies
     */
    Set<RichEnemy> getEnemies();

    /**
     * Method to know wheter any Enemy is still alive.
     *
     * @return True if no Enemy is currently alive, False otherwise
     */
    boolean areDead();

    /**
     * Registers an observer to be notified on the death of every enemy.
     *
     * @param o the new observer
     */
    void addDeathObserver(Observer<Enemy> o);
}
