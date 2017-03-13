package br.com.trbc.flappyghost.display;

import com.badlogic.gdx.graphics.Color;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import br.com.trbc.flappyghost.FlappyGhost;


/**
 * Created by turboc on 23/02/17.
 */
public class Hud extends Stage {

    private Viewport viewport;

    private Integer worldTimer;
    private float   timeCount;
    private Integer score;

    private SpriteBatch sb;

    Label scoreLabel;
    Label timeLabel;
    Label incrementaTempo;
    Label pontosLabel;

    public Hud (SpriteBatch sb) {


        viewport = new FitViewport(FlappyGhost.WIDTH, FlappyGhost.HEIGHT, new OrthographicCamera());
        this.sb = sb;
        worldTimer = 0;
        timeCount = 0;
        score      = 0;


        Table table = new Table();

        table.top();
        table.setFillParent(true);


        incrementaTempo =
                new Label(String.format("%05d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel =
                new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        timeLabel =
                new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pontosLabel =
                new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(timeLabel).expandX().padTop(10);
        table.add(incrementaTempo).expandX();

        table.row();

        table.add(pontosLabel).expandX();
        table.add(scoreLabel).expandX();

        addActor(table);



    }

    public void update (float dt) {

        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer++;
            incrementaTempo.setText(String.format("%05d", worldTimer));
            timeCount = 0;
        }


    }

    public void addScore(int value) {

        score += value;
        scoreLabel.setText(String.format("%05d", score));
    }




}
