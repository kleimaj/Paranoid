package com.example.kleimaj.paranoid;

import android.graphics.Rect;

/**
 * Created by kleimaj on 12/8/18.
 */

public class Brick {
    private Rect rect;
    private boolean active;
    int left;
    int right;
    int top;
    int bottom;

    public Brick(int left, int top, int right, int bottom) {
        rect = new Rect(left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        active = true;
    }

    public Rect getRect() { return rect; }

    public int getLeft() { return left; }

    public int getTop() { return top; }

    public int getBottom() { return bottom; }

    public int getRight() { return right; }

    public int getWidth() { return rect.width(); }

    public int getHeight () { return rect.height(); }

    public boolean isActive() { return active; }

    public void setUnactive() { active = false; }
    
}
