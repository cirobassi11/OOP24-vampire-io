package it.unibo.vampireio.model;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Shape;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CharacterLoader {

    public static Character loadCharacterById(String id) {
        String JSON;
        try {
            JSON = new String(Files.readAllBytes(Paths.get("/data/characters.json")));

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(JSON, JsonObject.class);

            JsonObject baseStats = jsonObject.getAsJsonObject("base_stats");
            JsonObject characters = jsonObject.getAsJsonObject("characters");

            JsonObject characterData = characters.getAsJsonObject(id);

            String name = characterData.get("name").getAsString();
            JsonObject modifiers = characterData.getAsJsonObject("modifiers");

            int maxHealth = baseStats.get("max_health").getAsInt() + modifiers.get("max_health").getAsInt();
            int armor = baseStats.get("armor").getAsInt() + modifiers.get("armor").getAsInt();
            double speed = baseStats.get("speed").getAsDouble() + modifiers.get("speed").getAsDouble();
            double moveSpeed = baseStats.get("move_speed").getAsDouble() + modifiers.get("move_speed").getAsDouble();
            double recovery = baseStats.get("recovery").getAsDouble() + modifiers.get("recovery").getAsDouble();
            double might = baseStats.get("might").getAsDouble() + modifiers.get("might").getAsDouble();
            double area = baseStats.get("area").getAsDouble() + modifiers.get("area").getAsDouble();
            double duration = baseStats.get("duration").getAsDouble() + modifiers.get("duration").getAsDouble();
            double amount = baseStats.get("amount").getAsDouble() + modifiers.get("amount").getAsDouble();
            double cooldown = baseStats.get("cooldown").getAsDouble() + modifiers.get("cooldown").getAsDouble();
            double luck = baseStats.get("luck").getAsDouble() + modifiers.get("luck").getAsDouble();
            double growth = baseStats.get("growth").getAsDouble() + modifiers.get("growth").getAsDouble();
            double greed = baseStats.get("greed").getAsDouble() + modifiers.get("greed").getAsDouble();
            double curse = baseStats.get("curse").getAsDouble() + modifiers.get("curse").getAsDouble();
            double magnet = baseStats.get("magnet").getAsDouble() + modifiers.get("magnet").getAsDouble();
            double revival = baseStats.get("revival").getAsDouble() + modifiers.get("revival").getAsDouble();
            double reroll = baseStats.get("reroll").getAsDouble() + modifiers.get("reroll").getAsDouble();
            double skip = baseStats.get("skip").getAsDouble() + modifiers.get("skip").getAsDouble();
            double banish = baseStats.get("banish").getAsDouble() + modifiers.get("banish").getAsDouble();
            double charm = baseStats.get("charm").getAsDouble() + modifiers.get("charm").getAsDouble();
            double defang = baseStats.get("defang").getAsDouble() + modifiers.get("defang").getAsDouble();

            JsonObject hitboxData = characterData.getAsJsonObject("hitbox");
            Shape hitbox = new Rectangle2D.Double(0, 0, hitboxData.get("width").getAsInt(),
                    hitboxData.get("height").getAsInt()); // Un rettangolo largo 50 e alto 50

            return new Character(id, name, maxHealth, armor, speed, moveSpeed, recovery, might, area, duration, amount,
                    cooldown, luck, growth, greed, curse, magnet, revival, reroll, skip, banish, charm, defang, hitbox);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while loading character data");
        }
    }
}
