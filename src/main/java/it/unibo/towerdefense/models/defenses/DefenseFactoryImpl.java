package it.unibo.towerdefense.models.defenses;

import java.util.Set;

/**
 * Implementation of the defenseFactory interface.
 */
public class DefenseFactoryImpl implements DefenseFactory {

    /**
     * an internal factory for the strategies.
     */
    private EnemyChoiceStrategyFactory strategyFactory;

    /**
     * @return the defense representing the archer tower.
     * @param fileName the json file with the attribute values for this defense.
     * @TODO create upgrades.
     */
    @Override
    public Defense archerTower(final String fileName) {
        return new DefenseImpl(fileName, strategyFactory.closestTargets(1, 10), Set.of());
    }
}