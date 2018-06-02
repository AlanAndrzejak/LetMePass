package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture wall, marioLeft, marioMid, marioRight, marioWin;
    private Sprite sprite, mario;

    private static final float defaultSize = 200f; // 10 pixels per second
    private static final float SPEED = 6f; // 10 pixels per second
    private static final float SPEED_SLOW = 1f; // 1 pixel per second
    private static boolean leftPosition =true;
    private static boolean middlePosition = true;
    private static  Rectangle screenBounds;
    private static boolean rightPosition = true;

    @Override
    public void create() {


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        screenBounds  = new Rectangle(0, 0, w, h);
        batch = new SpriteBatch();
        wall = new Texture("wall.jpg");
        marioLeft = new Texture("marioLeft.png");
        marioMid = new Texture("marioMid.png");
        marioRight = new Texture("marioRight.png");
        sprite = new Sprite(wall);
        mario = new Sprite(marioMid);
        sprite.setOriginCenter();
        mario.setOriginCenter();
        mario.setPosition(w * 0.8343f, h * 0.45f);
        mario.setSize(defaultSize, defaultSize);
    }

    @Override
    public void render() {
        final float dt = Gdx.graphics.getDeltaTime();

        update(dt);
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wall.dispose();
    }

    private void update(final float dt) {

        // Calculate directional speed
        float speedX = SPEED;
        float speedY = 0f;

//        if (leftPosition){mario.setTexture(marioLeft);}
//        else if (rightPosition){mario.setTexture(marioRight);}
//        else {mario.setTexture(marioMid);}
        // Move sprite to new position
        sprite.translate(speedX, speedY);

        // Get the bounding rectangle that describes the boundary of our sprite based on position, size, and scale.
//        final Rectangle bounds = sprite.getBoundingRectangle();

        // Get the bounding rectangle that our screen.  If using a camera you would create this based on the camera's
        // position and viewport width/height instead.
      //  final Rectangle screenBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Sprite
//        float left = bounds.getX();
//        float bottom = bounds.getY();
//        float top = bottom + bounds.getHeight();
//        float right = left + bounds.getWidth();

        // Used for adjustments below since our origin is now the center.
//        final float halfWidth = bounds.getWidth() * .5f;
//        final float halfHeight = bounds.getHeight() * .5f;

        // Screen
        float screenLeft = screenBounds.getX();
        //   float screenBottom = screenBounds.getY();
//        float screenTop = screenBottom + screenBounds.getHeight();
        float screenRight = screenLeft + screenBounds.getWidth() - sprite.getBoundingRectangle().getWidth();

        // Current position
        float newX = sprite.getX();
        float newY = sprite.getY();

//        // Correct horizontal axis
//        if(left < screenLeft)
//        {
//            // Clamp to left
//            newX = screenLeft + halfWidth;
//        }
//        else if(right > screenRight)
//        {
//            // Clamp to right
//            newX = screenRight - halfWidth;
//        }
//
//        // Correct vertical axis
//        if(bottom < screenBottom)
//        {
//            // Clamp to bottom
//            newY = screenBottom + halfHeight;
//        }
//        else if(top > screenTop)
//        {
//            // Clamp to top
//            newY = screenTop - halfHeight;
//        }
        if (newX >= mario.getX()-100) {

            mario.getX();
            if (leftPosition) {
                mario.setTexture(marioLeft);
                mario.setPosition(Gdx.graphics.getWidth() * 0.8343f, Gdx.graphics.getHeight() * 0.15f);
            } else if (rightPosition) {
                mario.setTexture(marioRight);
                mario.setPosition(Gdx.graphics.getWidth() * 0.8343f, Gdx.graphics.getHeight() * 0.85f);
            } else {
                mario.setTexture(marioMid);
                mario.setPosition(Gdx.graphics.getWidth() * 0.8343f, Gdx.graphics.getHeight() * 0.45f);
            }

            sprite.setOriginCenter();
            sprite.setPosition(newX * 0f, newY * 0f);
            return;
        }

        // Set sprite position.
        sprite.setPosition(newX, newY);
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        mario.draw(batch);
        batch.end();
    }
}
