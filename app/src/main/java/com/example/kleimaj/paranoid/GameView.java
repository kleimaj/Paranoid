package com.example.kleimaj.paranoid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by kleimaj on 11/26/18.
 */

public class GameView extends View{
    public static int DELTA_TIME = 50;
    private static Paint paint;
    private static Paranoid game;
    private static int width;
    private static int height;
    private static Canvas canvas;
    private Rect bat;
    private Rect ball;
    private int bricksrc = R.drawable.brick;
    private Bitmap brick = BitmapFactory.decodeResource(getResources(),bricksrc);
    private Bitmap trophy = BitmapFactory.decodeResource(getResources(),R.drawable.sophia);
    private static int ballX;
    private static int ballY;
    private GestureDetector detector;

    public GameView(Context context,int width, int height){
        super(context);
        game = MainActivity.getGame();
        this.width = width;
        this.height = height;
        ballX = game.getBallX();
        ballY = game.getBallY();
        detector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                System.out.println("word");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                updateBat(motionEvent1);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                updateBat(motionEvent1);
                return true;
            }

            public void updateBat(MotionEvent event) {
                int x = (int)event.getX(); //- game.getBatMid();
                if (x < 0) {
                    game.setBat(90);
                }
                else if (x >1080) {
                    game.setBat(1080-90);
                }
                else {
                    game.setBat(x);
                }
                //System.out.println("in here");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    public static void setDeltaTime(int time) {
        DELTA_TIME = time;
    }

    public void onDraw(Canvas canvas){
        if (game.started()) {
            super.onDraw(canvas);
            this.canvas = canvas;
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(10.0f);
            //possible line?
            drawBat(canvas);
            drawBricks(canvas);
            drawBall(canvas);
        }
    }
    public static void drawBat(Canvas canvas) {
        //batL = 450;
        //batR = x-450;
        int batL = game.getBatL();
        int batR = game.getBatR();
        canvas.drawLine(batL, height - height / 4, batR, height - (height / 4) + 1,
          paint);
    }

    public static void drawBall(Canvas canvas) {
        /*Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.STROKE);
        white.setAntiAlias(true);
        white.setStrokeWidth(10.0f);
        canvas.drawCircle(ballX,ballY,5,white);
        */
        int newBallX = game.getBallX();
        int newBallY = game.getBallY();
        canvas.drawCircle(newBallX,newBallY,5,paint);
        ballX = newBallX;
        ballY = newBallY;
    }

    /*public static void drawBall(Canvas canvas){
        int mid = game.getBatMidpoint();
        ballX = mid;
        int heightBall = height - height/4;
        ballY = heightBall;
        canvas.drawCircle(mid,heightBall-100,5,paint);
    }*/

    public void drawBricks(Canvas canvas) {

        ArrayList<Brick>[] table = game.getTable();
        Paint fill = new Paint();
        fill.setColor(Color.CYAN);
        fill.setStyle(Paint.Style.FILL);
        for (int i = 0; i < table[0].size(); i++) {
            Brick currBrick = table[0].get(i);
            if (currBrick.isActive()) {
                canvas.drawRect(currBrick.getRect(), paint);
                canvas.drawRect(currBrick.getRect(),fill);
            }
        }
        fill.setColor(Color.RED);
        for (int i = 0; i < table[1].size(); i++) {
            Brick currBrick = table[1].get(i);
            if (currBrick.isActive()) {
                canvas.drawRect(currBrick.getRect(), paint);
                canvas.drawRect(currBrick.getRect(),fill);
            }
        }
        fill.setColor(Color.BLUE);
        for (int i = 0; i < table[2].size(); i++) {
            Brick currBrick = table[2].get(i);
            if (currBrick.isActive()) {
                canvas.drawRect(currBrick.getRect(), paint);
                canvas.drawRect(currBrick.getRect(),fill);
            }
        }
        fill.setColor(Color.GREEN);
        for (int i = 0; i < table[3].size(); i++) {
            Brick currBrick = table[3].get(i);
            if (currBrick.isActive()) {
                canvas.drawRect(currBrick.getRect(), paint);
                canvas.drawRect(currBrick.getRect(),fill);
            }
        }
        fill.setColor(Color.YELLOW);
        for (int i = 0; i < table[4].size(); i++) {
            Brick currBrick = table[4].get(i);
            if (currBrick.isActive()) {
                canvas.drawRect(currBrick.getRect(), paint);
                canvas.drawRect(currBrick.getRect(),fill);
            }
        }

    }

    /*private class TouchHandler extends GestureDetector.SimpleOnGestureListener {
        public boolean onDoubleTapEvent( MotionEvent event ) {
            System.out.println("DOUBLE TAP");
            return true;}

        public boolean onScroll(MotionEvent event1, MotionEvent event2, float d1, float d2) {
            System.out.println("YO");
            return true;
        }

        public boolean onDown(MotionEvent e) {
            System.out.println("Here");
            return true;
        }
    }*/
}

