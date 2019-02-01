package com.example.kleimaj.paranoid;

import android.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;

/**
 * Created by kleimaj on 11/25/18.
 */

public class GameInterfaceFragment extends Fragment{
    private Paranoid game;
    private GameView gameView;
    public GameInterfaceFragment() {
        game = MainActivity.getGame();
        game.setStatus("");
        game.setScore(0);
        gameView = new GameView(MainActivity.context, game.getX(),game.getY());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,                                                                                                Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.)
        return gameView;
    }
    public void onStart(){
        super.onStart();
        game.startGame(); //start ball above bat, initialize all bricks
        Timer gameTimer = new Timer();
        gameTimer.schedule(new GameTimerTask(gameView), 0, gameView.DELTA_TIME);
    }
    public void onResume(){
        super.onResume();
    }
}
