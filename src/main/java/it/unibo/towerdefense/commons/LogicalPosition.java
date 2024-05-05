package it.unibo.towerdefense.commons;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;

public class LogicalPosition extends PositionImpl implements Cloneable {

    /**
     * 1/scalingFactor of a cell is the smallest variation registered
     * a cell is scalingFactor positions long
     */
    public final static int SCALING_FACTOR = 3600;

    /**
     * Constructor for the class.
     * @param x the x
     * @param y the y
     */
    public LogicalPosition(int x, int y) {
        super(x, y);
    }

    /**
     * Getter for the x of the corresponding cell.
     *
     * @return the x of the corresponding cell.
     */
    public int getCellX() {
        return getCellCoord(getX());
    }

    /**
     * Getter for the y of the corresponding cell.
     *
     * @return the y of the corresponding cell.
     */
    public int getCellY() {
        return getCellCoord(getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition clone() {
        return new LogicalPosition(this.getX(), this.getY());
    }

    /**
     * Compact setter.
     * @param x the new x
     * @param y the new y
     */
    public void set(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    /**
     * overload for fromJson method.
     */
    public static LogicalPosition fromJson(String jsonData) {
        Position result = Position.fromJson(jsonData);
        return new LogicalPosition(result.getX(), result.getY());
    }

    private static int getCellCoord(final int posCoord) {
        if (posCoord < 0) {
            return (posCoord + 1) / SCALING_FACTOR - 1;
        }
        return posCoord / SCALING_FACTOR;
    }

    /**
     * Returns an immutable LogicalPosition with the same x and y as the argument.
     * @return an immutable LogicalPosition with the same x and y as the argument
     */
    public static LogicalPosition copyOf(LogicalPosition pos) {
        return new LogicalPosition(pos.getX(), pos.getY()){
            /**
             * {@inheritDoc}
             */
            @Override
            public void set(int x, int y){
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public void setX(final int x) {
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public void setY(final int y) {
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public void add(final Position position) {
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public void subtract(final Position position) {
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }
        };
    }
}
