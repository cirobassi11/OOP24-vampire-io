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

public class GenericDataLoader<T extends Identifiable> {

    private final GameController gameController;
    private final String path;
    private final Class<T> type;
    private final Gson gson;

    private Map<String, T> dataById;

    public GenericDataLoader(GameController gameController, String path, Class<T> type) {
        this.gameController = gameController;
        this.path = path;
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<T> getAll() {
        if (dataById == null) {
            load();
        }
        return new ArrayList<>(dataById.values());
    }

    public Optional<T> get(String id) {
        if (dataById == null) {
            load();
        }
        return Optional.ofNullable(dataById.get(id));
    }

    public void reload() {
        dataById = null;
    }

    private void load() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                gameController.showErrorWithExit("Cannot find resource: " + path);
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
            gameController.showErrorWithExit("Error while loading data from " + path);
            dataById = Map.of();
        }
    }
}