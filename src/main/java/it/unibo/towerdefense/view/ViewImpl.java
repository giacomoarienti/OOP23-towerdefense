package it.unibo.towerdefense.view;

import java.util.Objects;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;
import it.unibo.towerdefense.model.game.GameStatusEnum;
import it.unibo.towerdefense.view.game.GameInfoRenderImpl;
import it.unibo.towerdefense.view.game.GameInfoRendererImpl;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherViewImpl;
import it.unibo.towerdefense.view.graphics.GameRenderer;
import it.unibo.towerdefense.view.graphics.GameRendererImpl;
import it.unibo.towerdefense.view.menus.StartMenuViewImpl;
import it.unibo.towerdefense.view.savings.SavingsViewImpl;
import it.unibo.towerdefense.view.scoreboard.ScoreboardViewImpl;
import it.unibo.towerdefense.view.window.Window;
import it.unibo.towerdefense.view.window.WindowImpl;

/**
 * Implementation of the View interface.
 */
public class ViewImpl implements View {

    private Window window;
    private GameRenderer gameRenderer;
    private GameInfoRenderImpl gameInfoRenderer;

    /**
     * Empty constructor.
     */
    public ViewImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayLauncher(final GameLauncherController controller) {
        // instantiate the game launcher view
        final var gameLauncherView = new GameLauncherViewImpl(controller);
        gameLauncherView.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayWindow(final Size size) {
        this.window = new WindowImpl(size.copy());
        this.window.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings(final SavingsController controller) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        final var savingsView = new SavingsViewImpl(controller);
        this.window.displayModal("Savings", savingsView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayStartMenu(final StartMenuController controller) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        final var startMenu = new StartMenuViewImpl(controller);
        this.window.displayModal("Start Menu", startMenu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        this.window.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayScoreboard(final ScoreboardDTO dto) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        this.window.displayModal("Scoreboard", new ScoreboardViewImpl(dto));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMapSize(final Size mapSize) {
        this.initRenderers(mapSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderGameInfo(final GameDTO dto) {
        if (Objects.isNull(this.gameInfoRenderer)) {
            throw new IllegalStateException("GameInfoRenderer not created yet");
        }
        this.gameInfoRenderer.render(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderState(final GameStatusEnum state) {
        // on first call, init the game renderer and it's renderers
        if (Objects.isNull(this.gameRenderer)) {
            throw new IllegalStateException("GameRenderer not created yet");
        }
        // TODO: render the state
    }

    private void initRenderers(final Size mapSize) {
        this.gameRenderer = new GameRendererImpl(mapSize, this.window);
        // create all the renderers
        this.gameInfoRenderer = new GameInfoRendererImpl(this.gameRenderer);
    }
}
