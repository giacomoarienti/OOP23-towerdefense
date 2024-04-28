package it.unibo.towerdefense.models.defenses;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDefenseFactoryImpl {
    private DefenseFactory factory = new DefenseFactoryImpl();

    /**Files paths.*/
    private static final String TEST_PATH1 = "TOCREATE.json";

    @BeforeEach
    /**set up some collections.*/
    void setUp() {

    }

    /**Test the archer tower methods.*/
    @Test
    void testArcherTower() throws IOException {
        /**Expected values for getters.*/
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedLevel = 1;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        Defense tower = factory.archerTowerFromSaveFile(TEST_PATH1);

        /**Test getters */
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());

        /**Test internal strategy */
    }

    /**Test the bomber tower methods.*/
    @Test
    void testBomberTower() throws IOException {
        /**Expected values for getters.*/
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedLevel = 1;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        Defense tower = factory.archerTowerFromSaveFile(TEST_PATH1);

        /**Test getters */
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());

        /**Test internal strategy */
    }
}
