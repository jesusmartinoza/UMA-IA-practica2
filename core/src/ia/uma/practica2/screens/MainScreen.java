package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import ia.uma.practica2.ThemeColors;

/**
 * Created by jesusmartinoza on 10/31/19.
 */
public class MainScreen implements Screen {

    private Game game;
    private Skin skin;
    private Stage stage;

    public MainScreen(Game game) {
        this.game = game;
    }

    /**
     * Init buttons and title menu
     */
    private void _initUI() {
        // Instantiate UI elements
        TextButton button1 = new TextButton("Ejercicio 1", skin);
        TextButton button2 = new TextButton("Ejercicio 2", skin);
        TextButton button3 = new TextButton("Ejercicio 3", skin);
        Label title = new Label("Universidad de Málaga", skin);
        Label author = new Label("Práctica 2\n Jesús Alberto Martínez Mendoza", skin);

        // Config UI elements
        title.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        title.setColor(Color.WHITE);
        title.setFontScale(1.5f);
        author.setAlignment(Align.center);
        author.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Set listeners
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Exercise1(game));
            }
        });
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Exercise3(game));
            }
        });

        // Create table to show buttons in a row
        Table table = new Table();
        table.setFillParent(true);
        table.add(title).padBottom(10).row();
        table.add(author).padBottom(40).row();
        table.add(button1).padBottom(10).row();
        table.add(button2).padBottom(10).row();
        table.add(button3).padBottom(10).row();

        stage.addActor(table);
    }

    @Override
    public void show() {
        // Load assets
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Add buttons to stage
        _initUI();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(ThemeColors.primary.r, ThemeColors.primary.g, ThemeColors.primary.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
