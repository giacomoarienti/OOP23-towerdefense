package it.unibo.towerdefense.models.savingloader;

import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.models.savingloader.saving.Saving;
import it.unibo.towerdefense.utils.file.FileUtils;

/**
 * Class implementing the SavingLoader interface.
 */
public class SavingLoaderImpl implements SavingLoader {

    private static final String SAVED_GAMES_FOLDER = Constants.GAME_FOLDER
            + File.separator
            + "savings";

    private final Logger logger;
    private final String folderPath;

    /**
     * Constructor with file path.
     * @param path the path of the folder containing the saved games
     * @throws IOException if the path cannot be created
     */
    public SavingLoaderImpl(final String path) throws IOException {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.folderPath = path;
        // create the SAVED_GAMES_FOLDER if it does not exist
        FileUtils.createFolder(this.folderPath);
    }

    /**
     * Zero-argument constructor.
     * @throws IOExceptions if the SAVED_GAMES_FOLDER cannot be created
     */
    public SavingLoaderImpl() throws IOException {
        this(SAVED_GAMES_FOLDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Saving> loadSavings() {
        // read all files from the folderPath
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            // for each file, read the content and convert it to a Game object
            return paths
                .filter(Files::isRegularFile)
                .map(FileUtils::readFileOptional)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter((jsonData) -> !jsonData.isEmpty())
                .map(Saving::fromJson)
                .toList();
        } catch (final IOException e) {
            logger.error("Error loading saved games", e);
        }
        // return empty list
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeSaving(final Saving saving) {
        // convert the Game object to a JSON string
        final String jsonData = saving.toJSON();
        // create game name from current timestamp
        final String gameName = "game_" + System.currentTimeMillis() + ".json";
        final String filePath = folderPath + File.separator + gameName;
        // save the JSON string to file
        try {
            FileUtils.writeFile(filePath, jsonData);
        } catch (final IOException e) {
            logger.error("Error writing saving", e);
            return false;
        }
        return true;
    }
}
