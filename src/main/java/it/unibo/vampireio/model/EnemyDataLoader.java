package it.unibo.vampireio.model;

import it.unibo.vampireio.controller.GameController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class EnemyDataLoader {
    private GameController gameController;
    private final Gson gson;

    private final String loadError = "An error occurred while data loading";
    
    public EnemyDataLoader(GameController gameController) {
        this.gameController = gameController;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public List<EnemyData> loadAllEnemies() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/enemies.json");

        if (inputStream == null) {
            this.gameController.showError(this.loadError);
            return List.of();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type listType = new TypeToken<List<EnemyData>>(){}.getType();

            List<EnemyData> enemies = gson.fromJson(reader, listType);
            return enemies != null ? enemies : List.of();
        } catch (Exception e) {
            this.gameController.showError(this.loadError);
            return List.of();
        }
    }
}