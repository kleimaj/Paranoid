package com.example.kleimaj.paranoid;

import java.util.TimerTask;

/**
 * Created by kleimaj on 11/26/18.
 */

public class GameTimerTask extends TimerTask {
    private Paranoid game;
    private GameView gameView;
    private GameStatusFragment fragment;
    public GameTimerTask(GameView view) {
        gameView = view;
        game = MainActivity.getGame();
    }

    public void run(){
        if (!game.started()) {
            cancel();
        }
        else if (game.isGameOver()) {
            fragment = MainActivity.gameStatus(gameView);
            cancel();
        }
        else if (game.ballOffScreen()) {
            game.reset(); //start ball above bat, bat refreshes to middle bricks dont refresh
            fragment = MainActivity.gameStatus(gameView);
            gameView.postInvalidate();
        }
        else if (!game.isPaused()){
            if (game.ballIsMoving()) {
                game.moveBall(gameView);
            }
            fragment = MainActivity.gameStatus(gameView);
            gameView.postInvalidate();
        }
    }
}
