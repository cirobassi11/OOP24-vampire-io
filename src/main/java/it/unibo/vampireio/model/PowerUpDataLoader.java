package it.unibo.vampireio.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PowerUpDataLoader {
    private final Gson gson;

    public PowerUpDataLoader() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public List<UnlockablePowerUp> loadAllPowerUps() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/powerups.json");

        if (inputStream == null) {
            System.out.println("File not found in resources: data/powerups.json");
            return List.of();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type listType = new TypeToken<List<UnlockablePowerUp>>(){}.getType();

            List<UnlockablePowerUp> powerups = gson.fromJson(reader, listType);
            return powerups != null ? powerups : List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}