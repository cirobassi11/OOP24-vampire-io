package it.unibo.vampireio.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class CharacterDataLoader {
    private final Gson gson;

    public CharacterDataLoader() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public List<UnlockableCharacter> loadAllCharacters() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/characters.json");

        if (inputStream == null) {
            System.out.println("File not found in resources: data/characters.json");
            return List.of();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type listType = new TypeToken<List<UnlockableCharacter>>(){}.getType();

            List<UnlockableCharacter> characters = gson.fromJson(reader, listType);
            return characters != null ? characters : List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}