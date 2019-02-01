package com.example.kleimaj.paranoid;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kleimaj on 12/7/18.
 */

public class GameStatusFragment extends Fragment {
    private Paranoid game;
    private boolean won;
    private boolean lose;
    TextView mLifeDisplay, mLifeCount, mScoreDisplay, mScoreCount, mStatus;
    View view;
    boolean dataLoaded;

    public GameStatusFragment() {
        game = MainActivity.getGame();
        won = false;
        lose = false;
        dataLoaded = false;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.activity_main, container, false);
        this.view = view;
        return  view;
    }

    public void onStart() {
        super.onStart();
        mLifeDisplay = view.findViewById(R.id.mLifeDisplay);
        mLifeCount = view.findViewById(R.id.mLifeCount);
        mStatus = view.findViewById(R.id.mStatus);
        mScoreDisplay = view.findViewById(R.id.mScoreDisplay);
        mScoreCount = view.findViewById(R.id.mScoreCount);
        mLifeDisplay.setVisibility(View.VISIBLE);
        mLifeCount.setVisibility(View.VISIBLE);
        mScoreDisplay.setVisibility(View.VISIBLE);
        mScoreCount.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
        mLifeCount.setText(String.valueOf(game.getLives()));
        mScoreCount.setText(String.valueOf(game.getScore()));
        mStatus.setText(game.getStatus());
        System.out.println(game.getStatus());
        dataLoaded = true;
    }

    public void upDate(int numLives, int score) {
        if (dataLoaded) {
            mLifeCount.setText(numLives);
            mScoreCount.setText(score);
        }
    }

    public void updateStatus(String status) {
        mStatus.setText(status);
    }


}
