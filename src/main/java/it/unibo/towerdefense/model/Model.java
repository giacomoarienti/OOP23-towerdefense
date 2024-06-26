package it.unibo.towerdefense.model;

import java.util.List;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.game.ControlAction;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.saves.Save;
import it.unibo.towerdefense.model.scoreboard.Score;

/**
 * Interface that represents the main model.
 * This interface should be used by the Controller since
 * it hides the implementation details of the model.
 */
public interface Model {

    /**
     * Initialize the model.
     * @param playerName the player name
     * @param mapSize the size of the map
     */
    void init(String playerName, Size mapSize);

    /**
     * Initialize the model from a save object.
     * @param save the save object
     */
    void init(Save save);

    /**
     * Checks if the game is playing.
     * @return true if the game is playing
     */
    boolean isPlaying();

    /**
     * Resume the game.
     */
    void resume();

    /**
     * Adds an observer to the game model.
     * @param observer the observer to add
     */
    void addGameObserver(Observer<GameDTO> observer);

    /**
     * Returns a stream of dtos for the currently alive enemies.
     * @return a stream of dtos for the currently alive enemies
     */
    Stream<EnemyInfo> getEnemiesDTOs();

    /**
     * Returns a stream of dtos for the currently built defenses.
     * @return a stream of dtos for the currently built defenses
     */
    Stream<DefenseDescription> getDefensesDTOs();

    /**
     * Returns a stream of dtos for the map cells.
     * @return a stream of dtos for the map cells
     */
    Stream<CellInfo> getMapDTOs();

    /**
     * Return the possible building options in the selected Cell.
     * @return list of options
     */
    List<BuildingOption> getBuildingOptions();

    /**
     * Select the cell at given position.
     * @param position position of cell.
     */
    void selectCell(Position position);

    /**
     * Starts the first wave.
     */
    void startWave();

    /**
     * Updates the model.
     */
    void update();

    /**
     * Builds a defense at the given cell.
     * @param index the index  cell where to build the defense
     */
    void build(int index);

    /**
     * Handles the controls.
     * @param action the control action
     */
    void handleControls(ControlAction action);

    /**
     * Returns if the game is over.
     * @return true if the game is over
     */
    boolean isGameOver();

    /**
     * Saves the score.
     * @return the saved score
     */
    Score saveScore();

    /**
     * Saves the game.
     */
    void save();
}
