package com.mygdx.game;


public class AccelerometerHandler {

    AccelerometerHandler(String input) {
        this.movedTo = input;
    }

    private String movedTo;
    private boolean stop;
    private int position;

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getPosition() {
        return position;
    }


    public boolean isStop() {
        return stop;
    }

    public String getmovedTo() {
        return this.movedTo;
    }

    public void calculateDirection(float x, float z) {
        if (Math.abs(x) < Math.abs(z)*0.7f) {
            this.stop = true;
        } else if (x < 0) {
            if (this.movedTo.equals("RIGHT")) {
                this.movedTo = "MIDDLE";
                this.position = 0;
            } else {

                this.movedTo = "LEFT";
                this.position = -1;
            }
        } else

        {
            if (this.movedTo.equals("LEFT")) {
                this.movedTo = "MIDDLE";
                this.position = 0;
            } else {
                this.movedTo = "RIGHT";
                this.position = 1;
            }
        }

    }
}
