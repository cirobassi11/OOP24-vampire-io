package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class CharacterDataLoader {
    private GameController gameController;
    private final Gson gson;

    private final String loadError = "An error occurred while data loading";
    
    public CharacterDataLoader(GameController gameController) {
        this.gameController = gameController;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public List<UnlockableCharacter> loadAllCharacters() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/characters.json");

        if (inputStream == null) {
            this.gameController.showError(this.loadError);
            return List.of();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type listType = new TypeToken<List<UnlockableCharacter>>(){}.getType();

            List<UnlockableCharacter> characters = gson.fromJson(reader, listType);
            return characters != null ? characters : List.of();
        } catch (Exception e) {
            this.gameController.showError(this.loadError);
            return List.of();
        }
    }
}