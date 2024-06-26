package it.unibo.towerdefense.model.game;

import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observable;
import it.unibo.towerdefense.model.Manager;

/**
 * Model containing base game's statistics and info.
 */
public interface GameManager extends Manager, Observable<GameDTO>, JsonSerializable {

    /**
     * Player name getter.
     * @return the player's name
     */
    String getPlayerName();

    /**
     * Lives getter.
     * @return the amount of lives of the player
     */
    int getLives();

    /**
     * Decrease by 1 the number of lives of the player.
     * @return true if number of lives is greater then 0
     */
    boolean decreaseLives();

    /**
     * Money getter.
     * @return the amount of money the player has
     */
    int getMoney();

    /**
     * Increases the amount of money by amount .
     * @param amount quantity of money to be increased
     */
    void addMoney(int amount);

    /**
     * Decrease the player's money by amount .
     * @param amount quantity of money to be decreased
     * @return true if the operation was successful
     */
    boolean purchase(int amount);

    /**
     * Check if the player has enough money to buy something.
     * @param amount the amount of money to be checked
     * @return true if the player has enough money
     */
    boolean isPurchasable(int amount);

    /**
     * Check if the game is playing.
     * @return true if the game is RUNNING
     */
    boolean isPlaying();

    /**
     * Wave getter.
     * @return the wave number
     */
    int getWave();

    /**
     * Advances to the next wave.
     */
    void advanceWave();

    /**
     * GameStatus getter.
     * @return the state of the game
     */
    GameStatus getGameStatus();

    /**
     * Sets gameStatus to PLAYING.
     */
    void resume();

    /**
     * Sets gameStatus to PAUSE.
     */
    void pause();

    /**
     * Update the current game state.
     * @param state the new game state
     */
    void setGameStatus(GameStatus state);

    /**
     * Getter game speed.
     * @return current game speed
     */
    double getGameSpeed();

    /**
     * Starts the first wave.
     */
    void startWave();

    /**
     * Returns the DTO representation of the Game object.
     * @return the GameDTO object
     */
    GameDTO toDTO();

    /**
     * Called on every cycle, updates the model.
     *
     * Will call EnemyManager.advanceWave() if the current wave is over.
     */
    void update();

    /**
     * Check if the game is over.
     * @return true if the game is over
     */
    boolean isGameOver();

    /**
     * Returns the Game object from a GameDTO object.
     * @param dto the GameDTO object
     * @return the Game object
     */
    static GameManager fromDTO(final GameDTO dto) {
        return new GameManagerImpl(dto);
    }
}
