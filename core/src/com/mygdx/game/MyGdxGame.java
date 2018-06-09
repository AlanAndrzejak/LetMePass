package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;


public class MyGdxGame extends ApplicationAdapter {


    SpriteBatch batch, spriteFont;
    Texture wall, marioLeft, marioMid, marioRight, marioWin;
    private Sprite sprite, mario;
    private Rectangle rectangleMario;
    private Rectangle rectangleSprite;
    private String yourScoreName = "Score:";
    private String bestScoreName = "Best Score:";
    BitmapFont yourBitmapFontName;
    Label.LabelStyle labelStyle;
    Label myLabel, bestScore;

    private final float defaultSize = 200f;
    private final float SPEED = 5f;
    private Rectangle screenBounds;
    private int counter;
    private int bestScorePonits;
    private long start;
    AccelerometerHandler Accelerometer = new AccelerometerHandler("MIDDLE");

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        screenBounds = new Rectangle(0, 0, w, h);
        yourBitmapFontName = new BitmapFont();
        labelStyle = new Label.LabelStyle(yourBitmapFontName, Color.BLACK);
        bestScore = new Label(bestScoreName, labelStyle);
        myLabel = new Label(yourScoreName, labelStyle);
        myLabel.setPosition(700, 0);
        bestScore.setPosition(700, Gdx.graphics.getHeight()-17);
        myLabel.setAlignment(Align.center);
        bestScore.setAlignment(Align.center);
        myLabel.setColor(Color.BLACK);
        bestScore.setColor(Color.BLUE);

        batch = new SpriteBatch();
        wall = new Texture("wall.jpg");
        marioLeft = new Texture("marioLeft.png");
        marioMid = new Texture("marioMid.png");
        marioRight = new Texture("marioRight.png");
        marioWin = new Texture("marioWin.png");
        sprite = new Sprite(wall);
        mario = new Sprite(marioMid);
        sprite.setOriginCenter();
        mario.setOriginCenter();
        mario.setPosition(w * 0.8343f, h * 0.45f);
        mario.setSize(defaultSize, defaultSize);
        rectangleMario = new Rectangle(mario.getX(), mario.getY(), mario.getWidth(), mario.getHeight());
        rectangleSprite = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        start = System.currentTimeMillis();
    }

    @Override
    public void render() {
        float[] moved = {Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ()};
        update(moved);
        myLabel.setText(yourScoreName + " " + counter);
        bestScore.setText(bestScoreName + " " + bestScorePonits);
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wall.dispose();
    }

    private void update(float[] dt) {

        float speedX = SPEED;
        float speedY = 0f;
        float accelationSquareRoot = (dt[0] * dt[0] + dt[2] * dt[2]) / (9.5f * 9.5f);
        if (accelationSquareRoot >= 2 && isNotTooOften()) {
            Accelerometer.calculateDirection(dt[0], dt[2]);
            if (Accelerometer.isStop()) {
                ++counter;
                mario.setTexture(marioWin);
                create();
            }

            if (Accelerometer.getmovedTo().equals("LEFT")) {
                mario.setTexture(marioLeft);
                mario.setPosition(Gdx.graphics.getWidth() * 0.8343f, Gdx.graphics.getHeight() * 0.15f);
            } else if (Accelerometer.getmovedTo().equals("RIGHT")) {
                mario.setTexture(marioRight);
                mario.setPosition(Gdx.graphics.getWidth() * 0.8343f, Gdx.graphics.getHeight() * 0.67f);
            } else {
                mario.setTexture(marioMid);
                mario.setPosition(Gdx.graphics.getWidth() * 0.8343f, Gdx.graphics.getHeight() * 0.45f);
            }
        }

        sprite.translate(speedX, speedY);

        float screenLeft = screenBounds.getX();
        float screenRight = screenLeft + screenBounds.getWidth() - sprite.getBoundingRectangle().getWidth();
        float newX = sprite.getX();
        float newY = sprite.getY();
        rectangleMario = mario.getBoundingRectangle();
        rectangleSprite = sprite.getBoundingRectangle();

        boolean isOverlaping = rectangleMario.overlaps(rectangleSprite);
        if (isOverlaping) {
            //       mario.setTexture(marioWin);
            //  Gdx.app.exit();
            if (bestScorePonits < counter) {
                bestScorePonits = counter;
            }
            counter = 0;
            create();
        }

        // Set sprite position.


        //   sprite.setPosition(newX, newY);
    }

    private boolean isNotTooOften() {
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        float elapsedTimeSec = elapsedTimeMillis / 1000f;
        if (elapsedTimeSec > 1F) {
            start = System.currentTimeMillis();
            return true;
        } else return false;
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        myLabel.draw(batch, 4);
        bestScore.draw(batch,4);
        sprite.draw(batch);
        mario.draw(batch);
        batch.end();
    }
}
