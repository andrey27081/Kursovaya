package com.company;

/**
 * Created by Andrey on 16.12.2016.
 */
public class Apple {

    Main m;

    public int posX;
    public int posY;


    public Apple( int x, int y){
        posX = x;
        posY = y;
    }
    public void setRandomPosition(){
        posX = (int) (Math.random() * m.WIDTH );
        posY = (int) (Math.random() * m.HEIGHT );
    }
}

