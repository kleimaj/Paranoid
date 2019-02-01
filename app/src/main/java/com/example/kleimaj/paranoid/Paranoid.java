package com.example.kleimaj.paranoid;

import android.content.Context;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kleimaj on 11/25/18.
 */

public class Paranoid {

    Context context;
    /*Screen positions*/
    int x; //width of screen 1080
    int y; //height of screen 1730

    int batL; //left most pixel of bat
    int batR; //right most pixcel of bat

    int ballVX;
    int ballVY;

    int ballX;
    int ballY;

    int DEFAULT_LIVES = 3;

    int batH;
    int batM;

    int ballAngle;

    boolean win = false;
    boolean lose = false;
    boolean isStarted = false;
    boolean ballOffScreen;
    boolean paused;

    String status;

    int score;
    int lives;

    public static ArrayList<Brick>[] table = new ArrayList[5];

    public Paranoid(Context context, int x, int y) {
        this.context = context;
        this.x = x;
        this.y = y-1;
        lives = DEFAULT_LIVES;
        score = 0;
        batL = 450; //450
        batR = x-450; //630
        batM = 540;
        //batL = 0;
        //batR = x;
        batH = 1300;
        table[0] = new ArrayList();
        table[1] = new ArrayList();
        table[2] = new ArrayList();
        table[3] = new ArrayList();
        table[4] = new ArrayList();

        status = "";

        paused = false;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getBatL() { return batL; }
    public int getBatR() { return batR; }

    public int getBallX() {return ballX;}
    public int getBallY() {return ballY;}

    public int getBatMid() {
        return batM;
    }

    public void initializeBallPos() {
        ballY = 1220;
        ballX = 540;
        ballVX = 1;
        ballVY = -1;
    }

    public void initializeBatPos() {
        batL = 450;
        batR = x - 450;
    }

    public void setBat(int m) {
        batM = m;
        batL = m-90;
        batR = m+90;
    }

    public boolean ballOffScreen(){
        if (ballY > 1500) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isGameOver() {
        if (lives == 0) {
            //lose
            status = "Lose";
            return true;
        }
        else if ((!table[0].get(0).isActive())&&(!table[0].get(1).isActive())) { //win
            status = "Win";
            return true;
        }
        return false;
    }

    public boolean batHit() {
        if (ballY == batH) {
            if (ballX > batL && ballX < batR) {
                ballVY = -ballVY;
                if (ballX > batM) {
                    ballVX = -ballVX;
                }
                MainActivity.playBoing();
                return true;
            }
        }
        //invert ballVX and ballVY
        return false;
    }

    public boolean ballIsMoving() { //will check brick hit, bat hit, screen hit
        if (isStarted) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean screenHit(){
        boolean returnVal = false;
        if (ballX >= x) {
            ballVX = -1;
            returnVal = true;
        }
        else if (ballX <= 0) {
            ballVX = 1;
            returnVal = true;
        }
        if (ballY >= y-200) {
            ballVY = -1;
            returnVal = true;
        }
        else if (ballY <= 0) {
            ballOffScreen();
            ballVY = 1;
            returnVal = true;
        }
        return returnVal;
    }

    public boolean started(){
        return isStarted;
    }
    public void startGame() {
        isStarted = true;
        ballOffScreen = false;
        paused = false;
        lives = DEFAULT_LIVES;
        initializeBallPos();
        initializeBricks();
        initializeBatPos();
        //moveBall(); called in timer
    }

    public void pause() {
        paused = true;
    }

    public void unPause(){
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int s) { score = s;}

    public int getLives() {
        return lives;
    }

    public void stop() {
        isStarted = false;
        table[0].clear();
        table[1].clear();
        table[2].clear();
        table[3].clear();
        table[4].clear();
    }

    public void moveBall(GameView gameView) {
        screenHit();
        batHit();
        brickHit();
        if (ballVX > 0 && ballVY < 0) {
            ballX += 20; //moves ball right
            ballY -= 20; //moves ball up
        }
        else if (ballVX > 0 && ballVY > 0) {
            ballX +=20;
            ballY +=20;
        }
        else if (ballVX < 0 && ballVY < 0) {
            ballX -= 20;
            ballY -= 20;
        }
        else {
            ballX -= 20;
            ballY += 20;
        }
    }
    public ArrayList<Brick>[] getTable(){
        return table;
    }

    public String getStatus() { return status; }

    public void setStatus(String s) { status = s; }

    public void loseLife(){
        lives--;
    }

    public void reset() {
        loseLife();
        if (lives > 0) {
            initializeBallPos();
            initializeBatPos();
        }
        else {
            ballX = -1;
            ballY = -1;
        }
    }

    //1080
    //1730
    public void initializeBricks() {
        int currRight = x/2;
        int currTop = 420;
        int currBottom = 420;
        int currLeft = x/2-100;
        Brick trophyTL = new Brick(currLeft,currTop-400,currRight,currBottom-300);
        Brick trophyTL2 = new Brick(currLeft+100,currTop-400,currRight+100,currBottom-300);
        table[0].add(trophyTL);
        table[0].add(trophyTL2);
        currRight = 100;
        currLeft = 0;
        while (currRight <= x) {
            Brick currBrick = new Brick(currLeft, currTop+20, currRight, currTop -40);
            table[1].add(currBrick);
            currLeft+=x/10;
            currRight+=x/10;
        }
        currRight = 100;
        currLeft = 0;
        while (currRight <= x) {
            Brick currBrick = new Brick(currLeft, currTop-40, currRight, currTop -100);
            table[2].add(currBrick);
            currLeft+=x/10;
            currRight+=x/10;
        }
        currRight = 100;
        currLeft = 0;
        while (currRight <= x) {
            Brick currBrick = new Brick(currLeft, currTop-100, currRight, currTop -160);
            table[3].add(currBrick);
            currLeft+=x/10;
            currRight+=x/10;
        }
        currRight = 100;
        currLeft = 0;
        while (currRight <= x) {
            Brick currBrick = new Brick(currLeft, currTop-160, currRight, currTop -220);
            table[4].add(currBrick);
            currLeft+=x/10;
            currRight+=x/10;
        }

    }

    public boolean brickHit() {
        for (int i = 0; i < table[0].size(); i++) {
            Brick brick = table[0].get(i);
            if (brick.isActive()) {
                int bottom = brick.getBottom();
                int top = brick.getTop();
                int left = brick.getLeft();
                int right = brick.getRight();
                if (ballY == bottom && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
                else if (ballX == left && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballX == right && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballY == top && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
            }
        }
        for (int i = 0; i < table[1].size(); i++) {
            Brick brick = table[1].get(i);
            if (brick.isActive()) {
                int bottom = brick.getBottom();
                int top = brick.getTop();
                int left = brick.getLeft();
                int right = brick.getRight();
                if (ballY == bottom && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
                else if (ballX == left && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballX == right && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballY == top && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
            }
        }
        for (int i = 0; i < table[2].size(); i++) {
            Brick brick = table[2].get(i);
            if (brick.isActive()) {
                int bottom = brick.getBottom();
                int top = brick.getTop();
                int left = brick.getLeft();
                int right = brick.getRight();
                if (ballY == bottom && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
                else if (ballX == left && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballX == right && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballY == top && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
            }
        }
        for (int i = 0; i < table[3].size(); i++) {
            Brick brick = table[3].get(i);
            if (brick.isActive()) {
                int bottom = brick.getBottom();
                int top = brick.getTop();
                int left = brick.getLeft();
                int right = brick.getRight();
                if (ballY == bottom && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
                else if (ballX == left && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballX == right && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballY == top && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
            }
        }
        for (int i = 0; i < table[4].size(); i++) {
            Brick brick = table[4].get(i);
            if (brick.isActive()) {
                //check bottom
                int bottom = brick.getBottom();
                int top = brick.getTop();
                int left = brick.getLeft();
                int right = brick.getRight();
                if (ballY == bottom && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }
                else if (ballX == left && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballX == right && (ballY <= top && ballY >= bottom)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVX = -ballVX;
                    return true;
                }
                else if (ballY == top && (ballX <= right && ballX >= left)) {
                    brick.setUnactive();
                    MainActivity.playBreak();
                    score++;
                    ballVY = -ballVY;
                    return true;
                }


            }
        }
        return false;
    }

}