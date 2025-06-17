package it.unibo.vampireio.model.data;

import it.unibo.vampireio.model.api.GameModel;
import it.unibo.vampireio.model.api.Identifiable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * GenericDataLoader is responsible for loading and managing game data from a
 * JSON file.
 * It provides methods to retrieve all data or specific items by their ID.
 *
 * @param <T> the type of data being loaded, which must implement Identifiable
 */
public final class GenericDataLoader<T extends Identifiable> {

    private final GameModel model;
    private final String path;
    private final Class<T> type;
    private final Gson gson;

    private Map<String, T> dataById;

    /**
     * Constructs a GenericDataLoader with the specified model, path, and type.
     *
     * @param model the GameWorld model to notify on errors
     * @param path  the path to the JSON file containing the data
     * @param type  the class type of the data being loaded
     */
    GenericDataLoader(final GameModel model, final String path, final Class<T> type) {
        this.model = model;
        this.path = path;
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Retrieves all data items as a list.
     * If the data has not been loaded yet, it will load the data from the JSON
     * file.
     *
     * @return a list of all data items
     */
    public List<T> getAll() {
        if (dataById == null) {
            load();
        }
        return new ArrayList<>(dataById.values());
    }

    /**
     * Retrieves a specific data item by its ID.
     * If the data has not been loaded yet, it will load the data from the JSON
     * file.
     *
     * @param id the unique identifier of the data item
     * @return an Optional containing the data item if found, or empty if not found
     */
    public Optional<T> get(final String id) {
        if (dataById == null) {
            load();
        }
        return Optional.ofNullable(dataById.get(id));
    }

    void reload() {
        dataById = null;
    }

    void load() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                this.model.notifyError("Cannot find resource: " + path);
                dataById = Map.of();
                return;
            }

            final InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            final JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            dataById = new HashMap<>();

            for (final JsonElement element : jsonArray) {
                final T item = gson.fromJson(element, type);
                if (item != null) {
                    dataById.put(item.getId(), item);
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            this.model.notifyError("Error while loading data from " + path);
            dataById = Map.of();
        }
    }
}
