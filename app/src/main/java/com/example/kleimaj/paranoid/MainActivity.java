package com.example.kleimaj.paranoid;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static Paranoid game;
    public static int option; //optionItemSelected
    private static FragmentManager fragmentManager;
    public static Context context;
    public static TextView display;
    private static SoundPool pool;
    private static int boingId;
    private static int brickbreakId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.main_display);
        display.setText("Start the Game!");
        context = this;
        fragmentManager = getFragmentManager();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        if (game==null) {
            game = new Paranoid(this,size.x,size.y - getStatusBarHeight() );
        }
        SoundPool.Builder poolBuilder = new SoundPool.Builder();
        poolBuilder.setMaxStreams(2);
        pool = poolBuilder.build();
        boingId = pool.load(this, R.raw.boing,1);
        brickbreakId = pool.load(this,R.raw.brickbreak,1);

        FrameLayout frame = findViewById(R.id.frame_container);
        frame.setMinimumHeight(size.y-getStatusBarHeight()); //need this?
        setContentView(R.layout.activity_main); //will say, please start game
        Intent intent = getIntent();
        if (!intent.hasExtra("dialog")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Press Start to start the game!").setTitle("Welcome to Paranoid");
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        option = id;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GameControllerFragment fragment = new GameControllerFragment();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
        return true;
    }

    public static Paranoid getGame(){
        return game;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getOption(){
        return option;
    }

    public static void gameInterface() {
// replace
        display.setText("");
        FragmentTransaction ft = fragmentManager.beginTransaction();
        GameInterfaceFragment fragment = new GameInterfaceFragment();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }
    //updates view for score, lives, win or lose
    public static GameStatusFragment gameStatus(GameView view) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        GameStatusFragment fragment = new GameStatusFragment();
        ft.replace(R.id.frame_display,fragment);
        ft.commit();
        return fragment;
    }

    public static void playBoing() {
        pool.play(boingId,1.0f,1.0f,1,0,1.0f);
    }

    public static void playBreak() {
        pool.play(brickbreakId,1.0f,1.0f,1,0,1.0f);
    }
}