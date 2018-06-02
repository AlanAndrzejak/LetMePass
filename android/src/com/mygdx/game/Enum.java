package com.mygdx.game;


public class Enum {
    public enum WhichStateOfPosition {
        Left(-1),
        Middle(0),
        Right(1);

        private final int position;
        WhichStateOfPosition(int pos){
            this.position=pos;
        }
        public int doChangeIfNeeded(int direction){
            if (this.position+direction>=1)
                return 1;
            else if (this.position+direction==0)
                return 0;
            else return -1;
        }
    }
}