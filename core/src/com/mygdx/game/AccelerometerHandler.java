package com.mygdx.game;


public class AccelerometerHandler {

    AccelerometerHandler(String input){
        this.movedTo=input;
    }
    private String movedTo;
    private boolean stop;


    public boolean isStop() {
        return stop;
    }


    public String getmovedTo() {
        return this.movedTo;
    }

    public void calculateDirection(float x, float z) {
        if (Math.abs(x) < Math.abs(z)) {
            this.stop = true;
        } else if (x < 0) {
            if (this.movedTo.equals("RIGHT")) {
                this.movedTo = "MIDDLE";
            } else

                this.movedTo = "LEFT";
        } else

        {
            if (this.movedTo.equals("LEFT")) {
                this.movedTo = "MIDDLE";
            } else
                this.movedTo = "RIGHT";
        }

    }
}
