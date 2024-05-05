package it.unibo.towerdefense.models.enemies;

import java.io.InputStream;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.google.common.math.IntMath;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * {@inheritDoc}
 */
public class ConfigurableEnemyCatalogue implements EnemyCatalogue {

    private final int valueFactor; //scaling factor to adjust value from powerlevel
    private final Map<EnemyArchetype, Integer> rateos; //speed = hp * rateo/100
    private final Map<EnemyLevel, Integer> powerlevels; //powerlevel = speed * hp
    private final Set<RichEnemyType> availableTypes;

    /**
     * Constructor for the class.
     *
     * @param configFile name of the file from which to load configurations.
     */
    ConfigurableEnemyCatalogue(final String configFile) {
        Triple<Integer, Map<EnemyArchetype, Integer>, Map<EnemyLevel, Integer>> configValues = loadConfig(configFile);
        this.valueFactor = configValues.getLeft();
        this.rateos = configValues.getMiddle();
        this.powerlevels = configValues.getRight();
        availableTypes = Arrays.stream(EnemyLevel.values())
                .flatMap(l -> Arrays.stream(EnemyArchetype.values()).map(t -> build(l, t)))
                .collect(Collectors.toSet());
    }

    /**
     * Method which separates the loading logic from the rest of the class.
     *
     * Changes to configuration file format only affect this part of the class.
     *
     * @param configFile name of the file from which to load configurations.
     * @return a Pair containing the two maps which represent the information stored
     *         in the file.
     */
    private Triple<Integer, Map<EnemyArchetype, Integer>, Map<EnemyLevel, Integer>> loadConfig(final String configFile) {
        Integer vf;
        Map<EnemyArchetype, Integer> r = new HashMap<>();
        Map<EnemyLevel, Integer> pl = new HashMap<>();
        try (InputStream configStream = ClassLoader.getSystemResourceAsStream(configFile)) {
            JSONObject config = new JSONObject(new String(configStream.readAllBytes()));

            vf = config.getInt("vf");
            assert vf > 0;

            config.getJSONArray("archetypes").forEach(
                    (Object o) -> {
                        assert o instanceof JSONObject;
                        JSONObject level = (JSONObject) o;
                        r.put(EnemyArchetype.valueOf(level.getString("archetype")), level.getInt("rateo"));
                    });
            assert r.keySet().containsAll(Set.of(EnemyArchetype.values()));

            config.getJSONArray("levels").forEach(
                    (Object o) -> {
                        assert o instanceof JSONObject;
                        JSONObject level = (JSONObject) o;
                        pl.put(EnemyLevel.valueOf(level.getString("level")), level.getInt("powerlevel"));
                    });
            assert pl.keySet().containsAll(Set.of(EnemyLevel.values()));

            return Triple.of(vf, ImmutableMap.copyOf(r), ImmutableMap.copyOf(pl));

        } catch (Throwable t) {
            throw new RuntimeException("Failed to load enemy types configuration from " + configFile, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<RichEnemyType> getEnemyTypes() {
        return getEnemyTypes(et -> true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<RichEnemyType> getEnemyTypes(final Predicate<? super RichEnemyType> test) {
        return availableTypes.stream().filter(test).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * {@inheritDoc}
     */
    private record BasicEnemyType(EnemyLevel level, EnemyArchetype type, int getMaxHP, int getSpeed, int getValue)
            implements RichEnemyType {
                /**
                 * Two enemy types are the same if they have same level and type.
                */
                @Override
                public final boolean equals(Object o) {
                    return o instanceof EnemyType &&
                        ((EnemyType)o).level() == this.level() &&
                        ((EnemyType)o).type() == this.type();
                }
                /**
                 * Overridden to support the new equals definition.
                 */
                @Override
                public final int hashCode() {
                    return Objects.hash(this.level(), this.type());
                }
    };

    /**
     * Builds an EnemyType of given Level and Archetype
     *
     * @param l the Level of the enemy, determines total stats
     * @param t the Archetype of the enemy, determines stats rateo
     * @return the built EnemyType
     */
    private RichEnemyType build(final EnemyLevel l, final EnemyArchetype t) {
        final int speed = IntMath.sqrt((powerlevels.get(l) * rateos.get(t)) / 100, RoundingMode.DOWN);
        final int hp = (speed * 100) / rateos.get(t);
        final int value = powerlevels.get(l) * (valueFactor / 100);
        return new BasicEnemyType(l, t, hp, speed, value);
    }
}
