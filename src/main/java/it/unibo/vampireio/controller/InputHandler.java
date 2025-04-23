package it.unibo.vampireio.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputHandler {
    private final Set<Integer> pressedKeys = new HashSet<>();

    public void setupKeyBindings(JComponent component) {
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = component.getInputMap(condition);
        ActionMap actionMap = component.getActionMap();

        int[] keys = {
            KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
            KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT
        };

        for (int key : keys) {
            String keyName = KeyEvent.getKeyText(key);
            String press = "pressed " + keyName;
            String release = "released " + keyName;

            inputMap.put(KeyStroke.getKeyStroke(key, 0, false), press);
            inputMap.put(KeyStroke.getKeyStroke(key, 0, true), release);

            actionMap.put(press, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pressedKeys.add(key);
                }
            });

            actionMap.put(release, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pressedKeys.remove(key);
                }
            });
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }
}