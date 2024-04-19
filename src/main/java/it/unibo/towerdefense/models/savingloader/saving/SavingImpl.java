package it.unibo.towerdefense.models.savingloader.saving;

import java.util.List;
import java.util.Collections;

import org.json.JSONObject;

import it.unibo.towerdefense.models.game.GameDTO;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private final String name;
    private final GameDTO game;
    private final Object map;
    private final List<Object> defenses;

    /**
     * SavingImpl constructor from a game, a map and a list of defenses.
     * @param name the name of the player
     * @param game the game instance
     * @param map the map instance
     * @param defenses the list of defenses
     */
    public SavingImpl(
        final String name,
        final GameDTO game,
        final Object map,
        final List<Object> defenses
    ) {
        this.name = name;
        this.game = game;
        this.map = map;
        this.defenses = Collections.unmodifiableList(defenses);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO getGame() {
        return this.game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getMap() {
        return this.map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getDefenses() {
        return this.defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put("name", this.name)
            .put("game", this.game.toJSON())
            // .put("map", map.toJSON())
            // .put("defenses", null)
            .toString();
    }

    /**
     * Returns the saving object from JSON string.
     * @param jsonData the JSON representation of the saving
     * @return the saving object
     */
    public static Saving fromJson(final String jsonData) {
        // TODO implement map and defenses deserialization
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new SavingImpl(
            jsonObject.getString("name"),
            GameDTO.fromJson(jsonObject.getString("game")),
            null, //Map.fromJson(jsonObject.getString("map")),
            null //List.of(...);
        );
    }
}
