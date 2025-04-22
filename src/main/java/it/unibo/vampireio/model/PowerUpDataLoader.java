package it.unibo.vampireio.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import it.unibo.vampireio.controller.GameController;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class PowerUpDataLoader {
    private GameController gameController;
    private final Gson gson;
    
    private final String loadError = "An error occurred while data loading";

    public PowerUpDataLoader(GameController gameController) {
        this.gameController = gameController;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public List<UnlockablePowerUp> loadAllPowerUps() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/powerups.json");

        if (inputStream == null) {
            this.gameController.showError(this.loadError);
            return List.of();
            
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type listType = new TypeToken<List<UnlockablePowerUp>>(){}.getType();

            List<UnlockablePowerUp> powerups = gson.fromJson(reader, listType);
            return powerups != null ? powerups : List.of();
        } catch (Exception e) {
            this.gameController.showError(this.loadError);
            return List.of();
        }
    }
}