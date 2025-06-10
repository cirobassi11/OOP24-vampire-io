package it.unibo.vampireio.controller;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InputHandler is responsible for managing keyboard input in the game.
 * It sets up key bindings for movement and actions, tracks pressed keys,
 * and provides methods to check if a key is pressed or to clear the pressed
 * keys.
 */
public final class InputHandler {
    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();

    /**
     * Sets up key bindings for the specified component.
     * This method binds keys for movement (W, A, S, D, arrow keys) and escape.
     *
     * @param component the JComponent to set up key bindings for
     */
    public void setupKeyBindings(final JComponent component) {
        final int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        final InputMap inputMap = component.getInputMap(condition);
        final ActionMap actionMap = component.getActionMap();

        final List<Integer> keysList = List.of(
                KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
                KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                KeyEvent.VK_ESCAPE);

        for (final int key : keysList) {
            final String keyName = KeyEvent.getKeyText(key);
            final String press = "pressed " + keyName;
            final String release = "released " + keyName;

            inputMap.put(KeyStroke.getKeyStroke(key, 0, false), press);
            inputMap.put(KeyStroke.getKeyStroke(key, 0, true), release);

            actionMap.put(press, new AbstractAction() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    pressedKeys.add(key);
                }
            });

            actionMap.put(release, new AbstractAction() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    pressedKeys.remove(key);
                }
            });
        }
    }

    boolean isKeyPressed(final int keyCode) {
        return this.pressedKeys.contains(keyCode);
    }

    void clearPressedKeys() {
        this.pressedKeys.clear();
    }
}
