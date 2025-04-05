package it.unibo.vampireio.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utility per leggere e parsare file JSON utilizzando Gson
 */
public class DataLoader {

    /* public JsonObject getJsonObject() {
        return jsonObject;
    }

    public <T> T parseAs(Class<T> classOfT) {
        return gson.fromJson(jsonObject, classOfT);
    } */
    

    public static List<UnlockableCharacter> loadAllCharacters() {
        // Path to the directory containing JSON files
        String directoryPath = "src/main/resources/characters"; // Relative path to the resources folder
        
        List<UnlockableCharacter> characters = new LinkedList<>();

        //getClass().getResource(path)        List<UnlockableCharacter> characters = new ArrayList<>();
        Gson gson = new Gson();
        
        try {
            Files.list(Paths.get(directoryPath))
                 .filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".json"))
                 .forEach(path -> {
                     try {
                         UnlockableCharacter character = gson.fromJson(new FileReader(path.toFile()), UnlockableCharacter.class);
                         characters.add(character);
                         System.out.println("Caricato: " + path.getFileName());
                     } catch (IOException e) {
                         System.err.println("Errore durante la lettura del file: " + path);
                         e.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            System.err.println("Errore durante l'accesso alla directory: " + directoryPath);
            e.printStackTrace();
        }
        
        return characters;
    }
}