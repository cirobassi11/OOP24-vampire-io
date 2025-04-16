package it.unibo.vampireio.view;

import java.util.List;
import java.awt.event.ActionListener;
import it.unibo.vampireio.controller.GameData;

public interface GameView {
    void update(GameData data);
    void updateSaveList(List<String> saves);
    void showScreen(String name);
    void setStartListener(ActionListener listener);
    void setNewSaveListener(ActionListener listener);
    void setShowSaveListener(ActionListener listener);
    void setChooseSaveListener(ActionListener listener);
    String getSelectedCharacter();
    String getSelectedSave();
}
