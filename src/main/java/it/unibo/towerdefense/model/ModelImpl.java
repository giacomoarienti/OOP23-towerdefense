package it.unibo.towerdefense.model;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.game.ControlAction;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.defenses.DefenseManagerImpl;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.enemies.EnemiesManagerImpl;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.game.GameManagerImpl;
import it.unibo.towerdefense.model.game.GameStatus;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.MapManagerImpl;
import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.model.saving.SavingFieldsEnum;
import it.unibo.towerdefense.model.saving.SavingImpl;
import it.unibo.towerdefense.model.saving.SavingsImpl;
import it.unibo.towerdefense.model.score.Score;
import it.unibo.towerdefense.model.scoreboard.ScoreboardImpl;

/**
 * Implementation of the ModelManager interface.
 */
public class ModelImpl implements ModelManager, Model {

    private MapManager map;
    private DefenseManager defenses;
    private EnemiesManager enemies;
    private GameManager game;
    private boolean initialized;
    private Saving saving;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final String playerName, final Size cellSize) {
        // init model managers
        map = new MapManagerImpl(cellSize);
        defenses = new DefenseManagerImpl();
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(playerName);
        // bind managers
        this.bindManagers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Saving s) {
        // init model managers
        map = new MapManagerImpl(s.getMapJson());
        defenses = new DefenseManagerImpl(s.getDefensesJson());
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(
            GameDTO.fromJson(s.getGameJson())
        );
        // save the saving
        this.saving = s;
        // bind managers
        this.bindManagers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapManager getMap() {
        if (Objects.isNull(map)) {
            throw new IllegalStateException("Map not initialized");
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefenseManager getDefenses() {
        if (Objects.isNull(defenses)) {
            throw new IllegalStateException("Defenses not initialized");
        }
        return defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnemiesManager getEnemies() {
        if (Objects.isNull(enemies)) {
            throw new IllegalStateException("Enemies not initialized");
        }
        return enemies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(
        value = "EI",
        justification = "Safe to return internal representation of game"
    )
    public GameManager getGame() {
        if (Objects.isNull(game)) {
            throw new IllegalStateException("Game not initialized");
        }
        return game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaying() {
        return game.isPlaying();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        game.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGameObserver(final Observer<GameDTO> observer) {
        game.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    public Stream<EnemyInfo> getEnemiesDTOs() {
        return enemies.getEnemies().stream().map(e -> e.info());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<DefenseDescription> getDefensesDTOs() {
        return defenses.getDefenses().stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<CellInfo> getMapDTOs() {
        return map.getMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuildingOption> getBuildingOptions() {
        return map.getBuildingOptions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectCell(final Position position) {
        map.select(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build(final int index) {
        map.build(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startWave() {
        this.game.startWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.initializationCheck();
        // update the models
        game.update();
        enemies.update();
        defenses.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleControls(final ControlAction action) {
        this.initializationCheck();
        game.setGameStatus(GameStatus.fromControlAction(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return game.isGameOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Score saveScore() {
        try {
            final var scoreboard = new ScoreboardImpl();
            return scoreboard.saveScore(game.getPlayerName(), game.getWave());
        } catch (final IOException e) {
           throw new UncheckedIOException("Error saving score", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        // create json saving map
        final var json = Map.of(
            SavingFieldsEnum.GAME, game.toJSON(),
            SavingFieldsEnum.MAP, map.toJSON(),
            SavingFieldsEnum.DEFENSES, defenses.toJSON()
        );
        if (Objects.isNull(saving)) {
            // create saving
            this.saving = new SavingImpl(json);
        } else {
            // update saving
            this.saving = new SavingImpl(
                json,
                saving.getDate()
            );
        }
        // create savingloader
        try {
            final var savingLoader = new SavingsImpl(game.getPlayerName());
            savingLoader.writeSaving(this.saving);
        } catch (final IOException e) {
            throw new UncheckedIOException("Error saving game", e);
        }
    }

    private void bindManagers() {
        game.bind(this);
        map.bind(this);
        defenses.bind(this);
        enemies.bind(this);
        // set initialized to true
        initialized = true;
    }

    private void initializationCheck() {
        if (!initialized) {
            throw new IllegalStateException("Model not initialized");
        }
    }
}
