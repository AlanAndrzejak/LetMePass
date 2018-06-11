package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;


public class MyGdxGame extends ApplicationAdapter {


    private int posAx = 20;
    private int posAy = -80;
    private int posBx = 320;
    private int posBy = -80;
    private int posCx = 580;
    private int posCy = -80;
    private int posQx = 280;
    private int posQy = -15;
    private float angle = 90;
    private SpriteBatch batch, spriteBatch;
    private Texture wall, marioLeft, marioMid, marioRight, marioWin;
    private Sprite sprite, mario;
    private Rectangle rectangleMario;
    private Rectangle rectangleSprite;
    private String yourScoreName = "Score:";
    private String bestScoreName = "Best Score:";
    private BitmapFont yourBitmapFontName;
    private Label.LabelStyle labelStyle;
    private Label myLabel, bestScore;
    private BitmapFont font;
    Matrix4 mx4Font = new Matrix4();
    private ArrayList<Question> questions = Question.getActualQuestions();
    int random;
    private final float defaultSize = 200f;
    private final float SPEED = 1f;
    private int counter;
    private int bestScorePonits;
    private long start;
    AccelerometerHandler Accelerometer = new AccelerometerHandler("MIDDLE");


    @Override
    public void create() {
        Question.setDefualtActualQuestions();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        mx4Font.rotate(new Vector3(0, 0, 1), angle);
        mx4Font.trn(0, 0, 0);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        yourBitmapFontName = new BitmapFont();
        labelStyle = new Label.LabelStyle(yourBitmapFontName, Color.BLACK);
        bestScore = new Label(bestScoreName, labelStyle);
        myLabel = new Label(yourScoreName, labelStyle);

        myLabel.setPosition(700, 0);
        bestScore.setPosition(700, Gdx.graphics.getHeight() - 17);
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
        if (accelationSquareRoot >= 3 && isNotTooOften()) {
            Accelerometer.calculateDirection(dt[0], dt[2]);
            if (Accelerometer.isStop()) {
                Accelerometer.setStop(false);
                if (questions.get(random).isCorrectAnswer(Accelerometer.getPosition())) {
                    win();
                    newRandom();
                } else {
                    if (bestScorePonits < counter) {
                        bestScorePonits = counter;
                    }
                    losse();
                    newRandom();
                }


            } else if (Accelerometer.getmovedTo().equals("LEFT")) {
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

        rectangleMario = mario.getBoundingRectangle();
        rectangleSprite = sprite.getBoundingRectangle();

        boolean isOverlaping = rectangleMario.overlaps(rectangleSprite);
        if (isOverlaping) {
            if (bestScorePonits < counter) {
                bestScorePonits = counter;
            }
            losse();
        }
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

        spriteBatch.setTransformMatrix(mx4Font);
        spriteBatch.begin();
        font.getData().setScale(2f);
        font.setColor(Color.BLACK);
        font.draw(spriteBatch, questions.get(random).getOptionA(), posAx, posAy);
        font.draw(spriteBatch, questions.get(random).getOptionB(), posBx, posBy);
        font.draw(spriteBatch, questions.get(random).getOptionC(), posCx, posCy);
        font.draw(spriteBatch, questions.get(random).getQuestion(), posQx, posQy);

        spriteBatch.end();

        batch.begin();
        myLabel.draw(batch, 4);
        bestScore.draw(batch, 4);
        sprite.draw(batch);
        mario.draw(batch);
        batch.end();
    }

    private void losse() {
        counter = 0;
        sprite.setPosition(0, 0);
        Question.clearQuestions();
        Question.setDefualtActualQuestions();
    }

    private void win() {
        ++counter;
        mario.setTexture(marioWin);
        sprite.setPosition(0, 0);
        questions.get(random).removeElements(questions, questions.get(random));
    }

    private void newRandom() {
        random = questions.get(0).randomGenerator();
        validateRandom();
    }

    private void validateRandom() {
        if (random >= questions.size())
            random = questions.size() - 1;
        if (questions.size() == 1) {
            Question.setDefualtActualQuestions();
            questions = Question.getActualQuestions();
        }
    }
}
