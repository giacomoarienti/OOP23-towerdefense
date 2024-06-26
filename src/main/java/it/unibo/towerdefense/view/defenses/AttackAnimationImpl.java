package it.unibo.towerdefense.view.defenses;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**Implementation of AttackAnimation.*/
public class AttackAnimationImpl implements AttackAnimation {

    /**private fields.*/
    private int timeToLive;
    private final  boolean isAreaBased;
    private final  LogicalPosition attacker, attacked;
    private final  DefenseType type;
    private static final int INITIAL_TIME_TO_LIVE = 40;

    /**Constructor.
     * @param isAreaBased if it is true it may change scaling of projectile.
     * @param attacker position of the attacking entity.
     * @param attacked position of the attacked entity.
     * @param type
    */
    public AttackAnimationImpl(final boolean isAreaBased, final LogicalPosition attacker,
    final LogicalPosition attacked, final DefenseType type) {
        this.timeToLive = INITIAL_TIME_TO_LIVE;
        this.isAreaBased = isAreaBased;
        this.attacked = LogicalPosition.copyOf(attacked);
        this.attacker = LogicalPosition.copyOf(attacker);
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.timeToLive > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAreaBased() {
        return this.isAreaBased;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition getAttacker() {
        return LogicalPosition.copyOf(this.attacker);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition getAttacked() {
        return LogicalPosition.copyOf(this.attacked);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseTimeToLive() {
        this.timeToLive--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefenseType bulletToRender() {
        return this.type;
    }

}
