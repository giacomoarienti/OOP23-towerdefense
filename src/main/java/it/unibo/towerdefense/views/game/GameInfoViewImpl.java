package it.unibo.towerdefense.views.game;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.game.GameDTOImpl;

/**
 * Game info view implementation.
 */
public class GameInfoViewImpl implements GameInfoView {

    private static final int DEFAULT_VALUE = 0;
    private GameDTO gameInfo;
    private boolean shouldRender;

    /**
     * Zero-argument constructor.
     */
    public GameInfoViewImpl() {
        // initialize the game info with default values
        this.gameInfo = new GameDTOImpl("", DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
        this.shouldRender = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        // reset the flag to render the view
        this.shouldRender = false;
        // build the panel
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // add wave, money and lives labels
        panel.add(new JLabel("Player: " + this.gameInfo.getPlayerName()));
        panel.add(new JLabel("Wave: " + this.gameInfo.getWave()));
        panel.add(new JLabel("Money: " + this.gameInfo.getMoney()));
        panel.add(new JLabel("Lives: " + this.gameInfo.getLives()));
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final GameDTO dto) {
        // store a copy of the game info
        this.gameInfo = dto.copy();
        // set the flag to render the view
        this.shouldRender = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldRender() {
        return this.shouldRender;
    }
}
