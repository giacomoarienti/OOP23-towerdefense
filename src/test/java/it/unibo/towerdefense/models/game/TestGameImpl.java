package it.unibo.towerdefense.models.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the GameImpl class.
 */
class TestGameImpl {

    private static final int INVALID_MONEY_AMOUNT = -1;
    private static final int VALID_MONEY_AMOUNT = 10;
    private static final int INITIAL_WAVE = 1;
    private static final int INITIAL_LIVES = 100;
    private static final int INITIAL_MONEY = 100;
    private static final GameState PAUSE_STATE = GameState.PAUSE;

    private Game game;

    /**
     * Configuration step: this is performed BEFORE each test.
     * @throws IOException if the file cannot be created
     */
    @BeforeEach
    void setUp() throws IOException {
        this.game = new GameImpl();
    }

    /**
     * Test the decreaseLives method.
     */
    @Test
    void testDecreaseLives() {
        final int init_lives = this.game.getLives();
        IntStream.range(1 ,init_lives).forEach((i) -> {
            System.out.println(i);
            Assertions.assertTrue(this.game.decreaseLives());
            Assertions.assertEquals(init_lives - i, this.game.getLives());
        });
        // make sure at next call it will return false, as the lives are 0
        Assertions.assertFalse(this.game.decreaseLives());
    }

    /**
     * Test the addMoney and purchase methods.
     */
    @Test
    void testMoney() {
        int money = this.game.getMoney();
        // add money errors
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> this.game.addMoney(INVALID_MONEY_AMOUNT)
        );
        // correct add money
        this.game.addMoney(VALID_MONEY_AMOUNT);
        money += 10;
        Assertions.assertEquals(money, this.game.getMoney());
        // purchase errors
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> this.game.purchase(INVALID_MONEY_AMOUNT)
        );
        Assertions.assertFalse(this.game.purchase(money + 1));
        // correct purchase
        this.game.purchase(money);
        money -= money;
        Assertions.assertEquals(money, this.game.getMoney());
    }

    /**
     * Test advanceWave method.
     */
    @Test
    void testWave() {
        int wave = this.game.getWave();
        Assertions.assertEquals(INITIAL_WAVE, wave);
        // advance wave
        this.game.advanceWave();
        wave++;
        Assertions.assertEquals(wave, this.game.getWave());
    }

    /**
     * Test GameImpl constructor providing initial values.
     */
    @Test
    void testConstructor() {
        final var game = new GameImpl(INITIAL_LIVES, INITIAL_MONEY, INITIAL_WAVE);
        Assertions.assertEquals(INITIAL_MONEY, game.getMoney());
        Assertions.assertEquals(INITIAL_MONEY, game.getLives());
        Assertions.assertEquals(INITIAL_WAVE, game.getWave());
        Assertions.assertEquals(PAUSE_STATE, game.getGameState());
    }
}