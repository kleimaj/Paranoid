package com.example.kleimaj.paranoid;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/**
 * Created by kleimaj on 11/25/18.
 */

public class GameControllerFragment extends Fragment{
    private Paranoid game;

    public GameControllerFragment() {

    }

    public void onStart() {
        super.onStart();
        game = MainActivity.getGame();
        int option = MainActivity.option;

        //switch to GameControllerFragment
        switch(option){
            case R.id.action_start:
                Log.w("MainActivity","Start Game");
                startGame();
                break;
            case R.id.action_pause:
                Log.w("MainActivity","Pause Game");
                pauseGame();
                break;
            case R.id.action_stop:
                Log.w("MainActivity","Stop Game");
                stopGame();
                break;
        }
    }

    public void startGame() {
        if (game.started()) {
            if (game.isPaused()) {
                game.unPause();
            }
        }
        else {
            //start game
            MainActivity.gameInterface();
            //switch fragments

        }
    }

    public void pauseGame() {
        game.pause();
    }

    public void stopGame() {
        if (!game.started()) {
            getActivity().finish();
        }
        else {
            game.stop();
            Intent myIntent = new Intent(getActivity(), MainActivity.class);
            myIntent.putExtra("dialog","1");
            startActivity(myIntent);
            getActivity().finish();
        }
    }
}

