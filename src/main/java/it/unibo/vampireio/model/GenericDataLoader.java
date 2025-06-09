package it.unibo.vampireio.model;

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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.unibo.vampireio.controller.GameController;

/**
 * GenericDataLoader is responsible for loading and managing game data from a JSON file.
 * It provides methods to retrieve all data or specific items by their ID.
 *
 * @param <T> the type of data being loaded, which must implement Identifiable
 */
class GenericDataLoader<T extends Identifiable> {

    private final GameWorld model;
    private final String path;
    private final Class<T> type;
    private final Gson gson;

    private Map<String, T> dataById;

    /**
     * Constructs a GenericDataLoader with the specified model, path, and type.
     *
     * @param model the GameWorld model to notify on errors
     * @param path the path to the JSON file containing the data
     * @param type the class type of the data being loaded
     */
    GenericDataLoader(GameWorld model, String path, Class<T> type) {
        this.model = model;
        this.path = path;
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    List<T> getAll() {
        if (dataById == null) {
            load();
        }
        return new ArrayList<>(dataById.values());
    }

    Optional<T> get(String id) {
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

            InputStreamReader reader = new InputStreamReader(input);
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            List<T> list = gson.fromJson(reader, listType);

            if (list == null) {
                list = List.of();
            }

            dataById = new HashMap<>();
            for (T element : list) {
                dataById.put(element.getId(), element);
            }
        } catch (IOException | JsonSyntaxException e) {
            this.model.notifyError("Error while loading data from " + path);
            dataById = Map.of();
        }
    }
}