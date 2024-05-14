package it.unibo.towerdefense.model;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.defenses.DefenseManagerImpl;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.enemies.EnemiesManagerImpl;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.game.GameManagerImpl;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.MapManagerImpl;

public class ModelManagerImpl implements ModelManager {

    private final MapManager map;
    private final DefenseManager defenses;
    private final EnemiesManager enemies;
    private final GameManager game;

    public ModelManagerImpl(final Size cellSize, final String playerName){
        map = new MapManagerImpl(cellSize);
        defenses = new DefenseManagerImpl(null);
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(null);
        map.bind(this);
        defenses.bind(this);
        enemies.bind(this);
    }

    public ModelManagerImpl(final Saving s){
        map = new MapManagerImpl(null);
        defenses = new DefenseManagerImpl(null, null);
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(null);
        map.bind(this);
        defenses.bind(this);
        enemies.bind(this);
    }

    @Override
    public MapManager getMap() {
        return map;
    }

    @Override
    public DefenseManager getDefenses() {
        return defenses;
    }

    @Override
    public Enemies getEnemies() {
        return enemies;
    }

    @Override
    public GameManager getGame() {
        return game;
    }
}
