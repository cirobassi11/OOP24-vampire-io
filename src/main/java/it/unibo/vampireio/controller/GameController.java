package it.unibo.vampireio.controller;

public interface GameController extends Runnable {
    void setRunning(boolean running);
    boolean isRunning();
}
